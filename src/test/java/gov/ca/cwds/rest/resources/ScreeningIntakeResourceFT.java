package gov.ca.cwds.rest.resources;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.Screening;
import io.dropwizard.jackson.Jackson;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;


import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * CWDS API Team
 */
public class ScreeningIntakeResourceFT extends IntakeBaseTest {

  @Test
  public void testGet() throws Exception {
    String actualJson = doGetCall("52");
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/screening-get-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, true);
  }

  @Test
  public void testPost() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/screening-post-request.json");
    String actualJson = doPostCall(request);
    Screening screening = Jackson.newObjectMapper().readValue(actualJson.getBytes(), Screening.class);

    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/screening-post-response.json");
    expectedResponse = expectedResponse.replace("${screening_id}", screening.getId());

    CrossReport crossReport = screening.getCrossReports().iterator().next();
    expectedResponse = expectedResponse.replace("${cross_report_id}", crossReport.getId());

    Set<GovernmentAgency> agencies = crossReport.getAgencies();
    for (GovernmentAgency agency : agencies) {
      if ("DISTRICT_ATTORNEY".equals(agency.getType())) {
        expectedResponse = expectedResponse.replace("${agency1_id}", agency.getId());
      }
      if ("LAW_ENFORCEMENT".equals(agency.getType())) {
        expectedResponse = expectedResponse.replace("${agency2_id}", agency.getId());
      }
    }

    AddressIntakeApi address = screening.getIncidentAddress();
    expectedResponse = expectedResponse.replace("${address_id}", address.getId());

    Allegation allegation = screening.getAllegations().iterator().next();
    expectedResponse = expectedResponse.replace("${allegation_id}", String.valueOf(allegation.getId()));

    ParticipantIntakeApi participantIntakeApi = screening.getParticipantIntakeApis().iterator().next();
    expectedResponse = expectedResponse.replace("${participant_id}", participantIntakeApi.getId());
    expectedResponse = expectedResponse.replace("${participant_address_id}",
        participantIntakeApi.getAddresses().iterator().next().getId());
    expectedResponse = expectedResponse.replace("${participant_phone_number_id}",
        String.valueOf(participantIntakeApi.getPhoneNumbers().iterator().next().getId()));

    JSONAssert.assertEquals(expectedResponse, actualJson, true);
  }

  @Test
  public void testPut() throws Exception {
    String request = fixture("fixtures/gov/ca/cwds/rest/resources/screening-put-request.json");
    String actualJson = doPutCall("52", request);
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/screening-put-response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, true);
  }

  private String doGetCall(String id) throws java.io.IOException {
    WebTarget target = clientTestRule.target(RESOURCE_INTAKE_SCREENINGS + "/" + id);
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  private String doPostCall(String request) throws java.io.IOException {
    WebTarget target = clientTestRule.target(RESOURCE_INTAKE_SCREENINGS);
    Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(request,
        MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  private String doPutCall(String id, String request) throws java.io.IOException {
    WebTarget target = clientTestRule.target(RESOURCE_INTAKE_SCREENINGS + "/" + id);
    Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(request,
        MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }
}
