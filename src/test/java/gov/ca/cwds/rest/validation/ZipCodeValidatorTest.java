package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.junit.Before;
import org.junit.Test;

public class ZipCodeValidatorTest {

	private ZipCode constraintAnnotation = mock(ZipCode.class);
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
		ZipCodeValidator validator = new ZipCodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("777777", context), is(equalTo(false)));
	}

	@Test
	public void isValidReturnsFalseWhenNotRequiredAnd4Digits() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipCodeValidator validator = new ZipCodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("7777", context), is(equalTo(false)));
	}
	
	@Test
	public void isValidReturnsFalseWhenNotRequiredAndNotAllDigits() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipCodeValidator validator = new ZipCodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("7b777", context), is(equalTo(false)));
	}

	@Test
	public void isValidReturnsTrueWhenNotRequiredAndBlank() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipCodeValidator validator = new ZipCodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid(" ", context), is(equalTo(true)));
	}

	@Test
	public void isValidReturnsTrueNotRequiredWhenAllDigitsAndStartsWith0() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		ZipCodeValidator validator = new ZipCodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("05555", context), is(equalTo(true)));
	}
	
	@Test
	public void isValidReturnsTrueWhenRequiredAndAllDigits() throws Exception {
		when(constraintAnnotation.required()).thenReturn(true);
		ZipCodeValidator validator = new ZipCodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid("05555", context), is(equalTo(true)));
	}
	
	@Test
	public void isValidReturnsFalseWhenRequiredAndEmpty() throws Exception {
		when(constraintAnnotation.required()).thenReturn(true);
		ZipCodeValidator validator = new ZipCodeValidator();
		validator.initialize(constraintAnnotation);
		assertThat(validator.isValid(" ", context), is(equalTo(false)));
	}

}
