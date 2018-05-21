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
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
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

    nsCodeToNsLovMap = new HashMap<String, IntakeLov>();
    nsCodeToNsLovMap.put("in_person", intakeLovInPerson);
    nsCodeToNsLovMap.put("evaluate_out", intakeLovResponseTime);
    IntakeLov intakeLov = mock(IntakeLov.class);
    when(intakeLov.getLegacyLogicalCode()).thenReturn("34");
    cmsSysIdToNsLovMap = new HashMap<String, IntakeLov>();
    cmsSysIdToNsLovMap.put("1101", intakeLov);

    screening = new Screening();
    screening.setId("1");
    screening.setReferralId("");
    screening.setEndedAt("2017-01-01");
    screening.setIncidentCounty("34");
    screening.setIncidentDate("2017-01-01");
    screening.setLocationType("Foster Home");
    screening.setCommunicationMethod("in_person");
    screening.setCurrentLocationOfChildren(null);
    screening.setName("The Rocky Horror Show");
    screening.setReportNarrative("Narrative 123 test");
    screening.setReference("123ABC");
    screening.setRestrictionsRationale("");
    screening.setStartedAt("2017-01-01");
    screening.setAssignee("Michael Bastow");
    screening.setAssigneeStaffId("0X5");
    screening.setAdditionalInformation("additional information");
    screening.setScreeningDecision("Screening Decision");
    screening.setScreeningDecisionDetail("evaluate_out");
    screening.setIncidentAddress(null);
    screening.setParticipantIntakeApis(null);
    screening.setSafetyAlerts(new HashSet<String>());
    screening.setSafetyInformation(null);
    screening.setAccessRestrictions("none");
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
}
