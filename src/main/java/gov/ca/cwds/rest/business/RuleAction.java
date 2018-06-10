package gov.ca.cwds.rest.business;

/**
 * A common interface for executing legacy business rules
 *
 * <p>
 * Validators perform some sort of Business Rule action and does not return a value.
 * </p>
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface RuleAction {

  /**
   * Execute validation. Implementations typically log warnings and throws runtime exceptions on
   * error.
   */
  public void execute();

}
