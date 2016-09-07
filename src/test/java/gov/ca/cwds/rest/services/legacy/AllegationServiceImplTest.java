package gov.ca.cwds.rest.services.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.Allegation;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.legacy.AllegationService;
import gov.ca.cwds.rest.services.legacy.AllegationServiceImpl;
import io.dropwizard.jackson.Jackson;

public class AllegationServiceImplTest {
	private static AllegationService allegationService;
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	private CrudsService<Allegation, gov.ca.cwds.rest.api.persistence.legacy.Allegation> crudsService;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		allegationService = new AllegationServiceImpl(crudsService);
	}

	@Test
	public void findDelegatesToCrudsService() {
		allegationService.find("1");
		verify(crudsService, times(1)).find("1");

	}

	@Test
	public void deleteDelegatesToCrudsService() {
		allegationService.delete("1");
		verify(crudsService, times(1)).delete("1");
	}

	@Test
	public void createDelegatesToCrudsService() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"), Allegation.class);
		allegationService.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}

	@Test
	public void updateDelegatesToCrudsService() throws Exception {
		Allegation toUpdate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"), Allegation.class);
		allegationService.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
