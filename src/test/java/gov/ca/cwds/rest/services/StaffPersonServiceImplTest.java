package gov.ca.cwds.rest.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gov.ca.cwds.rest.api.persistence.StaffPerson;

import org.junit.Before;
import org.junit.Test;

public class StaffPersonServiceImplTest {
	private static StaffPersonService staffPersonService;
	
	private CrudsService<StaffPerson> crudsService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		staffPersonService = new StaffPersonServiceImpl(crudsService);
	}

	@Test
	public void findDelegatesToCrudsService() {
		staffPersonService.find("1");
		verify(crudsService, times(1)).find("1");

	}
	
	@Test
	public void deleteDelegatesToCrudsService() {
		staffPersonService.delete("1");
		verify(crudsService, times(1)).delete("1");
	}
	
	@Test
	public void createDelegatesToCrudsService() {
		StaffPerson toCreate = new StaffPerson("1", "Joe", "Caseworker", "A");
		staffPersonService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() {
		StaffPerson toUpdate = new StaffPerson("1", "Joe", "Caseworker", "A");
		staffPersonService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
