package gov.ca.cwds.rest.business.rules;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.business.RuleValidatator;

public class R04530AssignmentEndDateValidator implements RuleValidatator {

  Assignment assignment = null;

  /**
   * validating assignment end date
   */
  @Override
  public boolean isValid() {

    boolean validEndDate = false;
    // end date is optional value.. it can be null.
    if (StringUtils.isBlank(assignment.getEndDate())
        && StringUtils.isBlank(assignment.getEndTime())) {
      return true;
    } else {
      Date assignemntEndDate = DomainChef.concatenateDateAndTime(
          unCookDateString(assignment.getEndDate()), unCookTimeString(assignment.getEndTime()));

      Date assignemntStartDate = DomainChef.concatenateDateAndTime(
          unCookDateString(assignment.getStartDate()), unCookTimeString(assignment.getStartTime()));
      Date currentDate = new Date();

      if ((assignemntStartDate.before(assignemntEndDate)
          || (assignemntStartDate.equals(assignemntEndDate)))
          && (currentDate.before(assignemntEndDate) || currentDate.equals(assignemntEndDate))) {
        validEndDate = true;
      }
    }

    return validEndDate;

  }

  /**
   * constructor to set assignment object
   * 
   * @param assignment - assignment object
   */
  public R04530AssignmentEndDateValidator(Assignment assignment) {
    this.assignment = assignment;

  }

  /**
   * service to convert strong to date object.
   * 
   * @param date - date string object
   * @return converted date object.
   */
  private Date unCookDateString(String date) {
    return DomainChef.uncookDateString(date);
  }

  /**
   * service to convert strong to date object.
   * 
   * @param date - date string object
   * @return converted date object.
   */
  private Date unCookTimeString(String time) {
    return DomainChef.uncookTimeString(time);
  }



}
