package gov.ca.cwds.rest.services.referentialintegrity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a record refers to a valid referral, lawEnforcement and mandatedReporterDocument.
 * Returns true if all parent foreign keys exist when the transaction commits, otherwise false.
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
 * &#64;OneToOne(optional = false)
 * &#64;JoinColumn(name = "FKREFERL_T", nullable = true, updatable = false, insertable = false)
 * private Referral referral;
 * 
 * &#64;ManyToOne(optional = true)
 * &#64;JoinColumn(name = "FKLAW_ENFT", nullable = true, updatable = false, insertable = false)
 * private LawEnforcement lawEnforcement;
 * 
 * &#64;ManyToOne(optional = true)
 * &#64;JoinColumn(name = "FDBACK_DOC", nullable = true, updatable = false, insertable = false)
 * private DrmsDocument drmsDocument;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIReporter implements ApiReferentialCheck<Reporter> {

  private static final String DRMS_REPORTER_DOCUMENT_ID_MISSING_ERROR =
      "Reporter => DrmsReporterDocument with given Identifier is not present in database";
  private static final String LAWENFORCEMENT_ID_MISSING_ERROR =
      "Reporter => LawEnforcement with given Identifier is not present in database";
  private static final String REFERRAL_ID_MISSING_ERROR =
      "Reporter => Referral with given Identifier is not present in database";

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RIReporter.class);

  private transient ReferralDao referralDao;
  private transient LawEnforcementDao lawEnforcementDao;
  private transient DrmsDocumentDao drmsDocumentDao;

  /**
   * Constructor
   * 
   * @param referralDao - referralDao
   * @param lawEnforcementDao - lawEnforcementDao
   * @param drmsDocumentDao - drmsDocumentDao
   * 
   */
  @Inject
  public RIReporter(final ReferralDao referralDao, LawEnforcementDao lawEnforcementDao,
      DrmsDocumentDao drmsDocumentDao) {
    this.referralDao = referralDao;
    this.lawEnforcementDao = lawEnforcementDao;
    this.drmsDocumentDao = drmsDocumentDao;
    ApiHibernateInterceptor.addHandler(Reporter.class, reporter -> apply((Reporter) reporter));
  }

  @Override
  public Boolean apply(Reporter reporter) {
    String getLawEnforcementId = reporter.getLawEnforcementId();
    String getDrmsMandatedRprtrFeedback = reporter.getDrmsMandatedRprtrFeedback();
    LOGGER.debug("RI: Reporter");
    if (referralDao.find(reporter.getReferralId()) == null) {
      throw new ReferentialIntegrityException(REFERRAL_ID_MISSING_ERROR);
    } else {
      if (StringUtils.isNotBlank(getLawEnforcementId)
          && lawEnforcementDao.find(getLawEnforcementId) == null) {
        throw new ReferentialIntegrityException(LAWENFORCEMENT_ID_MISSING_ERROR);
      } else {
        if (StringUtils.isNotBlank(getDrmsMandatedRprtrFeedback)
            && drmsDocumentDao.find(getDrmsMandatedRprtrFeedback) == null) {
          throw new ReferentialIntegrityException(DRMS_REPORTER_DOCUMENT_ID_MISSING_ERROR);
        }
      }
    }
    return Boolean.TRUE;
  }

}
