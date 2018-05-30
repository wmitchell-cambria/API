package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a client collateral record refers to a valid client. Returns true if all parent
 * foreign keys exist when the transaction commits, otherwise false.
 * 
 * <p>
 * Validate any other "last ditch" constraints or business rules here before committing a
 * transaction to the database.
 * </p>
 * 
 * <p>
 * Enforce foreign key constraints using "normal" Hibernate mechanisms, such as this typical FK:
 * </p>
 * <blockquote>
 * 
 * <pre>
 * &#064;ManyToOne(optional = false)
 * &#064;JoinColumn(name = &quot;FKCLIENT_T&quot;, nullable = false, updatable = false, insertable = false)
 * private Client client;
 * &#064;ManyToOne(optional = false)
 * &#064;JoinColumn(name = &quot;FKCOLTRL_T&quot;, nullable = false, updatable = false, insertable = false)
 * private CollateralIndividual collateralIndividual;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIClientCollateral implements ApiReferentialCheck<ClientCollateral> {

  private static final String COLLATERAL_ID_MISSING_ERROR =
      "ClientCollateral => Collateral Individual with given Identifier is not present in database";
  private static final String CLIENT_ID_MISSING_ERROR =
      "ClientCollateral => Client with given Identifier is not present in database";

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RIClientCollateral.class);

  private transient ClientDao clientDao;
  private transient CollateralIndividualDao collateralIndividualDao;

  /**
   * Constructor.
   * 
   * @param clientDao client DAO
   * @param collateralIndividualDao collateral Individual DAO
   */
  @Inject
  public RIClientCollateral(final ClientDao clientDao,
      CollateralIndividualDao collateralIndividualDao) {
    this.clientDao = clientDao;
    this.collateralIndividualDao = collateralIndividualDao;
    ApiHibernateInterceptor.addHandler(ClientCollateral.class, c -> apply((ClientCollateral) c));
  }

  /**
   * Verifies that a client collateral record refers to a valid client and collateral Individual.
   * Returns true if all parent foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(ClientCollateral clientCollateral) {
    LOGGER.debug("RI: ClientCollateral");
    if (clientDao.find(clientCollateral.getClientId()) == null) {
      throw new ReferentialIntegrityException(CLIENT_ID_MISSING_ERROR);

    } else if (collateralIndividualDao.find(clientCollateral.getCollateralIndividualId()) == null) {
      throw new ReferentialIntegrityException(COLLATERAL_ID_MISSING_ERROR);
    }
    return Boolean.TRUE;
  }

}
