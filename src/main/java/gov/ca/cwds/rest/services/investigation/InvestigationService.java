package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.std.ApiLanguageAware;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class InvestigationService implements TypedCrudsService<String, Investigation, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvestigationService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private InvestigationDao investigationDao;
  private StaffPersonDao staffPersonDao;
  private AddressDao addressDao;
  private LongTextService longTextService;
  private ClientDao clientDao;

  private Investigation validInvestigation = new InvestigationEntityBuilder().build();


  /**
   * 
   * @param investigationDao - investigationDao
   * @param staffPersonDao - staffPersonDao
   * @param addressDao - addressDao
   * @param longTextService - longTextService service
   * @param clientDao - ClientDao
   */
  @Inject
  public InvestigationService(InvestigationDao investigationDao, StaffPersonDao staffPersonDao,
      AddressDao addressDao, LongTextService longTextService, ClientDao clientDao) {
    super();
    this.investigationDao = investigationDao;
    this.addressDao = addressDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextService = longTextService;
    this.clientDao = clientDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */

  @Override
  public Response find(String primaryKey) {
    Investigation validInvestigation = null;

    if (primaryKey.equals("999999")) {
      return this.validInvestigation;

    }

    Referral referral = investigationDao.find(primaryKey);

    if (referral == null) {
      throw new ServiceException("Referral/Investigation not found for provided id :" + primaryKey);
    } else {

      Address address = this.findIncidentAddress(referral.getAllegesAbuseOccurredAtAddressId());
      StaffPerson staffPerson = this.findStaffPersonById(referral.getPrimaryContactStaffPersonId());
      LongText rptNarrativeLongText =
          this.findLongTextById(referral.getCurrentLocationOfChildren());
      LongText addInfoLongText = findLongTextById(referral.getResponseRationaleText());

      Set<gov.ca.cwds.rest.api.domain.investigation.Allegation> allegations =
          this.populateAllegations(referral.getAllegations());
      Set<Person> peoples = this.populatePeople(referral.getReferralClients());

      validInvestigation = new Investigation(referral, address, staffPerson, rptNarrativeLongText,
          addInfoLongText, allegations, peoples);
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
   * finding Allegations based on referral id
   * 
   * @param referralId - referral id
   * @return - list of allegation
   */
  private Set<gov.ca.cwds.rest.api.domain.investigation.Allegation> populateAllegations(
      Set<Allegation> allegationsEntity) {
    Set<gov.ca.cwds.rest.api.domain.investigation.Allegation> apiAllegations = new HashSet<>();
    for (Allegation persisterAllocation : allegationsEntity) {
      apiAllegations
          .add(new gov.ca.cwds.rest.api.domain.investigation.Allegation(persisterAllocation));

    }
    return apiAllegations;
  }

  /**
   * 
   * @param referralClients
   */
  private Set<Person> populatePeople(Set<ReferralClient> referralClients) {
    Set<Person> persons = new HashSet<Person>();
    Person person = null;
    Client client = null;
    for (ReferralClient refClient : referralClients) {
      if (refClient != null) {
        client = clientDao.find(refClient.getClientId());
        person = new Person(client, getLanguages(client.getLanguages()));
        persons.add(person);
      }
    }
    return persons;
  }

  /**
   * 
   * @param apiLanguages - api languages
   * @return list of languages
   */
  private Set<String> getLanguages(ApiLanguageAware[] apiLanguages) {
    Set<String> languages = new HashSet<>();
    for (ApiLanguageAware api : apiLanguages) {
      languages.add(api.getLanguageSysId().toString());
    }

    return languages;
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
    LongText longText = null;
    if (StringUtils.isNotBlank(responseRationaleTextId)) {
      longText = longTextService.find(responseRationaleTextId);
    }
    return longText;

  }



}
