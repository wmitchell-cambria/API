package gov.ca.cwds.rest.business.reminders;

import java.util.Date;

import org.joda.time.DateTime;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Tickle;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * Business rule class for R - 03276.
 * 
 * @author CWDS API Team
 *
 */
public class R03276MandatedReporterFollowUpReportReminder {

  private static final String AFFECTED_BY_CODE = "RP";
  private static final short TICKLE_MESSAGE_TYPE = (short) 2052;

  private ReferralDao referralDao;
  private ReporterDao reporterDao;
  private TickleService tickleService;

  /**
   * Construct with parameters.
   * 
   * @param referralDao Referral DAO
   * @param reporterDao Reporter DAO
   * @param tickleService Tickle Service
   */
  @Inject
  public R03276MandatedReporterFollowUpReportReminder(ReferralDao referralDao,
      ReporterDao reporterDao, TickleService tickleService) {
    this.referralDao = referralDao;
    this.reporterDao = reporterDao;
    this.tickleService = tickleService;
  }

  /**
   * Create tickle entry for mandated reporter if feedback is required.
   * 
   * @param screeningToReferral The screening to referral
   */
  public void createMandatedReporterFollowUpReportReminder(
      PostedScreeningToReferral screeningToReferral) {
    String referralId = screeningToReferral.getReferralId();

    Referral referral = referralDao.find(referralId);
    Reporter reporter = reporterDao.find(referralId);

    if (reporter != null) {
      String reporterId = reporter.getPrimaryKey();
      boolean mandatedReporterIndicator =
          DomainChef.uncookBooleanString(reporter.getMandatedReporterIndicator());
      boolean feedbackRequiredIndicator =
          DomainChef.uncookBooleanString(reporter.getFeedbackRequiredIndicator());
      Date feedbackDate = reporter.getFeedbackDate();
      Date referralReceiveDate = referral.getReceivedDate();
      Date referralClosureDate = referral.getClosureDate();

      boolean datesCondition = feedbackDate == null && referralReceiveDate != null
          && referralClosureDate == null;
      if (mandatedReporterIndicator && feedbackRequiredIndicator && datesCondition) {
        DateTime reportDueDate = new DateTime(referralReceiveDate).plusDays(30);
        createTickle(referralId, reporterId, reportDueDate, referral.getScreenerNoteText());
      }

      //
      // We don't update or delete at this time
      //
    }
  }

  private void createTickle(String referralId, String reporterId, DateTime dueDate,
      String noteText) {
    Tickle tickle = new Tickle(referralId, AFFECTED_BY_CODE, reporterId, null,
        DomainChef.cookDate(dueDate.toDate()), noteText, TICKLE_MESSAGE_TYPE);
    tickleService.create(tickle);
  }
}
