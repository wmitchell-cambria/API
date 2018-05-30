
package gov.ca.cwds.rest.services.referentialintegrity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDaoImpl;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a referral record refers to a valid referral. Returns true if all parent foreign
 * keys exist when the transaction commits, otherwise false.
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
public class RIReferral implements ApiReferentialCheck<Referral> {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RIReferral.class);

  private transient AddressDao addressDao;
  private transient StaffPersonDao staffPersonDao;
  private transient DrmsDocumentDao drmsDocumentDao;
  private transient LongTextDaoImpl longTextDao;
  private transient ReferralDao referralDao;

  private static final String STAFF_PERSON_ID_MISSING_ERROR =
      "Referral => Staff Person with given Identifier is not present in database";
  private static final String FIRST_RESPONSE_ID_MISSING_ERROR =
      "Referral => First Response Determined Staff Person with given Identifier is not present in database";
  private static final String ADDRESS_ID_MISSING_ERROR =
      "Referral => Address with given Identifier is not present in database";
  private static final String PRIMARY_REFERRAL_ID_MISSING_ERROR =
      "Referral => LinkToPrimaryReferralId with given Identifier is not present in database";
  private static final String DRMS_ALLEGATION_DOC_ID_MISSING_ERROR =
      "Referral => Drms Allegation DescriptionDoc with given Identifier is not present in database";
  private static final String DRMS_REFFERAL_DOC_MISSING_ERROR =
      "Referral => Drms Referral Doc with given Identifier is not present in database";
  private static final String DRMS_INVESTIGATION_DOC_MISSING_ERROR =
      "Referral => Drms Investigation Doc with given Identifier is not present in database";
  private static final String CHILDREN_ID_MISSING_ERROR =
      "Referral => Current Location Of Children with given Identifier is not present in database";
  private static final String RATIONAL_TEXT_ID_MISSING_ERROR =
      "Referral => Response Rationale Text with given Identifier is not present in database";
  private static final String NOTE_TEXT_ID_MISSING_ERROR =
      "Referral => Screener Note Text with given Identifier is not present in database";

  /**
   * Constructor.
   * 
   * @param addressDao address DAO
   * @param staffPersonDao staffPerson DAO
   * @param drmsDocumentDao drmsDocument DAO
   * @param longTextDao longText DAO
   * @param referralDao referral DAO
   */
  @Inject
  public RIReferral(final AddressDao addressDao, StaffPersonDao staffPersonDao,
      DrmsDocumentDao drmsDocumentDao, LongTextDaoImpl longTextDao, ReferralDao referralDao) {
    this.addressDao = addressDao;
    this.staffPersonDao = staffPersonDao;
    this.drmsDocumentDao = drmsDocumentDao;
    this.longTextDao = longTextDao;
    this.referralDao = referralDao;
    ApiHibernateInterceptor.addHandler(Referral.class, c -> apply((Referral) c));
  }

  /**
   * Verifies that a referral record refers to a valid referral. Returns true if all parent foreign
   * keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(Referral referral) {
    LOGGER.debug("RI: Referral");
    checkStaffPersonReferentialIntegrity(referral);
    checkAddressReferentialIntegrity(referral);
    checkReferralReferentialIntegrity(referral);
    checkDocumentReferentialIntegrity(referral);
    checkLongTextReferentialIntegrity(referral);

    return Boolean.TRUE;
  }

  private void checkStaffPersonReferentialIntegrity(Referral referral) {
    if (staffPersonDao.find(referral.getPrimaryContactStaffPersonId()) == null) {
      throw new ReferentialIntegrityException(STAFF_PERSON_ID_MISSING_ERROR);
    } else if (StringUtils.isNotBlank(referral.getFirstResponseDeterminedByStaffPersonId())
        && staffPersonDao.find(referral.getFirstResponseDeterminedByStaffPersonId()) == null) {
      throw new ReferentialIntegrityException(FIRST_RESPONSE_ID_MISSING_ERROR);
    }
  }

  private void checkAddressReferentialIntegrity(Referral referral) {
    if (StringUtils.isNotBlank(referral.getAllegesAbuseOccurredAtAddressId())
        && addressDao.find(referral.getAllegesAbuseOccurredAtAddressId()) == null) {
      throw new ReferentialIntegrityException(ADDRESS_ID_MISSING_ERROR);
    }
  }

  private void checkReferralReferentialIntegrity(Referral referral) {
    if (StringUtils.isNotBlank(referral.getLinkToPrimaryReferralId())
        && referralDao.find(referral.getLinkToPrimaryReferralId()) == null) {
      throw new ReferentialIntegrityException(PRIMARY_REFERRAL_ID_MISSING_ERROR);
    }
  }

  private void checkDocumentReferentialIntegrity(Referral referral) {
    if (StringUtils.isNotBlank(referral.getDrmsAllegationDescriptionDoc())
        && drmsDocumentDao.find(referral.getDrmsAllegationDescriptionDoc()) == null) {
      throw new ReferentialIntegrityException(DRMS_ALLEGATION_DOC_ID_MISSING_ERROR);
    } else if (StringUtils.isNotBlank(referral.getDrmsErReferralDoc())
        && drmsDocumentDao.find(referral.getDrmsErReferralDoc()) == null) {
      throw new ReferentialIntegrityException(DRMS_REFFERAL_DOC_MISSING_ERROR);
    } else if (StringUtils.isNotBlank(referral.getDrmsInvestigationDoc())
        && drmsDocumentDao.find(referral.getDrmsInvestigationDoc()) == null) {
      throw new ReferentialIntegrityException(DRMS_INVESTIGATION_DOC_MISSING_ERROR);
    }
  }

  private void checkLongTextReferentialIntegrity(Referral referral) {
    if (StringUtils.isNotBlank(referral.getCurrentLocationOfChildren())
        && longTextDao.find(referral.getCurrentLocationOfChildren()) == null) {
      throw new ReferentialIntegrityException(CHILDREN_ID_MISSING_ERROR);
    } else if (StringUtils.isNotBlank(referral.getResponseRationaleText())
        && longTextDao.find(referral.getResponseRationaleText()) == null) {
      throw new ReferentialIntegrityException(RATIONAL_TEXT_ID_MISSING_ERROR);
    } else if (StringUtils.isNotBlank(referral.getScreenerNoteText())
        && longTextDao.find(referral.getScreenerNoteText()) == null) {
      throw new ReferentialIntegrityException(NOTE_TEXT_ID_MISSING_ERROR);
    }
  }
}
