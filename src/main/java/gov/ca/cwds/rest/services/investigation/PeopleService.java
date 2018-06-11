package gov.ca.cwds.rest.services.investigation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientScpEthnicityDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.dao.investigation.PeopleDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.ClientScpEthnicity;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.std.ApiLanguageAware;
import gov.ca.cwds.fixture.investigation.PeopleEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.Role;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.util.CmsRecordUtils;

/**
 * Business layer object to work on Investigation People.
 * 
 * @author CWDS API Team
 */
public class PeopleService implements TypedCrudsService<String, People, Response> {

  private PeopleDao peopleDao;
  private ReporterDao reporterDao;
  private ClientScpEthnicityDao clientScpEthnicityDao;
  private static final String HISPANIC_CODE_OTHER_ID = "02";
  private static final Short CARIBBEAN_RACE_CODE = 3162;

  private People validPeople = new PeopleEntityBuilder().build();

  /**
   * 
   * @param peopleDao - people Dao object
   * @param reporterDao - reporter Dao object
   * @param clientScpEthnicityDao - client SCP EthnicityDao
   */
  @Inject
  public PeopleService(PeopleDao peopleDao, ReporterDao reporterDao,
      ClientScpEthnicityDao clientScpEthnicityDao) {
    super();
    this.peopleDao = peopleDao;
    this.reporterDao = reporterDao;
    this.clientScpEthnicityDao = clientScpEthnicityDao;
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
   * Populate peoples who are related to investigations/referral.
   * 
   * @param referral - referral object
   * @return list of persons
   */
  public Set<Person> getInvestigationPeople(Referral referral) {
    Set<Person> persons = new HashSet<>();
    Set<InvestigationAddress> address = new HashSet<>();
    Set<PhoneNumber> phoneNumbers = new HashSet<>();
    Set<String> victims = new HashSet<>();
    Set<String> perpetrators = new HashSet<>();

    // populating victims and perpetrators
    for (Allegation allegation : referral.getAllegations()) {
      victims.add(allegation.getVictimClientId());
      perpetrators.add(allegation.getPerpetratorClientId());
    }

    Person person = null;
    Client client = null;
    CmsRecordDescriptor cmsRecordDescriptor = null;
    for (ReferralClient refClient : referral.getReferralClients()) {
      client = peopleDao.find(refClient.getClientId());
      for (ClientAddress clientAddress : client.getClientAddress()) {
        cmsRecordDescriptor =
            this.getLegacyDescriptor(clientAddress.getAddresses().getId(), LegacyTable.ADDRESS);
        address.add(new InvestigationAddress(clientAddress, cmsRecordDescriptor));
        phoneNumbers.add(new PhoneNumber(clientAddress.getAddresses(), cmsRecordDescriptor));
      }

      final Set<String> roles = this.pouplatePeopleRoles(refClient, victims, perpetrators);
      RaceAndEthnicity raceAndEthnicity = this.populateRaceAndEthnicity(client);
      person = new Person(client, getLanguages(client.getLanguages()),
          this.getLegacyDescriptor(client.getId(), LegacyTable.CLIENT), address, phoneNumbers,
          roles, raceAndEthnicity);
      persons.add(person);
    }

    this.populateReporters(persons, referral.getId());
    return persons;
  }

  /**
   * 
   * determining people roles
   * 
   * @param refClient - ReferralClient object
   * @param victims - list of victims
   * @param perpetrators - list of perpetrators
   * @return list of people roles
   */
  private Set<String> pouplatePeopleRoles(ReferralClient refClient, Set<String> victims,
      Set<String> perpetrators) {
    Set<String> roles = new HashSet<>();
    if (StringUtils.equals("Y", refClient.getSelfReportedIndicator())) {
      roles.add("Self Reported");
    }
    if (victims.contains(refClient.getClientId())) {
      roles.add("Victim");
    }
    if (perpetrators.contains(refClient.getClientId())) {
      roles.add("Perpetrator");
    }
    return roles;
  }

  /**
   * populating reporters
   * 
   * @param persons - list of persons
   * @param referralId - referral id
   */
  private void populateReporters(Set<Person> persons, String referralId) {
    Set<InvestigationAddress> address = new HashSet<>();
    Set<PhoneNumber> phoneNunbers = new HashSet<>();
    Set<String> roles = new HashSet<>();
    Set<String> languages = new HashSet<>();
    String role = null;
    Person person = null;
    CmsRecordDescriptor cmsRecordDescriptor =
        CmsRecordUtils.createLegacyDescriptor(referralId, LegacyTable.REPORTER);
    Reporter[] reporters = this.reporterDao.findInvestigationReportersByReferralId(referralId);
    if (reporters != null) {
      for (Reporter reporter : reporters) {
        address.add(new InvestigationAddress(reporter, cmsRecordDescriptor));
        phoneNunbers.add(new PhoneNumber(reporter, cmsRecordDescriptor));
        role = StringUtils.equals(reporter.getMandatedReporterIndicator(), "Y")
            ? Role.MANDATED_REPORTER_ROLE.getType()
            : Role.NON_MANDATED_REPORTER_ROLE.getType();
        roles.add(role);
        person = new Person(reporter, languages, cmsRecordDescriptor, address, phoneNunbers, roles);
        persons.add(person);
      }
    }
  }

  /**
   * populating RaceAndEthnicity
   * 
   * @param client - Client object
   * @return constructed RaceAndEthnicity object
   */
  private RaceAndEthnicity populateRaceAndEthnicity(Client client) {
    List<Short> raceCodes = new ArrayList<>();
    List<Short> hispanicCodes = new ArrayList<>();
    addRaceAndEthnicity(client.getPrimaryEthnicityType(), raceCodes, hispanicCodes);

    List<ClientScpEthnicity> clientScpEthnicityList =
        clientScpEthnicityDao.getClientScp(client.getId());
    for (ClientScpEthnicity clientScpEthnicity : clientScpEthnicityList) {
      addRaceAndEthnicity(clientScpEthnicity.getEthnicity(), raceCodes, hispanicCodes);
    }
    return new RaceAndEthnicity(client, raceCodes, hispanicCodes);
  }

  private static void addRaceAndEthnicity(Short codeId, final List<Short> raceCodes,
      final List<Short> hispanicCodes) {
    if (codeId != null && codeId != 0) {
      boolean isHispanicCode = false;

      final SystemCode systemCode = SystemCodeCache.global().getSystemCode(codeId);
      if (systemCode != null) {
        // if OTHER_CD is '02' and not Caribbean race - then put in hispanic codes array
        isHispanicCode = (HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd())
            && (!CARIBBEAN_RACE_CODE.equals(systemCode.getSystemId())));
      }

      if (isHispanicCode) {
        hispanicCodes.add(codeId);
      } else {
        raceCodes.add(codeId);
      }
    }
  }

  /**
   * finding legacy record descriptor object.
   * 
   * @param id - legacy id
   * @param legacyTable - legacy table
   * @return CmsRecordDescriptor
   */
  private CmsRecordDescriptor getLegacyDescriptor(String id, LegacyTable legacyTable) {
    return CmsRecordUtils.createLegacyDescriptor(id, legacyTable);
  }

  /**
   * 
   * @param apiLanguages - languages
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
