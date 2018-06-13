package gov.ca.cwds.rest.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * CWDS API Team
 */
public class FerbDateUtilsTest {

  @Test
  public void testShiftTimeZone() throws Exception {
    String ldtValue = "2017-01-03T03:10:09";
    LocalDateTime ldt = LocalDateTime.parse(ldtValue);
    LocalDateTime shiftedLdt = FerbDateUtils.shiftTimeZone(ldt, ZoneOffset.UTC, ZoneId.of("+1"));
    assertEquals("2017-01-03T04:10:09", shiftedLdt.toString());
  }

  @Test
  public void testUtcToSystemTime() throws Exception {
    String supposedSystemValue = "2017-01-03T03:10:09";
    LocalDateTime supposedSystemTime = LocalDateTime.parse(supposedSystemValue);
    LocalDateTime utcDateTimeForCurrentSystemTime = FerbDateUtils.shiftTimeZone(supposedSystemTime, ZoneId.systemDefault(), ZoneOffset.UTC);

    LocalDateTime shiftedLdt = FerbDateUtils.utcToSystemTime(utcDateTimeForCurrentSystemTime);
    assertEquals("2017-01-03T03:10:09", shiftedLdt.toString());
  }

}