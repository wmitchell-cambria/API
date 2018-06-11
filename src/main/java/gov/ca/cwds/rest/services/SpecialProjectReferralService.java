package gov.ca.cwds.rest.services;

import java.time.LocalDateTime;
import java.util.List;

import com.google.inject.Inject;

import gov.ca.cwds.data.legacy.cms.dao.NonCWSNumberDao;
import gov.ca.cwds.data.legacy.cms.dao.SafelySurrenderedBabiesDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectReferralDao;
import gov.ca.cwds.data.legacy.cms.entity.NonCWSNumber;
import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.security.utils.PrincipalUtils;

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
   * @param ssb Safely Surrendered Babies
   */
  public void processSafelySurrenderedBabies(String childClientId, String referralId,
      gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies ssb) {
    LocalDateTime now = LocalDateTime.now();
    String userId = PrincipalUtils.getStaffPersonId();
    String countyCode = PrincipalUtils.getPrincipal().getCountyCode();
    String countyCwsCode = PrincipalUtils.getPrincipal().getCountyCwsCode();

    final List<SpecialProject> ssbSpecialProjects =
        specialProjectDao.findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(
            Short.valueOf(countyCode));
    final SpecialProject ssbSpecialProject =
        (ssbSpecialProjects != null && !ssbSpecialProjects.isEmpty()) ? ssbSpecialProjects.get(0)
            : null;

    if (ssbSpecialProject == null) {
      throw new ServiceException(
          "Special project not found for Safely Surrendered Babies for county " + countyCode);
    }

    /**
     * Create SpecialProjectReferral persistence record.
     */
    final SpecialProjectReferral spr = new SpecialProjectReferral();
    spr.setCountySpecificCode(countyCwsCode);
    spr.setId(CmsKeyIdGenerator.getNextValue(userId));
    spr.setPartStartDate(now.toLocalDate());
    spr.setReferralId(referralId);
    spr.setSpecialProjectId(ssbSpecialProject.getId());
    spr.setLastUpdateId(userId);
    spr.setLastUpdateTime(now);
    SpecialProjectReferral createdSpr = specialProjectReferralDao.create(spr);

    /**
     * Create SafelySurrenderedBabies persistence record.
     */
    final SafelySurrenderedBabies ssbEntity = new SafelySurrenderedBabies();
    ssbEntity.setBraceletIdInfoCode(ssb.getBraceletInfoCode());
    ssbEntity.setChildClientId(childClientId);
    ssbEntity.setCommentDescription(ssb.getComments());
    ssbEntity.setCpsNotofiedIndicator(Boolean.TRUE);
    ssbEntity.setCpsNotofiedDate(now.toLocalDate());
    ssbEntity.setMedicalQuestionnaireCode(ssb.getMedicalQuestionaireCode());
    ssbEntity.setQuestionnaireReceivedDate(ssb.getMedicalQuestionaireReturnDate());
    ssbEntity.setCpsNotofiedTime(now.toLocalTime());
    ssbEntity.setLastUpdateId(userId);
    ssbEntity.setLastUpdateTime(now);
    ssbEntity.setRelationToClient(ssb.getRelationToChild().shortValue());
    ssbEntity.setSpecialProjectReferral(createdSpr.getId());
    ssbEntity.setSurrenderedByName(ssb.getSurrenderedByName());
    ssbEntity.setSurrenderedDate(now.toLocalDate());
    ssbEntity.setSurrenderedTime(now.toLocalTime());
    safelySurrenderedBabiesDao.create(ssbEntity);

    /**
     * Create bracelet ID.
     */
    final NonCWSNumber braceltInfo = new NonCWSNumber();
    braceltInfo.setClientId(childClientId);
    braceltInfo.setThirdId(CmsKeyIdGenerator.getNextValue(userId));
    braceltInfo.setLastUpdateId(userId);
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
