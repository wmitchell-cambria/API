package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * CWDS API Team
 */
public class R04966NamesMustHaveAtLeastOneAlphaCharTest {
  @Test
  public void testValid() {
    Client client = new ClientEntityBuilder().
        setCommonFirstName(null).
        setCommonLastName(null).
        setCommonMiddleName("1a2").
        build();
    assertTrue(new R04966NamesMustHaveAtLeastOneAlphaChar(client).isValid());
  }

  @Test
  public void testInvalid() {
    Client client = new ClientEntityBuilder().
        setCommonFirstName("1").
        setCommonLastName("22").
        setCommonMiddleName("333").
        build();
    assertFalse(new R04966NamesMustHaveAtLeastOneAlphaChar(client).isValid());
  }

}