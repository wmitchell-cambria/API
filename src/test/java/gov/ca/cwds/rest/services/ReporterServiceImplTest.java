package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.Reporter;
import io.dropwizard.jackson.Jackson;

public class ReporterServiceImplTest {
	private static ReporterService reportService;
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private CrudsService<Reporter, gov.ca.cwds.rest.api.persistence.legacy.Reporter> crudsService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		reportService = new ReporterServiceImpl(crudsService);
	}

	@Test
	public void findDelegatesToCrudsService() {
		reportService.find("1");
		verify(crudsService, times(1)).find("1");

	}
	
	@Test
	public void deleteDelegatesToCrudsService() {
		reportService.delete("1");
		verify(crudsService, times(1)).delete("1");
	}
	
	@Test
	public void createDelegatesToCrudsService() throws Exception {
		Reporter toCreate = MAPPER.readValue(fixture("fixtures/legacy/Reporter/valid/valid.json"), Reporter.class);
		reportService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() throws Exception {
		Reporter toUpdate = MAPPER.readValue(fixture("fixtures/legacy/Reporter/valid/valid.json"), Reporter.class);
		reportService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
