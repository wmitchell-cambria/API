package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Screening History Of Involvement.
 *
 * @author CWDS API Team
 */
public class InvolvementHistoryService
    implements TypedCrudsService<String, InvolvementHistory, Response> {

  @Inject
  ParticipantDao participantDao;

  @Inject
  HOICaseService hoiCaseService;

  @Inject
  HOIReferralService hoiReferralService;

  @Inject
  HOIScreeningService hoiScreeningService;

  public InvolvementHistoryService() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String screeningId) {
    return findInvolvementHistory(new InvolvementHistoryData(screeningId));
  }

  /**
   * @param clientIds - clientIds
   * @return the history of involvement for the clients
   */
  public InvolvementHistory findInvolvementHistoryByClientIds(Collection<String> clientIds) {
    return findInvolvementHistory(new InvolvementHistoryData(clientIds));
  }

  private InvolvementHistory findInvolvementHistory(InvolvementHistoryData ihd) {
    loadDataFromNS(ihd);
    loadDataFromCMS(ihd);
    buildHoiScreenings(ihd);
    return new InvolvementHistory(ihd.getScreeningId(), ihd.getHoiCases(), ihd.getHoiReferrals(),
        ihd.getHoiScreenings());
  }

  // @UnitOfWork(value = "ns", readOnly = true, transactional = false)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected void loadDataFromNS(InvolvementHistoryData ihd) {
    HOIScreeningData hsd = ihd.getHoiScreeningData();
    if (hsd.getClientIds().isEmpty() && ihd.getScreeningId() != null) {
      // load client ID-s by incoming screening ID-s
      hsd.setClientIds(participantDao.findLegacyIdListByScreeningId(ihd.getScreeningId()));
    }
    if (!hsd.getClientIds().isEmpty()) {
      hoiScreeningService.fetchDataFromNS(hsd);
    }
  }

  // @UnitOfWork(value = "cms", readOnly = true, transactional = false)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected void loadDataFromCMS(InvolvementHistoryData ihd) {
    HOIScreeningData hsd = ihd.getHoiScreeningData();
    if (!hsd.getClientIds().isEmpty()) {
      hoiScreeningService.fetchDataFromCMS(hsd);
      HOIRequest hoiRequest = new HOIRequest(hsd.getClientIds());
      ihd.setHoiCases(hoiCaseService.handleFind(hoiRequest).getHoiCases());
      ihd.setHoiReferrals(hoiReferralService.handleFind(hoiRequest).getHoiReferrals());
    }
  }

  private void buildHoiScreenings(InvolvementHistoryData ihd) {
    Set<HOIScreening> hoiScreeningSet =
        hoiScreeningService.buildHoiScreenings(ihd.getHoiScreeningData());
    List<HOIScreening> hoiScreenings = new ArrayList<>(hoiScreeningSet);
    if (ihd.getScreeningId() != null) {
      // exclude the screening with the incoming screening ID
      hoiScreenings.removeIf(screening -> screening.getId().equals(ihd.getScreeningId()));
    }
    ihd.setHoiScreenings(hoiScreenings);
  }

  @Override
  public Response create(InvolvementHistory request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, InvolvementHistory request) {
    throw new NotImplementedException("update not implemented");
  }

}
