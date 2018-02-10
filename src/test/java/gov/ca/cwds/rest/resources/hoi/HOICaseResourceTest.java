package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;

import gov.ca.cwds.IntakeBaseTest;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author CWDS API Team
 *
 */
public class HOICaseResourceTest extends IntakeBaseTest {

  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    WebTarget target = clientTestRule.withSecurityToken("security/social-worker-only-principal.json")
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of("-1").collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    Response response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

    String value = IOUtils.toString((InputStream) response.getEntity(), "UTF-8");

    JSONAssert.assertEquals("[]", value, true);
  }
}
