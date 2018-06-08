package gov.ca.cwds.rest.services.cms;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityExistsException;
import javax.validation.Validator;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import gov.ca.cwds.data.cms.SpecialProjectDao;
import gov.ca.cwds.data.cms.SpecialProjectReferralDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.SpecialProject;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedSpecialProjectReferral;
import gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RISpecialProjectReferral;
/**
 * Business layer object to work on {@link SpecialProjectReferral}
 * 
 * @author CWDS API Team
 */
public class SpecialProjectReferralService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral, 
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpecialProjectReferral.class);
  
  private SpecialProjectReferralDao specialProjectReferralDao;
  private SpecialProjectDao specialProjectDao;
  private RISpecialProjectReferral riSpecialProjectReferral;
  
  private Validator validator;
  
  /**
   * Constructor
   * 
   * @param specialProjectDao - special Project ReferralDao
   * @param riSpecialProjectReferral - referential integrity special project referral
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
  

  @Override
  public PostedSpecialProjectReferral create(gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain) {
    try {
      gov.ca.cwds.data.persistence.cms.SpecialProjectReferral persisted = 
          new gov.ca.cwds.data.persistence.cms.SpecialProjectReferral(CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
              sprDomain,
              RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      return new PostedSpecialProjectReferral(specialProjectReferralDao.create(persisted));
    } catch (EntityExistsException e) {
      LOGGER.info("Special Project Referral already exists :{}", sprDomain);
      throw new ServiceException(e);      
    }
  }
  
  public PostedSpecialProjectReferral saveSpecialProjectReferral(gov.ca.cwds.rest.api.domain.Csec csecDomain,
      String referralId, String incidentCounty, MessageBuilder messageBuilder) {
    
    PostedSpecialProjectReferral postedSpecialProjectReferral = null;
    
    short governmentEntityType = convertLogicalIdToSystemCodeFor(incidentCounty, 
        LegacyTable.GOVERNMENT_ORGANIZATION_ENTITY.getName());
    
    String specialProjectId = findSpecialProjectId(csecDomain.getCsecCodeId(), governmentEntityType);   
    if (StringUtils.isBlank(specialProjectId)) {
      String message = "Special Project does not exist for: " 
        + csecDomain.getCsecCodeId() + " " + governmentEntityType;
      messageBuilder.addMessageAndLog(message, LOGGER);
      return null;
    }
    
    try {
      gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain =
          new gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral(incidentCounty, referralId,
              specialProjectId, csecDomain.getEndDate().toString(), csecDomain.getStartDate().toString(), 
              Boolean.FALSE);
      messageBuilder.addDomainValidationError(validator.validate(sprDomain));
      
      postedSpecialProjectReferral = this.create(sprDomain);
    
    } catch (Exception e) {
      messageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
    }
    
    return postedSpecialProjectReferral;
    
  }
  
  private String findSpecialProjectId(String specialProjectName, Short governmentEntityType) {
    List<SpecialProject> specialProjects = specialProjectDao.findSpecialProjectsByGovernmentEntityAndName(specialProjectName, governmentEntityType);
    String specialProjectId = null;
    
    // use the first matching Special Project that is not end dated
    for (SpecialProject specialProject : specialProjects) {
      if (StringUtils.isNotBlank(DomainChef.cookDate(specialProject.getEndDate()))) {
        specialProjectId = specialProject.getId();
        break;
      }
    }
    return specialProjectId;
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
  public RISpecialProjectReferral getRiSpecialProjectReporter() {
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
