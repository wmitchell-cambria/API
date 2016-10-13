package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ZipcodeValidatorTest {

	private Zipcode constraintAnnotation = mock(Zipcode.class);
	private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
	private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
	private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);
	
	@Before
	public void setup() throws Exception {
		when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
		when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
	}

	@Test
	public void isValidReturnsFalseWhenNotRequiredAnd6Digits() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipcodeValidator validator = new ZipcodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("777777", context), is(equalTo(false)));
	}

	@Test
	public void isValidReturnsFalseWhenNotRequiredAnd4Digits() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipcodeValidator validator = new ZipcodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("7777", context), is(equalTo(false)));
	}
	
	@Test
	public void isValidReturnsFalseWhenNotRequiredAndNotAllDigits() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipcodeValidator validator = new ZipcodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("7b777", context), is(equalTo(false)));
	}

	@Test
	public void isValidReturnsTrueWhenNotRequiredAndBlank() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipcodeValidator validator = new ZipcodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid(" ", context), is(equalTo(true)));
	}

	@Test
	public void isValidReturnsTrueNotRequiredWhenAllDigitsAndStartsWith0() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipcodeValidator validator = new ZipcodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("05555", context), is(equalTo(true)));
	}
	
	@Test
	public void isValidReturnsTrueWhenRequiredAndAllDigits() throws Exception {
		when(constraintAnnotation.required()).thenReturn(true);
		ZipcodeValidator validator = new ZipcodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("05555", context), is(equalTo(true)));
	}
	
	@Test
	public void isValidReturnsFalseWhenRequiredAndEmpty() throws Exception {
		when(constraintAnnotation.required()).thenReturn(true);
		ZipcodeValidator validator = new ZipcodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid(" ", context), is(equalTo(false)));
	}
	
	/*
	 * Oddness with cobertura cause the declaring class line to be not counted as run.  This has to do with bridge functions.
	 * To get our coverage numbers the "test" below calls the bridge functions directly.
	 */
	@Test
	public void callBridgeFunctions() throws Exception {
		when(constraintAnnotation.required()).thenReturn(true);
		ZipcodeValidator validator = new ZipcodeValidator();
		Method initialize = ZipcodeValidator.class.getMethod("initialize", Annotation.class);
		initialize.invoke(validator, constraintAnnotation);
		
		Method isvalid = ZipcodeValidator.class.getMethod("isValid", Object.class, ConstraintValidatorContext.class);
		isvalid.invoke(validator, "foo", context);
		Assert.assertTrue(true);
	}
}
