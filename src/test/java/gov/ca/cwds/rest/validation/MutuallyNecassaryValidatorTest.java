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

public class MutuallyNecassaryValidatorTest {
	private String abc;
	private String def;
	private String ghi;

	private MutuallyNecassary requiredConstraintAnnotation = mock(MutuallyNecassary.class);
	private MutuallyNecassary notRequiredConstraintAnnotation = mock(MutuallyNecassary.class);
	private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
	private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
	private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);
	
	private MutuallyNecassaryValidator validator = new MutuallyNecassaryValidator();

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
	public void validReturnsTrueWhenRequiredAndAllSet() throws Exception {
		MutuallyNecassaryValidatorTest bean = new MutuallyNecassaryValidatorTest();
		bean.abc = "abc";
		bean.def = "def";
		bean.ghi = "ghi";
		
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void validReturnsFalseWhenRequiredAndNotAllSet() throws Exception {
		MutuallyNecassaryValidatorTest bean = new MutuallyNecassaryValidatorTest();
		bean.abc = "abc";
		
		validator.initialize(requiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
	}

	@Test
	public void validReturnsTrueWhenNotRequiredAndAllSet() throws Exception {
		MutuallyNecassaryValidatorTest bean = new MutuallyNecassaryValidatorTest();
		bean.abc = "abc";
		bean.def = "def";
		bean.ghi = "ghi";
		
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}
	
	@Test
	public void validReturnsTrueWhenNotRequiredAndNoneSet() throws Exception {
		MutuallyNecassaryValidatorTest bean = new MutuallyNecassaryValidatorTest();
		
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(true)));
	}

	@Test
	public void validReturnsFalseWhenNotRequiredAndSomeButNotAllSet() throws Exception {
		MutuallyNecassaryValidatorTest bean = new MutuallyNecassaryValidatorTest();
		bean.abc = "abc";
		
		validator.initialize(notRequiredConstraintAnnotation);
		assertThat(validator.isValid(bean, context), is(equalTo(false)));
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
