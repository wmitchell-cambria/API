package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.business.RuleAction;

/**
 * CWDS API Team
 *
 * R - 04880 Estimated_DOB_Code Setting
 * <p>
 * Rule Text<br>
 * If Age and Age Unit are entered, set the CLIENT.Estimated_DOB_Code to Y and set the
 * CLIENT.Birth_Date to <br>
 * System_Date - Age (by Unit); Else, If Date of Birth is entered set the Estimated_DOB_Code to N
 * and set <br>
 * Birth_Date to Date of Birth entered; Else If Date of Birth, Age, and Age Unit are blank, <br>
 * then set Estimated_DOB_Code and Birth_Date U.
 * <p>
 * Access Logic
 * <p>
 * If Age and Age Unit are entered <br>
 * then (set CLIENT.Estimated_DOB_Code to = 'Y' and set the CLIENT.Birth_Date to System_Date - Age
 * (by Unit)); <br>
 * Else, If Date of Birth is entered <br>
 * then (set the Estimated_DOB_Code to N and set Birth_Date to Date of Birth entered); <br>
 * Else If Date of Birth, Age, and Age Unit are blank, then (set Estimated_DOB_Code and Birth_Date
 * U).
 * <p>
 * --- /referrals/POST allows date of birth. It does not currently allow age and time unit to be
 * specified.<br>
 * If the CLIENT.BIRTH_DATE is blank or null, then set CLIENT.ESTIMATED_DOB_CODE to "U". <br>
 * Otherwise set CLIENT.ESTIMATED_DOB_CODE to "N".
 */
public class R04880EstimatedDOBCodeSetting implements RuleAction {

  private Client client;

  public R04880EstimatedDOBCodeSetting(Client client) {
    this.client = client;
  }

  @Override
  public void execute() {
    if (client.getBirthDate() == null) {
      client.setEstimatedDobCode(
          gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_NOT_PROVIDED);
    } else {
      client.setEstimatedDobCode(
          gov.ca.cwds.rest.api.domain.cms.Client.ESTIMATED_DOB_CODE_ACTUALLY_ENTERED);
    }
  }
}
