package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.fixture.ClientCollateralResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.CollateralIndividualEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * 
 * @author CWDS API Team
 * 
 */
public class RIClientCollateralTest {

  private ClientDao clientDao;
  private CollateralIndividualDao collateralIndividualDao;

  /**
   * 
   */
  @Before
  public void setup() {
    clientDao = mock(ClientDao.class);
    collateralIndividualDao = mock(CollateralIndividualDao.class);
  }

  /**
   * @throws Exception on generic error
   */
  @Test
  public void type() throws Exception {
    assertThat(RIClientCollateral.class, notNullValue());
  }

  /**
   * @throws Exception- exception
   */
  @Test
  public void instantiation() throws Exception {
    RIClientCollateral target = new RIClientCollateral(clientDao, collateralIndividualDao);
    assertThat(target, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenClientNotFound() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientCollateral domainClientCollateral =
        new ClientCollateralResourceBuilder().buildClientCollateral();
    gov.ca.cwds.data.persistence.cms.ClientCollateral clientCollateral =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral("ABC1234567", domainClientCollateral,
            "0X5", new Date());

    when(clientDao.find(any(String.class))).thenReturn(null);
    RIClientCollateral target = new RIClientCollateral(clientDao, collateralIndividualDao);
    target.apply(clientCollateral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenCollateralIndividualNotFound() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientCollateral domainClientCollateral =
        new ClientCollateralResourceBuilder().buildClientCollateral();
    gov.ca.cwds.data.persistence.cms.ClientCollateral clientCollateral =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral("ABC1234567", domainClientCollateral,
            "0X5", new Date());

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC123456k", clientDomain, "0X5", new Date());

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(collateralIndividualDao.find(any(String.class))).thenReturn(null);
    RIClientCollateral target = new RIClientCollateral(clientDao, collateralIndividualDao);
    target.apply(clientCollateral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void riCheckPassWhenClientAndCollateralFound() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientCollateral domainClientCollateral =
        new ClientCollateralResourceBuilder().buildClientCollateral();
    gov.ca.cwds.data.persistence.cms.ClientCollateral clientCollateral =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral("ABC1234567", domainClientCollateral,
            "0X5", new Date());

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC123456k", clientDomain, "0X5", new Date());

    CollateralIndividual collateralIndividual = new CollateralIndividualEntityBuilder().build();

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(collateralIndividualDao.find(any(String.class))).thenReturn(collateralIndividual);
    RIClientCollateral target = new RIClientCollateral(clientDao, collateralIndividualDao);
    target.apply(clientCollateral);
  }

}
