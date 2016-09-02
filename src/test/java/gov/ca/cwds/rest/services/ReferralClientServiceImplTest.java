package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.MessageFormat;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.ReferralClient;
import io.dropwizard.jackson.Jackson;

public class ReferralClientServiceImplTest {
	private static ReferralClientService referralClient;
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private CrudsService<ReferralClient, gov.ca.cwds.rest.api.persistence.legacy.ReferralClient> crudsService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsService = mock(CrudsService.class);
		referralClient = new ReferralClientServiceImpl(crudsService);
	}

	@Test
	public void findDelegatesToCrudsService() {
		gov.ca.cwds.rest.api.persistence.legacy.ReferralClient.PrimaryKey pk = new gov.ca.cwds.rest.api.persistence.legacy.ReferralClient.PrimaryKey("abc", "abc");
		String pkString = MessageFormat.format("referralId={0},clientId={1}", "abc", "abc");
		referralClient.find(pkString);
		verify(crudsService, times(1)).find(pk);

	}
	
	@Test
	public void deleteDelegatesToCrudsService() {
		gov.ca.cwds.rest.api.persistence.legacy.ReferralClient.PrimaryKey pk = new gov.ca.cwds.rest.api.persistence.legacy.ReferralClient.PrimaryKey("abc", "abc");
		String pkString = MessageFormat.format("referralId={0},clientId={1}", "abc", "abc");
		referralClient.delete(pkString);
		verify(crudsService, times(1)).delete(pk);
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
