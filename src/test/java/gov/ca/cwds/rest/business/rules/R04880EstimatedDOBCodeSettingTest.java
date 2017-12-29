package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Client;
import java.util.Date;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * CWDS API Team
 */
public class R04880EstimatedDOBCodeSettingTest {

  @Test
  public void testNullDob() throws Exception {
    Client client = new Client();
    R04880EstimatedDOBCodeSetting rule = new R04880EstimatedDOBCodeSetting(client);
    rule.execute();
    assertEquals("U", client.getEstimatedDobCode());
  }

  @Test
  public void testNotNullDob() throws Exception {
    Client client = new Client();
    client.setBirthDate(new Date());
    R04880EstimatedDOBCodeSetting rule = new R04880EstimatedDOBCodeSetting(client);
    rule.execute();
    assertEquals("N", client.getEstimatedDobCode());

  }
}