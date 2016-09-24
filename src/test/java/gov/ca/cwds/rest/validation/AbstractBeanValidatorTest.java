package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.hamcrest.junit.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

public class AbstractBeanValidatorTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private AbstractBeanValidator abstractBeanValidator = new AbstractBeanValidator() {};
	
	private String property = "somevalue";
	
	
	@Test
	public void readBeanValueReturnsCorrectValue() throws Exception {
		assertThat(abstractBeanValidator.readBeanValue(this, "property"), is(equalTo("somevalue")));
	}
	
	@Test
	public void readBeanValueThrowsValidationExceptionWhenReflectionIssue() throws Exception {
		thrown.expect(ValidationException.class);
		
		abstractBeanValidator.readBeanValue(this, "foobar");
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}
	
	
}
