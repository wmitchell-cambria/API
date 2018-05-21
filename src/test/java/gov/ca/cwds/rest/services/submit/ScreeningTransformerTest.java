package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.fixture.AddressIntakeApiResourceBuilder;
import gov.ca.cwds.fixture.LegacyDescriptorEntityBuilder;
import gov.ca.cwds.fixture.ScreeningResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ScreeningTransformerTest {

  private Map<String, IntakeLov> nsCodeToNsLovMap;
  private Map<String, IntakeLov> cmsSysIdToNsLovMap;
  private Screening screening;


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() throws Exception {
    IntakeLov intakeLovInPerson = mock(IntakeLov.class);
    when(intakeLovInPerson.getLegacySystemCodeId()).thenReturn(new Long(408));
    IntakeLov intakeLovResponseTime = mock(IntakeLov.class);
    when(intakeLovResponseTime.getLegacySystemCodeId()).thenReturn(new Long(1519));
    IntakeLov intakeLovStateCa = mock(IntakeLov.class);
    when(intakeLovStateCa.getLegacySystemCodeId()).thenReturn(new Long(1828));

    nsCodeToNsLovMap = new HashMap<String, IntakeLov>();
    nsCodeToNsLovMap.put("in_person", intakeLovInPerson);
    nsCodeToNsLovMap.put("evaluate_out", intakeLovResponseTime);
    nsCodeToNsLovMap.put("CA", intakeLovStateCa);

    IntakeLov intakeLov = mock(IntakeLov.class);
    when(intakeLov.getLegacyLogicalCode()).thenReturn("34");
    cmsSysIdToNsLovMap = new HashMap<String, IntakeLov>();
    cmsSysIdToNsLovMap.put("1101", intakeLov);

    screening = new ScreeningResourceBuilder().build();
  }

  @Test
  public void transformConvertsScreeningToScreeningToReferral() {
    Set<CrossReport> crossReports = new HashSet<CrossReport>();
    Set<Allegation> allegations = new HashSet<Allegation>();
    Set<String> safetyAlerts = new HashSet<String>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-01T00:00:00.000Z").setStartedAt("2017-01-01T00:00:00.000Z")
        .setIncidentDate("2017-01-01").setLimitedAccessDate(null).setResponseTime((short) 1519)
        .setScreeningDecisionDetail("evaluate_out").setLimitedAccessAgency("34")
        .setLimitedAccessCode("N").setCommunicationMethod((short) 408)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setCurrentLocationOfChildren(null)
        .setParticipants(null).setCrossReports(crossReports).setAddress(null)
        .setAllegations(allegations).setSafetyAlerts(safetyAlerts).setSafetyAlertInformationn(null)
        .createScreeningToReferral();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34",
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsScreeningToScreeningToReferralWhenCommunicationMethodNull() {
    Set<CrossReport> crossReports = new HashSet<CrossReport>();
    Set<Allegation> allegations = new HashSet<Allegation>();
    Set<String> safetyAlerts = new HashSet<String>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-01T00:00:00.000Z").setStartedAt("2017-01-01T00:00:00.000Z")
        .setIncidentDate("2017-01-01").setLimitedAccessDate(null).setResponseTime((short) 1519)
        .setScreeningDecisionDetail("evaluate_out").setLimitedAccessAgency("34")
        .setLimitedAccessCode("N").setCommunicationMethod(null)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setCurrentLocationOfChildren(null)
        .setParticipants(null).setCrossReports(crossReports).setAddress(null)
        .setAllegations(allegations).setSafetyAlerts(safetyAlerts).setSafetyAlertInformationn(null)
        .createScreeningToReferral();
    Screening screening = new ScreeningResourceBuilder().setCommunicationMethod("").build();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34",
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsScreeningToScreeningToReferralWhenScreeningDecisionDetailBlank() {
    Set<CrossReport> crossReports = new HashSet<CrossReport>();
    Set<Allegation> allegations = new HashSet<Allegation>();
    Set<String> safetyAlerts = new HashSet<String>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-01T00:00:00.000Z").setStartedAt("2017-01-01T00:00:00.000Z")
        .setIncidentDate("2017-01-01").setLimitedAccessDate(null).setResponseTime(null)
        .setScreeningDecisionDetail("evaluate_out").setLimitedAccessAgency("34")
        .setLimitedAccessCode("N").setCommunicationMethod((short) 408)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setScreeningDecisionDetail("")
        .setCurrentLocationOfChildren(null).setParticipants(null).setCrossReports(crossReports)
        .setAddress(null).setAllegations(allegations).setSafetyAlerts(safetyAlerts)
        .setSafetyAlertInformationn(null).createScreeningToReferral();
    Screening screening = new ScreeningResourceBuilder().setScreeningDecisionDetail("").build();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34",
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);

    assertEquals(actual, expected);
  }


  @Test
  public void transformConvertsScreeningToScreeningToReferralWhenIncidentAddressProvided() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptorEntityBuilder().build();
    AddressIntakeApi nsAddress = new AddressIntakeApiResourceBuilder().setType("28")
        .setLegacyDescriptor(legacyDescriptor).build();

    Address address = new Address("ADDRESS_T", "1234567ABC", "742 Evergreen Terrace", "Springfield",
        1828, "93838", 28, legacyDescriptor);

    Set<CrossReport> crossReports = new HashSet<CrossReport>();
    Set<Allegation> allegations = new HashSet<Allegation>();
    Set<String> safetyAlerts = new HashSet<String>();
    ScreeningToReferral expected = new ScreeningToReferralResourceBuilder()
        .setEndedAt("2017-01-01T00:00:00.000Z").setStartedAt("2017-01-01T00:00:00.000Z")
        .setIncidentDate("2017-01-01").setLimitedAccessDate(null).setResponseTime((short) 1519)
        .setScreeningDecisionDetail("evaluate_out").setLimitedAccessAgency("34")
        .setLimitedAccessCode("N").setCommunicationMethod((short) 408)
        .setAdditionalInformation("additional information")
        .setScreeningDecision("Screening Decision").setCurrentLocationOfChildren(null)
        .setParticipants(null).setCrossReports(crossReports).setAddress(address)
        .setAllegations(allegations).setSafetyAlerts(safetyAlerts).setSafetyAlertInformationn(null)
        .createScreeningToReferral();
    Screening screening = new ScreeningResourceBuilder().setIncidentAddress(nsAddress).build();
    ScreeningToReferral actual = new ScreeningTransformer().transform(screening, "0X5", "34",
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);

    assertEquals(actual, expected);
  }


}
