package gov.ca.cwds.rest.services.investigation.contact;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class ReferralClientDeliveredService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ReferralClientDeliveredService.class);

  private String currentUserStaffId = RequestExecutionContext.instance().getStaffId();
  private Date currentRequestStartTime = RequestExecutionContext.instance().getRequestStartTime();
  private ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  private ReferralClientDao referralClientDao;
  private ChildClientDao childClientDao;

  /**
   * @param referralClientDeliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity}
   *        objects
   * @param referralClientDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ReferralClient} objects
   * @param childClientDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ChildClient} objects
   */
  @Inject
  public ReferralClientDeliveredService(
      ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao,
      ReferralClientDao referralClientDao, ChildClientDao childClientDao) {
    this.referralClientDeliveredServiceDao = referralClientDeliveredServiceDao;
    this.referralClientDao = referralClientDao;
    this.childClientDao = childClientDao;
  }


  /**
   * Check if Contact Id is valid for the Referral Id
   * 
   * @param referralId the Referral Id
   * @param contactId the Contact Id
   * 
   */
  protected void checkContactIdValidForGivenReferralId(String referralId, String contactId) {

    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        findReferralClientDeliveredServiceEntities(referralId);

    for (ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity : referralClientDeliveredServiceEntities) {
      if (referralClientDeliveredServiceEntity.getReferralClientDeliveredServiceEmbeddable()
          .getDeliveredServiceId().equals(contactId)) {
        return;
      }
    }
    throw new ServiceException("ContactId is not Valid For the Given ReferralId");
  }


  private ReferralClientDeliveredServiceEntity[] findReferralClientDeliveredServiceEntities(
      String referralId) {
    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        referralClientDeliveredServiceDao.findByReferralId(referralId);
    if (referralClientDeliveredServiceEntities.length == 0) {
      throw new ServiceException("There are no Contacts For the Given ReferralId");
    }
    return referralClientDeliveredServiceEntities;
  }


  /**
   * Adds a record to ReferralClientDeliveredService for each child client associated with the
   * Referral
   * 
   * @param deliveredServiceId the identifier for the DeliveredService
   * @param referralId the identifier for the Referral
   * @param countySpecificCode the county of the referral
   */
  protected void addOnBehalfOfClients(String deliveredServiceId, String referralId,
      String countySpecificCode) {
    ChildClient[] childClients = childClientDao.findVictimClients(referralId);
    if (childClients != null && childClients.length > 0) {
      for (ChildClient childClient : childClients) {
        ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity =
            new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId,
                childClient.getPrimaryKey(), countySpecificCode, currentUserStaffId,
                currentRequestStartTime);
        if (referralClientDeliveredServiceDao
            .find(referralClientDeliveredServiceEntity.getPrimaryKey()) == null) {
          referralClientDeliveredServiceDao.create(referralClientDeliveredServiceEntity);
        }
      }
    } else {
      throw new ServiceException(
          "An  On Behalf Of Client for the referral could not be found. At least one On Behalf Of Client should exist");
    }
  }

  public ReferralClientDeliveredServiceEntity[] findByReferralId(String referralId) {
    return referralClientDeliveredServiceDao.findByReferralId(referralId);
  }


  public void updateOnBehalfOfClients(String deliveredServiceId, String referralId,
      String countySpecificCode) {
    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServices =
        referralClientDeliveredServiceDao.findByReferralId(referralId);

    ChildClient[] childClients = childClientDao.findVictimClients(referralId);
    if (childClients != null && childClients.length > 0) {
      for (ChildClient childClient : childClients) {
        Boolean create = true;
        for (ReferralClientDeliveredServiceEntity referralClientDeliveredService : referralClientDeliveredServices) {
          if (referralClientDeliveredService.getPrimaryKey().getClientId()
              .equals(childClient.getPrimaryKey())) {
            create = false;
            break;
          }
        }
        if (create) {
          ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity =
              new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId,
                  childClient.getPrimaryKey(), countySpecificCode, currentUserStaffId,
                  currentRequestStartTime);
          referralClientDeliveredServiceDao.create(referralClientDeliveredServiceEntity);
        }
      }
    } else {
      throw new ServiceException(
          "An  On Behalf Of Client for the referral could not be found. At least one On Behalf Of Client should exist");
    }
  }

}
