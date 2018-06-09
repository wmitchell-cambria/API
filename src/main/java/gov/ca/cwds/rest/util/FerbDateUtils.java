package gov.ca.cwds.rest.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

  public static LocalDateTime utcToPst(LocalDateTime dateTime) {
    return shiftTimeZone(dateTime, ZoneOffset.UTC, ZoneId.of("America/Los_Angeles"));
  }

  public static LocalDateTime shiftTimeZone(LocalDateTime dateTime, ZoneId from, ZoneId to) {
    return dateTime.atZone(from).withZoneSameInstant(to).toLocalDateTime();
  }

}
