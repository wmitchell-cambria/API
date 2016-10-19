package gov.ca.cwds.rest.services;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;

public class PersonServiceTest {
  private PersonService personService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    // personService = new PersonService();
  }

  /*
   * find tests
   */
  // @Test
  // public void findReturnsCorrectPersonWhenFoundWhenFound() throws Exception {
  // Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
  // Person expected = new Person("Bart", "Simpson", "M", "04/01/1990", "1234556789", address);
  //
  // Person found = (Person) personService.find(123L);
  //
  // assertThat(found, is(expected));
  // }
  //
  // @Test
  // public void findReturnsNullWhenNotFound() throws Exception {
  // Person found = (Person) personService.find("notfound");
  //
  // assertThat(found, is(nullValue()));
  // }
  //
  // /*
  // * create tests
  // */
  // @Test
  // public void createReturnsPersonCreatedOnCreate() throws Exception {
  // Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
  // Person toCreate = new Person("Bart", "Simpson", "M", "04/01/1990", "1234556789", address);
  // Response response = personService.create(toCreate);
  //
  // assertThat(response, is(notNullValue()));
  // assertThat(response.getClass(), is(PersonCreated.class));
  // }
  //
  // /*
  // * delete tests
  // */
  // @Test
  // public void deleteThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  // personService.delete("someid");
  // }
  //
  // /*
  // * update tests
  // */
  // @Test
  // public void updateThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  //
  // Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
  // Person toUpdate = new Person("Bart", "Simpson", "M", "04/01/1990", "1234556789", address);
  // personService.update(1L, toUpdate);
  // }

  /*
   * Oddness with cobertura cause the declaring class line to be not counted as run. This has to do
   * with bridge functions. To get our coverage numbers the "test" below calls the bridge functions
   * directly.
   */
  // @Test
  // public void callBridgeFunctions() throws Exception {
  // Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
  // Person person = new Person("Bart", "Simpson", "M", "04/01/1990", "1234556789", address);
  // Method create = PersonService.class.getMethod("create", DomainObject.class);
  // create.invoke(personService, person);
  // Assert.assertTrue(true);
  // }


}
