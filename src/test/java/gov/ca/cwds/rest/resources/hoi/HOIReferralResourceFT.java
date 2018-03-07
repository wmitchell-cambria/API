package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralResourceFT extends IntakeBaseTest {

  private static final String USER_SOCIAL_WORKER_ONLY =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-social-worker-only.json";
  private static final String USER_COUNTY_SENSITIVE =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-county-sensitive.json";
  private static final String USER_COUNTY_SEALED =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-county-sealed.json";
  private static final String USER_STATE_SENSITIVE =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-state-sensetive.json";
  private static final String USER_STATE_SEALED =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-state-sealed.json";

  private static final String CLIENT_NO_CONDITIONS_ID = "SMHmoJl00d";
  private static final String CLIENT_NO_CONDITIONS_HOI_RESPONSE =
      "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-referral-client-no-conditions-response.json";

  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    assertHandleFind(USER_SOCIAL_WORKER_ONLY, "-1", "[]");
  }

  private void assertHandleFind(String userFilePath, String clientIds, String expectedResponse)
      throws java.io.IOException, JSONException {
    String actualJson = doHandleFindCall(userFilePath, clientIds);

    JSONAssert.assertEquals(expectedResponse, actualJson, true);
  }

  private String doHandleFindCall(String userFilePath, String clientIds)
      throws java.io.IOException {
    WebTarget target = clientTestRule.withSecurityToken(userFilePath)
        .target(RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of(clientIds).collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    Response response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), "UTF-8");
  }

}
