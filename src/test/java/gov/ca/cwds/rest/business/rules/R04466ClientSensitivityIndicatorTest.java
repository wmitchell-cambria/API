package gov.ca.cwds.rest.business.rules;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
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
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SENSITIVE.getValue());
    Client client = clientResourceBuilder.build();

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SEALED, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SENSITIVE.getValue(), client.getSensitivityIndicator());
  }

  @Test
  public void testSoc158SealedClientIndicatorFalse() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSoc158SealedClientIndicator(false);
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SENSITIVE.getValue());
    Client client = clientResourceBuilder.build();

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SEALED, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SEALED.getValue(), client.getSensitivityIndicator());
  }

  @Test
  public void testNewVictimWithNewSensitiveReferral() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SEALED.getValue());
    Client client = clientResourceBuilder.build();

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SENSITIVE, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SENSITIVE.getValue(), client.getSensitivityIndicator());
  }

  @Test
  public void testNewVictimWithNewSealedReferral() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SENSITIVE.getValue());
    Client client = clientResourceBuilder.build();

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SEALED, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SEALED.getValue(), client.getSensitivityIndicator());
  }

  @Test
  public void testExistingVictimWithExistingSealedReferral() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SENSITIVE.getValue());
    clientResourceBuilder.setExistingClientId("1111111111");
    Client client = clientResourceBuilder.build();

    CmsCase[] cmsCases = {new CmsCase(), new CmsCase()};
    cmsCases[0].setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    cmsCases[1].setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    when(caseDao.findAllRelatedByVictimClientId(any(String.class))).thenReturn(cmsCases);

    ReferralClient[] referralClients = {new ReferralClient(), new ReferralClient()};
    referralClients[0].setReferral(new Referral());
    referralClients[0].getReferral().setLimitedAccessCode(LimitedAccessType.SENSITIVE.getValue());
    referralClients[1].setReferral(new Referral());
    referralClients[1].getReferral().setLimitedAccessCode(LimitedAccessType.SEALED.getValue());
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SENSITIVE, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SEALED.getValue(), client.getSensitivityIndicator());
  }

  @Test
  public void testExistingVictimWithExistingSensitiveReferral() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SEALED.getValue());
    clientResourceBuilder.setExistingClientId("1111111111");
    Client client = clientResourceBuilder.build();

    CmsCase[] cmsCases = {new CmsCase(), new CmsCase()};
    cmsCases[0].setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    cmsCases[1].setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    when(caseDao.findAllRelatedByVictimClientId(any(String.class))).thenReturn(cmsCases);

    ReferralClient[] referralClients = {new ReferralClient(), new ReferralClient()};
    referralClients[0].setReferral(new Referral());
    referralClients[0].getReferral().setLimitedAccessCode(LimitedAccessType.SENSITIVE.getValue());
    referralClients[1].setReferral(new Referral());
    referralClients[1].getReferral().setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SENSITIVE, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SENSITIVE.getValue(), client.getSensitivityIndicator());
  }

  @Test
  public void testExistingVictimWithExistingSealedCase() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SENSITIVE.getValue());
    clientResourceBuilder.setExistingClientId("1111111111");
    Client client = clientResourceBuilder.build();

    CmsCase[] cmsCases = {new CmsCase(), new CmsCase()};
    cmsCases[0].setLimitedAccessCode(LimitedAccessType.SENSITIVE.getValue());
    cmsCases[1].setLimitedAccessCode(LimitedAccessType.SEALED.getValue());
    when(caseDao.findAllRelatedByVictimClientId(any(String.class))).thenReturn(cmsCases);

    ReferralClient[] referralClients = {new ReferralClient(), new ReferralClient()};
    referralClients[0].setReferral(new Referral());
    referralClients[0].getReferral().setLimitedAccessCode(LimitedAccessType.SENSITIVE.getValue());
    referralClients[1].setReferral(new Referral());
    referralClients[1].getReferral().setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SENSITIVE, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SEALED.getValue(), client.getSensitivityIndicator());
  }

  @Test
  public void testExistingVictimWithExistingSensitiveCase() {
    ClientResourceBuilder clientResourceBuilder = new ClientResourceBuilder();
    clientResourceBuilder.setSensitivityIndicator(LimitedAccessType.SEALED.getValue());
    clientResourceBuilder.setExistingClientId("1111111111");
    Client client = clientResourceBuilder.build();

    CmsCase[] cmsCases = {new CmsCase(), new CmsCase()};
    cmsCases[0].setLimitedAccessCode(LimitedAccessType.SENSITIVE.getValue());
    cmsCases[1].setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    when(caseDao.findAllRelatedByVictimClientId(any(String.class))).thenReturn(cmsCases);

    ReferralClient[] referralClients = {new ReferralClient(), new ReferralClient()};
    referralClients[0].setReferral(new Referral());
    referralClients[0].getReferral().setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    referralClients[1].setReferral(new Referral());
    referralClients[1].getReferral().setLimitedAccessCode(LimitedAccessType.NONE.getValue());
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SENSITIVE, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SENSITIVE.getValue(), client.getSensitivityIndicator());
  }


}
