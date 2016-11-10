package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ServiceRegistryTest {

  private ServiceRegistry registry;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    registry = mock(ServiceRegistry.class);
  }

  @Test
  public void instantiation() throws Exception {
    ServiceRegistry target = new ServiceRegistry();
    assertThat(target, notNullValue());
  }

  @Test
  public void register_A$Class$Object() throws Exception {
    // final PersonService personService =
    // new PersonService((PersonDao) DataAccessEnvironment.get(Person.class));
    // when(registry.get( ).thenReturn(new gov.ca.cwds.rest.api.persistence.ns.Address(
    // 1L, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700)));

    Class clazz = null;
    Service v = null;
    ServiceRegistry.register(clazz, v);
  }

  public void test_register_A$Class$Service() throws Exception {

    Class clazz = null;
    Service v = null;
    ServiceRegistry.register(clazz, v);
  }

}
