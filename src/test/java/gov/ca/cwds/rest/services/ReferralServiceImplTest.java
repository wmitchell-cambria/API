package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.api.domain.ReferralSummary;

import org.junit.Before;
import org.junit.Test;

public class ReferralServiceImplTest {
	private static ReferralService referralService;
	
	@Before
	public void setup() {
		referralService = new ReferralServiceImpl();
	}

	//TODO : ReferralService is currently a stub - will implement and test later.
//	@Test
//	public void findReferralSummaryReturnsSummaryWithCorrectId() {
//		
//		
//		ReferralSummary returnedSummary = referralService.findReferralSummary("myid");
//		
//		assertThat(returnedSummary.getId(), is(equalTo("myid")));
//	}

}
