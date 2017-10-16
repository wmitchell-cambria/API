package gov.ca.cwds.rest.services.investigation.contact;

import java.util.Date;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class DeliveredService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveredService.class);

  private String currentUserStaffId = RequestExecutionContext.instance().getStaffId();
  private Date currentRequestStartTime = RequestExecutionContext.instance().getRequestStartTime();

  private DeliveredServiceDao deliveredServiceDao;
  private StaffPersonDao staffPersonDao;
  private LongTextHelper longTextHelper;

  /**
   * @param deliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   * @param staffPersonDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param longTextHelper the helper class handling
   *        {@link gov.ca.cwds.data.persistence.cms.LongText} objects
   * 
   */
  @Inject
  public DeliveredService(DeliveredServiceDao deliveredServiceDao, StaffPersonDao staffPersonDao,
      LongTextHelper longTextHelper) {
    this.deliveredServiceDao = deliveredServiceDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextHelper = longTextHelper;
  }

  /**
   * Find the Staff Person who last updated the deliveredServiceEntity persistence object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return The Staff Person who last updated the persistence object
   */
  protected LastUpdatedBy getTheLastUpdatedByStaffPerson(
      DeliveredServiceEntity deliveredServiceEntity) {
    String lastUpdatedByStaffPersonId = deliveredServiceEntity.getLastUpdatedId();
    if (StringUtils.isNotBlank(lastUpdatedByStaffPersonId)) {
      StaffPerson lastUpdtatedId = staffPersonDao.find(lastUpdatedByStaffPersonId);
      if (lastUpdtatedId != null) {
        return new LastUpdatedBy(lastUpdtatedId);
      }
    }
    return new LastUpdatedBy();
  }

  /**
   * Combine DetailText And DetailTextContinuation from the deliveredServiceEntity persistence
   * object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return the combined DetailText And DetailTextContinuation
   */
  String combineDetailTextAndContinuation(DeliveredServiceEntity deliveredServiceEntity) {
    String detailText = deliveredServiceEntity.getDetailText();
    String detailTextContinuation = deliveredServiceEntity.getDetailTextContinuation();
    return longTextHelper.getLongText(detailText)
        + longTextHelper.getLongText(detailTextContinuation);
  }

  public DeliveredServiceEntity find(String contactId) {
    return deliveredServiceDao.find(contactId);
  }

  /**
   * Creates a record in DeliveredService and returns the unique identifier
   * 
   * @param request the request
   * @param countySpecificCode the county of the referral
   * @return the DeliveredServiceDomain
   */
  public String create(ContactReferralRequest request, String countySpecificCode) {

    DeliveredServiceDomain deliveredServiceDomain =
        constructDeliveredServiceDomainForCreate(request, countySpecificCode);

    try {
      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity persistedDeliveredService =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(
              CmsKeyIdGenerator.generate(currentUserStaffId), deliveredServiceDomain,
              currentUserStaffId, currentRequestStartTime);
      persistedDeliveredService = deliveredServiceDao.create(persistedDeliveredService);
      return new gov.ca.cwds.rest.api.contact.DeliveredServiceDomain(persistedDeliveredService)
          .getId();
    } catch (EntityExistsException e) {
      LOGGER.info("deliveredServiceEntity already exists : {}", deliveredServiceDomain);
      throw new ServiceException(e);
    }

  }

  /**
   * Construct the DeliveredService object for create
   *
   * @param contactId the Delivered Service Identifier
   * @param contactRequest the request
   * @param countySpecificCode
   * @return the DeliveredServiceDomain
   */
  private DeliveredServiceDomain constructDeliveredServiceDomainForCreate(
      ContactReferralRequest request, String countySpecificCode) {
    ContactRequest contactRequest = request.getContactRequest();
    Integer serviceContactType = Integer.parseInt(contactRequest.getPurpose());
    String endDate = getDateStringFromDateTime(contactRequest.getEndedAt());
    String endTime = getTimeStringFromDateTime(contactRequest.getEndedAt());
    String startDate = getDateStringFromDateTime(contactRequest.getStartedAt());
    String startTime = getTimeStringFromDateTime(contactRequest.getStartedAt());
    String note = getNoteFromRequest(contactRequest.getNote());
    String noteContinuation = getNoteContinuationFromRequest(contactRequest.getNote());

    String longTextId =
        (StringUtils.isNotBlank(note)) ? longTextHelper.createLongText(note, countySpecificCode)
            : null;
    String longTextContinuationId = (StringUtils.isNotBlank(noteContinuation))
        ? longTextHelper.createLongText(noteContinuation, countySpecificCode)
        : null;

    return DeliveredServiceDomain.createWithDefaultsForFieldsNotPopulatedByUI(
        Integer.parseInt(contactRequest.getCommunicationMethod()),
        Integer.parseInt(contactRequest.getLocation()), countySpecificCode, longTextId,
        longTextContinuationId, endDate, endTime, serviceContactType, startDate, startTime,
        contactRequest.getStatus());

  }

  /**
   * Update a record in DeliveredService and return the DeliveredServiceDomain object
   *
   * @param contactId the Delivered Service Identifier
   * @param request the request
   * @param countySpecificCode the county of the referral
   * @return the DeliveredServiceDomain
   */
  public DeliveredServiceDomain update(String contactId, ContactReferralRequest request,
      String countySpecificCode) {

    DeliveredServiceDomain deliveredServiceDomain =
        constructDeliveredServiceDomainForUpdate(contactId, request, countySpecificCode);

    try {
      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity persistedDeliveredService =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(contactId,
              deliveredServiceDomain, currentUserStaffId, currentRequestStartTime);
      persistedDeliveredService = deliveredServiceDao.update(persistedDeliveredService);
      return new gov.ca.cwds.rest.api.contact.DeliveredServiceDomain(persistedDeliveredService);

    } catch (Exception e) {
      LOGGER.info("deliveredServiceEntity Exception", deliveredServiceDomain);
      throw new ServiceException(e);
    }

  }

  /**
   * Construct the DeliveredService object for update
   *
   * @param contactId the Delivered Service Identifier
   * @param contactRequest the request
   * @param countySpecificCode
   * @return the DeliveredServiceDomain
   */
  private DeliveredServiceDomain constructDeliveredServiceDomainForUpdate(String contactId,
      ContactReferralRequest request, String countySpecificCode) {
    ContactRequest contactRequest = request.getContactRequest();
    Integer serviceContactType = Integer.parseInt(contactRequest.getPurpose());
    String endDate = getDateStringFromDateTime(contactRequest.getEndedAt());
    String endTime = getTimeStringFromDateTime(contactRequest.getEndedAt());
    String startDate = getDateStringFromDateTime(contactRequest.getStartedAt());
    String startTime = getTimeStringFromDateTime(contactRequest.getStartedAt());
    String note = getNoteFromRequest(contactRequest.getNote());
    String noteContinuation = getNoteContinuationFromRequest(contactRequest.getNote());

    DeliveredServiceEntity ds = deliveredServiceDao.find(contactId);

    String longTextId = longTextHelper.updateLongText(ds.getDetailText(), note, countySpecificCode);

    String longTextContinuationId = longTextHelper.updateLongText(ds.getDetailTextContinuation(),
        noteContinuation, countySpecificCode);

    return DeliveredServiceDomain.updateWithDeliveredServiceEntityValuesForFieldsNotPopulatedByUI(
        ds, Integer.parseInt(contactRequest.getCommunicationMethod()),
        Integer.parseInt(contactRequest.getLocation()), countySpecificCode, longTextId,
        longTextContinuationId, endDate, endTime, serviceContactType, startDate, startTime,
        contactRequest.getStatus());
  }

  private String getNoteFromRequest(String note) {
    return (StringUtils.isNotBlank(note) && note.length() > 4000) ? note.substring(0, 3999) : note;
  }

  private String getNoteContinuationFromRequest(String note) {
    return (StringUtils.isNotBlank(note) && note.length() > 4000) ? note.substring(4000) : null;
  }

  private String getTimeStringFromDateTime(String dateTime) {
    return DomainChef.cookTime(DomainChef.uncookISO8601Timestamp(dateTime));
  }

  private String getDateStringFromDateTime(String dateTime) {
    return DomainChef.cookDate(DomainChef.uncookISO8601Timestamp(dateTime));
  }


}
