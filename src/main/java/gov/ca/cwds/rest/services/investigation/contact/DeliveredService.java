package gov.ca.cwds.rest.services.investigation.contact;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityExistsException;

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
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.cms.LongTextService;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class DeliveredService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveredService.class);

  private String lastUpdatedId = RequestExecutionContext.instance().getStaffId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();

  private DeliveredServiceDao deliveredServiceDao;
  private StaffPersonDao staffPersonDao;
  private LongTextService longTextService;

  /**
   * @param deliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   * @param staffPersonDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param longTextService the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.LongText} objects
   * 
   */
  @Inject
  public DeliveredService(DeliveredServiceDao deliveredServiceDao, StaffPersonDao staffPersonDao,
      LongTextService longTextService) {
    this.deliveredServiceDao = deliveredServiceDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextService = longTextService;

  }



  /**
   * Find the Staff Person who last updated the deliveredServiceEntity persistence object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return The Staff Person who last updated the persistence object
   */
  protected LastUpdatedBy getTheLastUpdatedByStaffPerson(
      DeliveredServiceEntity deliveredServiceEntity) {
    StaffPerson lastUpdtatedId = staffPersonDao.find(deliveredServiceEntity.getLastUpdatedId());
    if (lastUpdtatedId != null) {
      return new LastUpdatedBy(lastUpdtatedId);
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
    return getLongText(detailText) + getLongText(detailTextContinuation);
  }

  /**
   * Find from the Long Text table the text value of the Detail Text
   * 
   * @param detailText the Detail Text
   * @return text value of the Detail Text
   */
  private String getLongText(String detailText) {
    LongText detail = new LongText();
    if (detailText != null) {
      detail = longTextService.find(detailText);
    }

    return (Optional.of(detail)).map(LongText::getTextDescription).orElse("");
  }

  /**
   * Creates Long Text Entity and returns the identifier
   * 
   * @param countySpecificCode the county of the referral
   * @param textDescription the text
   * @return identifier of the posted LongText entity
   * @throws ServiceException
   */
  private String createLongText(String textDescription, String countySpecificCode)
      throws ServiceException {

    gov.ca.cwds.rest.api.domain.cms.LongText longText =
        new gov.ca.cwds.rest.api.domain.cms.LongText(countySpecificCode, textDescription);
    PostedLongText postedLongText = longTextService.create(longText);

    return postedLongText.getId();

  }

  /**
   * Creates a record in DeliveredService and returns the unique identifier
   * 
   * @param contactRequest the request
   * @param endDate the end date
   * @param endTime the end time
   * @param serviceContactType the service contact type
   * @param startDate the start date
   * @param startTime the start time
   * @param countySpecificCode
   * @return the identifier of the DeliveredService created
   */
  private String createDeliveredService(ContactRequest contactRequest, String endDate,
      String endTime, Integer serviceContactType, String startDate, String startTime,
      String countySpecificCode) {

    String note = contactRequest.getNote();
    String noteContinuation = "";
    if (note.length() > 4000) {
      noteContinuation = note.substring(4000);
      note = note.substring(0, 3999);
    }
    String longTextId = createLongText(note, countySpecificCode);
    String longTextContinuationId = createLongText(noteContinuation, countySpecificCode);

    DeliveredServiceDomain deliveredServiceDomain = DeliveredServiceDomain.createWithDefaults(
        Integer.parseInt(contactRequest.getCommunicationMethod()),
        Integer.parseInt(contactRequest.getLocation()), countySpecificCode, longTextId,
        longTextContinuationId, endDate, endTime, serviceContactType, startDate, startTime,
        contactRequest.getStatus());

    try {
      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity persistedDeliveredService =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(
              CmsKeyIdGenerator.generate(lastUpdatedId), deliveredServiceDomain, lastUpdatedId,
              lastUpdatedTime);
      persistedDeliveredService = deliveredServiceDao.create(persistedDeliveredService);
      return new gov.ca.cwds.rest.api.contact.DeliveredServiceDomain(persistedDeliveredService)
          .getId();
    } catch (EntityExistsException e) {
      LOGGER.info("deliveredServiceEntity already exists : {}", deliveredServiceDomain);
      throw new ServiceException(e);
    }

  }


  public DeliveredServiceEntity find(String contactId) {
    return deliveredServiceDao.find(contactId);
  }


  public String create(ContactReferralRequest request, String countySpecificCode) {
    ContactRequest contactRequest = request.getContactRequest();
    Date endedAt = DomainChef.uncookISO8601Timestamp(contactRequest.getEndedAt());
    Date startedAt = DomainChef.uncookISO8601Timestamp(contactRequest.getStartedAt());
    Integer serviceContactType = Integer.parseInt(contactRequest.getPurpose());

    return createDeliveredService(contactRequest, DomainChef.cookDate(endedAt),
        DomainChef.cookTime(endedAt), serviceContactType, DomainChef.cookDate(startedAt),
        DomainChef.cookTime(startedAt), countySpecificCode);

  }

}
