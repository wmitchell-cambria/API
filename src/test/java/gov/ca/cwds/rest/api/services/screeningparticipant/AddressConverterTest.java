package gov.ca.cwds.rest.api.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.cms.Client;
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

}
