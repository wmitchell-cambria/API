
package gov.ca.cwds.rest.services.referentialintegrity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a cross report record refers to a valid referral. Returns true if all parent
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
public class RICrossReport implements ApiReferentialCheck<CrossReport> {

  private static final String LAWENFORCEMENT_ID_MISSING_ERROR =
      "CrossReport => Law enforcement with given Identifier is not present in database";
  private static final String STAFFPERSON_ID_MISSING_ERROR =
      "CrossReport => Staff Person with given Identifier is not present in database";
  private static final String REFERRAL_ID_MISSING_ERROR =
      "CrossReport => Referral with given Identifier is not present in database";

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RICrossReport.class);

  private transient ReferralDao referralDao;
  private transient StaffPersonDao staffPersonDao;
  private transient LawEnforcementDao lawEnforcementDao;

  /**
   * Constructor.
   * 
   * @param referralDao referral DAO
   * @param staffPersonDao staffPerson DAO
   * @param lawEnforcementDao lawEnforcement DAO
   */
  @Inject
  public RICrossReport(final ReferralDao referralDao, StaffPersonDao staffPersonDao,
      LawEnforcementDao lawEnforcementDao) {
    this.referralDao = referralDao;
    this.staffPersonDao = staffPersonDao;
    this.lawEnforcementDao = lawEnforcementDao;
    ApiHibernateInterceptor.addHandler(CrossReport.class, c -> apply((CrossReport) c));
  }

  /**
   * Verifies that a cross report record refers to a valid referral. Returns true if all parent
   * foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(CrossReport crossReport) {
    LOGGER.debug("RI: CrossReport");
    if (referralDao.find(crossReport.getReferralId()) == null) {
      throw new ReferentialIntegrityException(REFERRAL_ID_MISSING_ERROR);
    } else if (staffPersonDao.find(crossReport.getStaffPersonId()) == null) {
      throw new ReferentialIntegrityException(STAFFPERSON_ID_MISSING_ERROR);
    } else if (StringUtils.isNotBlank(crossReport.getLawEnforcementId())
        && lawEnforcementDao.find(crossReport.getLawEnforcementId()) == null) {
      throw new ReferentialIntegrityException(LAWENFORCEMENT_ID_MISSING_ERROR);
    }
    return Boolean.TRUE;
  }

}
