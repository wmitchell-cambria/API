package gov.ca.cwds.rest.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.rest.services.ServiceException;

public class ServiceUtilsTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testExtractKeyValuePairsThrowsServiceExceptionWhenNotString() {
		thrown.expect(ServiceException.class);
		thrown.expectMessage("Unable to read nameValuePairs as string");
		ServiceUtils.extractKeyValuePairs(Boolean.TRUE);
	}
	
	@Test
	public void testExtractKeyValuePairsWorksOnValidString() {
		String valid = "abc=123,def=456,hig=789";
		Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(valid);
		assertThat(nameValuePairs.size(), is(equalTo(3)));
		assertThat(nameValuePairs.get("abc"), is(equalTo("123")));
		assertThat(nameValuePairs.get("def"), is(equalTo("456")));
		assertThat(nameValuePairs.get("hig"), is(equalTo("789")));
	}
	
	@Test
	public void testExtractKeyValuePairsWorksOnValidStringWithWhitespace() {
		String valid = " abc = 123 , def= 456,hig =789 ";
		Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(valid);
		assertThat(nameValuePairs.size(), is(equalTo(3)));
		assertThat(nameValuePairs.get("abc"), is(equalTo("123")));
		assertThat(nameValuePairs.get("def"), is(equalTo("456")));
		assertThat(nameValuePairs.get("hig"), is(equalTo("789")));
	}
	
	
	@Test
	public void testExtractKeyValuePairsThrowsExceptionOnInvalidString() {
		thrown.expect(ServiceException.class);
		thrown.expectMessage("Problem parsing name value pairs");
		String valid = "aljdfdkkkkk;ddfaa,";
		ServiceUtils.extractKeyValuePairs(valid);
	}
}
