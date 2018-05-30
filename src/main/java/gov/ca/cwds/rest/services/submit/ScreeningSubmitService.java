package gov.ca.cwds.rest.services.submit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.xa.XaNsIntakeLovDaoImpl;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.StaffPersonService;

/**
 * Business layer object to work on submit a {@link Screening}. Create a {@link ScreeningToReferral}
 * and Update the {@link Screening} with the Referral Id.
 * 
 * @author CWDS API Team
 */
public class ScreeningSubmitService implements CrudsService {

  @Inject
  private ScreeningService screeningService;

  @Inject
  private XaNsIntakeLovDaoImpl intakeLovDao;

  @Inject
  private ScreeningToReferralService screeningToReferralService;

  @Inject
  private StaffPersonService staffPersonService;

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    return submit(primaryKey);
  }

  @XAUnitOfWork
  public Response submit(Serializable id) {
    final Screening screening = screeningService.getScreening((String) id);
    final String staffId = RequestExecutionContext.instance().getStaffId();
    final String userCountyCode =
        staffPersonService.find(RequestExecutionContext.instance().getStaffId()).getCountyCode();

    // NEXT: abstract this out and cache these values.
    final Map<String, IntakeLov> nsCodeToNsLovMap = new HashMap<>();
    final Map<String, IntakeLov> cmsSysIdToNsLovMap = new HashMap<>();
    final List<IntakeLov> intakeLovs = intakeLovDao.findAll();

    for (IntakeLov e : intakeLovs) {
      nsCodeToNsLovMap.put(e.getIntakeCode(), e);
    }

    for (IntakeLov e : intakeLovs) {
      if (e.getLegacySystemCodeId() != null) {
        cmsSysIdToNsLovMap.put((e.getLegacySystemCodeId().toString()), e);
      }
    }

    // CMS session
    final ScreeningToReferral screeningToReferral = new ScreeningTransformer().transform(screening,
        staffId, userCountyCode, nsCodeToNsLovMap, cmsSysIdToNsLovMap);

    final ScreeningToReferral str =
        (ScreeningToReferral) screeningToReferralService.create(screeningToReferral);
    screening.setReferralId(str.getReferralId());

    // NS session
    screeningService.updateScreening(screening.getId(), screening);
    return screening;
  }

  @Override
  public Response create(Request request) {
    throw new NotImplementedException("Create is not implemented");
  }

  @Override
  public Response delete(Serializable id) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public Response update(Serializable id, Request request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
