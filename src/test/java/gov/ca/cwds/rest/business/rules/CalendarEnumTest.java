package gov.ca.cwds.rest.business.rules;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class CalendarEnumTest {

  /**
   * 
   */
  @Test
  public void type() {
    assertThat(CalendarEnum.class, notNullValue());
  }

  /**
   * 
   */
  @Test
  public void getName_Args__() {
    CalendarEnum target = CalendarEnum.YEARS;
    String actual = target.getName();
    String expected = "Y";
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void getDescription_Args__() {
    CalendarEnum target = CalendarEnum.YEARS;
    String actual = target.getDescription();
    String expected = "years";
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void lookUpByDescriptionNull_Args__() {
    String calendar = null;
    CalendarEnum actual = CalendarEnum.lookUpByDescription(calendar);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void lookUpByDescription_Args__() {
    String calendar = "years";
    String actual = CalendarEnum.lookUpByDescription(calendar).getName();
    String expected = "Y";
    assertThat(actual, is(equalTo(expected)));
  }

}
