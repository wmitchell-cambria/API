package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;


import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;

/**
 * CWDS API Team
 */
public class ParticipantIntakeApiResourceIRT extends IntakeBaseTest {
  @Test
  public void testGet() throws Exception {
    String actualJson = doGetCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/25");
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-get-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPost() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-request.json");
    String actualJson = doPostCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS, request);
    ParticipantIntakeApi participant = objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-response.json");
    expectedResponse = expectedResponse.replace("${participant_id}", participant.getId());
    expectedResponse = expectedResponse.replace("${address_id}", participant.getAddresses().iterator().next().getId());
    expectedResponse = expectedResponse.replace("${csec_id}", participant.getCsecs().get(0).getId());
    expectedResponse = expectedResponse.replace("${participant_id}", participant.getSafelySurenderedBabies().getParticipantId());
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPut() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-request.json");
    String actualJson = doPutCall(RESOURCE_SCREENINGS + "/36/" + RESOURCE_PARTICIPANTS + "/25", request);
    ParticipantIntakeApi participant = objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-response.json");
    expectedResponse = expectedResponse.replace("${address_id}", participant.getAddresses().iterator().next().getId());
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  @Ignore
  public void testFullCycle() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-request.json");
    ParticipantIntakeApi participant = objectMapper.readValue(request.getBytes(), ParticipantIntakeApi.class);
    AddressIntakeApi address = new AddressIntakeApi();
    address.setType("");

  }
}
