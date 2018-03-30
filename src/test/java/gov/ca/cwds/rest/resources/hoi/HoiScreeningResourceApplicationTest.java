package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_HOI_SCREENINGS;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;

@Ignore
public class HoiScreeningResourceApplicationTest extends IntakeBaseTest {

  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    WebTarget target =
        clientTestRule.withSecurityToken("fixtures/gov/ca/cwds/rest/resources/hoi/user-social-worker-only.json")
            .target(RESOURCE_HOI_SCREENINGS);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of("-1").collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    HOIScreening[] response = invocation
        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), HOIScreening[].class);
    String actualJson = clientTestRule.getMapper().writeValueAsString(response);

    JSONAssert.assertEquals("[]", actualJson, true);
  }
}
