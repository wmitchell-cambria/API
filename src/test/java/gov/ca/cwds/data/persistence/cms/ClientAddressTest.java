package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class ClientAddressTest {

  @Test
  public void shouldCreateClientAddressFromValues() {
    String id = "111";
    Short addressType = 0;
    String bkInmtId = "";
    Date effEndDt = new Date();
    Date effStartDt = new Date();
    String fkAddress = "123";
    String fkClient = "456";
    String homelessInd = "homeless";
    String fkReferral = "567";
    Address addresses = new Address();
    ClientAddress address = new ClientAddress(id, addressType, bkInmtId, effEndDt, effStartDt,
        fkAddress, fkClient, homelessInd, fkReferral, addresses);
    assertNotNull("Expected an addresss to be created", address);

    assertEquals("Expected id set in ctor", id, address.getId());
    assertEquals("Expected addressType set in ctor", addressType, address.getAddressType());
    assertEquals("Expected bkInmtId set in ctor", bkInmtId, address.getBkInmtId());
    assertEquals("Expected effEndDt set in ctor", effEndDt, address.getEffEndDt());
    assertEquals("Expected effStartDt set in ctor", effStartDt, address.getEffStartDt());
    assertEquals("Expected fkAddress set in ctor", fkAddress, address.getFkAddress());
    assertEquals("Expected fkClient set in ctor", fkClient, address.getFkClient());
    assertEquals("Expected homelessInd set in ctor", homelessInd, address.getHomelessInd());
    assertEquals("Expected fkReferral set in ctor", fkReferral, address.getFkReferral());
    assertEquals("Expected addresses set in ctor", addresses, address.getAddresses());
    assertEquals("Expected Client to be null", null, address.getClient());
    // assertEquals("Expected Referral to be null", null, address.getReferral());
  }

  @Test
  public void shouldCreateClientAddressFromValuesWithLastUpdatedId() {
    String id = "111";
    Short addressType = 0;
    String bkInmtId = "";
    Date effEndDt = new Date();
    Date effStartDt = new Date();
    String fkAddress = "123";
    String fkClient = "456";
    String homelessInd = "homeless";
    String fkReferral = "567";
    Address addresses = new Address();
    String lastUpdateId = "0X5";
    ClientAddress address = new ClientAddress(id, addressType, bkInmtId, effEndDt, effStartDt,
        fkAddress, fkClient, homelessInd, fkReferral, addresses, lastUpdateId);
    assertNotNull("Expected an addresss to be created", address);

    assertEquals("Expected id set in ctor", id, address.getId());
    assertEquals("Expected addressType set in ctor", addressType, address.getAddressType());
    assertEquals("Expected bkInmtId set in ctor", bkInmtId, address.getBkInmtId());
    assertEquals("Expected effEndDt set in ctor", effEndDt, address.getEffEndDt());
    assertEquals("Expected effStartDt set in ctor", effStartDt, address.getEffStartDt());
    assertEquals("Expected fkAddress set in ctor", fkAddress, address.getFkAddress());
    assertEquals("Expected fkClient set in ctor", fkClient, address.getFkClient());
    assertEquals("Expected homelessInd set in ctor", homelessInd, address.getHomelessInd());
    assertEquals("Expected fkReferral set in ctor", fkReferral, address.getFkReferral());
    assertEquals("Expected addresses set in ctor", addresses, address.getAddresses());
    assertEquals("Expected lastUpdateId set in ctor", lastUpdateId, address.getLastUpdatedId());
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ClientAddress.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    ClientAddress ca = new ClientAddress();
    assertTrue(ca.equals(ca));
  }

}
