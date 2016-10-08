package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class ReporterTest {

	/*
	 * Constructor test
	 */
	@Test
	public void emtpyConstructorIsNotNull() throws Exception {
		assertThat(Reporter.class.newInstance(), is(notNullValue()));
	}

}
