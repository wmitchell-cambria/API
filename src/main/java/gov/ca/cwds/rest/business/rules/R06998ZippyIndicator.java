package gov.ca.cwds.rest.business.rules;

/**
 * BUSINESS RULE: "R - 06998"
 * 
 * Zippy indicator will be "Yes" if user submitting referral with optional fields.
 * 
 * @author CWDS API Team
 *
 */
public enum R06998ZippyIndicator {

  Yes(Boolean.TRUE), No(Boolean.FALSE);

  Boolean code;

  R06998ZippyIndicator(Boolean code) {
    this.code = code;

  }

  /**
   * @return the code
   */
  public Boolean getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(Boolean code) {
    this.code = code;
  }


}
