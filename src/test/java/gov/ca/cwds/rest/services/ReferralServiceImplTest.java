package gov.ca.cwds.rest.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import gov.ca.cwds.rest.api.persistence.Referral;

import org.junit.Before;
import org.junit.Test;

public class ReferralServiceImplTest {
	private static ReferralService referralService;
	
	private CrudsService<Referral> crudsService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		referralService = new ReferralServiceImpl(crudsService);
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
