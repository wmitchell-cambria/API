package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_HOI_SCREENINGS;
import static gov.ca.cwds.rest.resources.hoi.InvolvementHistoryResourceIRT.VALID_HOI_JSON;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.hoi.HOIScreeningService;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;

public class HoiScreeningResourceIRT extends IntakeBaseTest {

  @Test
  public void testGet() throws Exception {
    String actualJson = doGet();
    List<HOIScreening> expectedHOIScreenings = getExpectedHOIScreenings();
    List<HOIScreening> actualHOIScreenings = objectMapper
        .readValue(actualJson.getBytes(), new TypeReference<List<HOIScreening>>() {
        });
    assertEquals(expectedHOIScreenings, actualHOIScreenings);
    assertHOIScreeningsAreSortedWithNullStartDateOnTop(actualHOIScreenings);
  }

  private String doGet() throws Exception {
    WebTarget target = clientTestRule.target(RESOURCE_HOI_SCREENINGS);
    Response response = target.queryParam("clientIds", "5DK5THO0DW", "SFpVhtC0DW").request()
        .accept(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  private List<HOIScreening> getExpectedHOIScreenings() throws IOException {
    String hoiJSON = fixture(VALID_HOI_JSON);
    InvolvementHistory involvementHistory = objectMapper
        .readValue(hoiJSON.getBytes(), InvolvementHistory.class);
    List<HOIScreening> hoiScreenings = involvementHistory.getScreenings();

    String hoiScreening714JSON = fixture(
        "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-screening-714.json");
    HOIScreening hoiScreening714 = objectMapper
        .readValue(hoiScreening714JSON.getBytes(), HOIScreening.class);
    hoiScreenings.add(hoiScreening714);

    return hoiScreenings;
  }

  private void assertHOIScreeningsAreSortedWithNullStartDateOnTop(
      List<HOIScreening> actualHOIScreenings) {
    assertNull(actualHOIScreenings.get(0).getStartDate());
    assertEquals("885", actualHOIScreenings.get(1).getId());
    assertEquals("862", actualHOIScreenings.get(2).getId());
    assertEquals("714", actualHOIScreenings.get(3).getId());
  }

  @Test(expected = NotImplementedException.class)
  public void handleRequestNotImplemented() {
    new HOIScreeningService().handleRequest(new HOIScreening());
  }
}
