package gov.ca.cwds.rest.services.referentialintegrity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a allegation record refers to a valid client and referral. Returns true if all
 * parent foreign keys exist when the transaction commits, otherwise false.
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
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKREFERL_T", nullable = false, updatable = false, insertable = false)
 * private Referral referral;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIAllegation implements ApiReferentialCheck<Allegation> {

  private static final String PERPETRATOR_CLIENT_ID_MISSING_ERROR =
      "Allegation => Perpetrator Client with given Identifier is not present in database";

  private static final String VICTIM_CLIENT_ID_MISSING_ERROR =
      "Allegation => Victim Client with given Identifier is not present in database";

  private static final String REFERRAL_ID_MISSING_ERROR =
      "Allegation => Referral with given Identifier is not present in database";

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RIAllegation.class);

  private transient ClientDao clientDao;
  private transient ReferralDao referralDao;

  /**
   * Constructor.
   * 
   * @param clientDao client DAO
   * @param referralDao referral DAO
   */
  @Inject
  public RIAllegation(final ClientDao clientDao, ReferralDao referralDao) {
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    ApiHibernateInterceptor.addHandler(Allegation.class, c -> apply((Allegation) c));
  }

  /**
   * Verifies that a allegation record refers to a valid client and referral. Returns true if all
   * parent foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(Allegation allegation) {
    LOGGER.debug("RI: Allegation");
    if (referralDao.find(allegation.getReferralId()) == null) {
      throw new ReferentialIntegrityException(REFERRAL_ID_MISSING_ERROR);

    } else if (clientDao.find(allegation.getVictimClientId()) == null) {
      throw new ReferentialIntegrityException(VICTIM_CLIENT_ID_MISSING_ERROR);

    } else if (StringUtils.isNotBlank(allegation.getPerpetratorClientId())
        && clientDao.find(allegation.getPerpetratorClientId()) == null) {
      throw new ReferentialIntegrityException(PERPETRATOR_CLIENT_ID_MISSING_ERROR);
    }
    return Boolean.TRUE;
  }
}
