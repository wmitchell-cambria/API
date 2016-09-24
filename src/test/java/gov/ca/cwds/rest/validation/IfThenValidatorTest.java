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

import org.hamcrest.junit.ExpectedException;
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
	public void isValidReturnsFalseWhenNotRequiredAndIfPropertySetButNotThenProperty() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);	
		validator.initialize(constraintAnnotation);
		IfThenValidatorTest bean = new IfThenValidatorTest();
		bean.abc = "value";
		
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
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
	public void isValidReturnsTrueWhenNotRequiredAndIfPropertyNotSetAndThenPropertyIs() throws Exception {
		when(constraintAnnotation.required()).thenReturn(false);
		validator.initialize(constraintAnnotation);
		IfThenValidatorTest bean = new IfThenValidatorTest();
		bean.def = "value";
		
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void isValidReturnsFalseWhenRequiredAndIfPropertyNotSetAndThenPropertyIs() throws Exception {
		when(constraintAnnotation.required()).thenReturn(true);
		validator.initialize(constraintAnnotation);
		IfThenValidatorTest bean = new IfThenValidatorTest();
		bean.def = "value";
		
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
	}

	@Test
	public void validThrowsExceptionWhenPropertyNotExistsInBean() throws Exception {
		thrown.expect(ValidationException.class);
		validator.initialize(constraintAnnotation);
		validator.isValid(new Object(), context);
	}
	
	public String getAbc() {
		return abc;
	}

	public String getDef() {
		return def;
	}
}
