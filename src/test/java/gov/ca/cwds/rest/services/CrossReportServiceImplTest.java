package gov.ca.cwds.rest.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;

public class CrossReportServiceImplTest {
	private static CrossReportService crossReportService;
	
	private CrudsService<CrossReport> crudsService;
	
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
	public void createDelegatesToCrudsService() {
		CrossReport toCreate = new CrossReport("aa");
		crossReportService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() {
		CrossReport toUpdate = new CrossReport("aa");
		crossReportService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
