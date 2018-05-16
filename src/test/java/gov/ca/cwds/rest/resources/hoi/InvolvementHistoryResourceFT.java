package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import gov.ca.cwds.IntakeBaseTest;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class InvolvementHistoryResourceFT extends IntakeBaseTest {

  @Test
  public void testGet() throws Exception {
    String actualJson = doGet("714");
    String expectedResponse = fixture("fixtures/gov/ca/cwds/rest/resources/hoi/history_of_involvements.valid.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  private String doGet(String id) throws java.io.IOException {
    WebTarget target = clientTestRule.target(RESOURCE_SCREENINGS + "/" + id + "/history_of_involvements");
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }
}
