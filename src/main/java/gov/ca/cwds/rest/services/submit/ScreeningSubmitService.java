package gov.ca.cwds.rest.services.submit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
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
 * and Update the {@link Screening } with the Referral Id.
 * 
 * @author CWDS API Team
 */
public class ScreeningSubmitService implements CrudsService {

  @Inject
  private ScreeningService screeningService;

  @Inject
  private IntakeLovDao intakeLovDao;

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

  public Response submit(Serializable id) {
    Screening screening = screeningService.getScreening((String) id);

    String staffId = RequestExecutionContext.instance().getStaffId();
    String userCountyCode =
        staffPersonService.find(RequestExecutionContext.instance().getStaffId()).getCountyCode();

    Map<String, IntakeLov> nsCodeToNsLovMap = new HashMap<>();

    Map<String, IntakeLov> cmsSysIdToNsLovMap = new HashMap<>();


    List<IntakeLov> intakeLovs = intakeLovDao.findAll();

    for (IntakeLov e : intakeLovs) {
      nsCodeToNsLovMap.put(e.getIntakeCode(), e);
    }

    for (IntakeLov e : intakeLovs) {
      if (e.getLegacySystemCodeId() != null) {
        cmsSysIdToNsLovMap.put((e.getLegacySystemCodeId().toString()), e);
      }
    }

    ScreeningToReferral screeningToReferral = new ScreeningTransformer().transform(screening, staffId,
        userCountyCode, nsCodeToNsLovMap, cmsSysIdToNsLovMap);

    ScreeningToReferral str =
        (ScreeningToReferral) screeningToReferralService.create(screeningToReferral);
    screening.setReferralId(str.getReferralId());
    screeningService.updateScreening(screening.getId(), screening);

    return screening;
  }


  @Override
  public Response create(Request arg0) {
    throw new NotImplementedException("Create is not implemented");
  }


  @Override
  public Response delete(Serializable arg0) {
    throw new NotImplementedException("Delete is not implemented");
  }


  @Override
  public Response update(Serializable arg0, Request arg1) {
    throw new NotImplementedException("Update is not implemented");
  }


}
