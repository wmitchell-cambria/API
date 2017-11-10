package gov.ca.cwds.rest.business.rules;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.RuleValidatator;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 00824"
 * 
 * IF referralResponseTypeCode is set to Evaluate Out, Approval Status is set to Approved and Client
 * age is below 19
 * 
 * THEN referralClient - dispositionCode is set to the "A"
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R00824SetDispositionCode implements RuleValidatator {

  private ScreeningToReferral screeningToReferral;
  private Participant incomingParticipant;

  /**
   * @param screeningToReferral - screeningToReferral
   * @param incomingParticipant - incomingParticipant
   */
  public R00824SetDispositionCode(ScreeningToReferral screeningToReferral,
      Participant incomingParticipant) {
    super();
    this.screeningToReferral = screeningToReferral;
    this.incomingParticipant = incomingParticipant;
  }

  @Override
  public boolean isValid() {
    return clientAge() < 19 && screeningToReferral.getResponseTime() == 1519
        && screeningToReferral.getApprovalStatus() == 122;
  }

  private int clientAge() {
    String date = screeningToReferral.getStartedAt().split("T")[0];
    String dob = incomingParticipant.getDateOfBirth();
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTime time1 = formatter.parseDateTime(date);
    DateTime time2 = formatter.parseDateTime(dob);
    return Years.yearsBetween(time1, time2).getYears();
  }

}
