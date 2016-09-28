package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;

import org.junit.Before;
import org.junit.Test;

public class OnlyIfValidatorTest {
	private String abc;
	private String def;

	private OnlyIf constraintAnnotation = mock(OnlyIf.class);
	private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
	
	private OnlyIfValidator validator = new OnlyIfValidator();

	@Before
	public void setup() throws Exception {
		when(constraintAnnotation.ifProperty()).thenReturn("abc");
		when(constraintAnnotation.property()).thenReturn("def");
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
	}

	public String getAbc() {
		return abc;
	}

	public String getDef() {
		return def;
	}
}
