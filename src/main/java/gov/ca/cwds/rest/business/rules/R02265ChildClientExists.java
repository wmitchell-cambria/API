package gov.ca.cwds.rest.business.rules;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.business.RuleValidator;

/**
 * <p>
 * BUSINESS RULE: "R - 02265"
 * 
 * IF childClient is created
 * 
 * THEN Set CLIENT.CHILD_CLIENT_IND_VAR = Y.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R02265ChildClientExists implements RuleValidator {

  private Participant incomingParticipant;
  private String dateStarted;
  private static final int ADULT = 19;

  /**
   * @param incomingParticipant - incomingParticipant
   * @param dateStarted - date referral started
   */
  public R02265ChildClientExists(Participant incomingParticipant, String dateStarted) {
    super();
    this.incomingParticipant = incomingParticipant;
    this.dateStarted = dateStarted;
  }

  @Override
  public boolean isValid() {
    boolean childClientIndicatorVar;
    String dob = incomingParticipant.getDateOfBirth();
    if (dob == null) {
      childClientIndicatorVar = false;
    } else {
      childClientIndicatorVar = clientAge(dob) < ADULT;
    }
    return childClientIndicatorVar;
  }

  private int clientAge(String dob) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTime receivedDate = formatter.parseDateTime(dateStarted);
    DateTime clientDob = formatter.parseDateTime(dob);
    return Years.yearsBetween(clientDob, receivedDate).getYears();
  }
}
