package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.junit.Before;
import org.junit.Test;

public class DateValidatorTest {
	
	private final static String FORMAT = "yyyy-MM-dd";
	private final static String VALID = "1963-11-22";
	private final static String NOT_VALID = "11-22-1963";
	
	private final static Date constraintAnnotationRequired;
	private final static Date constraintAnnotationNotRequired;

	
	
	private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
	
	@Before
	public void setup() throws Exception {
		
	}

	@Test
	public void isValidReturnsTrueWhenRequiredAndValid() throws Exception {
		DateValidator validator = new DateValidator();
		validator.initialize(constraintAnnotationRequired);
		assertThat(validator.isValid("1962-11-22", context), is(equalTo(true)));
	}
	
	@Test
	public void isValidReturnsFalseWhenRequiredAndNotValid() throws Exception {
		DateValidator validator = new DateValidator();
		validator.initialize(constraintAnnotationRequired);
		assertThat(validator.isValid(NOT_VALID, context), is(equalTo(false)));
		
	}
	
	@Test 
	public void isValidReturnsTrueWhenNotRequiredAndValid() throws Exception {
		DateValidator validator = new DateValidator();
		validator.initialize(constraintAnnotationNotRequired);
		assertThat(validator.isValid(VALID, context), is(equalTo(true)));
		
	}
	
	@Test
	public void isValidReturnsFalseWhenNotRequiredAndNotValid() throws Exception {
		DateValidator validator = new DateValidator();
		validator.initialize(constraintAnnotationNotRequired);
		assertThat(validator.isValid(NOT_VALID, context), is(equalTo(false)));
		
	}
	
	public void isValidReturnsTrueWhenNotRequiredAndEmpty() throws Exception {
		DateValidator validator = new DateValidator();
		validator.initialize(constraintAnnotationRequired);
		assertThat(validator.isValid("", context), is(equalTo(true)));
	}

	/*
	 * static initialization
	 */
	static {
		constraintAnnotationRequired = new Date() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public boolean required() {
				return true;
			}
			
			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
			@Override
			public String message() {
				return null;
			}
			
			@Override
			public Class<?>[] groups() {
				return null;
			}
			
			@Override
			public String format() {
				return FORMAT;
			}
		};
		
		constraintAnnotationNotRequired = new Date() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public boolean required() {
				return false;
			}
			
			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
			@Override
			public String message() {
				return null;
			}
			
			@Override
			public Class<?>[] groups() {
				return null;
			}
			
			@Override
			public String format() {
				return FORMAT;
			}
		};
	}
}

