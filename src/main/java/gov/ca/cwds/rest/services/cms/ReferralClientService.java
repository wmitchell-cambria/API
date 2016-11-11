package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.legacy.PostedReferralClient;
import gov.ca.cwds.rest.api.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.ReferralClientDao;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.ServiceUtils;

// import gov.ca.cwds.rest.util.ServiceUtils;

/**
 * Business layer object to work on {@link ReferralClient}
 * 
 * @author CWDS API Team
 *
 */
public class ReferralClientService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralClientService.class);

  private ReferralClientDao referralClientDao;

  private static final String KEY_REFERRAL_ID = "referralId";
  private static final String KEY_CLIENT_ID = "clientId";

  /**
   * Constructor
   * 
   * @param referralClientDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.cms.ReferralClient} objects.
   */
  public ReferralClientService(ReferralClientDao referralClientDao) {
    this.referralClientDao = referralClientDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.ReferralClient find(Serializable primaryKey) {
    ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient persistedReferralClient =
        referralClientDao.find(primaryKeyObject);
    if (persistedReferralClient != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.ReferralClient(persistedReferralClient);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.ReferralClient delete(Serializable primaryKey) {
    ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient persistedReferralClient =
        referralClientDao.delete(primaryKeyObject);
    if (persistedReferralClient != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.ReferralClient(persistedReferralClient);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
   */
  @Override
  public PostedReferralClient create(Request request) {
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.ReferralClient);

    gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient =
        ((gov.ca.cwds.rest.api.domain.legacy.ReferralClient) request);

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now
      ReferralClient managed = new ReferralClient(referralClient.getReferralId(),
          referralClient.getClientId(), referralClient, "q1p");
      managed = referralClientDao.create(managed);
      return new PostedReferralClient(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Referral Client already exists : {}", referralClient);
      throw new ServiceException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.ReferralClient update(Serializable primaryKeyObject,
      Request request) {

    assert (primaryKeyObject instanceof String);
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.ReferralClient);
    gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient =
        ((gov.ca.cwds.rest.api.domain.legacy.ReferralClient) request);


    try {
      ReferralClient managed = new ReferralClient(getReferralId(primaryKeyObject),
          getClientId(primaryKeyObject), referralClient, "q1p");

      managed = referralClientDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.legacy.ReferralClient(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Referral not found : {}", referralClient);
      String message = e.getMessage();
      System.out.print(message);
      throw new ServiceException(e);
    }
  }

  private ReferralClient.PrimaryKey extractPrimaryKey(Serializable primaryKey) {
    Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(primaryKey);
    String referralId = nameValuePairs.get(KEY_REFERRAL_ID);
    String clientId = nameValuePairs.get(KEY_CLIENT_ID);
    ReferralClient.PrimaryKey primaryKeyObject =
        new ReferralClient.PrimaryKey(referralId, clientId);
    return primaryKeyObject;
  }

  private String getReferralId(Serializable primaryKey) {
    Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(primaryKey);
    String referralId = nameValuePairs.get(KEY_REFERRAL_ID);
    return referralId;
  }

  private String getClientId(Serializable primaryKey) {
    Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(primaryKey);
    String clientId = nameValuePairs.get(KEY_CLIENT_ID);
    return clientId;
  }

}
