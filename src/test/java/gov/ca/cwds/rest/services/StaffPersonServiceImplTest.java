package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.StaffPerson;
import io.dropwizard.jackson.Jackson;

public class StaffPersonServiceImplTest {
	private static StaffPersonService staffPersonService;
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private CrudsService<StaffPerson, gov.ca.cwds.rest.api.persistence.legacy.StaffPerson> crudsService;
	
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
	public void createDelegatesToCrudsService() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		staffPersonService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() throws Exception {
		StaffPerson toUpdate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		staffPersonService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
