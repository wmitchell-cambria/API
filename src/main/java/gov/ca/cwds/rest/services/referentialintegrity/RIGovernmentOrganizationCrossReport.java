package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.GovernmentOrganizationCrossReportDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a record refers to a valid referral, crossreport and
 * governmentOrganizationCrossReport. Returns true if all parent foreign keys exist when the
 * transaction commits, otherwise false.
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
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKCRSS_RPT", nullable = false, updatable = false, insertable = false)
 * private Referral referral;
 * 
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKCRSS_RP0", nullable = false, updatable = false, insertable = false)
 * private CrossReport crossreport;
 * 
 * &#64;ManyToOne(optional = true)
 * &#64;JoinColumn(name = "FKGV_ORG_T", nullable = true, updatable = false, insertable = false)
 * private GovernmentOrganizationCrossReport governmentOrganizationCrossReportId;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIGovernmentOrganizationCrossReport
    implements ApiReferentialCheck<GovernmentOrganizationCrossReport> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RIGovernmentOrganizationCrossReport.class);

  private transient ReferralDao referralDao;
  private transient CrossReportDao crossReportDao;
  private transient GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao;


  /**
   * Constructor
   * 
   * @param referralDao - referralDao
   * @param crossReportDao - crossReportDao
   * @param governmentOrganizationCrossReportDao - governmentOrganizationCrossReportDao
   */
  @Inject
  public RIGovernmentOrganizationCrossReport(final ReferralDao referralDao,
      CrossReportDao crossReportDao,
      GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao) {
    this.referralDao = referralDao;
    this.crossReportDao = crossReportDao;
    this.governmentOrganizationCrossReportDao = governmentOrganizationCrossReportDao;
    ApiHibernateInterceptor.addHandler(GovernmentOrganizationCrossReport.class,
        governmentOrganizationCrossReport -> apply(
            (GovernmentOrganizationCrossReport) governmentOrganizationCrossReport));
  }

  @Override
  public Boolean apply(GovernmentOrganizationCrossReport t) {
    LOGGER.debug("RI: GovernmentOrganizationCrossReport");
    if (referralDao.find(t.getReferralId()) == null) {
      throw new ReferentialIntegrityException(
          "GovernmentOrganizationCrossReport => Referral with given Identifier is not present in database");
    } else if (crossReportDao.find(t.getThirdId()) == null) {
      throw new ReferentialIntegrityException(
          "GovernmentOrganizationCrossReport => CrossReport with given Identifier is not present in database");
    } else if (t.getGovernmentOrganizationId() != null
        && governmentOrganizationCrossReportDao.find(t.getGovernmentOrganizationId()) == null) {
      throw new ReferentialIntegrityException(
          "GovernmentOrganizationCrossReport => GovernmentOrganization with given Identifier is not present in database");
    }
    return Boolean.TRUE;
  }

}
