package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import gov.ca.cwds.rest.business.rules.R00785AllegationClientRestriction;

/**
 * Test cases for R - 00785 Client Restriction.
 * 
 * @author CWDS API Team
 *
 */
public class R00785AllegationClientRestrictionTest {

  @Test
  public void testValidVictimAndPerpetratorClientId() {
    String victimClientId = "8769";
    String perpetratorClientId = "34567";
    Boolean retValue =
        new R00785AllegationClientRestriction(victimClientId, perpetratorClientId).isValid();
    assertThat(retValue, is(equalTo(Boolean.TRUE)));

  }

  @Test
  public void testValidVictimAndPerpetratorClientIdIsNull() {
    String victimClientId = "8769";
    String perpetratorClientId = null;
    Boolean retValue =
        new R00785AllegationClientRestriction(victimClientId, perpetratorClientId).isValid();
    assertThat(retValue, is(equalTo(Boolean.TRUE)));

  }

  @Test
  public void testInValidVictimAndPerpetratorValuesAreSame() {
    String victimClientId = "8769";
    String perpetratorClientId = "8769";
    Boolean retValue =
        new R00785AllegationClientRestriction(victimClientId, perpetratorClientId).isValid();
    assertThat(retValue, is(equalTo(Boolean.FALSE)));

  }

}
