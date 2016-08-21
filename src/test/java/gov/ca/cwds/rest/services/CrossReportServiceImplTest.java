package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.CrossReport;
import io.dropwizard.jackson.Jackson;

public class CrossReportServiceImplTest {
	private static CrossReportService crossReportService;
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private CrudsService<CrossReport, gov.ca.cwds.rest.api.persistence.legacy.CrossReport> crudsService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		crossReportService = new CrossReportServiceImpl(crudsService);
	}

	@Test
	public void findDelegatesToCrudsService() {
		crossReportService.find("1");
		verify(crudsService, times(1)).find("1");

	}
	
	@Test
	public void deleteDelegatesToCrudsService() {
		crossReportService.delete("1");
		verify(crudsService, times(1)).delete("1");
	}
	
	@Test
	public void createDelegatesToCrudsService() throws Exception {
		CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/valid/valid.json"), CrossReport.class);
		crossReportService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() throws Exception {
		CrossReport toUpdate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/valid/valid.json"), CrossReport.class);
		crossReportService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
