package gov.ca.cwds.rest.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;

public class ReferralClientServiceImplTest {
	private static ReferralClientService referralClient;
	
	private CrudsService<ReferralClient> crudsService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		referralClient = new ReferralClientServiceImpl(crudsService);
	}

	@Test
	public void findDelegatesToCrudsService() {
		referralClient.find("1");
		verify(crudsService, times(1)).find("1");

	}
	
	@Test
	public void deleteDelegatesToCrudsService() {
		referralClient.delete("1");
		verify(crudsService, times(1)).delete("1");
	}
	
	@Test
	public void createDelegatesToCrudsService() {
		ReferralClient toCreate = new ReferralClient("aa");
		referralClient.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() {
		ReferralClient toUpdate = new ReferralClient("aa");
		referralClient.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
