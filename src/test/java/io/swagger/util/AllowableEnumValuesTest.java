package io.swagger.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import io.swagger.models.properties.PropertyBuilder;

public class AllowableEnumValuesTest {

  @BeforeClass
  public static void setupSuite() {
    TestSystemCodeCache cache = new TestSystemCodeCache();
  }

  @Test
  public void type() throws Exception {
    assertThat(AllowableEnumValues.class, notNullValue());
  }

  @Test
  public void create_Args__String_blank() throws Exception {
    final String allowableValues = "";
    final AllowableEnumValues actual = AllowableEnumValues.create(allowableValues);
    final AllowableEnumValues expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void create_Args__String_array() throws Exception {
    final String allowableValues = "['804','805'}";
    final AllowableEnumValues actual = AllowableEnumValues.create(allowableValues);
    assertThat(actual.getItems().size(), is(equalTo(2)));
  }

  @Test
  public void create_Args__String_dynamic_syscodeid() throws Exception {
    final String allowableValues = "$ID:GVR_ENTC";
    final AllowableEnumValues actual = AllowableEnumValues.create(allowableValues);
    final String expected = "1101";
    assertThat(actual.getItems().get(0), is(equalTo(expected)));
  }

  @Test
  public void create_Args__String_dynamic_logical_id() throws Exception {
    final String allowableValues = "$LG:GVR_ENTC";
    final AllowableEnumValues actual = AllowableEnumValues.create(allowableValues);
    final String expected = "34";
    assertThat(actual.getItems().get(0), is(equalTo(expected)));
  }

  @Test
  public void getItems_Args__() throws Exception {
    AllowableEnumValues target = AllowableEnumValues.create("['804','805'}");
    assertThat(target.getItems().size(), is(equalTo(2)));
  }

  @Test
  public void asPropertyArguments_Args__() throws Exception {
    final AllowableEnumValues target = AllowableEnumValues.create("['804','805'}");
    final Map<PropertyBuilder.PropertyId, Object> actual = target.asPropertyArguments();
    assertThat(actual, notNullValue());
  }

}
