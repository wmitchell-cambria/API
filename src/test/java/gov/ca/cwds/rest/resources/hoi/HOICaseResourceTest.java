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
import org.junit.Ignore;
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
    WebTarget target = clientTestRule.withSecurityToken("fixtures/gov/ca/cwds/rest/resources/hoi/user-social-worker-only.json")
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of("-1").collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    HOICase[] response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), HOICase[].class);
    String actualJson = clientTestRule.getMapper().writeValueAsString(response);

    JSONAssert.assertEquals("[]", actualJson, true);
  }


  @Ignore
  @Test
  public void testHandleFind() throws Exception {
    WebTarget target = clientTestRule.withSecurityToken("fixtures/gov/ca/cwds/rest/resources/hoi/user-county-sensitive.json")
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of("AhGPhcm0T1").collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    HOICase[] response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), HOICase[].class);
    String actualJson = clientTestRule.getMapper().writeValueAsString(response);

    JSONAssert.assertEquals("[]", actualJson, true);
  }

}
