package gov.ca.cwds.rest.api.domain.comparator;

import org.joda.time.DateTime;

/**
 * 
 * <p>
 * Date time comparator interface compares the time difference between the incoming object and the
 * saved object.
 * <p>
 * 
 * @author CWDS API Team
 * @see DateTimeComparator
 *
 */
public interface DateTimeComparatorInterface {

  /**
   * @param incomingDateTime - incomingDateTime
   * @param savedDateTime - savedDateTime
   * @return the comparison
   */
  public boolean compare(DateTime incomingDateTime, DateTime savedDateTime);

}
