package gov.ca.cwds.rest.business.rules;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.business.RuleAction;

/**
 * @author CWDS API Team
 *
 */
public class R00834AgeUnitRestriction implements RuleAction {

  private Client client;
  private String age;
  private String ageUnits;

  /**
   * @param client - client
   */
  public R00834AgeUnitRestriction(Client client) {
    this.client = client;
  }

  @Override
  public void execute() {
    if (client == null) {
      return;
    }
    Date creationDate = DomainChef.uncookDateString(client.getCreationDate());
    if (StringUtils.isNotBlank(client.getBirthDate())
        && client.getEstimatedDobCode().contains("N")) {
      Date birthDate = DomainChef.uncookDateString(client.getBirthDate());
      long noOfDays = TimeUnit.DAYS.convert(creationDate.getTime() - birthDate.getTime(),
          TimeUnit.MILLISECONDS);
      calculateAgeByDob(noOfDays);
    }

  }

  private void calculateAgeByDob(long noOfDays) {
    if (noOfDays > 365) {
      age = Long.toString(noOfDays / 365);
      ageUnits = "Y";
    } else if (noOfDays > 30) {
      age = Long.toString(noOfDays / 30);
      ageUnits = "M";
    } else if (noOfDays > 7) {
      age = Long.toString(noOfDays / 7);
      ageUnits = "W";
    } else if (noOfDays > 1) {
      age = Long.toString(noOfDays);
      ageUnits = "D";
    }
  }

  /**
   * @return the age
   */
  public String getAge() {
    return age;
  }

  /**
   * @return the ageUnits
   */
  public String getAgeUnits() {
    return ageUnits;
  }

}
