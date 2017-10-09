package io.swagger.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import io.swagger.models.properties.PropertyBuilder;

public class AllowableEnumValuesTest {

  @Test
  public void type() throws Exception {
    assertThat(AllowableEnumValues.class, notNullValue());
  }

  @Test
  public void create_Args__String() throws Exception {
    String allowableValues = "";
    final AllowableEnumValues actual = AllowableEnumValues.create(allowableValues);
    final AllowableEnumValues expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getItems_Args__() throws Exception {
    AllowableEnumValues target = AllowableEnumValues.create("['804','805'}");
    final List<String> actual = target.getItems();
    assertThat(actual.size(), is(equalTo(2)));
  }

  @Test
  public void asPropertyArguments_Args__() throws Exception {
    final AllowableEnumValues target = AllowableEnumValues.create("['804','805'}");
    final Map<PropertyBuilder.PropertyId, Object> actual = target.asPropertyArguments();
    assertThat(actual, notNullValue());
  }

}
