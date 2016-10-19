package gov.ca.cwds.rest.services;

import org.hamcrest.junit.ExpectedException;
import org.junit.Rule;

public class AddressServiceTest {
  private AddressService addressService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  // @Before
  // public void setup() throws Exception {
  // addressService = new AddressService();
  // }
  //
  // /*
  // * find tests
  // */
  // @Test
  // public void findReturnsCorrectAddressWhenFoundWhenFound() throws Exception {
  // Address expected = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
  //
  // Address found = (Address) addressService.find("found");
  //
  // assertThat(found, is(expected));
  // }
  //
  // @Test
  // public void findReturnsNullWhenNotFound() throws Exception {
  // Address found = (Address) addressService.find("notfound");
  //
  // assertThat(found, is(nullValue()));
  // }
  //
  // /*
  // * create tests
  // */
  // @Test
  // public void createReturnsPrimaryKeyResponseOnCreate() throws Exception {
  // Address tocreate = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
  // Response response = addressService.create(tocreate);
  //
  // assertThat(response, is(notNullValue()));
  // assertThat(response.getClass(), is(PostedAddress.class));
  // }
  //
  // /*
  // * delete tests
  // */
  // @Test
  // public void deleteThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  // addressService.delete("someid");
  // }
  //
  // /*
  // * update tests
  // */
  // @Test
  // public void updateThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  //
  // addressService.update(1L, new Address("street", "city", "state", 95555));
  // }

  /*
   * Oddness with cobertura cause the declaring class line to be not counted as run. This has to do
   * with bridge functions. To get our coverage numbers the "test" below calls the bridge functions
   * directly.
   */
  // @Test
  // public void callBridgeFunctions() throws Exception {
  // Method create = AddressService.class.getMethod("create", DomainObject.class);
  // create.invoke(addressService, new Address("street", "city", "state", 95555));
  // Assert.assertTrue(true);
  // }

}
