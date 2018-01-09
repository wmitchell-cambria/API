package gov.ca.cwds.rest.services.hoi;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on Screening History Of Involvement
 *
 * @author CWDS API Team
 */
public class InvolvementHistoryService
    implements TypedCrudsService<String, InvolvementHistory, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvolvementHistoryService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

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
    return findInvolvementHistoryByScreeningId(screeningId);
  }

  private Response findInvolvementHistoryByScreeningId(String screeningId) {
    Set<String> clientIds = findClientIdsByScreeningId(screeningId);
    if (!clientIds.isEmpty()) {
      Set<HOICase> hoiCases = findHOICasesByClientIds(clientIds);
      Set<HOIReferral> hoiReferrals = findHOIReferralsByClientIds(clientIds);
      Set<HOIScreening> hoiScreenings = findHOIScreeningsByClientIds(clientIds, screeningId);
      return new InvolvementHistory(screeningId, hoiCases, hoiReferrals, hoiScreenings);
    }
    return new InvolvementHistory(screeningId, new HashSet<HOICase>(), new HashSet<HOIReferral>(),
        new HashSet<HOIScreening>());
  }

  @UnitOfWork(value = "ns", readOnly = true, transactional = false)
  protected Set<String> findClientIdsByScreeningId(String screeningId) {
    return participantDao.findLegacyIdListByScreeningId(screeningId);
  }

  @UnitOfWork(value = "ns", readOnly = true, transactional = false)
  protected Set<HOIScreening> findHOIScreeningsByClientIds(Set<String> clientIds,
      String exceptScreeningId) {
    HOIRequest hoiScreeningRequest = new HOIRequest();
    hoiScreeningRequest.setClientIds(clientIds);
    return hoiScreeningService.handleFind(hoiScreeningRequest).getScreenings().stream()
        .filter(hoiScreening -> !hoiScreening.getId().equals(exceptScreeningId))
        .collect(Collectors.toSet());
  }

  @UnitOfWork(value = "cms", readOnly = true, transactional = false)
  protected Set<HOIReferral> findHOIReferralsByClientIds(Set<String> clientIds) {
    HOIRequest hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(clientIds);
    Set<HOIReferral> hoiReferrals = new HashSet<>();
    HOIReferralResponse referralResponse = hoiReferralService.handleFind(hoiRequest);
    hoiReferrals.addAll(referralResponse.getHoiReferrals());
    return hoiReferrals;
  }

  @UnitOfWork(value = "cms", readOnly = true, transactional = false)
  protected Set<HOICase> findHOICasesByClientIds(Set<String> clientIds) {
    Set<HOICase> hoicases = new HashSet<>();
    HOIRequest hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(clientIds);
    HOICaseResponse hoiCaseResponse = hoiCaseService.handleFind(hoiRequest);
    hoicases.addAll(hoiCaseResponse.getHoiCases());

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
