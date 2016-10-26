package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.legacy.PostedReferral;
import gov.ca.cwds.rest.api.persistence.cms.Referral;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.ReferralDao;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;

/**
 * Business layer object to work on {@link Referral}
 * 
 * @author CWDS API Team
 */
public class ReferralService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralService.class);

  private ReferralDao referralDao;

  /**
   * Constructor
   * 
   * @param sReferralnDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.cms.Referral} objects.
   */
  public ReferralService(ReferralDao referralDao) {
    this.referralDao = referralDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.Referral find(Serializable primaryKey) {
    assert (primaryKey instanceof String);

    gov.ca.cwds.rest.api.persistence.cms.Referral persistedReferral = referralDao.find(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.Referral(persistedReferral);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.Referral delete(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    gov.ca.cwds.rest.api.persistence.cms.Referral persistedReferral =
        referralDao.delete(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.Referral(persistedReferral);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedReferral create(Request request) {
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.Referral);

    gov.ca.cwds.rest.api.domain.legacy.Referral referral =
        ((gov.ca.cwds.rest.api.domain.legacy.Referral) request);

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now
      Referral managed = new Referral(IdGenerator.randomString(3), referral, "q1p");

      managed = referralDao.create(managed);
      return new PostedReferral(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Referral already exists : {}", referral);
      throw new ServiceException(e);
    }
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.Referral update(Serializable primaryKey,
      Request request) {
    assert (primaryKey instanceof String);
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.Referral);
    gov.ca.cwds.rest.api.domain.legacy.Referral referral =
        ((gov.ca.cwds.rest.api.domain.legacy.Referral) request);


    try {
      Referral managed = new Referral((String) primaryKey, referral, "q1p");

      managed = referralDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.legacy.Referral(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Referral not found : {}", referral);
      throw new ServiceException(e);
    }
  }

}

