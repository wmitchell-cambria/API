package gov.ca.cwds.rest.setup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.services.ReferralServiceImpl;
import gov.ca.cwds.rest.services.Service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
         
        Service<Referral> fakeService = new Service<Referral>() {

			@Override
			public Referral find(String id) {
				return null;
			}

			@Override
			public Referral delete(String id) {
				return null;
			}

			@Override
			public Referral create(Referral object) {
				return null;
			}

			@Override
			public Referral update(Referral object) {
				return null;
			}
			
		};
        
    	serviceEnvironment.register(ReferralService.class, Api.Version.JSON_VERSION_1, fakeService);
	}
	
	@Test
	public void checkCorrectServiceReturned() {;
		ReferralService referralService = new ReferralServiceImpl();
		serviceEnvironment.register(ReferralService.class, Api.Version.JSON_VERSION_1, referralService);
		
		assertThat((ReferralService)serviceEnvironment.getService(ReferralService.class, Api.Version.JSON_VERSION_1.getMediaType()), is(equalTo(referralService)));
	}
	
	@Test
	public void checkNoServiceReturnedOnNonTrackedService() {
		assertThat((ReferralService)serviceEnvironment.getService(ReferralService.class, Api.Version.JSON_VERSION_1.getMediaType()), is(equalTo(null)));
	}
	
	@Test
	public void checkNoServiceReturnedCorrectlyOnBadImplementationClass() {
		ReferralService referralService = new ReferralServiceImpl();
		serviceEnvironment.register(ReferralService.class, Api.Version.JSON_VERSION_1, referralService);
		
		assertThat((ReferralService)serviceEnvironment.getService(ReferralService.class, Api.Version.VERSION_NOOP.getMediaType()), is(equalTo(null)));
	}

}
