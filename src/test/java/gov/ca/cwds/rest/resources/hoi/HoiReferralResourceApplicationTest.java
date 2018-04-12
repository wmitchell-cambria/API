package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT;

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
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;

/**
 * @author CWDS API Team
 *
 */
@Ignore
public class HoiReferralResourceApplicationTest extends IntakeBaseTest {

  /**
   * Test to find the non existing client
   * 
   * @throws Exception - exception
   */
  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    WebTarget target = clientTestRule
        .withSecurityToken("fixtures/gov/ca/cwds/rest/resources/hoi/user-social-worker-only.json")
        .target(RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of("-1").collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE);
    HOIReferral[] response = invocation
        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), HOIReferral[].class);

    String actualJson = clientTestRule.getMapper().writeValueAsString(response);
    JSONAssert.assertEquals("[]", actualJson, true);
  }

}
