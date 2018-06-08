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
  public void testUtcToPst() throws Exception {
    String ldtValue = "2017-01-03T03:10:09";
    LocalDateTime ldt = LocalDateTime.parse(ldtValue);
    LocalDateTime shiftedLdt = FerbDateUtils.utcToPst(ldt);
    assertEquals("2017-01-02T19:10:09", shiftedLdt.toString());
  }

}