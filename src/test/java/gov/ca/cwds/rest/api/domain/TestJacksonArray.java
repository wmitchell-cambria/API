package gov.ca.cwds.rest.api.domain;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.ObjectMapperUtils;

public class TestJacksonArray {

  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class Foo {

    @JsonProperty
    public Foo1 title;

    @JsonProperty
    public Foo2 message;

  }

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
  @JsonSubTypes({@JsonSubTypes.Type(value = Foo1.class, name = "title"),
      @JsonSubTypes.Type(value = Foo2.class, name = "message")})
  public static class FooParent {

    @JsonProperty
    private String value;

    @JsonProperty
    private String variable;

  }

  public static class Foo1 extends FooParent {
  }

  public static class Foo2 extends FooParent {
  }

  public static void main(String[] args) throws IOException {
    ObjectMapper mapper = ObjectMapperUtils.createObjectMapper();

    Foo foo = new Foo();
    foo.title = new Foo1();
    foo.message = new Foo2();

    String serialized = mapper.writeValueAsString(foo);
    System.out.println(serialized);
  }

}
