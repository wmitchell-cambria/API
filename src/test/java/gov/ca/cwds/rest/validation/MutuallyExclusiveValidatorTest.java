package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MutuallyExclusiveValidatorTest {
	private String abc;
	private String def;
	private String ghi;

	private MutuallyExclusive requiredConstraintAnnotation = mock(MutuallyExclusive.class);
	private MutuallyExclusive notRequiredConstraintAnnotation = mock(MutuallyExclusive.class);
	private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
	private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
	private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);
	
	private MutuallyExclusiveValidator validator = new MutuallyExclusiveValidator();

	private String[] properties = { "abc", "def", "ghi" };

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() throws Exception {
		when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
		when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
		
		when(requiredConstraintAnnotation.properties()).thenReturn(properties);
		when(notRequiredConstraintAnnotation.properties()).thenReturn(properties);
		
		when(notRequiredConstraintAnnotation.required()).thenReturn(false);
		when(requiredConstraintAnnotation.required()).thenReturn(true);
	}

	@Test
	public void validReturnsTrueWhenSingleValueSet() throws Exception {
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.abc = "abc";
		
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void validReturnsTrueWhenNoValueSetAndNotRequired() throws Exception {
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void validReturnsFalseWhenNoValueSetAndRequired() throws Exception {
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
		verify(context,times(1)).buildConstraintViolationWithTemplate(endsWith("must have one of their values set"));
	}
	
	@Test
	public void validReturnsFalseWhenMoreThan1ValueSet() throws Exception {
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.abc = "abc";
		bean.def = "def";

		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
		verify(context,times(1)).buildConstraintViolationWithTemplate(endsWith("are mutually exclusive but multiple values are set"));
	}

	@Test
	public void validThrowsExceptionWhenPropertyNotExistsInBean() throws Exception {
		thrown.expect(ValidationException.class);
		validator.initialize(notRequiredConstraintAnnotation);
		validator.isValid(new InvalidBean(), context);
	}

	public String getAbc() {
		return abc;
	}

	public String getDef() {
		return def;
	}

	public String getGhi() {
		return ghi;
	}

	protected class InvalidBean {
		public String jkl;
	}
}
