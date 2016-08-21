package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import io.dropwizard.jackson.Jackson;

public class ReferralServiceImplTest {
	private static ReferralService referralService;
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private CrudsService<Referral> crudsService;
	
	private Referral FOUND_REFERRAL; 
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		FOUND_REFERRAL = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/validNonUnique.json"), Referral.class);
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
	public void createDelegatesToCrudsService() throws Exception {
		Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/valid.json"), Referral.class);
		referralService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() throws Exception {
		Referral toUpdate = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/valid.json"), Referral.class);
		referralService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
