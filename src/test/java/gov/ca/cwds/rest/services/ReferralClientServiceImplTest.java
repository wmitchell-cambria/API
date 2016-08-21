package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;
import io.dropwizard.jackson.Jackson;

public class ReferralClientServiceImplTest {
	private static ReferralClientService referralClient;
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
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
	public void createDelegatesToCrudsService() throws Exception {
		ReferralClient toCreate = MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
		referralClient.create(toCreate);
		verify(crudsService, times(1)).create(toCreate);
	}
	
	@Test
	public void updateDelegatesToCrudsService() throws Exception {
		ReferralClient toUpdate = MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
		referralClient.update(toUpdate);
		verify(crudsService, times(1)).update(toUpdate);
	}
}
