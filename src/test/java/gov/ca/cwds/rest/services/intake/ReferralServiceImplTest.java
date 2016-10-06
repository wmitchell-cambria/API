package gov.ca.cwds.rest.services.intake;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import gov.ca.cwds.rest.api.domain.legacy.Allegation;
import gov.ca.cwds.rest.api.domain.legacy.CrossReport;
import gov.ca.cwds.rest.api.domain.legacy.Referral;
import gov.ca.cwds.rest.api.domain.legacy.ReferralClient;
import gov.ca.cwds.rest.api.domain.legacy.Reporter;
import gov.ca.cwds.rest.services.legacy.AllegationService;
import gov.ca.cwds.rest.services.legacy.CrossReportService;
import gov.ca.cwds.rest.services.legacy.ReferralClientService;
import gov.ca.cwds.rest.services.legacy.ReferralService;
import gov.ca.cwds.rest.services.legacy.ReporterService;

@SuppressWarnings("deprecation")
public class ReferralServiceImplTest {
	private ReferralServiceImpl referralServiceImpl;
	
	private ReferralService referralService = mock(ReferralService.class);
	private AllegationService allegationService = mock(AllegationService.class);
	private CrossReportService crossReportService = mock(CrossReportService.class);
	private ReferralClientService referralClientService = mock(ReferralClientService.class);
	private ReporterService reporterService = mock(ReporterService.class);
	
	private IntakeReferral intakeReferral;
	
	@Before
	public void setup() throws Exception {
		when(referralService.create(any(Referral.class))).thenReturn("referralId");
		when(allegationService.create(any(Allegation.class))).thenReturn("allegationId");
		when(crossReportService.create(any(CrossReport.class))).thenReturn("crossReportId");
		when(referralClientService.create(any(ReferralClient.class))).thenReturn("referralClientId");
		when(reporterService.create(any(Reporter.class))).thenReturn("reporterId");
		
		intakeReferral = new IntakeReferral(mock(Referral.class), mock(Allegation.class), mock(CrossReport.class), mock(ReferralClient.class), mock(Reporter.class));
		
		referralServiceImpl = new ReferralServiceImpl(referralService, allegationService, crossReportService, referralClientService, reporterService);
	}

	@Test
	public void createContainsMapWithNonNullReferral() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("referral"), is(notNullValue()));
	}
	
	@Test
	public void createContainsMapWithReferralId() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("referral"), is(equalTo("referralId")));
	}

	@Test
	public void createContainsMapWithNonNullAllegation() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("allegation"), is(notNullValue()));
	}
	
	@Test
	public void createContainsMapWithAllegationId() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("allegation"), is(equalTo("allegationId")));
	}

	@Test
	public void createContainsMapWithNonNullCrossReport() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("crossReport"), is(notNullValue()));
	}
	
	@Test
	public void createContainsMapWithCrossReportId() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("crossReport"), is(equalTo("crossReportId")));
	}

	@Test
	public void createContainsMapWithNonNullReferralClient() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("referralClient"), is(notNullValue()));
	}
	
	@Test
	public void createContainsMapWithReferralClientId() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("referralClient"), is(equalTo("referralClientId")));
	}

	@Test
	public void createContainsMapWithNonNullReporter() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("reporter"), is(notNullValue()));
	}
	
	@Test
	public void createContainsMapWithReporterId() throws Exception {
		assertThat(referralServiceImpl.create(intakeReferral).get("reporter"), is(equalTo("reporterId")));
	}
}
