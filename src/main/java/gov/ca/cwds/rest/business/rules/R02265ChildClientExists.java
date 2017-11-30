package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.business.RuleValidatator;

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
public class R02265ChildClientExists implements RuleValidatator {

  private Participant incomingParticipant;

  /**
   * @param incomingParticipant - incomingParticipant
   */
  public R02265ChildClientExists(Participant incomingParticipant) {
    super();
    this.incomingParticipant = incomingParticipant;
  }

  @Override
  public boolean isValid() {
    boolean childClientIndicatorVar = false;
    if (incomingParticipant.getRoles().contains("Victim")) {
      return true;
    }
    return childClientIndicatorVar;
  }

}
