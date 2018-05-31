package gov.ca.cwds.rest.resources.submit;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.IntakeBaseTestConstants;
import gov.ca.cwds.rest.api.domain.Screening;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;


import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * CWDS API Team
 */
public class ScreeningSubmitResourceIRT extends IntakeBaseTest {
  @Test
  public void testPost() throws Exception {
    WebTarget target = clientTestRule.withSecurityToken(IntakeBaseTestConstants.USER_SOCIAL_WORKER_ONLY).target(RESOURCE_SCREENINGS + "/52/submit");
    Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(null,
        MediaType.APPLICATION_JSON_TYPE));
    String actualJson = IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);

/*
    String actualJson = doPostCall(RESOURCE_SCREENINGS + "/52/submit", "");
    Screening screening = objectMapper.readValue(actualJson.getBytes(), Screening.class);
*/

  }
}
