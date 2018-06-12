package gov.ca.cwds.rest.services.cms;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.validation.Validator;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;


import gov.ca.cwds.data.cms.SpecialProjectDao;
import gov.ca.cwds.data.cms.SpecialProjectReferralDao;

import gov.ca.cwds.data.legacy.cms.dao.NonCWSNumberDao;
import gov.ca.cwds.data.legacy.cms.dao.SafelySurrenderedBabiesDao;
import gov.ca.cwds.data.legacy.cms.entity.NonCWSNumber;
import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.SpecialProject;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedSpecialProjectReferral;
import gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RISpecialProjectReferral;
import gov.ca.cwds.security.utils.PrincipalUtils;
import io.dropwizard.hibernate.UnitOfWork;
/**
 * Business layer object to work on {@link SpecialProjectReferral}
 * 
 * @author CWDS API Team
 */
public class SpecialProjectReferralService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral, 
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpecialProjectReferral.class);
  
  private static final String S_CESC_REFERRAL = "S-CESC Referral";
  private static final short MEDICAL_RECORD_SYSTEM_CODE_ID = 1331;
  
  @Inject
  private SpecialProjectReferralDao specialProjectReferralDao;
  
  @Inject
  private SpecialProjectDao specialProjectDao;
  
  @Inject
  private RISpecialProjectReferral riSpecialProjectReferral;
  
  @Inject
  private SafelySurrenderedBabiesDao safelySurrenderedBabiesDao;

  @Inject
  private NonCWSNumberDao nonCWSNumberDao;
  
  private Validator validator;
  
  /**
   * Default no-argument constructor.
   */
  public SpecialProjectReferralService() {
    super();
  }

  /**
   * Constructor
   * 
   * @param specialProjectReferralDao - special project referral DAO
   * @param specialProjectDao - special Project DAO
   * @param riSpecialProjectReferral - referential integrity special project referral
   * @param validator - object validator
   * 
   */
  @Inject
  public SpecialProjectReferralService(SpecialProjectReferralDao specialProjectReferralDao,
      SpecialProjectDao specialProjectDao,
      RISpecialProjectReferral riSpecialProjectReferral, Validator validator) {
    this.specialProjectReferralDao = specialProjectReferralDao;
    this.specialProjectDao = specialProjectDao;
    this.riSpecialProjectReferral = riSpecialProjectReferral;
    this.validator = validator;
  }
  

  /**
   * create Special Project Referral
   * 
   * @param sprDomain - Special Project Referral domain object
   * 
   * @return PostedSpecialProjectReferral  - posted Special Project Referral
   * 
   */
  @Override
  @UnitOfWork(value = "cms")
  public PostedSpecialProjectReferral create(gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain) {
    assert sprDomain instanceof gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral;
    gov.ca.cwds.data.persistence.cms.SpecialProjectReferral persisted = 
        new gov.ca.cwds.data.persistence.cms.SpecialProjectReferral(CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
            sprDomain,
            RequestExecutionContext.instance().getStaffId(),
            RequestExecutionContext.instance().getRequestStartTime());
    return new PostedSpecialProjectReferral(specialProjectReferralDao.create(persisted));
  }

  /**
   * save Csec Special Project Referral
   * 
   * @param csecs - list of CSEC domain objects
   * @param referralId - referral ID
   * @param incidentCounty - county Code
   * @param messageBuilder - message builder
   * 
   * @return PostedSpecialProjectReferral - posted Special Project Referral
   * 
   */
  public PostedSpecialProjectReferral saveCsecSpecialProjectReferral(List<Csec> csecs,
      String referralId, String incidentCounty, MessageBuilder messageBuilder) {
    
    PostedSpecialProjectReferral postedSpecialProjectReferral = null;
    
    if (csecs.isEmpty()) {
      String message = "CSEC data not sent or empty";
      messageBuilder.addMessageAndLog(message, LOGGER);
      return null;
    }
    
    short governmentEntityType = convertLogicalIdToSystemCodeFor(incidentCounty, 
        LegacyTable.GOVERNMENT_ORGANIZATION_ENTITY.getName());
    if (governmentEntityType == 0) {
      String message = "Invalid Government Entity Type for county code : " + incidentCounty;
      messageBuilder.addMessageAndLog(message, LOGGER);
      return null;
    }
    
    String specialProjectId = findSpecialProjectId(S_CESC_REFERRAL, governmentEntityType);   
    if (StringUtils.isBlank(specialProjectId)) {
      String message = "Special Project does not exist for: " 
        + S_CESC_REFERRAL + " " + governmentEntityType;
      messageBuilder.addMessageAndLog(message, LOGGER);
      return null;
    }
    
    try {
      Csec csecDomain = csecs.get(0);
      gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain =
          new gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral(incidentCounty, referralId,
              specialProjectId, csecDomain.getEndDate().toString(), csecDomain.getStartDate().toString(), 
              Boolean.FALSE);
      messageBuilder.addDomainValidationError(validator.validate(sprDomain));
      
      if (!specialProjectReferralExists(referralId, specialProjectId)) {
        postedSpecialProjectReferral = this.create(sprDomain);        
        return postedSpecialProjectReferral;
      } else {
        return null;
      }
    
    } catch (Exception e) {
      messageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
      return null;
    }
  }
  
  private Boolean specialProjectReferralExists(String referralId, String specialProjectId) {
    List<gov.ca.cwds.data.persistence.cms.SpecialProjectReferral> specialProjectReferrals = 
        specialProjectReferralDao
        .findSpecialProjectReferralsByReferralIdAndSpecialProjectId(referralId, specialProjectId);
    if (specialProjectReferrals.size() > 0) {
      return true;
    }
    return false;
  }
  
  private String findSpecialProjectId(String specialProjectName, Short governmentEntityType) {
    List<SpecialProject> specialProjects = specialProjectDao.findSpecialProjectsByGovernmentEntityAndName(specialProjectName, governmentEntityType);
    String specialProjectId = null;
    
    // use the first matching Special Project that is not end dated
    for (SpecialProject specialProject : specialProjects) {
      if (StringUtils.isBlank(DomainChef.cookDate(specialProject.getEndDate()))) {
        specialProjectId = specialProject.getId();
        break;
      }
    }
    return specialProjectId;
  }
  
  /**
   * Process special project for Safely Surrendered Babies.
   * 
   * @param childClientId Child cleint ID.
   * @param referralId Referral ID
   * @param ssb Safely Surrendered Babies
   */
  public void processSafelySurrenderedBabies(String childClientId, String referralId,
      gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies ssb) {

    LocalDateTime now = LocalDateTime.now();
    String userId = PrincipalUtils.getStaffPersonId();
    String countyCode = PrincipalUtils.getPrincipal().getCountyCode();
    String countyCwsCode = PrincipalUtils.getPrincipal().getCountyCwsCode();

    List<SpecialProject> ssbSpecialProjects =
        specialProjectDao.findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(
            Short.valueOf(countyCode));
    SpecialProject ssbSpecialProject =
        (ssbSpecialProjects != null && !ssbSpecialProjects.isEmpty()) ? ssbSpecialProjects.get(0)
            : null;

    if (ssbSpecialProject == null) {
      throw new ServiceException(
          "Special project not found for Safely Surrendered Babies for county " + countyCode);
    }

    /**
     * Create SpecialProjectReferral persistence record.
     */
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral spr = 
        new gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral();
    spr.setCountySpecificCode(countyCwsCode);
    spr.setParticipantStartDate(now.toLocalDate().toString());
    spr.setReferralId(referralId);
    spr.setSpecialProjectId(ssbSpecialProject.getId());
    
    gov.ca.cwds.rest.api.domain.cms.PostedSpecialProjectReferral createdSpr = this.create(spr);

    /**
     * Create SafelySurrenderedBabies persistence record.
     */
    SafelySurrenderedBabies ssbEntity = new SafelySurrenderedBabies();
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
    NonCWSNumber braceltInfo = new NonCWSNumber();
    braceltInfo.setClientId(childClientId);
    braceltInfo.setThirdId(CmsKeyIdGenerator.getNextValue(userId));
    braceltInfo.setLastUpdateId(userId);
    braceltInfo.setLastUpdateTime(now);
    braceltInfo.setOtherId(ssb.getBraceletId());
    braceltInfo.setOtherIdCode(MEDICAL_RECORD_SYSTEM_CODE_ID);
    nonCWSNumberDao.create(braceltInfo);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public SpecialProjectReferral delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public SpecialProjectReferral find(String primaryKey) {
    throw new NotImplementedException("find not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public SpecialProjectReferral update(String primaryKey, SpecialProjectReferral request) {
    throw new NotImplementedException("update not implemented");
  } 
  
  /**
   * @return the riSpecialProjectReferral
   */
  public RISpecialProjectReferral getRiSpecialProjectReferral() {
    return riSpecialProjectReferral;
  }

  /**
   * look up the sysId of the row in the SystemCode table that matches the passed user defined logical code
   * and FKSMeta
   * 
   * @param logicalCode
   * @param governmentEntityCode
   * @return - sysId of the SystemCode 
   */
  private short convertLogicalIdToSystemCodeFor(String logicalCode, String metaTableName) {
    short foundCode = 0;
    Set<SystemCode> systemCodes =
        SystemCodeCache.global().getSystemCodesForMeta(metaTableName);
    for (SystemCode systemCode : systemCodes) {
      if (systemCode.getLogicalId().equals(logicalCode)) {
        foundCode = systemCode.getSystemId();
        break;
      }
    }
    return foundCode;
  }

}
