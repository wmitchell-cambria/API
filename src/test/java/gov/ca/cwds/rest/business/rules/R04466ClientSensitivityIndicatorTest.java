package gov.ca.cwds.rest.business.rules;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.CaseDao;
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
  private ReferralClientDao referralClientDao;

  @Before
  public void setup() throws Exception {
    caseDao = mock(CaseDao.class);
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

    when(caseDao.findAllRelatedByVictimClientId(any(String.class)))
        .thenReturn(createCases(LimitedAccessType.NONE, LimitedAccessType.NONE));

    when(referralClientDao.findByClientIds(any(Collection.class)))
        .thenReturn(createReferralClients(LimitedAccessType.SENSITIVE, LimitedAccessType.SEALED));

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

    when(caseDao.findAllRelatedByVictimClientId(any(String.class)))
        .thenReturn(createCases(LimitedAccessType.NONE, LimitedAccessType.NONE));

    when(referralClientDao.findByClientIds(any(Collection.class)))
        .thenReturn(createReferralClients(LimitedAccessType.SENSITIVE, LimitedAccessType.NONE));

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

    when(caseDao.findAllRelatedByVictimClientId(any(String.class)))
        .thenReturn(createCases(LimitedAccessType.SENSITIVE, LimitedAccessType.SEALED));

    when(referralClientDao.findByClientIds(any(Collection.class)))
        .thenReturn(createReferralClients(LimitedAccessType.SENSITIVE, LimitedAccessType.NONE));

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

    when(caseDao.findAllRelatedByVictimClientId(any(String.class)))
        .thenReturn(createCases(LimitedAccessType.SENSITIVE, LimitedAccessType.NONE));

    when(referralClientDao.findByClientIds(any(Collection.class)))
        .thenReturn(createReferralClients(LimitedAccessType.NONE, LimitedAccessType.NONE));

    R04466ClientSensitivityIndicator r04466ClientSensitivityIndicator =
        new R04466ClientSensitivityIndicator(client, LimitedAccessType.SENSITIVE, caseDao,
            referralClientDao);
    r04466ClientSensitivityIndicator.execute();

    Assert.assertEquals(LimitedAccessType.SENSITIVE.getValue(), client.getSensitivityIndicator());
  }

  private CmsCase[] createCases(LimitedAccessType firstCaseLimitedAccessCode,
      LimitedAccessType secondCaseLimitedAccessCode) {
    CmsCase[] cmsCases = {new CmsCase(), new CmsCase()};
    cmsCases[0].setLimitedAccessCode(firstCaseLimitedAccessCode.getValue());
    cmsCases[1].setLimitedAccessCode(secondCaseLimitedAccessCode.getValue());
    return cmsCases;
  }

  private ReferralClient[] createReferralClients(LimitedAccessType firstReferralLimitedAccessCode,
      LimitedAccessType secondReferralLimitedAccessCode) {
    ReferralClient[] referralClients = {new ReferralClient(), new ReferralClient()};
    referralClients[0].setReferral(new Referral());
    referralClients[0].getReferral()
        .setLimitedAccessCode(firstReferralLimitedAccessCode.getValue());
    referralClients[1].setReferral(new Referral());
    referralClients[1].getReferral()
        .setLimitedAccessCode(secondReferralLimitedAccessCode.getValue());
    return referralClients;
  }

}
