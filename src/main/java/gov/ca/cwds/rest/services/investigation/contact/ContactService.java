package gov.ca.cwds.rest.services.investigation.contact;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link DeliveredServiceEntity} and related entities
 * {@link IndividualDeliveredServiceEntity}, {@link ReferralClientDeliveredServiceEntity},
 * {@link ContactPartyDeliveredServiceEntity}, {@link gov.ca.cwds.data.persistence.cms.LongText}
 * 
 * @author CWDS API Team
 */
public class ContactService implements TypedCrudsService<String, ContactReferralRequest, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

  private String currentUserStaffId = RequestExecutionContext.instance().getStaffId();
  private Date currentRequestStartTime = RequestExecutionContext.instance().getRequestStartTime();
  private DeliveredService deliveredService;
  private ReferralClientDeliveredService referralClientDeliveredService;
  private DeliveredToIndividualService deliveredToIndividualService;
  private ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  private ReferralDao referralDao;

  private Contact validContact = validContact();
  private Set<Integer> services = new HashSet<>();

  /**
   * @param deliveredService the {@link gov.ca.cwds.rest.services.Service} handling
   *        {@link DeliveredServiceEntity} objects
   * @param referralClientDeliveredService the {@link gov.ca.cwds.rest.services.Service} handling
   *        {@link ReferralClientDeliveredServiceEntity} objects
   * @param contactPartyDeliveredServiceDao the {@link Dao} handling
   *        {@link ContactPartyDeliveredServiceEntity} objects
   * @param deliveredToIndividualService the deliveredToIndividualService
   * @param referralDao the {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects
   */
  @Inject
  public ContactService(DeliveredService deliveredService,
      ReferralClientDeliveredService referralClientDeliveredService,
      DeliveredToIndividualService deliveredToIndividualService,
      ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao, ReferralDao referralDao) {
    this.deliveredService = deliveredService;
    this.referralClientDeliveredService = referralClientDeliveredService;
    this.contactPartyDeliveredServiceDao = contactPartyDeliveredServiceDao;
    this.deliveredToIndividualService = deliveredToIndividualService;
    this.referralDao = referralDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(String primaryKey) {
    if (!primaryKey.contains(":")) {
      return findAllContactsForTheReferral(primaryKey);
    } else {
      String contactId = retrieveContactId(primaryKey);
      final DeliveredServiceEntity deliveredServiceEntity = deliveredService.find(contactId);
      if (deliveredServiceEntity == null) {
        return null;
      }
      LastUpdatedBy lastUpdatedBy =
          deliveredService.getTheLastUpdatedByStaffPerson(deliveredServiceEntity);
      String note = deliveredService.combineDetailTextAndContinuation(deliveredServiceEntity);

      Set<PostedIndividualDeliveredService> peopleInIndividualDeliveredService =
          deliveredToIndividualService
              .getPeopleInIndividualDeliveredService(deliveredServiceEntity);

      return new Contact(deliveredServiceEntity, lastUpdatedBy, note,
          peopleInIndividualDeliveredService);
    }
  }

  private ContactList findAllContactsForTheReferral(String primaryKey) {
    Contact contact = validContact;
    final Set<Contact> contacts = new HashSet<>();
    contacts.add(contact);
    return new ContactList(contacts);
  }

  /**
   * Extract Contact Id from the primary key and return it if it is valid for the Referral Id
   * 
   * @param primaryKey the combination of referralId and contactId
   * @return The contactId
   */
  private String retrieveContactId(String primaryKey) {
    String[] identifiers = primaryKey.split(":");
    String referralId = identifiers[0].trim();
    String contactId = identifiers[1].trim();
    validateContactId(referralId, contactId);
    return contactId;
  }

  /**
   * Check if Contact Id is valid for the Referral Id
   * 
   * @param referralId the Referral Id
   * @param contactId the Contact Id
   */
  private void validateContactId(String referralId, String contactId) {

    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        referralClientDeliveredService.findByReferralId(referralId);
    if (referralClientDeliveredServiceEntities.length == 0) {
      throw new ServiceException("There are no Contacts For the Given ReferralId");
    }
    for (ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity : referralClientDeliveredServiceEntities) {
      if (referralClientDeliveredServiceEntity.getReferralClientDeliveredServiceEmbeddable()
          .getDeliveredServiceId().equals(contactId)) {
        return;
      }
    }
    throw new ServiceException("ContactId Is Not Valid For the Given ReferralId");
  }

  /**
   * <pre>
   * Create a Contact ... this includes the following,
   * creates two records in LongText creates a record in DeliveredService
   * creates a record in ReferralClientDeliveredService for each Child Client associated with the Referral
   * create a record in IndividualDeliveredService for each Person in persons 
   * create a record in ContactPartyDeliveredService
   * </pre>
   * 
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   * 
   * 
   */
  @Override
  public Response create(ContactReferralRequest request) {

    ContactRequest contactRequest = request.getContactRequest();
    Referral referral = validateReferral(request);
    String referralId = referral.getId();
    String countySpecificCode = referral.getCountySpecificCode();
    String deliveredServiceId = deliveredService.create(request, countySpecificCode);
    Integer serviceContactType = Integer.parseInt(contactRequest.getPurpose());
    referralClientDeliveredService.addOnBehalfOfClients(deliveredServiceId, referralId,
        countySpecificCode);
    deliveredToIndividualService.addPeopleToIndividualDeliveredService(deliveredServiceId,
        contactRequest, countySpecificCode);

    contactPartyDeliveredServiceDao.create(new ContactPartyDeliveredServiceEntity(
        CmsKeyIdGenerator.generate(currentUserStaffId), serviceContactType.shortValue(),
        countySpecificCode, deliveredServiceId, currentUserStaffId, currentRequestStartTime));

    return this.find(referralId + ":" + deliveredServiceId);
  }

  /**
   * Validates that there exists a Referral for the given identifier and returns the Referral
   * 
   * @param request the ContactReferralRequest
   * @return the Referral
   */
  Referral validateReferral(ContactReferralRequest request) {
    String referralId = request.getReferralId();
    Referral referral = referralDao.find(referralId);
    if (referral == null) {
      throw new ServiceException("ReferralId is not Valid");
    }
    return referral;
  }

  /**
   * <pre>
   * Update a Contact ... this includes the following,
   * update the two records in LongText and update the record in DeliveredService
   * creates a record in ReferralClientDeliveredService for each new Child Client associated with the Referral
   * delete records in ReferralClientDeliveredService for each Child Client removed from association with the Referral
   * create a record in IndividualDeliveredService for each new Person in persons 
   * delete a record in IndividualDeliveredService for each Person removed from persons 
   * update a record in ContactPartyDeliveredService
   * </pre>
   * 
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(String primaryKey, ContactReferralRequest request) {

    ContactRequest contactRequest = request.getContactRequest();
    Referral referral = validateReferral(request);
    String referralId = referral.getId();
    String countySpecificCode = referral.getCountySpecificCode();
    deliveredService.update(primaryKey, request, countySpecificCode);
    Integer serviceContactType = Integer.parseInt(contactRequest.getPurpose());
    String deliveredServiceId = primaryKey;
    referralClientDeliveredService.updateOnBehalfOfClients(deliveredServiceId, referralId,
        countySpecificCode);
    deliveredToIndividualService.updatePeopleToIndividualDeliveredService(deliveredServiceId,
        contactRequest, countySpecificCode);
    ContactPartyDeliveredServiceEntity entity =
        contactPartyDeliveredServiceDao.findByDeliveredServiceId(primaryKey);
    contactPartyDeliveredServiceDao.update(new ContactPartyDeliveredServiceEntity(
        entity.getPrimaryKey(), serviceContactType.shortValue(), countySpecificCode,
        deliveredServiceId, currentUserStaffId, currentRequestStartTime));

    return this.find(referralId + ":" + deliveredServiceId);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Contact delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  private Contact validContact() {
    final Set<PostedIndividualDeliveredService> people = validPeople();
    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr.");
    return new Contact("1234567ABC", lastUpdatedByPerson, "2010-04-27T23:30:14.000Z", "", "433",
        "408", "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
  }

  private Set<PostedIndividualDeliveredService> validPeople() {
    final Set<PostedIndividualDeliveredService> ret = new HashSet<>();
    ret.add(new PostedIndividualDeliveredService("CLIENT_T", "3456789ABC", "John", "Bob", "Smith",
        "Mr.", "Jr.", ""));
    ret.add(new PostedIndividualDeliveredService("REPTR_T", "4567890ABC ", "Sam", "Bill", "Jones",
        "Mr.", "III", "Reporter"));
    return ret;
  }

}
