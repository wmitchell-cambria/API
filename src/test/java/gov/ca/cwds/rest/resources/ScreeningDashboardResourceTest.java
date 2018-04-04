package gov.ca.cwds.rest.resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.fixture.ScreeningDashboardResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.api.domain.ScreeningDashboardList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ScreeningService;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ScreeningDashboardResourceTest {

  private final static ScreeningService service = mock(ScreeningService.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
	  .addResource(new ScreeningDashboardResource(service)).build();

  @Before
  public void setup() {
	reset(service);
	new TestingRequestExecutionContext("0X5");
  }

  @Test
  public void shouldCallScreeningsEndPoint() throws Exception {
	String sdd = "sdd1, sdd2";
	String sd = "screening decision, next screening decision";
	String referralId = "1234567ABC";
	List<String> screeningDecisionDetail = Arrays.asList(sdd);
	List<String> screeningDecision = Arrays.asList(sd);
	ScreeningDashboard screeningDashboard1 = new ScreeningDashboardResourceBuilder().build();
	ScreeningDashboard screeningDashboard2 = new ScreeningDashboardResourceBuilder().build();
	List<ScreeningDashboard> sdList = new ArrayList<>();
	sdList.add(screeningDashboard1);
	sdList.add(screeningDashboard2);
	ScreeningDashboardList screeningDashboardList = new ScreeningDashboardList(sdList);

	when(service.findScreeningDashboard(screeningDecisionDetail, screeningDecision, referralId))
		.thenReturn(screeningDashboardList);

	resources.client().target("/screenings").queryParam("screening_decision_detail", sdd)
		.queryParam("screening_decision", sd).queryParam("referral_id", referralId).request()
		.accept(MediaType.APPLICATION_JSON).get();
	verify(service).findScreeningDashboard(screeningDecisionDetail, screeningDecision, referralId);
  }

}
