package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class ReferralClientTest {

	/*
	 * Constructor test
	 */
	@Test
	public void emtpyConstructorIsNotNull() throws Exception {
		assertThat(ReferralClient.class.newInstance(), is(notNullValue()));
	}

}
