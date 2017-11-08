package gov.ca.cwds.rest.api.domain.comparator;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * @author CWDS API Team
 *
 */
public interface DateTimeComparatorInterface {

  /**
   * @param legacyDescriptor - legacyDescriptor
   * @param datetime - dateTime
   * @return the compare
   */
  public boolean compare(LegacyDescriptor legacyDescriptor, DateTime datetime);

}
