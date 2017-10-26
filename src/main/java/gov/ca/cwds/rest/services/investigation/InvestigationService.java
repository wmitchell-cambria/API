package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class InvestigationService implements TypedCrudsService<String, Investigation, Response> {

  private InvestigationDao investigationDao;
  private StaffPersonDao staffPersonDao;
  private AddressDao addressDao;
  private LongTextService longTextService;
  private PeopleService peopleService;
  private AllegationService allegationService;
  private RelationshipListService relationshipListService;
  private ContactService contactService;

  private Investigation validInvestigation = new InvestigationEntityBuilder().build();

  /**
   * 
   * @param investigationDao - investigationDao
   * @param staffPersonDao - staffPersonDao
   * @param addressDao - addressDao
   * @param longTextService - longText Service
   * @param peopleService - People Service
   * @param allegationService - Allegation Service
   * @param relationshipListService - RelationshipList Service
   * @param contactService - contact service
   */
  @Inject
  public InvestigationService(InvestigationDao investigationDao, StaffPersonDao staffPersonDao,
      AddressDao addressDao, LongTextService longTextService, PeopleService peopleService,
      AllegationService allegationService, RelationshipListService relationshipListService,
      ContactService contactService) {
    super();
    this.investigationDao = investigationDao;
    this.addressDao = addressDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextService = longTextService;
    this.peopleService = peopleService;
    this.allegationService = allegationService;
    this.relationshipListService = relationshipListService;
    this.contactService = contactService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String referralId) {
    Investigation validInvestigation;

    if (referralId.equals("999999")) {
      // Stub data.
      return this.validInvestigation;
    }

    final Referral referral = investigationDao.find(referralId);

    if (referral == null) {
      throw new ServiceException("Referral/Investigation not found for provided id :" + referralId);
    } else {
      Address address = this.findIncidentAddress(referral.getAllegesAbuseOccurredAtAddressId());
      StaffPerson staffPerson = this.findStaffPersonById(referral.getPrimaryContactStaffPersonId());
      LongText rptNarrativeLongText =
          this.findLongTextById(referral.getCurrentLocationOfChildren());
      LongText addInfoLongText = this.findLongTextById(referral.getResponseRationaleText());

      Set<gov.ca.cwds.rest.api.domain.investigation.Allegation> allegations =
          this.allegationService.populateAllegations(referral.getAllegations());
      Set<Person> peoples = this.peopleService.getInvestigationPeoples(referral);
      Set<Relationship> relationshipList = new HashSet<>();

      // TODO - uncomment below when its needed
      // this.relationshipListService.findRelationshipByReferralId(referral);

      Set<String> safetyAlerts = new HashSet<>();
      Set<String> crossReports = new HashSet<>();
      Set<Contact> contacts = this.findContactsByReferralId(referralId);
      validInvestigation =
          new Investigation(referral, address, staffPerson, rptNarrativeLongText, addInfoLongText,
              allegations, peoples, relationshipList, safetyAlerts, crossReports, contacts);
    }

    return validInvestigation;
  }


  @Override
  public Investigation delete(String primaryKey) {
    return validInvestigation;
  }

  @Override
  public Response create(Investigation request) {
    return validInvestigation;
  }

  @Override
  public Response update(String primaryKey, Investigation request) {
    return validInvestigation;
  }

  /**
   * finding incident address details
   * 
   * @param allegesAbuseOccurredAtAddressId - allegesAbuseOccurredAtAddressId
   * @return instance of object
   */
  private Address findIncidentAddress(String allegesAbuseOccurredAtAddressId) {
    Address address = null;
    if (StringUtils.isNotBlank(allegesAbuseOccurredAtAddressId)) {
      address = this.addressDao.find(allegesAbuseOccurredAtAddressId);

    }
    return address;
  }

  /**
   * Find the Staff Person who last updated the deliveredServiceEntity persistence object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return The Staff Person who last updated the persistence object
   */
  private StaffPerson findStaffPersonById(String staffPersonId) {
    StaffPerson staffPerson = null;
    if (StringUtils.isNotBlank(staffPersonId)) {
      staffPerson = this.staffPersonDao.find(staffPersonId);
    }
    return staffPerson;
  }

  /**
   * finding LongText instance based on responseRationaleTextId
   * 
   * @param responseRationaleTextId - long text id value
   * @return instance of LongText
   */
  private LongText findLongTextById(String responseRationaleTextId) {
    return StringUtils.isNotBlank(responseRationaleTextId)
        ? longTextService.find(responseRationaleTextId) : null;
  }

  /**
   * finds investigation/referral contacts by referral id
   * 
   * @param referralId - referral id
   * @return - list of contacts
   */
  private Set<Contact> findContactsByReferralId(String referralId) {
    ContactList contactList = this.contactService.findAllContactsForTheReferral(referralId);
    return contactList.getContacts() != null ? contactList.getContacts() : new HashSet<>();
  }

}
