package gov.ca.cwds.rest.business.rules;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.Client;

/**
 * Tests for R04466ClientSensitivityIndicator
 * 
 * @author CWDS API Team
 */
public class R04466ClientSensitivityIndicatorTest {

  private CaseDao caseDao;
  private ClientRelationshipDao clientRelationshipDao;
  private ReferralClientDao referralClientDao;

  @Before
  public void setup() throws Exception {
    caseDao = mock(CaseDao.class);
    clientRelationshipDao = mock(ClientRelationshipDao.class);
    referralClientDao = mock(ReferralClientDao.class);
  }

  @Test
  public void testSoc158SealedClientIndicatorTrue() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSoc158SealedClientIndicator(true);
    clientResourceBuilder.setSensitivityIndicator("S");
    Client client = clientResourceBuilder.build();

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SEALED, caseDao,
            clientRelationshipDao, referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals("S", client.getSensitivityIndicator());
  }

  @Test
  public void testSoc158SealedClientIndicatorFalse() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSoc158SealedClientIndicator(false);
    clientResourceBuilder.setSensitivityIndicator("S");
    Client client = clientResourceBuilder.build();

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SEALED, caseDao,
            clientRelationshipDao, referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals("R", client.getSensitivityIndicator());
  }

  @Test
  public void testClientAlreadyAtHighestLevel() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSoc158SealedClientIndicator(false);
    clientResourceBuilder.setSensitivityIndicator("R");
    Client client = clientResourceBuilder.build();

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SEALED, caseDao,
            clientRelationshipDao, referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals("R", client.getSensitivityIndicator());
  }
}
