package gov.ca.cwds.rest.util;

import java.util.Date;

public class FerbDateUtils {

  private FerbDateUtils() {
    // static methods only
  }

  /**
   * Work-around for SonarQube: "exposes date implementation."
   * 
   * @param incoming date to process
   * @return new, fresh date
   */
  public static Date freshDate(Date incoming) {
    return incoming != null ? new Date(incoming.getTime()) : null;
  }

}
