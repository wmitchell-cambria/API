package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.MessageFormat;

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
		gov.ca.cwds.rest.api.persistence.legacy.CrossReport.PrimaryKey pk = new gov.ca.cwds.rest.api.persistence.legacy.CrossReport.PrimaryKey("abc", "abc");
		String pkString = MessageFormat.format("referralId={0},thirdId={1}", "abc", "abc");
		crossReportService.find(pkString);
		verify(crudsService, times(1)).find(pk);

	}
	
	@Test
	public void deleteDelegatesToCrudsService() {
		gov.ca.cwds.rest.api.persistence.legacy.CrossReport.PrimaryKey pk = new gov.ca.cwds.rest.api.persistence.legacy.CrossReport.PrimaryKey("abc", "abc");
		String pkString = MessageFormat.format("referralId={0},thirdId={1}", "abc", "abc");
		crossReportService.delete(pkString);
		verify(crudsService, times(1)).delete(pk);
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
