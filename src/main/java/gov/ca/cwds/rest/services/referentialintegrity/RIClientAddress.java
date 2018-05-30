package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a record refers to a valid address, client and referral. Returns true if all parent
 * foreign keys exist when the transaction commits, otherwise false.
 * 
 * <p>
 * Validate any other constraints or business rules here before committing a transaction to the
 * database.
 * </p>
 * 
 * <p>
 * Enforce foreign key constraints using "normal" Hibernate mechanisms, such as this typical FK:
 * </p>
 * <blockquote>
 * 
 * <pre>
 * &#64;ManyToOne(cascade = CascadeType.ALL, optional = false)
 * &#64;JoinColumn(name = "FKADDRS_T", nullable = false, insertable = false, updatable = false)
 * private Address addresses;
 * 
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
 * private Client client;
 * 
 * &#64;OneToOne(optional = true)
 * &#64;JoinColumn(name = "FKREFERL_T", nullable = true, updatable = false, insertable = false)
 * private Referral referral;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIClientAddress implements ApiReferentialCheck<ClientAddress> {

  private static final String REFERRAL_ID_MISSING_ERROR =
      "ClientAddress => Referral with given Identifier is not present in database";
  private static final String CLIENT_ID_MISSING_ERROR =
      "ClientAddress => Client with given Identifier is not present in database";
  private static final String ADDRESS_ID_MISSING_ERROR =
      "ClientAddress => Address with given Identifier is not present in database";

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RIClientAddress.class);

  private transient AddressDao addressDao;
  private transient ClientDao clientDao;
  private transient ReferralDao referralDao;

  /**
   * Constructor
   * 
   * @param addressDao - addressDao
   * @param clientDao - clientDao
   * @param referralDao - referralDao
   */
  @Inject
  public RIClientAddress(final AddressDao addressDao, ClientDao clientDao,
      ReferralDao referralDao) {
    this.addressDao = addressDao;
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    ApiHibernateInterceptor.addHandler(ClientAddress.class,
        clientAddress -> apply((ClientAddress) clientAddress));
  }

  @Override
  public Boolean apply(ClientAddress clientAddress) {
    LOGGER.debug("RI: ClientAddress");
    if (addressDao.find(clientAddress.getFkAddress()) == null) {
      throw new ReferentialIntegrityException(ADDRESS_ID_MISSING_ERROR);

    } else if (clientDao.find(clientAddress.getFkClient()) == null) {
      throw new ReferentialIntegrityException(CLIENT_ID_MISSING_ERROR);

    } else if (clientAddress.getFkReferral() != null
        && referralDao.find(clientAddress.getFkReferral()) == null) {
      throw new ReferentialIntegrityException(REFERRAL_ID_MISSING_ERROR);
    }
    return Boolean.TRUE;
  }

}
