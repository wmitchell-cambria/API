package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;
import com.google.inject.Inject;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.PeopleDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.std.ApiLanguageAware;
import gov.ca.cwds.fixture.investigation.PeopleEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.util.LegacyRecordUtils;

/**
 * Business layer object to work on Investigation People
 * 
 * @author CWDS API Team
 */
public class PeopleService implements TypedCrudsService<String, People, Response> {

  private PeopleDao peopleDao;

  private People validPeople = new PeopleEntityBuilder().build();

  /**
   * @param peopleDao {@link Dao} handling {@link gov.ca.cwds.rest.api.domain.investigation.People}
   *        objects
   */
  @Inject
  public PeopleService(PeopleDao peopleDao) {
    super();
    this.peopleDao = peopleDao;
  }

  @Override
  public Response find(String primaryKey) {
    return validPeople;
  }

  @Override
  public Response create(People request) {
    return validPeople;
  }

  @Override
  public Response delete(String primaryKey) {
    return validPeople;
  }

  @Override
  public Response update(String primaryKey, People request) {
    return validPeople;
  }


  /**
   * populating peoples who are related to investigations/referral
   * 
   * @param referral - referral object
   * @return list of persons
   */
  public Set<Person> getInvestigationPeoples(Referral referral) {
    Set<Person> persons = new HashSet<Person>();
    Set<InvestigationAddress> address = new HashSet<>();
    Set<PhoneNumber> phoneNunbers = new HashSet<>();
    Person person = null;
    Client client = null;
    for (ReferralClient refClient : referral.getReferralClients()) {
      if (refClient != null) {

        client = peopleDao.find(refClient.getClientId());
        for (ClientAddress clientAddress : client.getClientAddress()) {

          address.add(new InvestigationAddress(clientAddress.getAddresses(),
              this.getLegacyDescriptor(clientAddress.getAddresses().getId(), LegacyTable.ADDRESS)));
          phoneNunbers.add(new PhoneNumber(clientAddress.getAddresses(),
              this.getLegacyDescriptor(clientAddress.getAddresses().getId(), LegacyTable.ADDRESS)));

        }

        person = new Person(client, getLanguages(client.getLanguages()),
            this.getLegacyDescriptor(client.getId(), LegacyTable.CLIENT), address, phoneNunbers);
        persons.add(person);

      }
    }
    return persons;
  }


  /**
   * finding legacy record descriptor object.
   * 
   * @param id - legacy id
   * @param legacyTable - legacy table
   * @return CmsRecordDescriptor
   */
  private CmsRecordDescriptor getLegacyDescriptor(String id, LegacyTable legacyTable) {
    return LegacyRecordUtils.createLegacyDescriptor(id, legacyTable);
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
}
