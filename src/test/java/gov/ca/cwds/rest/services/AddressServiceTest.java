package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.apache.commons.lang3.NotImplementedException;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.Address;

public class AddressServiceTest {
	private AddressService addressService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() throws Exception {
		addressService = new AddressService();
	}

	/*
	 * find tests
	 */
	@Test 
	public void findReturnsCorrectAddressWhenFoundWhenFound() throws Exception {
		Address expected = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
		
		Address found = addressService.find("found");
		
		assertThat(found, is(expected));
	}

	@Test 
	public void findReturnsNullWhenNotFound() throws Exception {
		Address found = addressService.find("notfound");
		
		assertThat(found, is(nullValue()));
	}

	/*
	 * create tests
	 */
	@Test
	public void createReturnsIdOnCreate() throws Exception {
		Address tocreate = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
		String id = (String)addressService.create(tocreate);
		
		assertThat(id, is(notNullValue()));
	}
	
	/*
	 * delete tests
	 */
	@Test
	public void deleteThrowsNotImplementedException() throws Exception {
		thrown.expect(NotImplementedException.class);
		addressService.delete("someid");
	}
	
	/*
	 * update tests
	 */
	@Test
	public void updateThrowsNotImplementedException() throws Exception {
		thrown.expect(NotImplementedException.class);
		
		addressService.update(new Address("street", "city", "state", 95555));
	}

}
