package gov.ca.cwds.rest.resources.hoi;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.rest.services.hoi.HOIReferralService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class HoiReferralResourceTest {

  private static final HOIReferralService hoiReferralService = mock(HOIReferralService.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new HoiReferralResource(hoiReferralService)).build();

  /**
   * 
   */
  @Before
  public void setup() {
    reset(hoiReferralService);
  }

  /**
   * test to find the hoi referrals using clientIds
   */
  @Test
  public void findHoiReferralByClientIds() {
    List<String> clientIds = new ArrayList<>();
    clientIds.add("1zxcydd");
    inMemoryResource.client().target("/hoi_referrals").queryParam("clientIds", "1zxcydd").request()
        .accept(MediaType.APPLICATION_JSON).get();
    verify(hoiReferralService, atLeastOnce()).handleFind(new HOIRequest(clientIds));
  }

}
