package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gov.ca.cwds.fixture.AddressIntakeApiResourceBuilder;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;

public class AddressesTest {
  private String id = "12345";
  private String streetAddress = "123 first street";
  private String city = "surf city";
  private String state = "CA";
  private String zip = "98765";
  private String type = "home";
  private String legacyId = "1234567ABC";
  private String legacySourceTable = "ADDRESS_T";

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Addresses.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testConstructor() throws Exception {
    Addresses addresses =
        new Addresses(id, streetAddress, city, state, zip, type, legacyId, legacySourceTable);
    assertThat(addresses.getStreetAddress(), is(equalTo(streetAddress)));
    assertThat(addresses.getCity(), is(equalTo(city)));
    assertThat(addresses.getState(), is(equalTo(state)));
    assertThat(addresses.getZip(), is(equalTo(zip)));
    assertThat(addresses.getType(), is(equalTo(type)));
    assertThat(addresses.getLegacyId(), is(equalTo(legacyId)));
    assertThat(addresses.getLegacySourceTable(), is(equalTo(legacySourceTable)));
  }

  @Test
  public void testConstructorUsingDomainObject() throws Exception {
    AddressIntakeApi persistent = new AddressIntakeApiResourceBuilder().build();
    Addresses addresses = new Addresses(persistent);
    assertThat(addresses.getStreetAddress(), is(equalTo(persistent.getStreetAddress())));
    assertThat(addresses.getCity(), is(equalTo(persistent.getCity())));
    assertThat(addresses.getState(), is(equalTo(persistent.getState())));
    assertThat(addresses.getZip(), is(equalTo(persistent.getZip())));
    assertThat(addresses.getType(), is(equalTo(persistent.getType())));
    assertThat(addresses.getLegacyId(), is(equalTo(persistent.getLegacyId())));
    assertThat(addresses.getLegacySourceTable(), is(equalTo(legacySourceTable)));
  }

  @Test
  public void testConstructorUsingDomainObjectWithId() throws Exception {
    AddressIntakeApi persistent = new AddressIntakeApiResourceBuilder().build();
    Addresses addresses = new Addresses(id, persistent);
    assertThat(addresses.getId(), is(equalTo(id)));
    assertThat(addresses.getPrimaryKey(), is(equalTo(id)));

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    Addresses addresses = new Addresses();
    assertTrue(addresses.equals(addresses));
  }

}
