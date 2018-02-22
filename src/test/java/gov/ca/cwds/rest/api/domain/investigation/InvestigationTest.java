package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.AddressEntityBuilder;
import gov.ca.cwds.fixture.LongTextResourceBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.fixture.investigation.AllegationListEntityBuilder;
import gov.ca.cwds.fixture.investigation.InvestigationAddressEntityBuilder;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.fixture.investigation.InvolvementHistoryResourceBuilder;
import gov.ca.cwds.fixture.investigation.PeopleEntityBuilder;
import gov.ca.cwds.fixture.investigation.PersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;
import gov.ca.cwds.fixture.investigation.ScreeningSummaryEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class InvestigationTest {

  private final static DateFormat tf = new SimpleDateFormat("HH:mm:ss");
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String tableName = "REFERL_T";
  private String id = "1234567ABC";
  private String lastUpdatedBy = "OX5";
  private Date lastUpdatedAt =
      DomainChef.uncookStrictTimestampString("2010-10-01T15:26:42.000-0700");
  private String incidentCounty = "20";
  private Date incidentDate = DomainChef.uncookDateString("2017-08-20");
  private String locationType = "Home";
  private Short communicationMethod = 408;
  private String name = "The test invetigation";
  private String reportNarrative = "Summary of an investigation would appear here.";
  private String reference = "REF-TEST";
  private Short responseTime = 1518;
  private Date startedAt = DomainChef.uncookStrictTimestampString("2017-08-03T01:00:00.000-0000");
  private String additionalInformation = "Additional information about the investigation.";
  private Boolean sensitive = Boolean.FALSE;
  private Boolean sealed = Boolean.FALSE;
  private Long phone = 4445555L;
  private Integer phoneExtension = 1122;
  private Short phoneType = 1111;

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Referral");

  private Assignee assignee = new Assignee("CWS Staff", incidentCounty, "Madera CWS", "0X5");

  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private PhoneNumber phoneNumber =
      new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().setStreetAddress("111 first st").build();

  private ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();

  private InvolvementHistory historyOfInvolvement = new InvolvementHistoryResourceBuilder().build();

  private Allegation allegation = new AllegationEntityBuilder().build();
  private Set<Allegation> allegations = new HashSet<>();

  // private AllegationList allegations = new AllegationListEntityBuilder().build();

  private Person person = new PersonEntityBuilder().build();
  private Set<Person> people = new HashSet<>();

  private Relationship relationship = new RelationshipEntityBuilder().build();
  private Set<Relationship> relationships = new HashSet<>();
  private SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
  private Set<String> crossReports = new HashSet<>();
  private Set<Contact> contacts = new HashSet<>();

  @Before
  public void setup() {
    people.add(person);
    phoneNumbers.add(phoneNumber);
    relationships.add(relationship);
    allegations.add(allegation);
  }

  @Test
  public void testEmptyConstructorSuccess() {
    Investigation investigation = new Investigation();
    assertNotNull(investigation);
  }

  @Test
  public void testDomainConstructorSuccess() {
    Investigation investigation = new Investigation(cmsRecordDescriptor, lastUpdatedBy,
        lastUpdatedAt, incidentCounty, incidentDate, locationType, communicationMethod, name,
        reportNarrative, reference, responseTime, startedAt, assignee, additionalInformation,
        sensitive, sealed, phoneNumbers, address, screeningSummary, historyOfInvolvement,
        allegations, people, relationships, safetyAlerts, crossReports, contacts);
    assertThat(cmsRecordDescriptor, is(equalTo(investigation.getCmsRecordDescriptor())));
    assertThat(lastUpdatedBy, is(equalTo(investigation.getLastUpdatedBy())));
    assertThat(lastUpdatedAt, is(equalTo(investigation.getLastUpdatedAt())));
    assertThat(incidentCounty, is(equalTo(investigation.getIncidentCounty())));
    assertThat(incidentDate, is(equalTo(investigation.getIncidentDate())));
    assertThat(locationType, is(equalTo(investigation.getLocationType())));
    assertThat(communicationMethod, is(equalTo(investigation.getCommunicationMethod())));
    assertThat(name, is(equalTo(investigation.getName())));
    assertThat(reportNarrative, is(equalTo(investigation.getReportNarrative())));
    assertThat(reference, is(equalTo(investigation.getReference())));
    assertThat(responseTime, is(equalTo(investigation.getResponseTime())));
    assertThat(startedAt, is(equalTo(investigation.getStartedAt())));
    assertThat(assignee, is(equalTo(investigation.getAssignee())));
    assertThat(additionalInformation, is(equalTo(investigation.getAdditionalInformation())));
    assertThat(sensitive, is(equalTo(investigation.getSensitive())));
    assertThat(sealed, is(equalTo(investigation.getSealed())));
    assertThat(phoneNumbers, is(equalTo(investigation.getPhoneNumbers())));
    assertThat(address, is(equalTo(investigation.getAddress())));
    assertThat(screeningSummary, is(equalTo(investigation.getScreeningSummary())));
    assertThat(historyOfInvolvement, is(equalTo(investigation.getHistoryOfInvolvement())));
    assertThat(allegations, is(equalTo(investigation.getAllegations())));
    assertThat(people, is(equalTo(investigation.getPeople())));
    assertThat(relationships, is(equalTo(investigation.getRelationships())));
    assertThat(crossReports, is(equalTo(investigation.getCrossReports())));
    assertThat(safetyAlerts, is(equalTo(investigation.getSafetyAlerts())));
  }

  @Test
  public void testDomainConstructorWithNullHoiSuccess() {
    InvolvementHistory nullHoi = null;
    InvolvementHistory hoi = new InvolvementHistory();

    Investigation investigation = new Investigation(cmsRecordDescriptor, lastUpdatedBy,
        lastUpdatedAt, incidentCounty, incidentDate, locationType, communicationMethod, name,
        reportNarrative, reference, responseTime, startedAt, assignee, additionalInformation,
        sensitive, sealed, phoneNumbers, address, screeningSummary, nullHoi, allegations, people,
        relationships, safetyAlerts, crossReports, contacts);
    assertThat(investigation.getHistoryOfInvolvement(), is(equalTo(hoi)));
  }

  @Test
  public void testObjectConstructorSuccess() {
    Referral referral = new ReferralEntityBuilder().build();
    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertNotNull(investigation);
  }

  @Test
  public void testInvestigationToReferralMappingSuccess() throws ParseException {

    Date receivedDate;
    receivedDate = df.parse("2017-10-31");
    Date receivedTime;
    receivedTime = tf.parse("12:01:01");

    // construct the referral with the received date and time
    Referral referral = new ReferralEntityBuilder().setReceivedDate(receivedDate)
        .setReceivedTime(receivedTime).build();
    // reconstruct the started at date/time using the referral
    Date startedAt =
        DomainChef.concatenateDateAndTime(referral.getReceivedDate(), referral.getReceivedTime());

    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getCommunicationMethod(),
        is(equalTo(referral.getCommunicationMethodType())));
    assertThat(investigation.getName(), is(equalTo(referral.getReferralName())));
    assertThat(investigation.getResponseTime(), is(equalTo(referral.getReferralResponseType())));
    assertThat(investigation.getStartedAt(), is(equalTo(startedAt)));
    assertThat(investigation.getIncidentCounty(), is(equalTo(referral.getCountySpecificCode())));
    assertThat(investigation.getLastUpdatedBy(), is(equalTo(referral.getLastUpdatedId())));
    assertThat(investigation.getLastUpdatedAt(),
        is(equalTo(DomainChef.cookTimestamp(referral.getLastUpdatedTime()))));
    assertThat(investigation.getContacts(), is(equalTo(contacts)));

  }

  @Test
  public void testWithNullReferralReceivedDateHasNullStartedAt() {
    Referral referral = new ReferralEntityBuilder().build();

    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getStartedAt(), is(equalTo(null)));

  }

  @Test
  public void testObjectConstructorWithNullLongText() {
    Referral referral = new ReferralEntityBuilder().build();
    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = null;
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getAdditionalInformation(), is(equalTo("")));
    assertThat(investigation.getReportNarrative(), is(equalTo("")));
  }

  @Test
  public void testStaffPersonAssignmentSuccess() {
    Referral referral = new ReferralEntityBuilder().build();

    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson =
        new StaffPersonEntityBuilder().setId(referral.getPrimaryContactStaffPersonId()).build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);

    Assignee assignee = investigation.getAssignee();

    assertThat(assignee.getStaffId(), is(equalTo(referral.getPrimaryContactStaffPersonId())));
  }

  @Test
  public void testNullStaffPersonAssignmentSuccess() {
    Referral referral = new ReferralEntityBuilder().build();
    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);

    Assignee assignee = investigation.getAssignee();

    assertThat(assignee.getStaffId(), is(equalTo(referral.getPrimaryContactStaffPersonId())));
  }

  @Test
  public void testNullStaffPerson() {
    Referral referral = new ReferralEntityBuilder().build();
    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = null;
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getAssignee(), is(equalTo(null)));

  }

  @Test
  public void testNullAddress() {
    Referral referral = new ReferralEntityBuilder().build();
    Address address = null;
    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getAddress(), is(equalTo(null)));
  }

  @Test
  public void testReferralAddressIsSetOnInvestigationSuccess() {
    Referral referral = new ReferralEntityBuilder().build();
    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);

    InvestigationAddress investigationAddress = investigation.getAddress();
    assertThat(investigationAddress.getStreetAddress(), is(equalTo(address.getStreetAddress())));
    assertThat(investigationAddress.getCity(), is(equalTo(address.getCity())));
    assertThat(investigationAddress.getState().toString(), is(equalTo(address.getState())));
    assertThat(investigationAddress.getZip(), is(equalTo(address.getZip())));
  }

  @Test
  public void testInvestigationToReferralMappingSealedSuccess() {
    Referral referral = new ReferralEntityBuilder().setLimitedAccessCode("R").build();

    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getSealed(), is(equalTo(Boolean.TRUE)));

  }

  @Test
  public void testInvestigationToReferralMappingSensitiveSuccess() {
    Referral referral = new ReferralEntityBuilder().setLimitedAccessCode("S").build();

    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getSensitive(), is(equalTo(Boolean.TRUE)));

  }

  @Test
  public void testInvestigationToReferralMappingNotSensitiveSuccess() {
    Referral referral = new ReferralEntityBuilder().setLimitedAccessCode("N").build();

    Address address = new AddressEntityBuilder().build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);
    assertThat(investigation.getSensitive(), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void testReferralAddressPhoneMatchesInvestigationSuccess() {

    Long primaryPhone = 1234567L;

    Referral referral = new ReferralEntityBuilder().build();
    Address address = new AddressEntityBuilder().setPrimaryNumber(primaryPhone).build();
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    LongText longText = new LongTextResourceBuilder().build();
    AllegationList allegations = new AllegationListEntityBuilder().build();
    Set<Allegation> allgationSet = allegations.getAllegations();
    People people = new PeopleEntityBuilder().build();
    Set<Person> personSet = people.getPersons();
    Set<Relationship> relationshipList = new HashSet<Relationship>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    Set<String> crossReports = new HashSet<String>();
    Set<Contact> contacts = new HashSet<Contact>();
    ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();
    Investigation investigation =
        new Investigation(referral, address, staffPerson, longText, longText, allgationSet,
            personSet, relationshipList, safetyAlerts, crossReports, contacts, screeningSummary);

    Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();
    phoneNumbers = investigation.getPhoneNumbers();

    Long addressPhoneNumber = address.getPrimaryNumber();
    Long investigationPhoneNumber = 0L;

    for (PhoneNumber phone : phoneNumbers) {
      investigationPhoneNumber = phone.getNumber();
    }

    assertThat(addressPhoneNumber, is(equalTo(investigationPhoneNumber)));

  }

  @Test
  public void testInvestigationEntityBuilder() {
	// InvestigationEntityBuilder is used to create data for the 'stubbed' investigations end point
	final Short newCommunicationMethod = 401;
	final Short newResponseTime = 1517;
	final Date newLastUpdatedAt = DomainChef.uncookStrictTimestampString("2017-01-03T01:00:00.000-0700");
	final Date newIncidentDate = DomainChef.uncookDateString("2018-02-15");
	final Date newStartedAt = DomainChef.uncookStrictTimestampString("2018-02-15T01:00:00.000-0000");
	final Boolean newSensitive = Boolean.TRUE;
	final Boolean newSealed = Boolean.TRUE;
	final String newTableName = "CLIENT_T";
	final String newId = "2345678ABC";
	final String newLastUpdatedBy = "aab";
	final String newIncidentCounty = "34";
	final String newName = "new test investigation";
	final String newLocationType = "Residence";
	final String newReportNarrative = "new report narrative";
	final String newReference = "NEW-REF";
	final String newAdditionalInformation = "new additional information";
	final CmsRecordDescriptor newCmsRecordDescriptor = new CmsRecordDescriptor(newId, "222-333-444-5555", newTableName, "Client");
	final Assignee newAssignee = new Assignee("CWS Staff", incidentCounty, "Madera CWS", "aab");
	final  Set<PhoneNumber> newPhoneNumbers = new HashSet<>();
	final Long newPhone = Long.valueOf("9165556666");
	final Integer newPhoneExtension = Integer.valueOf("3333");
	final Short newPhoneType = Short.valueOf("2222");
	final  PhoneNumber newPhoneNumber =
	      new PhoneNumber(newPhone, newPhoneExtension, newPhoneType, cmsRecordDescriptor);

	final Allegation newAllegation = new AllegationEntityBuilder().setLegacyDescriptor(newCmsRecordDescriptor).build();
	final Set<Allegation> newAllegations = new HashSet<>();
	final Person newPerson = new PersonEntityBuilder().setLastName("last name").build();
	final Set<Person> newPeople = new HashSet<>();
	final Relationship newRelationship = new RelationshipEntityBuilder().setFirstName("first name").build();
	final Set<Relationship> newRelationships = new HashSet<>();
	final SafetyAlerts newSafetyAlerts = new SafetyAlertsEntityBuilder().setAlertInformation("new alert information").build();

	newAllegations.add(newAllegation);
	newPhoneNumbers.add(newPhoneNumber);
	newPeople.add(newPerson);
	newRelationships.add(newRelationship);
	
	Investigation investigation = new InvestigationEntityBuilder()
		.setLastUpdatedBy(newLastUpdatedBy)
		.setLastUpdatedAt(newLastUpdatedAt)
		.setIncidentCounty(newIncidentCounty)
		.setIncidentDate(newIncidentDate)
		.setLocationType(newLocationType)
		.setCommunicationMethod(newCommunicationMethod)
		.setName(newName)
		.setReportNarrative(newReportNarrative)
		.setReference(newReference)
		.setResponseTime(newResponseTime)
		.setStartedAt(newStartedAt)
		.setAdditionalInformation(newAdditionalInformation)
		.setSensitive(newSensitive)
		.setSealed(newSealed)
		.setCmsRecordDescriptor(newCmsRecordDescriptor)
		.setAssignee(newAssignee)
		.setPhoneNumbers(newPhoneNumbers)
		.setAddress(address)
		.setHistoryOfInvolvement(historyOfInvolvement)
		.setAllegations(newAllegations)
		.setPeople(newPeople)
		.setRelationships(newRelationships)
		.setSafetyAlerts(newSafetyAlerts)
		.setCrossReports(crossReports)
		.setContacts(contacts)
		.build();
		
	assertThat(investigation.getLastUpdatedBy(), is(equalTo(newLastUpdatedBy)));
	assertThat(investigation.getLastUpdatedAt(), is(equalTo(newLastUpdatedAt)));
	assertThat(investigation.getIncidentCounty(), is(equalTo(newIncidentCounty)));
	assertThat(investigation.getIncidentDate(), is(equalTo(newIncidentDate)));
	assertThat(investigation.getLocationType(), is(equalTo(newLocationType)));
	assertThat(investigation.getCommunicationMethod(), is(equalTo(newCommunicationMethod)));
	assertThat(investigation.getName(), is(equalTo(newName)));
	assertThat(investigation.getReportNarrative(), is(equalTo(newReportNarrative)));
	assertThat(investigation.getReference(), is(equalTo(newReference)));
	assertThat(investigation.getResponseTime(), is(equalTo(newResponseTime)));
	assertThat(investigation.getStartedAt(), is(equalTo(newStartedAt)));
	assertThat(investigation.getAdditionalInformation(), is(equalTo(newAdditionalInformation)));
	assertThat(investigation.getSensitive(), is(equalTo(newSensitive)));
	assertThat(investigation.getSealed(), is(equalTo(newSealed)));
	assertThat(investigation.getAssignee(), is(equalTo(newAssignee)));
	assertThat(investigation.getPhoneNumbers(), is(equalTo(newPhoneNumbers)));
	assertThat(investigation.getAddress(), is(equalTo(address)));
	assertThat(investigation.getHistoryOfInvolvement(), is(equalTo(historyOfInvolvement)));
	assertThat(investigation.getAllegations(), is(equalTo(newAllegations)));
	assertThat(investigation.getPeople(), is(equalTo(newPeople)));
	assertThat(investigation.getRelationships(), is(equalTo(newRelationships)));
	assertThat(investigation.getSafetyAlerts(), is(equalTo(newSafetyAlerts)));

  }
  
  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Investigation investigation = new InvestigationEntityBuilder().build();
    Investigation otherInvestigation = new InvestigationEntityBuilder().build();
    assertEquals(investigation, otherInvestigation);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Investigation investigation = new InvestigationEntityBuilder().build();
    Investigation otherInvestigation = new InvestigationEntityBuilder()
        .setIncidentDate(DomainChef.uncookDateString("2017-01-01")).build();
    assertThat(investigation, is(not(equals(otherInvestigation))));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Investigation.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
