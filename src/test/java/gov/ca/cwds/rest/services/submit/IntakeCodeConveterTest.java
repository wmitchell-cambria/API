package gov.ca.cwds.rest.services.submit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class IntakeCodeConveterTest {

  /**
   * 
   */
  @Test
  public void type() {
    assertThat(IntakeCodeConverter.class, notNullValue());
  }

  /**
   * 
   */
  @Test
  public void getIntakeValue_Args__() {
    IntakeCodeConverter target = IntakeCodeConverter.ABANDONED;
    String actual = target.getIntakeValue();
    String expected = "Abandoned";
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void getLegacyValue_Args__() {
    IntakeCodeConverter target = IntakeCodeConverter.AMERICAN_INDIAN;
    String actual = target.getLegacyValue();
    String expected = "American Indian*";
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findLegacyDescpretionNull_Args__String() {
    String intakeValue = null;
    IntakeCodeConverter actual = IntakeCodeConverter.findLegacyDescription(intakeValue);
    IntakeCodeConverter expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findLegacyDescpretion_ToGet_LegacyValue() {
    String intakeValue = "Unknown";
    String actual = IntakeCodeConverter.findLegacyDescription(intakeValue).getLegacyValue();
    String expected = IntakeCodeConverter.UNKNOWN.getLegacyValue();
    assertThat(actual, is(equalTo(expected)));
  }

}
