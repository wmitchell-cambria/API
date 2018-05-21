package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
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

  private Map<String, IntakeLov> nsCodeToNsLovMap;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    IntakeLov intakeLovStateCa = mock(IntakeLov.class);
    when(intakeLovStateCa.getLegacySystemCodeId()).thenReturn(new Long(1828));
    nsCodeToNsLovMap = new HashMap<String, IntakeLov>();
    nsCodeToNsLovMap.put("CA", intakeLovStateCa);
  }

  @Test
  public void transformConvertsAddressIntakeApiToAddress() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder().setType("28")
        .setLegacyDescriptor(legacyDescriptor).build();

    Address expected = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace",
        "Springfield", 1828, "93838", 28, legacyDescriptor);

    Address actual = new AddressTransformer().transform(intakeApi, nsCodeToNsLovMap);
    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsAddressIntakeApiToAddressWhenStateIsNull() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder().setType("28").setState(null)
        .setLegacyDescriptor(legacyDescriptor).build();

    Address expected = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace",
        "Springfield", null, "93838", 28, legacyDescriptor);

    Address actual = new AddressTransformer().transform(intakeApi, nsCodeToNsLovMap);
    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsAddressIntakeApiToAddressWhenTypeIsNull() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi intakeApi = new AddressIntakeApiResourceBuilder().setType(null)
        .setLegacyDescriptor(legacyDescriptor).build();

    Address expected = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace",
        "Springfield", 1828, "93838", null, legacyDescriptor);

    Address actual = new AddressTransformer().transform(intakeApi, nsCodeToNsLovMap);
    assertEquals(actual, expected);
  }

}
