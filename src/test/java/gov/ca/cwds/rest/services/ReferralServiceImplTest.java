package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ReferralServiceImplTest {
	private static ReferralService referralService;
	
	private CrudsService<Referral> crudsService;
	
	private Referral FOUND_REFERRAL = new Referral("found", "name", new Date());
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		
		when(crudsService.find(FOUND_REFERRAL.getId())).thenReturn(FOUND_REFERRAL);
		when(crudsService.find("notfound")).thenReturn(null);
		referralService = new ReferralServiceImpl(crudsService);
	}
	
	@Test 
	public void findReferralSummaryReturnsCorrectObjectOnSuccess() {
		assertThat(referralService.findReferralSummary(FOUND_REFERRAL.getId()).getId(), is(equalTo(FOUND_REFERRAL.getId())));
	}

	@Test 
	public void findReferralSummaryReturnsNullOnNotFound() {
		assertThat(referralService.findReferralSummary("notfound"), is(equalTo(null)));
	}

	@Test
	public void findDelegatesToCrudsService() {
		referralService.find("1");
		verify(crudsService, times(1)).find("1");

	}
	
	@Test
	public void deleteDelegatesToCrudsService() {
		referralService.delete("1");
		verify(crudsService, times(1)).delete("1");
	}
	
	@Test
	public void createDelegatesToCrudsService() {
		Referral toCreate = new Referral("1", "name", new Date());
		referralService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() {
		Referral toUpdate = new Referral("1", "name", new Date());
		referralService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
