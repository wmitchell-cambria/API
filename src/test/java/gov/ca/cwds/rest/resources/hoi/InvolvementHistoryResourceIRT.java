package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class InvolvementHistoryResourceIRT extends HOIBaseTest {

  @Test
  public void testGet() throws Exception {
    final String expectedResponse = fixture(VALID_HOI_JSON);
    final String actualJson = doGet("714");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);

    final InvolvementHistory actualInvolvementHistory = getInvolvementHistory(actualJson);
    assertHOICasesAreSorted(new String[]{"Co8uaDi0DW", "IdQImWo0DW"},
        actualInvolvementHistory.getCases());
    assertHOIReferralsAreSorted(new String[]{"MYsSPHW0DW", "9OQhOAE0DW"},
        actualInvolvementHistory.getReferrals());
    assertHOIScreeningsAreSorted(new String[]{"750", "885", "862"},
        actualInvolvementHistory.getScreenings());
  }

  private String doGet(String id) throws IOException {
    WebTarget target = clientTestRule
        .target(RESOURCE_SCREENINGS + "/" + id + "/history_of_involvements");
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }
}
