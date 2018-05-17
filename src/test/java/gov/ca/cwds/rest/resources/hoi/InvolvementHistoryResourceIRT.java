package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class InvolvementHistoryResourceIRT extends IntakeBaseTest {

  static final String VALID_HOI_JSON = "fixtures/gov/ca/cwds/rest/resources/hoi/history-of-involvements.valid.json";

  @Test
  public void testGet() throws Exception {
    String actualJson = doGet("714");
    String expectedResponse = fixture(VALID_HOI_JSON);
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);

    InvolvementHistory actualInvolvementHistory = objectMapper
        .readValue(actualJson.getBytes(), InvolvementHistory.class);
    assertHOICasesAreSorted(actualInvolvementHistory);
    assertHOIReferralsAreSorted(actualInvolvementHistory);
  }

  private String doGet(String id) throws IOException {
    WebTarget target = clientTestRule
        .target(RESOURCE_SCREENINGS + "/" + id + "/history_of_involvements");
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  private void assertHOICasesAreSorted(InvolvementHistory actualInvolvementHistory) {
    List<HOICase> hoiCases = actualInvolvementHistory.getCases();
    assertEquals("Co8uaDi0DW", hoiCases.get(0).getId());
    assertEquals("IdQImWo0DW", hoiCases.get(1).getId());
  }

  private void assertHOIReferralsAreSorted(InvolvementHistory actualInvolvementHistory) {
    List<HOIReferral> hoiReferrals = actualInvolvementHistory.getReferrals();
    assertEquals("MYsSPHW0DW", hoiReferrals.get(0).getId());
    assertEquals("9OQhOAE0DW", hoiReferrals.get(1).getId());
  }
}
