package gov.ca.cwds.rest.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.google.inject.Inject;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.data.legacy.cms.dao.NonCWSNumberDao;
import gov.ca.cwds.data.legacy.cms.dao.SafelySurrenderedBabiesDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectReferralDao;
import gov.ca.cwds.data.legacy.cms.entity.NonCWSNumber;
import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Special project services.
 * 
 * @author CWDS TPT Team
 */
public class SpecialProjectReferralService {

  private static final short MEDICAL_RECORD_SYSTEM_CODE_ID = 1331;

  @Inject
  private SpecialProjectDao specialProjectDao;

  @Inject
  private SpecialProjectReferralDao specialProjectReferralDao;

  @Inject
  private SafelySurrenderedBabiesDao safelySurrenderedBabiesDao;

  @Inject
  private NonCWSNumberDao nonCWSNumberDao;

  /**
   * Default no-argument constructor.
   */
  public SpecialProjectReferralService() {
    super();
  }

  /**
   * Process special project for Safely Surrendered Babies.
   * 
   * @param childClientId Child client ID.
   * @param referralId Referral ID
   * @param referralReceivedDate Referral Received Date
   * @param referralReceivedTime Referral Received Time
   * @param ssb Safely Surrendered Babies
   */
  public void processSafelySurrenderedBabies(String childClientId, String referralId,
      LocalDate referralReceivedDate, LocalTime referralReceivedTime,
      gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies ssb) {

    LocalDateTime now = LocalDateTime.now();

    PerryUserIdentity perryUser = RequestExecutionContext.instance().getUserIdentity();

    String staffId = RequestExecutionContext.instance().getStaffId();
    String staffCountySpecificCode = perryUser.getCountyCode(); // 2 gigit
    String staffCountyCode = perryUser.getCountyCwsCode(); // 4 digit

    /**
     * Find SSB special project for staff county
     */
    List<SpecialProject> ssbSpecialProjects =
        specialProjectDao.findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(
            Short.valueOf(staffCountyCode));
    SpecialProject ssbSpecialProject =
        (ssbSpecialProjects != null && !ssbSpecialProjects.isEmpty()) ? ssbSpecialProjects.get(0)
            : null;

    if (ssbSpecialProject == null) {
      throw new ServiceException(
          "Special project not found for Safely Surrendered Babies for staff county "
              + staffCountyCode);
    }

    /**
     * Create SpecialProjectReferral persistence record.
     */
    SpecialProjectReferral spr = new SpecialProjectReferral();
    spr.setCountySpecificCode(staffCountySpecificCode);
    spr.setId(CmsKeyIdGenerator.getNextValue(staffId));
    spr.setPartStartDate(referralReceivedDate);
    spr.setReferralId(referralId);
    spr.setSpecialProjectId(ssbSpecialProject.getId());
    spr.setSsbIndicator(Boolean.TRUE);
    spr.setLastUpdateId(staffId);
    spr.setLastUpdateTime(now);
    SpecialProjectReferral createdSpr = specialProjectReferralDao.create(spr);

    /**
     * Create SafelySurrenderedBabies persistence record.
     */
    SafelySurrenderedBabies ssbEntity = new SafelySurrenderedBabies();
    ssbEntity.setBraceletIdInfoCode(ssb.getBraceletInfoCode());
    ssbEntity.setChildClientId(childClientId);
    ssbEntity.setCommentDescription(ssb.getComments());
    ssbEntity.setCpsNotofiedIndicator(Boolean.TRUE);
    ssbEntity.setMedicalQuestionnaireCode(ssb.getMedicalQuestionaireCode());
    ssbEntity.setQuestionnaireReceivedDate(ssb.getMedicalQuestionaireReturnDate());
    ssbEntity.setCpsNotofiedDate(referralReceivedDate);
    ssbEntity.setCpsNotofiedTime(referralReceivedTime);
    ssbEntity.setLastUpdateId(staffId);
    ssbEntity.setLastUpdateTime(now);
    ssbEntity.setRelationToClient(ssb.getRelationToChild().shortValue());
    ssbEntity.setSpecialProjectReferral(createdSpr.getId());
    ssbEntity.setSurrenderedByName(ssb.getSurrenderedByName());
    ssbEntity.setSurrenderedDate(referralReceivedDate);
    ssbEntity.setSurrenderedTime(referralReceivedTime);
    safelySurrenderedBabiesDao.create(ssbEntity);

    /**
     * Create bracelet ID.
     */
    NonCWSNumber braceltInfo = new NonCWSNumber();
    braceltInfo.setClientId(childClientId);
    braceltInfo.setThirdId(CmsKeyIdGenerator.getNextValue(staffId));
    braceltInfo.setLastUpdateId(staffId);
    braceltInfo.setLastUpdateTime(now);
    braceltInfo.setOtherId(ssb.getBraceletId());
    braceltInfo.setOtherIdCode(MEDICAL_RECORD_SYSTEM_CODE_ID);
    nonCWSNumberDao.create(braceltInfo);
  }

  public SpecialProjectDao getSpecialProjectDao() {
    return specialProjectDao;
  }

  public void setSpecialProjectDao(SpecialProjectDao specialProjectDao) {
    this.specialProjectDao = specialProjectDao;
  }

  public SpecialProjectReferralDao getSpecialProjectReferralDao() {
    return specialProjectReferralDao;
  }

  public void setSpecialProjectReferralDao(SpecialProjectReferralDao specialProjectReferralDao) {
    this.specialProjectReferralDao = specialProjectReferralDao;
  }

  public SafelySurrenderedBabiesDao getSafelySurrenderedBabiesDao() {
    return safelySurrenderedBabiesDao;
  }

  public void setSafelySurrenderedBabiesDao(SafelySurrenderedBabiesDao safelySurrenderedBabiesDao) {
    this.safelySurrenderedBabiesDao = safelySurrenderedBabiesDao;
  }

  public NonCWSNumberDao getNonCWSNumberDao() {
    return nonCWSNumberDao;
  }

  public void setNonCWSNumberDao(NonCWSNumberDao nonCWSNumberDao) {
    this.nonCWSNumberDao = nonCWSNumberDao;
  }

}
