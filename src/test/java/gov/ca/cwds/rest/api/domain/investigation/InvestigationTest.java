package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.fixture.investigation.HistoryOfInvolvementEntityBuilder;
import gov.ca.cwds.fixture.investigation.InvestigationAddressEntityBuilder;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.fixture.investigation.PersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.fixture.investigation.SimpleScreeningEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class InvestigationTest {

  private ObjectMapper MAPPER = new ObjectMapper();

  private String tableName = "REFERL_T";
  private String id = "1234567ABC";
  private String lastUpdatedBy = "OX5";
  private String lastUpdatedAt = "2010-10-01T15:26:42.000-0700";
  private String incidentCounty = "20";
  private String incidentDate = "2017-08-20";
  private String locationType = "Home";
  private Short communicationMethod = 408;
  private String name = "The test invetigation";
  private String reportNarrative = "Summary of an investigation would appear here.";
  private String reference = "REF-TEST";
  private Short responseTime = 1518;
  private String startedAt = "2017-08-03T01:00:00.000Z";
  private String additionalInformation = "Additional information about the investigation.";
  private Boolean sensitive = Boolean.FALSE;
  private Boolean sealed = Boolean.FALSE;
  private BigDecimal phone = new BigDecimal(4445555);
  private Integer phoneExtension = 1122;
  private Short phoneType = 1111;

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Referral");

  private Assignee assignee = new Assignee("CWS Staff", incidentCounty, "Madera CWS", "0X5");

  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private PhoneNumber phoneNumber =
      new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();

  private SimpleScreening screening = new SimpleScreeningEntityBuilder().build();

  private HistoryOfInvolvement historyOfInvolvement =
      new HistoryOfInvolvementEntityBuilder().build();

  private Allegation allegation = new AllegationEntityBuilder().build();
  private Set<Allegation> allegations = new HashSet<>();

  // private AllegationList allegations = new AllegationListEntityBuilder().build();

  private Person person = new PersonEntityBuilder().build();
  private Set<Person> people = new HashSet<>();

  private Relationship relationship = new RelationshipEntityBuilder().build();
  private Set<Relationship> relationships = new HashSet<>();
  private Set<String> safetyAlerts = new HashSet<>();
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
        sensitive, sealed, phoneNumbers, address, screening, historyOfInvolvement, allegations,
        people, relationships, safetyAlerts, crossReports, contacts);
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
    assertThat(screening, is(equalTo(investigation.getScreening())));
    assertThat(historyOfInvolvement, is(equalTo(investigation.getHistoryOfInvolvement())));
    assertThat(allegations, is(equalTo(investigation.getAllegations())));
    assertThat(people, is(equalTo(investigation.getPeople())));
    assertThat(relationships, is(equalTo(investigation.getRelationships())));
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
    Investigation otherInvestigation =
        new InvestigationEntityBuilder().setIncidentDate("2017-01-01").build();
    assertThat(investigation, is(not(equals(otherInvestigation))));
  }

  @Test
  // @Ignore
  public void testSerilizedInvestigation()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    Investigation investigation = new InvestigationEntityBuilder().build();
    final String expected =
        MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(investigation);
    System.out.println(expected);

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Relationship.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
