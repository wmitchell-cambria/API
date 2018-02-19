package gov.ca.cwds.fixture.investigation;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.Assignee;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.LimitedAccess;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;

@SuppressWarnings("javadoc")
public class InvestigationEntityBuilder {

  private String tableName = "REFERL_T";
  private String id = "1234567ABC";
  private String lastUpdatedBy = "0X5";
  private Date lastUpdatedAt =
      DomainChef.uncookStrictTimestampString("2016-08-03T01:00:00.000-0700");
  private String incidentCounty = "20";
  private Date incidentDate = DomainChef.uncookDateString("2017-08-20");
  private String locationType = "Home";
  private Short communicationMethod = new Short("408");
  private String name = "The test investigation";
  private String reportNarrative = "Summary of an investigation would appear here.";
  private String reference = "REF-TEST";
  private Short responseTime = new Short("1518");
  private Date startedAt = DomainChef.uncookStrictTimestampString("2017-08-03T01:00:00.000-0000");
  private String additionalInformation = "Additional information about the investigation.";
  private Boolean sensitive = Boolean.FALSE;
  private Boolean sealed = Boolean.FALSE;
  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Referral");

  private Assignee assignee = new Assignee("CWS Staff", incidentCounty, "Madera CWS", "0X5");

  private Set<PhoneNumber> phoneNumbers = new HashSet<>();

  private LimitedAccess limitedAccess = new LimitedAccess("N", "20");

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();

  private ScreeningSummary screeningSummary = new ScreeningSummaryEntityBuilder().build();

  InvolvementHistory historyOfInvolvement = new InvolvementHistoryResourceBuilder().build();


  private Allegation allegation = new AllegationEntityBuilder().build();
  private Set<Allegation> allegations = new HashSet<>();

  private Person person = new PersonEntityBuilder().build();
  private Set<Person> people = new HashSet<>();

  private Relationship relationship = new RelationshipEntityBuilder().build();
  private Set<Relationship> relationships = new HashSet<>();

  private SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
  private Set<String> crossReports = new HashSet<>();
  private Set<Contact> contacts = new HashSet<>();

  public Investigation build() {
	final Long phone = Long.valueOf("9164445555");
	final Integer phoneExtension = Integer.valueOf("2222");
	final Short phoneType = Short.valueOf("1111");
	final  PhoneNumber phoneNumber =
	      new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);
    people.add(person);
    phoneNumbers.add(phoneNumber);
    relationships.add(relationship);
    allegations.add(allegation);

    return new Investigation(cmsRecordDescriptor, lastUpdatedBy, lastUpdatedAt, incidentCounty,
        incidentDate, locationType, communicationMethod, name, reportNarrative, reference,
        responseTime, startedAt, assignee, additionalInformation, sensitive, sealed, phoneNumbers,
        address, screeningSummary, historyOfInvolvement, allegations, people, relationships,
        safetyAlerts, crossReports, contacts);
  }

  public String getTableName() {
    return tableName;
  }

  public InvestigationEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public String getId() {
    return id;
  }

  public InvestigationEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public InvestigationEntityBuilder setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
    return this;
  }

  public Date getLastUpdatedAt() {
    return freshDate(lastUpdatedAt);
  }

  public InvestigationEntityBuilder setLastUpdatedAt(Date lastUpdatedAt) {
    this.lastUpdatedAt = freshDate(lastUpdatedAt);
    return this;
  }

  public String getIncidentCounty() {
    return incidentCounty;
  }

  public InvestigationEntityBuilder setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
    return this;
  }

  public Date getIncidentDate() {
    return freshDate(incidentDate);
  }

  public InvestigationEntityBuilder setIncidentDate(Date incidentDate) {
    this.incidentDate = freshDate(incidentDate);
    return this;
  }

  public String getLocationType() {
    return locationType;
  }

  public InvestigationEntityBuilder setLocationType(String locationType) {
    this.locationType = locationType;
    return this;
  }

  public Short getCommunicationMethod() {
    return communicationMethod;
  }

  public InvestigationEntityBuilder setCommunicationMethod(Short communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }

  public String getName() {
    return name;
  }

  public InvestigationEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public String getReportNarrative() {
    return reportNarrative;
  }

  public InvestigationEntityBuilder setReportNarrative(String reportNarrative) {
    this.reportNarrative = reportNarrative;
    return this;
  }

  public String getReference() {
    return reference;
  }

  public InvestigationEntityBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public Short getResponseTime() {
    return responseTime;
  }

  public InvestigationEntityBuilder setResponseTime(Short responseTime) {
    this.responseTime = responseTime;
    return this;
  }

  public Date getStartedAt() {
    return freshDate(startedAt);
  }

  public InvestigationEntityBuilder setStartedAt(Date startedAt) {
    this.startedAt = freshDate(startedAt);
    return this;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public InvestigationEntityBuilder setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
    return this;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public InvestigationEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public InvestigationEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }

  public InvestigationEntityBuilder setCmsRecordDescriptor(
      CmsRecordDescriptor cmsRecordDescriptor) {
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    return this;
  }

  public Assignee getAssignee() {
    return assignee;
  }

  public InvestigationEntityBuilder setAssignee(Assignee assignee) {
    this.assignee = assignee;
    return this;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public InvestigationEntityBuilder setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  public LimitedAccess getLimitedAccess() {
    return limitedAccess;
  }

  public InvestigationEntityBuilder setLimitedAccess(LimitedAccess limitedAccess) {
    this.limitedAccess = limitedAccess;
    return this;
  }

  public InvestigationAddress getAddress() {
    return address;
  }

  public InvestigationEntityBuilder setAddress(InvestigationAddress address) {
    this.address = address;
    return this;
  }

  public InvolvementHistory getHistoryOfInvolvement() {
    return historyOfInvolvement;
  }

  public InvestigationEntityBuilder setHistoryOfInvolvement(
      InvolvementHistory historyOfInvolvement) {
    this.historyOfInvolvement = historyOfInvolvement;
    return this;
  }

  public Set<Allegation> getAllegations() {
    return allegations;
  }

  public InvestigationEntityBuilder setAllegations(Set<Allegation> allegations) {
    this.allegations = allegations;
    return this;
  }

  public Person getPerson() {
    return person;
  }

  public InvestigationEntityBuilder setPerson(Person person) {
    this.person = person;
    return this;
  }

  public Set<Person> getPeople() {
    return people;
  }

  public InvestigationEntityBuilder setPeople(Set<Person> people) {
    this.people = people;
    return this;
  }

  public Relationship getRelationship() {
    return relationship;
  }

  public InvestigationEntityBuilder setRelationship(Relationship relationship) {
    this.relationship = relationship;
    return this;
  }

  public Set<Relationship> getRelationships() {
    return relationships;
  }

  public InvestigationEntityBuilder setRelationships(Set<Relationship> relationships) {
    this.relationships = relationships;
    return this;
  }

  public ScreeningSummary getScreeningSummary() {
    return screeningSummary;
  }

  public InvestigationEntityBuilder setScreeningSummary(ScreeningSummary screeningSummary) {
    this.screeningSummary = screeningSummary;
    return this;
  }

  public InvestigationEntityBuilder setSafetyAlerts(SafetyAlerts safetyAlerts) {
    this.safetyAlerts = safetyAlerts;
    return this;
  }

  public SafetyAlerts getSafetyAlerts() {
    return safetyAlerts;
  }

}
