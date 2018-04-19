package gov.ca.cwds.rest.api.domain.enums;

import static gov.ca.cwds.rest.api.domain.enums.RoleType.ANONIMOUS_REPORTER;
import static gov.ca.cwds.rest.api.domain.enums.RoleType.MANDATED_REPORTER;
import static gov.ca.cwds.rest.api.domain.enums.RoleType.NON_MANDATED_REPORTER;
import static gov.ca.cwds.rest.api.domain.enums.RoleType.PERPETRATOR;
import static gov.ca.cwds.rest.api.domain.enums.RoleType.VICTIM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

/**
 * @author Intake Team 4
 */
public class RoleTypeTest {

  String[] typesStringArrayValid = {"Anonymous Reporter", "Mandated Reporter",
      "Non-mandated Reporter", "Perpetrator", "Victim"};
  String[] typesStringArrayNotValid = {"Victim", "Perpetrator", "Anonymous Reporter",
      "Somebody Else"};

  @Test
  public void hasNeededTypes() {
    assertEquals(ANONIMOUS_REPORTER.getTheType(), "Anonymous Reporter");
    assertEquals(MANDATED_REPORTER.getTheType(), "Mandated Reporter");
    assertEquals(NON_MANDATED_REPORTER.getTheType(), "Non-mandated Reporter");
    assertEquals(VICTIM.getTheType(), "Victim");
    assertEquals(PERPETRATOR.getTheType(), "Perpetrator");
    assertTrue(RoleType.hasType(ANONIMOUS_REPORTER.getTheType()));
    assertTrue(RoleType.hasType(MANDATED_REPORTER.getTheType()));
    assertTrue(RoleType.hasType(NON_MANDATED_REPORTER.getTheType()));
    assertTrue(RoleType.hasType(VICTIM.getTheType()));
    assertTrue(RoleType.hasType(PERPETRATOR.getTheType()));
  }

  @Test
  public void hasTypes() {
    assertTrue(RoleType.hasTypes(typesStringArrayValid));
    assertFalse(RoleType.hasTypes(typesStringArrayNotValid));
  }

  @Test
  public void hasTypes1() {
    assertTrue(RoleType.hasTypes(Arrays.asList(typesStringArrayValid)));
    assertFalse(RoleType.hasTypes(Arrays.asList(typesStringArrayNotValid)));
  }

  @Test
  public void getAllTheTypes() {
    String[] allTypes = RoleType.getAllTheTypes();
    assertNotNull(allTypes);
    assertTrue(RoleType.hasTypes(allTypes));
    assertEquals(String.join(",", typesStringArrayValid), String.join(",", allTypes));
  }

}