package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class HoiReferralResourceIRT extends HOIBaseTest {

  @Test
  public void testGet() throws Exception {
    final List<HOIReferral> expectedHOIReferrals = getExpectedInvolvementHistory().getReferrals();
    final String actualJson = doGet();
    final List<HOIReferral> actualHOIReferrals = objectMapper
        .readValue(actualJson.getBytes(), new TypeReference<List<HOIReferral>>() {
        });
    assertEquals(expectedHOIReferrals, actualHOIReferrals);
    assertHOIReferralsAreSorted(new String[]{"MYsSPHW0DW", "9OQhOAE0DW"}, actualHOIReferrals);
  }

  private String doGet() throws Exception {
    WebTarget target = clientTestRule.target(RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT);
    Response response = target.queryParam("clientIds", "5DK5THO0DW", "SFpVhtC0DW").request()
        .accept(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }
}
