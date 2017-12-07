package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class LimitedAccessTypeTest {
  @Test
  public void shouldHaveNoneValueEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "N",
        LimitedAccessType.NONE.getValue());
  }

  @Test
  public void shouldHaveNoneDescriptionEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "None",
        LimitedAccessType.NONE.getDescription());
  }

  @Test
  public void shouldHaveSealedValueEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "R",
        LimitedAccessType.SEALED.getValue());
  }

  @Test
  public void shouldHaveSealedNoneDescriptionEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "Sealed",
        LimitedAccessType.SEALED.getDescription());
  }

  @Test
  public void shouldHaveSensitiveValueEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "S",
        LimitedAccessType.SENSITIVE.getValue());
  }

  @Test
  public void shouldHaveSensitivedNoneDescriptionEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "Sensitive",
        LimitedAccessType.SENSITIVE.getDescription());
  }

  @Test
  public void getDescription_Args__() throws Exception {
    LimitedAccessType target = LimitedAccessType.valueOf("NONE");
    String actual = target.getDescription();
    String expected = "None";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getValue_Args__() throws Exception {
    LimitedAccessType target = LimitedAccessType.valueOf("NONE");
    String actual = target.getValue();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void lookupByValue_Args__String() throws Exception {
    String tableName = null;
    LimitedAccessType actual = LimitedAccessType.getByValue(tableName);
    LimitedAccessType expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
