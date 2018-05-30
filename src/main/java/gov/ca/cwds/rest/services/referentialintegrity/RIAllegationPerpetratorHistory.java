package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a record refers to a valid allegation and client. Returns true if all parent
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
 * &#64;ManyToOne(optional = true)
 * &#64;JoinColumn(name = "FKCLIENT_T", nullable = true, updatable = false, insertable = false)
 * private Client client;
 * 
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKALLGTN_T", nullable = false, updatable = false, insertable = false)
 * private Allegation allegation;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIAllegationPerpetratorHistory
    implements ApiReferentialCheck<AllegationPerpetratorHistory> {

  private static final String ALLEGATION_ID_MISSING_ERROR =
      "AllegationPerpetratorHistory => Allegation with given Identifier is not present in database";

  private static final String CLIENT_ID_MISSING_ERROR =
      "AllegationPerpetratorHistory => Client with given Identifier is not present in database";

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RIAllegationPerpetratorHistory.class);

  private transient ClientDao clientDao;
  private transient AllegationDao allegationDao;

  /**
   * Constructor
   * 
   * @param clientDao - clientDao
   * @param allegationDao - allegationDao
   */
  @Inject
  public RIAllegationPerpetratorHistory(final ClientDao clientDao, AllegationDao allegationDao) {
    this.clientDao = clientDao;
    this.allegationDao = allegationDao;
    ApiHibernateInterceptor.addHandler(AllegationPerpetratorHistory.class,
        allegationPerpetratorHistory -> apply(
            (AllegationPerpetratorHistory) allegationPerpetratorHistory));
  }

  /**
   * Verifies that a allegationPerpetratorHistory record refers to a valid client and allegation.
   * Returns true if all parent foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(AllegationPerpetratorHistory allegationPerpetratorHistory) {
    LOGGER.debug("RI: AllegationPerpetratorHistory");
    if (allegationPerpetratorHistory.getPerpetratorClientId() != null
        && clientDao.find(allegationPerpetratorHistory.getPerpetratorClientId()) == null) {
      throw new ReferentialIntegrityException(CLIENT_ID_MISSING_ERROR);
    } else if (allegationDao.find(allegationPerpetratorHistory.getAllegationId()) == null) {
      throw new ReferentialIntegrityException(ALLEGATION_ID_MISSING_ERROR);
    }
    return Boolean.TRUE;
  }
}
