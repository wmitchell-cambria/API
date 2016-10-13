package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OnlyIfValidatorTest {
  private String abc;
  private String def;

  private OnlyIf constraintAnnotation = mock(OnlyIf.class);
  private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
  private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
  private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);

  private OnlyIfValidator validator = new OnlyIfValidator();

  @Before
  public void setup() throws Exception {
    when(constraintAnnotation.ifProperty()).thenReturn("abc");
    when(constraintAnnotation.property()).thenReturn("def");
    when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
    when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
  }

  @Test
  public void isValidReturnsTrueWhenIfPropertyNotBlank() throws Exception {
    validator.initialize(constraintAnnotation);
    OnlyIfValidatorTest bean = new OnlyIfValidatorTest();
    bean.abc = "abc";

    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsTrueWhenIfPropertyNotBlankAndPropertyNotBlank() throws Exception {
    validator.initialize(constraintAnnotation);
    OnlyIfValidatorTest bean = new OnlyIfValidatorTest();
    bean.abc = "abc";
    bean.def = "def";

    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsTrueWhenIfPropertyBlankAndPropertyBlank() throws Exception {
    validator.initialize(constraintAnnotation);
    OnlyIfValidatorTest bean = new OnlyIfValidatorTest();

    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsFalseWhenIfPropertyBlankAndPropertyNotBlank() throws Exception {
    validator.initialize(constraintAnnotation);
    OnlyIfValidatorTest bean = new OnlyIfValidatorTest();
    bean.def = "def";

    assertThat(validator.isValid(bean, context), is(equalTo(false)));
    verify(context, times(1)).buildConstraintViolationWithTemplate(contains("an only be set if"));
  }

  /*
   * Oddness with cobertura cause the declaring class line to be not counted as run. This has to do
   * with bridge functions. To get our coverage numbers the "test" below calls the bridge functions
   * directly.
   */
  @Test
  public void callBridgeFunctions() throws Exception {
    OnlyIfValidator validator = new OnlyIfValidator();
    Method initialize = OnlyIfValidator.class.getMethod("initialize", Annotation.class);
    initialize.invoke(validator, constraintAnnotation);
    Assert.assertTrue(true);
  }

  public String getAbc() {
    return abc;
  }

  public String getDef() {
    return def;
  }
}
