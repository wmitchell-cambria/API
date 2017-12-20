package gov.ca.cwds.rest.services.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;

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
import gov.ca.cwds.rest.resources.hoi.HOICaseResource;
import gov.ca.cwds.rest.resources.hoi.HOIReferralResource;
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

  private HOICaseResource hoiCaseResource;
  private HOIReferralResource hoiReferralResource;

  /**
   * 
   * @param hoiCaseResource the HOICaseResource
   * @param hoiReferralResource the HOIReferralResource
   */
  @Inject
  public InvolvementHistoryService(HOICaseResource hoiCaseResource,
      HOIReferralResource hoiReferralResource) {
    super();
    this.hoiCaseResource = hoiCaseResource;
    this.hoiReferralResource = hoiReferralResource;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String primaryKey) {
    if (!primaryKey.equals("999999")) {
      return findInvolvementHistoryByScreeningId(primaryKey);
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

  public Response findInvolvementHistoryByScreeningId(String primaryKey) {
    Set<String> clientIds = findClientIdsByScreeningId(primaryKey);
    Set<HOICase> hoicases = findHOICasesByScreeningId(clientIds);
    Set<HOIReferral> hoireferrals = findHOIReferralsByScreeningId(clientIds);
    Set<HOIScreening> hoiscreenings = findHOIScreeningsByScreeningId(primaryKey);
    return new InvolvementHistory(primaryKey, hoicases, hoireferrals, hoiscreenings);
  }

  private Set<String> findClientIdsByScreeningId(String primaryKey) {
    // TODO replace with a participant resource that will find participant legacy ids by the
    // screening id
    Set<String> clientIds = new HashSet<>();
    clientIds.add("2TG71JT04Y");
    return clientIds;
  }

  private Set<HOIScreening> findHOIScreeningsByScreeningId(String primaryKey) {
    // TODO replace with a screening resource that will find the screenings needed for HOI by the
    // screening id
    return new HashSet<>();
  }

  private Set<HOIReferral> findHOIReferralsByScreeningId(Set<String> clientIds) {
    Set<HOIReferral> hoireferrals = new HashSet<>();
    for (String clientId : clientIds) {
      HOIReferralResponse referralResponse =
          (HOIReferralResponse) hoiReferralResource.get(clientId).getEntity();
      hoireferrals.addAll(referralResponse.getHoiReferrals());
    }
    return hoireferrals;
  }

  private Set<HOICase> findHOICasesByScreeningId(Set<String> clientIds) {
    Set<HOICase> hoicases = new HashSet<>();
    for (String clientId : clientIds) {
      HOICaseResponse cmscaseResponse = (HOICaseResponse) hoiCaseResource.get(clientId).getEntity();
      hoicases.addAll(cmscaseResponse.getHoiCases());
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
