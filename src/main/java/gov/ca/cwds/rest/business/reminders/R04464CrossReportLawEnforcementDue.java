package gov.ca.cwds.rest.business.reminders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 04464" - TICKLE for Cross Report for Law Enforcement Due
 * 
 * If the Allegation typs is not a general Neglect and substantial risk, crossReport and reporter
 * doesnt have a lawEnforcement Id. Then create this reminder.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R04464CrossReportLawEnforcementDue {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(R04464CrossReportLawEnforcementDue.class);

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");

  private static final String MANDATED_REPORTER_ROLE = "Mandated Reporter";
  private static final String NON_MANDATED_REPORTER_ROLE = "Non-mandated Reporter";
  private static final String DEFAULT_TRUE_INDICATOR = "Y";
  private static final String DEFAULT_FALSE_INDICATOR = "N";
  private static final String REFERRAL = "R";
  private static final short CROSSREPORT_LAWENFORCEMENT_DUE = (short) 2049;
  private static final int GENERAL_NEGLECT = 2178;
  private static final int SUBSTANTIAL_RISK = 5624;
  private static final short ENTERED_IN_ERROR = (short) 5918;

  private ReferralDao referralDao;
  private AllegationDao allegationDao;
  private ReporterDao reporterDao;
  private CrossReportDao crossReportDao;
  private TickleService tickleService;

  /**
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects
   * @param allegationDao - The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.Allegation} objects
   * @param reporterDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Reporter}
   *        objects
   * @param crossReportDao - The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.CrossReport} objects
   * @param tickleService - tickleService
   */
  @Inject
  public R04464CrossReportLawEnforcementDue(ReferralDao referralDao, AllegationDao allegationDao,
      ReporterDao reporterDao, CrossReportDao crossReportDao, TickleService tickleService) {
    super();
    this.referralDao = referralDao;
    this.tickleService = tickleService;
    this.allegationDao = allegationDao;
    this.reporterDao = reporterDao;
    this.crossReportDao = crossReportDao;
  }

  /**
   * 
   * @param postedScreeningToReferral - postedScreeningToReferral
   */
  public void crossReportForLawEnforcmentDue(PostedScreeningToReferral postedScreeningToReferral) {
    Set<Participant> reporter = postedScreeningToReferral.getParticipants();
    boolean reminderCreated = false;
    Reporter persistedReporter = null;
    for (Participant participant : reporter) {
      if (participant.getRoles().contains(MANDATED_REPORTER_ROLE)
          || participant.getRoles().contains(NON_MANDATED_REPORTER_ROLE)) {
        persistedReporter = reporterDao.find(participant.getLegacyId());
        break;
      }
    }
    Set<Allegation> allegations = postedScreeningToReferral.getAllegations();
    Set<gov.ca.cwds.rest.api.domain.CrossReport> crossReports =
        postedScreeningToReferral.getCrossReports();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    /*
     * duedate is updated with adding 36 HRS to the referral receivedDate and the time referral was
     * created.
     */
    String time = timeFormat.format(referral.getReceivedTime());
    Calendar dueDate = Calendar.getInstance();
    dueDate.setTime(referral.getReceivedDate());
    if (Integer.parseInt(time.split(":")[0]) < 12) {
      dueDate.add(Calendar.HOUR, 36);
    } else {
      dueDate.add(Calendar.DATE, 2);
    }

    if (referral.getClosureDate() == null) {

      for (Allegation allegation : allegations) {
        gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
            allegationDao.find(allegation.getLegacyId());
        short allegationType = savedAllegation.getAllegationType();

        boolean allegationCheck =
            allegationType != GENERAL_NEGLECT && allegationType != SUBSTANTIAL_RISK
                && savedAllegation.getAllegationDispositionType() != ENTERED_IN_ERROR;

        boolean reporterCheck =
            persistedReporter.getMandatedReporterIndicator() != DEFAULT_TRUE_INDICATOR
                && persistedReporter.getLawEnforcementId() != null;

        if (!reminderCreated && allegationCheck && reporterCheck) {

          for (gov.ca.cwds.rest.api.domain.CrossReport crossReport : crossReports) {
            gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
                crossReportDao.find(crossReport.getLegacyId());
            if (!reminderCreated && savedCrossReport.getLawEnforcementId() != null
                && DEFAULT_FALSE_INDICATOR
                    .equals(savedCrossReport.getSatisfyCrossReportIndicator())) {

              gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                  new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(), REFERRAL, null, null,
                      dateFormat.format(dueDate.getTime()), referral.getScreenerNoteText(),
                      CROSSREPORT_LAWENFORCEMENT_DUE);
              tickleService.create(tickle);
              reminderCreated = true;
              LOGGER.info("crossReportForLawEnforcmentDue reminder is created");
            }

          }
        }
      }
    }
  }

}
