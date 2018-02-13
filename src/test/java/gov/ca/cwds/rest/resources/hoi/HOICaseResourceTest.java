package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author CWDS API Team
 *
 */
@Ignore
public class HOICaseResourceTest extends IntakeBaseTest {

  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    assertCallRestService("security/social-worker-only-principal.json", "-1", "[]");
  }

/*
  @Test
  public void testHandleFindSocialWorker() throws Exception {
    assertCallRestService("security/social-worker-only-principal.json", "0044Q7k0Rt", "[]");
  }
*/

  private void assertCallRestService(String userFixture, String clientIdsCommaSeparated, String expectedResponseFixture) throws java.io.IOException, JSONException {
    WebTarget target = clientTestRule.withSecurityToken(userFixture)
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of(clientIdsCommaSeparated.split(",")).collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    HOICase[] response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), HOICase[].class);
    String actualJson = clientTestRule.getMapper().writeValueAsString(response);

    JSONAssert.assertEquals(expectedResponseFixture, actualJson, true);
  }
}
