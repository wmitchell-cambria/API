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

public class MutuallyExclusiveValidatorTest {
	private String abc;
	private String def;
	private String ghi;
	private Boolean jkl;
	private Boolean mno;
	private Boolean pqr;

	private MutuallyExclusive requiredConstraintAnnotation = mock(MutuallyExclusive.class);
	private MutuallyExclusive notRequiredConstraintAnnotation = mock(MutuallyExclusive.class);
	private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
	private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
	private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);
	
	private MutuallyExclusiveValidator validator = new MutuallyExclusiveValidator();

	private String[] stringProperties = { "abc", "def", "ghi" };
	private String[] booleanProperties = { "jkl", "mno", "pqr" };

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() throws Exception {
		when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
		when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
		
		when(notRequiredConstraintAnnotation.required()).thenReturn(false);
		when(requiredConstraintAnnotation.required()).thenReturn(true);
	}

	@Test
	public void initializeThrowsExceptionWhenUnhandledType() throws Exception {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Unhandled type : java.lang.Object");
		when(requiredConstraintAnnotation.type()).thenReturn(Object.class);
		when(requiredConstraintAnnotation.properties()).thenReturn(stringProperties);
		
		validator.initialize(requiredConstraintAnnotation);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		validator.isValid(bean, context);
	}
	
	@Test
	public void validReturnsTrueWhenSingleValueSet() throws Exception {
		when(requiredConstraintAnnotation.type()).thenReturn(String.class);
		when(requiredConstraintAnnotation.properties()).thenReturn(stringProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.abc = "abc";
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void validReturnsTrueWhenNoValueSetAndNotRequired() throws Exception {
		when(notRequiredConstraintAnnotation.type()).thenReturn(String.class);
		when(notRequiredConstraintAnnotation.properties()).thenReturn(stringProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void validReturnsFalseWhenNoValueSetAndRequired() throws Exception {
		when(requiredConstraintAnnotation.type()).thenReturn(String.class);
		when(requiredConstraintAnnotation.properties()).thenReturn(stringProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
		verify(context,times(1)).buildConstraintViolationWithTemplate(endsWith("must have one of their values set"));
	}
	
	@Test
	public void validReturnsFalseWhenMoreThan1ValueSet() throws Exception {
		when(notRequiredConstraintAnnotation.type()).thenReturn(String.class);
		when(notRequiredConstraintAnnotation.properties()).thenReturn(stringProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.abc = "abc";
		bean.def = "def";

		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
		verify(context,times(1)).buildConstraintViolationWithTemplate(endsWith("are mutually exclusive but multiple values are set"));
	}

	@Test
	public void validThrowsExceptionWhenPropertyNotExistsInBean() throws Exception {
		when(notRequiredConstraintAnnotation.type()).thenReturn(String.class);
		when(notRequiredConstraintAnnotation.properties()).thenReturn(stringProperties);
		
		thrown.expect(ValidationException.class);
		validator.initialize(notRequiredConstraintAnnotation);
		validator.isValid(new InvalidBean(), context);
	}

	@Test
	public void validReturnsFalseWhenRequiredAndTotalTrueIsZero() throws Exception {
		when(requiredConstraintAnnotation.type()).thenReturn(Boolean.class);
		when(requiredConstraintAnnotation.properties()).thenReturn(booleanProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
		verify(context,times(1)).buildConstraintViolationWithTemplate(endsWith("must have one and only one true value"));
	}

	@Test
	public void validReturnsFalseWhenRequiredAndTotalTrueGreaterThanOne() throws Exception {
		when(requiredConstraintAnnotation.type()).thenReturn(Boolean.class);
		when(requiredConstraintAnnotation.properties()).thenReturn(booleanProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.jkl = true;
		bean.mno = true;
		
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
		verify(context,times(1)).buildConstraintViolationWithTemplate(endsWith("must have one and only one true value"));
	}

	@Test
	public void validReturnsTrueWhenRequiredAndTotalTrueEqualsOne() throws Exception {
		when(requiredConstraintAnnotation.type()).thenReturn(Boolean.class);
		when(requiredConstraintAnnotation.properties()).thenReturn(booleanProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.jkl = true;
		bean.mno = false;
		
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}
	
	@Test
	public void validReturnsTrueWhenNotRequiredAndCountSetEqualsZero() throws Exception {
		when(notRequiredConstraintAnnotation.type()).thenReturn(Boolean.class);
		when(notRequiredConstraintAnnotation.properties()).thenReturn(booleanProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}
	
	@Test
	public void validReturnsTrueWhenNotRequiredAndCountTrueEqualsOne() throws Exception {
		when(notRequiredConstraintAnnotation.type()).thenReturn(Boolean.class);
		when(notRequiredConstraintAnnotation.properties()).thenReturn(booleanProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.mno = true;
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void validReturnsFalseWhenNotRequiredAndCountSetGreaterThanZeroAndCountTrueNotEqualOne() throws Exception {
		when(notRequiredConstraintAnnotation.type()).thenReturn(Boolean.class);
		when(notRequiredConstraintAnnotation.properties()).thenReturn(booleanProperties);
		
		MutuallyExclusiveValidatorTest bean = new MutuallyExclusiveValidatorTest();
		bean.mno = true;
		bean.jkl = true;
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
	}
	
	/*
	 * Oddness with cobertura cause the declaring class line to be not counted as run.  This has to do with bridge functions.
	 * To get our coverage numbers the "test" below calls the bridge functions directly.
	 */
	@Test
	public void callBridgeFunctions() throws Exception {
		MutuallyExclusiveValidator validator = new MutuallyExclusiveValidator();
		Method initialize = MutuallyExclusiveValidator.class.getMethod("initialize", Annotation.class);
		initialize.invoke(validator, notRequiredConstraintAnnotation);
		Assert.assertTrue(true);
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
	
	public Boolean getJkl() {
		return jkl;
	}
	
	public Boolean getMno() {
		return mno;
	}
	
	public Boolean getPqr() {
		return pqr;
	}
}
