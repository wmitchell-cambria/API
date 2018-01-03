package gov.ca.cwds.rest.business.rules;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.business.RuleValidator;

/**
 * Implement R - 04530 The combination of Assignment End Date and End Time must be less than or
 * equal to the current system date and time AND must be greater than or equal to Assignment Start
 * Date and Start Time.
 * 
 * @author CWDS API Team
 *
 */
public class R04530AssignmentEndDateValidator implements RuleValidator {

  Assignment assignment = null;

  /**
   * constructor to set assignment object
   * 
   * @param assignment - assignment object
   */
  public R04530AssignmentEndDateValidator(Assignment assignment) {
    this.assignment = assignment;

  }

  /**
   * Rule - R04530 - validating assignment end date
   */
  @Override
  public boolean isValid() {

    boolean validEndDate = false;
    // end date is optional value.. it can be null.
    if (isNullEndDate()) {
      return true;
    }

    Date assignemntStartDate =
        this.concatenateDateAndTime(assignment.getStartDate(), assignment.getStartTime());
    Date assignemntEndDate =
        this.concatenateDateAndTime(assignment.getEndDate(), assignment.getEndTime());


    if (this.isAssignmentStartDateBeforeOrEqualsEndDate(assignemntStartDate, assignemntEndDate)
        && (this.isAssignmentEndDateBeforeOrEqualCurrentDate(assignemntEndDate))) {
      validEndDate = true;
    }
    return validEndDate;

  }

  private boolean isAssignmentEndDateBeforeOrEqualCurrentDate(Date assignemntEndDate) {
    Date currentDate = new Date();
    return (assignemntEndDate.compareTo(currentDate) <= 0);


  }

  /**
   * validating start date should be before or equals start date
   * 
   * @param startDate - assignment start date
   * @param endDate - assignment end date
   * @return
   */
  private boolean isAssignmentStartDateBeforeOrEqualsEndDate(Date startDate, Date endDate) {
    return (startDate.compareTo(endDate) <= 0);

  }

  /**
   * method to combine date and time
   * 
   * @param date - date object
   * @param time - time object
   * @return - combines date and time
   */
  private Date concatenateDateAndTime(String date, String time) {
    return DomainChef.concatenateDateAndTime(unCookDateString(date), unCookTimeString(time));

  }

  /**
   * checking end date is null or not;
   * 
   * @return true if endDate is null
   */
  private boolean isNullEndDate() {
    return (StringUtils.isBlank(assignment.getEndDate())
        && StringUtils.isBlank(assignment.getEndTime()));

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
