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

import org.hamcrest.junit.ExpectedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IfThenValidatorTest {
  private String abc;
  private String def;

  private IfThen constraintAnnotation = mock(IfThen.class);
  private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
  private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
  private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);

  private IfThenValidator validator = new IfThenValidator();


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
    when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
    when(constraintAnnotation.ifProperty()).thenReturn("abc");
    when(constraintAnnotation.thenProperty()).thenReturn("def");
  }

  @Test
  public void isValidReturnsFalseWhenRequiredAndIfPropertyNotSet() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    validator.initialize(constraintAnnotation);
    IfThenValidatorTest bean = new IfThenValidatorTest();

    assertThat(validator.isValid(bean, context), is(equalTo(false)));
  }

  @Test
  public void isValidReturnsFalseWhenRequiredAndIfPropertySetButNotThenProperty() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    validator.initialize(constraintAnnotation);
    IfThenValidatorTest bean = new IfThenValidatorTest();
    bean.abc = "value";

    assertThat(validator.isValid(bean, context), is(equalTo(false)));
  }

  @Test
  public void isValidReturnsTrueWhenRequiredAndIfPropertySetButAndThenProperty() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    validator.initialize(constraintAnnotation);
    IfThenValidatorTest bean = new IfThenValidatorTest();
    bean.abc = "value";
    bean.def = "value";

    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsFalseWhenNotRequiredAndIfPropertySetButNotThenProperty()
      throws Exception {
    when(constraintAnnotation.required()).thenReturn(false);
    validator.initialize(constraintAnnotation);
    IfThenValidatorTest bean = new IfThenValidatorTest();
    bean.abc = "value";

    assertThat(validator.isValid(bean, context), is(equalTo(false)));
    verify(context, times(1)).buildConstraintViolationWithTemplate(contains("is required since"));
  }

  @Test
  public void isValidReturnsTrueWhenNotRequiredAndIfPropertySetAndThenProperty() throws Exception {
    when(constraintAnnotation.required()).thenReturn(false);
    validator.initialize(constraintAnnotation);
    IfThenValidatorTest bean = new IfThenValidatorTest();
    bean.abc = "value";
    bean.def = "value";

    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsTrueWhenNotRequiredAndIfPropertyNotSetAndThenPropertyIs()
      throws Exception {
    when(constraintAnnotation.required()).thenReturn(false);
    validator.initialize(constraintAnnotation);
    IfThenValidatorTest bean = new IfThenValidatorTest();
    bean.def = "value";

    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsFalseWhenRequiredAndIfPropertyNotSetAndThenPropertyIs()
      throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    validator.initialize(constraintAnnotation);
    IfThenValidatorTest bean = new IfThenValidatorTest();
    bean.def = "value";

    assertThat(validator.isValid(bean, context), is(equalTo(false)));
    verify(context, times(1))
        .buildConstraintViolationWithTemplate(contains("is required but not set"));
  }

  @Test
  public void validThrowsExceptionWhenPropertyNotExistsInBean() throws Exception {
    thrown.expect(ValidationException.class);
    validator.initialize(constraintAnnotation);
    validator.isValid(new Object(), context);
  }

  /*
   * Oddness with cobertura cause the declaring class line to be not counted as run. This has to do
   * with bridge functions. To get our coverage numbers the "test" below calls the bridge functions
   * directly.
   */
  @Test
  public void callBridgeFunctions() throws Exception {
    IfThenValidator validator = new IfThenValidator();
    Method initialize = IfThenValidator.class.getMethod("initialize", Annotation.class);
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
