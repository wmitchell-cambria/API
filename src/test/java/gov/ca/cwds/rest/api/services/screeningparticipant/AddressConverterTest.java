package gov.ca.cwds.rest.api.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.fixture.AddressEntityBuilder;
import gov.ca.cwds.fixture.ClientAddressEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.services.screeningparticipant.AddressConverter;

/**
 * @author CWDS API Team
 *
 */
public class AddressConverterTest {

  private AddressConverter addressConverter = new AddressConverter();

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  /**
   * 
   */
  @Test
  public void testConvertIsNotNull() {
    Client client = new ClientEntityBuilder().build();
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);
    assertThat(addressIntakeApis, is(notNullValue()));
  }

  /**
   * 
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testConvertToAddressIntakeApi() {
    Address address = new AddressEntityBuilder().build();
    ClientAddress clientAddress = new ClientAddressEntityBuilder().setAddressType((short) 32)
        .setEffEndDt(null).setAddresses(address).buildClientAddress();
    Set<ClientAddress> clientAddresses = new HashSet<>(Arrays.asList(clientAddress));
    Client client = new ClientEntityBuilder().setClientAddress(clientAddresses).build();
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);
    assertThat(addressIntakeApis, containsInAnyOrder(hasProperty("city", is("Sacramento"))));
    assertThat(addressIntakeApis, containsInAnyOrder(hasProperty("type", is("Residence"))));
  }

}
