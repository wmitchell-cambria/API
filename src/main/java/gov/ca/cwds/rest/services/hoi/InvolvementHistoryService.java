package gov.ca.cwds.rest.services.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import gov.ca.cwds.data.ns.ParticipantDao;
import io.dropwizard.hibernate.UnitOfWork;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
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
    if (!("999999").equals(screeningId)) {
      return findInvolvementHistoryByScreeningId(screeningId);
    }
    try {
      return MAPPER.readValue(
          fixture("gov/ca/cwds/rest/services/hoi/involvementhistory/valid/valid.json"),
          InvolvementHistory.class);
    } catch (Exception e) {
      LOGGER.error("Exception in finding stubbed data for HistoryOfInvolvement {}", e.getMessage());
      throw new ServiceException("Exception In finding stubbed data for HistoryOfInvolvement", e);
    }
  }

  private Response findInvolvementHistoryByScreeningId(String screeningId) {
    Set<String> clientIds = findClientIdsByScreeningId(screeningId);
    Set<HOICase> hoiCases = findHOICasesByClientIds(clientIds);
    Set<HOIReferral> hoiReferrals = findHOIReferralsByClientIds(clientIds);
    Set<HOIScreening> hoiScreenings = findHOIScreeningsByScreeningId(screeningId);
    return new InvolvementHistory(screeningId, hoiCases, hoiReferrals, hoiScreenings);
  }

  @UnitOfWork("ns")
  protected Set<String> findClientIdsByScreeningId(String screeningId) {
    return participantDao.findLegacyIdListByScreeningId(screeningId);
  }

  @UnitOfWork("ns")
  protected Set<HOIScreening> findHOIScreeningsByScreeningId(String screeningId) {
    return hoiScreeningService.handleFind(screeningId).getScreenings();
  }

  @UnitOfWork("cms")
  protected Set<HOIReferral> findHOIReferralsByClientIds(Set<String> clientIds) {
    Set<HOIReferral> hoiReferrals = new HashSet<>();
    for (String clientId : clientIds) {
      HOIReferralResponse referralResponse = hoiReferralService.handleFind(clientId);
      hoiReferrals.addAll(referralResponse.getHoiReferrals());
    }
    return hoiReferrals;
  }

  @UnitOfWork("cms")
  protected Set<HOICase> findHOICasesByClientIds(Set<String> clientIds) {
    Set<HOICase> hoicases = new HashSet<>();
    for (String clientId : clientIds) {
      HOICaseResponse hoiCaseResponse = hoiCaseService.find(clientId);
      hoicases.addAll(hoiCaseResponse.getHoiCases());
    }
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
