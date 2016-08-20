package gov.ca.cwds.rest.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.legacy.Reporter;

public class ReporterServiceImplTest {
	private static ReporterService reportService;
	
	private CrudsService<Reporter> crudsService;
	
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
	public void createDelegatesToCrudsService() {
		Reporter toCreate = new Reporter("aa");
		reportService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() {
		Reporter toUpdate = new Reporter("aa");
		reportService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
