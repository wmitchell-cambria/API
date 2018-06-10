package gov.ca.cwds.rest.business;

/**
 * A common interface for validating legacy business rules.
 *
 * <p>
 * Validators are responsible for checking if rule is implemented correctly.
 * </p>
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface RuleValidator {

  /**
   * @return the valid boolean
   */
  boolean isValid();

}
