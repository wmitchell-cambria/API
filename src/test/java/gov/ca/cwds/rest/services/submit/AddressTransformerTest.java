package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.fixture.AddressIntakeApiResourceBuilder;
import gov.ca.cwds.fixture.LegacyDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AddressTransformerTest {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  @Test
  public void transformConvertsAddressIntakeApiToAddress() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder().setType("28")
        .setLegacyDescriptor(legacyDescriptor).build();

    Address expected = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace",
        "Springfield", 1828, "93838", 28, legacyDescriptor);

    Address actual = new AddressTransformer().transform(intakeApi);
    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsAddressIntakeApiToAddressWhenStateIsNull() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder().setType("28").setState(null)
        .setLegacyDescriptor(legacyDescriptor).build();

    Address expected = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace",
        "Springfield", null, "93838", 28, legacyDescriptor);

    Address actual = new AddressTransformer().transform(intakeApi);
    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsAddressIntakeApiToAddressWhenTypeIsNull() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder().setType(null)
        .setLegacyDescriptor(legacyDescriptor).build();

    Address expected = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace",
        "Springfield", 1828, "93838", null, legacyDescriptor);

    Address actual = new AddressTransformer().transform(intakeApi);
    assertEquals(actual, expected);
  }

}
