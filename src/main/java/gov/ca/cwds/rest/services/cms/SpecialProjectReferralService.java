package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import javax.persistence.EntityExistsException;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import gov.ca.cwds.data.cms.SpecialProjectReferralDao;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.api.domain.cms.PostedSpecialProjectReferral;
import gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral;
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
  private RISpecialProjectReferral riSpecialProjectReferral;
  
  /**
   * Constructor
   * 
   * @param specialProjectDao - special Project ReferralDao
   * @param riSpecialProjectReferral - referential integrity special project referral
   * 
   */
  @Inject
  public SpecialProjectReferralService(SpecialProjectReferralDao specialProjectReferralDao,
      RISpecialProjectReferral riSpecialProjectReferral) {
    this.specialProjectReferralDao = specialProjectReferralDao;
    this.riSpecialProjectReferral = riSpecialProjectReferral;
  }
  

  @Override
  public SpecialProjectReferral create(gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral domain) {
    try {
      gov.ca.cwds.data.persistence.cms.SpecialProjectReferral persisted = 
          new gov.ca.cwds.data.persistence.cms.SpecialProjectReferral(domain, 
              RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      persisted = specialProjectReferralDao.create(persisted);
      return new PostedSpecialProjectReferral(persisted);
    } catch (EntityExistsException e) {
      LOGGER.info("Special Project Referral already exists :{}", domain);
      throw new ServiceException(e);
    }
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

}
