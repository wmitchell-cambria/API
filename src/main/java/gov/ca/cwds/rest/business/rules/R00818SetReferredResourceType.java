package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.RuleValidatator;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 00818"
 * 
 * If the Response Decision is 'Evaluate Out', then Rationale is mandatory and Agency Referred to is
 * enabled and mandatory, else Agency Referred To is disabled.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R00818SetReferredResourceType implements RuleValidatator {

  private ScreeningToReferral screeningToReferral;

  /**
   * @param screeningToReferral - screeningToReferral
   */
  public R00818SetReferredResourceType(ScreeningToReferral screeningToReferral) {
    super();
    this.screeningToReferral = screeningToReferral;
  }

  @Override
  public boolean isValid() {
    Boolean referredToResourceType = Boolean.FALSE;
    if (screeningToReferral.getResponseTime() == 1519) {
      referredToResourceType = Boolean.TRUE;
    }
    return referredToResourceType;
  }

}
