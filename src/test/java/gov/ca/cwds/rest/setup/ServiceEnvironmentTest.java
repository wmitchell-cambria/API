package gov.ca.cwds.rest.setup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.services.ReferralServiceImpl;

public class ServiceEnvironmentTest {
	private ServiceEnvironment serviceEnvironment;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup() {
		serviceEnvironment = new ServiceEnvironment();
	}
	
	@Test
	public void checkIllegalArgumentBadSuperAndClassCombo() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Illegal Superclass");
         
        CrudsService<gov.ca.cwds.rest.api.domain.Referral, Referral> fakeService = new CrudsService<gov.ca.cwds.rest.api.domain.Referral, Referral>() {

			@Override
			public gov.ca.cwds.rest.api.domain.Referral find(Serializable primaryKey) {
				return null;
			}

			@Override
			public gov.ca.cwds.rest.api.domain.Referral delete(Serializable id) {
				return null;
			}

			@Override
			public String create(gov.ca.cwds.rest.api.domain.Referral object) {
				return null;
			}

			@Override
			public String update(gov.ca.cwds.rest.api.domain.Referral object) {
				return null;
			}
			
		};
        
    	serviceEnvironment.register(ReferralService.class, Api.Version.JSON_VERSION_1, fakeService);
	}
	
	@Test
	public void checkCorrectServiceReturned() {
		@SuppressWarnings("unchecked")
		CrudsService<gov.ca.cwds.rest.api.domain.Referral, Referral> crudsService = mock(CrudsService.class);
		ReferralService referralService = new ReferralServiceImpl(crudsService);
		serviceEnvironment.register(ReferralService.class, Api.Version.JSON_VERSION_1, referralService);
		
		assertThat((ReferralService)serviceEnvironment.getService(ReferralService.class, Api.Version.JSON_VERSION_1.getMediaType()), is(equalTo(referralService)));
	}
	
	@Test
	public void checkNoServiceReturnedOnNonTrackedService() {
		assertThat((ReferralService)serviceEnvironment.getService(ReferralService.class, Api.Version.JSON_VERSION_1.getMediaType()), is(equalTo(null)));
	}
	
	@Test
	public void checkNoServiceReturnedCorrectlyOnBadImplementationClass() {
		@SuppressWarnings("unchecked")
		CrudsService<gov.ca.cwds.rest.api.domain.Referral, Referral> crudsService = mock(CrudsService.class);
		ReferralService referralService = new ReferralServiceImpl(crudsService);
		serviceEnvironment.register(ReferralService.class, Api.Version.JSON_VERSION_1, referralService);
		
		assertThat((ReferralService)serviceEnvironment.getService(ReferralService.class, Api.Version.VERSION_NOOP.getMediaType()), is(equalTo(null)));
	}

}
