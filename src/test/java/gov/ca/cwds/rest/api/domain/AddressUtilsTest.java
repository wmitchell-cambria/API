package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AddressUtilsTest {

  @Test
  public void testZipCodeDefaultingWhenEmpty() throws Exception {
    String zip = "";
    String actual = AddressUtils.defaultIfBlank(zip);
    String expected = "0";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testZipCodeDefaultingWhenWhiteSpaces() throws Exception {
    String zip = "   ";
    String actual = AddressUtils.defaultIfBlank(zip);
    String expected = "0";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testZipCodeDefaultingTrim() throws Exception {
    String zip = " 66  ";
    String actual = AddressUtils.defaultIfBlank(zip);
    String expected = "66";
    assertThat(actual, is(equalTo(expected)));
  }

}
