package gov.ca.cwds.rest.resources;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.Screening;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;


import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS_INTAKE_API;
import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * CWDS API Team
 */
public class ParticipantIntakeApiResourceIRT extends IntakeBaseTest {
  @Test
  public void testGet() throws Exception {
    String actualJson = doGetCall(RESOURCE_PARTICIPANTS_INTAKE_API + "/25");
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-get-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPost() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-request.json");
    String actualJson = doPostCall(RESOURCE_PARTICIPANTS_INTAKE_API, request);
    ParticipantIntakeApi participant = objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-post-response.json");
    expectedResponse = expectedResponse.replace("${participant_id}", participant.getId());
    expectedResponse = expectedResponse.replace("${address_id}", participant.getAddresses().iterator().next().getId());
    expectedResponse = expectedResponse.replace("${csec_id}", participant.getCsecs().get(0).getId());
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testPut() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-request.json");
    String actualJson = doPutCall(RESOURCE_PARTICIPANTS_INTAKE_API + "/25", request);
    ParticipantIntakeApi participant = objectMapper.readValue(actualJson.getBytes(), ParticipantIntakeApi.class);
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/participant-intake-api-put-response.json");
    expectedResponse = expectedResponse.replace("${address_id}", participant.getAddresses().iterator().next().getId());
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}