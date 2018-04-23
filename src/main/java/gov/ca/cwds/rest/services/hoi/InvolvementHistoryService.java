package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.hibernate.UnitOfWork;

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
  @UnitOfWork(value = "cms", readOnly = true, transactional = false)
  public Response find(String screeningId) {
    return findInvolvementHistoryByScreeningId(screeningId);
  }

  private Response findInvolvementHistoryByScreeningId(String screeningId) {
    final Set<String> clientIds = findClientIdsByScreeningId(screeningId);
    if (!clientIds.isEmpty()) {
      final List<HOICase> hoiCases = findHOICasesByClientIds(clientIds);
      final List<HOIReferral> hoiReferrals = findHOIReferralsByClientIds(clientIds);
      final List<HOIScreening> hoiScreenings = findHOIScreeningsByClientIds(clientIds, screeningId);
      return new InvolvementHistory(screeningId, hoiCases, hoiReferrals, hoiScreenings);
    }
    return new InvolvementHistory(screeningId, new ArrayList<HOICase>(),
        new ArrayList<HOIReferral>(), new ArrayList<HOIScreening>());
  }

  /**
   * @param clientIds - clientIds
   * @return the history of involvement for the clients
   */
  @UnitOfWork(value = "cms", readOnly = true, transactional = false)
  public InvolvementHistory findInvolvementHistoryByClientIds(Set<String> clientIds) {
    if (!clientIds.isEmpty()) {
      final List<HOICase> hoiCases = findHOICasesByClientIds(clientIds);
      final List<HOIReferral> hoiReferrals = findHOIReferralsByClientIds(clientIds);
      final HOIRequest hoiScreeningRequest = new HOIRequest();
      hoiScreeningRequest.setClientIds(clientIds);

      final List<HOIScreening> hoiScreenings =
          new ArrayList<>(hoiScreeningService.handleFind(hoiScreeningRequest).getScreenings());
      return new InvolvementHistory(null, hoiCases, hoiReferrals, hoiScreenings);
    }
    return new InvolvementHistory(null, new ArrayList<HOICase>(), new ArrayList<HOIReferral>(),
        new ArrayList<HOIScreening>());
  }

  @UnitOfWork(value = "ns", readOnly = true, transactional = false)
  protected Set<String> findClientIdsByScreeningId(String screeningId) {
    return participantDao.findLegacyIdListByScreeningId(screeningId);
  }

  @UnitOfWork(value = "ns", readOnly = true, transactional = false)
  protected List<HOIScreening> findHOIScreeningsByClientIds(Set<String> clientIds,
      String exceptScreeningId) {
    final HOIRequest hoiScreeningRequest = new HOIRequest();
    hoiScreeningRequest.setClientIds(clientIds);
    return hoiScreeningService.handleFind(hoiScreeningRequest).getScreenings().stream()
        .filter(hoiScreening -> !hoiScreening.getId().equals(exceptScreeningId))
        .collect(Collectors.toList());
  }

  protected List<HOIReferral> findHOIReferralsByClientIds(Set<String> clientIds) {
    final HOIRequest hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(clientIds);

    final HOIReferralResponse referralResponse = hoiReferralService.handleFind(hoiRequest);
    final List<HOIReferral> hoiReferrals = referralResponse.getHoiReferrals();
    Collections.sort(hoiReferrals);
    return hoiReferrals;
  }

  protected List<HOICase> findHOICasesByClientIds(Set<String> clientIds) {
    final HOIRequest hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(clientIds);

    final HOICaseResponse hoiCaseResponse = hoiCaseService.handleFind(hoiRequest);
    final List<HOICase> hoicases = hoiCaseResponse.getHoiCases();

    Collections.sort(hoicases);
    return hoicases;
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
