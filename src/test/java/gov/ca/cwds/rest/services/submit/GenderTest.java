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
public class GenderTest {

  /**
   * 
   */
  @Test
  public void type() {
    assertThat(Gender.class, notNullValue());
  }

  /**
   * 
   */
  @Test
  public void getName_Args__() {
    Gender target = Gender.FEMALE;
    String actual = target.getCmsDescription();
    String expected = "F";
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findByNsDescription_Args__String() {
    String nsDescription = null;
    Gender actual = Gender.findByNsDescription(nsDescription);
    Gender expected = Gender.UNKNOWN;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * 
   */
  @Test
  public void findByNsDescription_ToGet_CMS_Descrption() {
    String nsDescription = "female";
    String actual = Gender.findByNsDescription(nsDescription).getCmsDescription();
    String expected = Gender.FEMALE.getCmsDescription();
    assertThat(actual, is(equalTo(expected)));
  }

}
