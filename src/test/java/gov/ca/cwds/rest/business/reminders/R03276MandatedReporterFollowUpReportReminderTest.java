package gov.ca.cwds.rest.business.reminders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * Unit tests for R03276MandatedReporterFollowUpReportReminder rule
 * 
 * @author CWDS API Team
 *
 */
public class R03276MandatedReporterFollowUpReportReminderTest {

  private TickleService tickleService;
  private ReferralDao referralDao;
  private ReporterDao reporterDao;


  /**
   * @throws Exception general test error
   */
  @Before
  public void setup() throws Exception {
    tickleService = mock(TickleService.class);
    referralDao = mock(ReferralDao.class);
    reporterDao = mock(ReporterDao.class);
  }

  @Test
  public void testFollowUpReminder() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Address address = new AddressResourceBuilder().createAddress();
    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", true, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5", new Date());

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);

    R03276MandatedReporterFollowUpReportReminder r03276MandatedReporterFollowUpReportReminder =
        new R03276MandatedReporterFollowUpReportReminder(referralDao, reporterDao, tickleService);

    r03276MandatedReporterFollowUpReportReminder
        .createMandatedReporterFollowUpReportReminder(postedScreeningToReferral);
    verify(tickleService, times(1)).create(any());
  }


  @Test
  public void shouldNotCreateReminderWhenReporterNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("1234567ABC", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder()
    		.setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00")
        .build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", domainReferral, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(reporterDao.find(any(String.class))).thenReturn(null);

    R03276MandatedReporterFollowUpReportReminder r03276MandatedReporterFollowUpReportReminder =
        new R03276MandatedReporterFollowUpReportReminder(referralDao, reporterDao, tickleService);

    r03276MandatedReporterFollowUpReportReminder
        .createMandatedReporterFollowUpReportReminder(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }
  
  @Test
  public void shouldNotCreateReminderWhenFeedbackDateIsNotNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder()
    		.setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00")
        .build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Reporter reporterDomain = new ReporterResourceBuilder()
    	.setFeedbackRequiredIndicator(Boolean.TRUE)
    	.setMandatedReporterIndicator(Boolean.TRUE)
    	.setFeedbackDate("2016-09-02")
    	.build();

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5", new Date());

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);

    R03276MandatedReporterFollowUpReportReminder r03276MandatedReporterFollowUpReportReminder =
        new R03276MandatedReporterFollowUpReportReminder(referralDao, reporterDao, tickleService);

    r03276MandatedReporterFollowUpReportReminder
        .createMandatedReporterFollowUpReportReminder(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  @Test
  public void shouldNotCreateReminderWhenReferralReceivedDateIsNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder()
    		.setReceivedDate("")
        .setReceivedTime("")
        .build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Reporter reporterDomain = new ReporterResourceBuilder()
    	.setFeedbackRequiredIndicator(Boolean.TRUE)
    	.setMandatedReporterIndicator(Boolean.TRUE)
    	.build();

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5", new Date());

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);

    R03276MandatedReporterFollowUpReportReminder r03276MandatedReporterFollowUpReportReminder =
        new R03276MandatedReporterFollowUpReportReminder(referralDao, reporterDao, tickleService);

    r03276MandatedReporterFollowUpReportReminder
        .createMandatedReporterFollowUpReportReminder(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  @Test
  public void shouldNotCreateReminderWhenReferralClosureDateIsNotNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2017-01-30")
        .setReceivedTime("13:00:00").setClosureDate("2017-02-30").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Reporter reporterDomain = new ReporterResourceBuilder()
    	.setFeedbackRequiredIndicator(Boolean.TRUE)
    	.setMandatedReporterIndicator(Boolean.TRUE)
    	.build();

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5", new Date());

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);

    R03276MandatedReporterFollowUpReportReminder r03276MandatedReporterFollowUpReportReminder =
        new R03276MandatedReporterFollowUpReportReminder(referralDao, reporterDao, tickleService);

    r03276MandatedReporterFollowUpReportReminder
        .createMandatedReporterFollowUpReportReminder(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  @Test
  public void shouldNotCreateReminderWhenNotMandatedReporter() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2017-01-30")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Reporter reporterDomain = new ReporterResourceBuilder()
    	.setFeedbackRequiredIndicator(Boolean.TRUE)
    	.setMandatedReporterIndicator(Boolean.FALSE)
    .build();

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5", new Date());

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);

    R03276MandatedReporterFollowUpReportReminder r03276MandatedReporterFollowUpReportReminder =
        new R03276MandatedReporterFollowUpReportReminder(referralDao, reporterDao, tickleService);

    r03276MandatedReporterFollowUpReportReminder
        .createMandatedReporterFollowUpReportReminder(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  @Test
  public void shouldNotCreateReminderWhenNotFeedbackRequired() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2017-01-30")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Reporter reporterDomain = new ReporterResourceBuilder()
    	.setFeedbackRequiredIndicator(Boolean.FALSE)
    	.setMandatedReporterIndicator(Boolean.TRUE)
    	.build();

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5", new Date());

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);

    R03276MandatedReporterFollowUpReportReminder r03276MandatedReporterFollowUpReportReminder =
        new R03276MandatedReporterFollowUpReportReminder(referralDao, reporterDao, tickleService);

    r03276MandatedReporterFollowUpReportReminder
        .createMandatedReporterFollowUpReportReminder(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }
}
