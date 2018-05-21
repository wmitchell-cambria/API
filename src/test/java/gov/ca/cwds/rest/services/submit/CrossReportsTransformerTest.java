package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.GovernmentAgencyResourceBuilder;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.GovernmentAgency;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CrossReportsTransformerTest {

  private Map<String, IntakeLov> nsCodeToNsLovMap;
  private Map<String, IntakeLov> cmsSysIdToNsLovMap;
  private CrossReportIntake crossReportIntake;


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    IntakeLov intakeLovElectronicReport = mock(IntakeLov.class);
    when(intakeLovElectronicReport.getLegacySystemCodeId()).thenReturn(new Long(2095));
    nsCodeToNsLovMap = new HashMap<String, IntakeLov>();
    nsCodeToNsLovMap.put("Electronic Report", intakeLovElectronicReport);
    IntakeLov intakeLov = mock(IntakeLov.class);
    when(intakeLov.getLegacyLogicalCode()).thenReturn("34");
    cmsSysIdToNsLovMap = new HashMap<String, IntakeLov>();
    cmsSysIdToNsLovMap.put("1101", intakeLov);

    Set<GovernmentAgency> agencies =
        Stream.of(new GovernmentAgencyResourceBuilder().build()).collect(Collectors.toSet());
    crossReportIntake = new CrossReportIntake();
    crossReportIntake.setId("");
    crossReportIntake.setLegacyId("");
    crossReportIntake.setLegacySourceTable("");
    crossReportIntake.setMethod("Electronic Report");
    crossReportIntake.setFiledOutOfState(false);
    crossReportIntake.setInformDate("2017-03-15");
    crossReportIntake.setAgencies(agencies);
    crossReportIntake.setCountyId("1101");
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReports() {
    crossReportIntake.setMethod("Electronic Report");

    CrossReport crossReport = new CrossReportResourceBuilder().setCountyId("34")
        .setInformDate("2017-03-15T00:00:00.000Z").createCrossReport();

    Set<CrossReportIntake> nsCrossReports =
        Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> expected = Stream.of(crossReport).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports,
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);
    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReportsWhenMethodEmpty() {
    crossReportIntake.setMethod("");

    CrossReport crossReport = new CrossReportResourceBuilder().setCountyId("34")
        .setInformDate("2017-03-15T00:00:00.000Z").setMethod(null).createCrossReport();

    Set<CrossReportIntake> nsCrossReports =
        Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> expected = Stream.of(crossReport).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports,
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);
    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReportsWhenCountyEmpty() {
    crossReportIntake.setMethod("Electronic Report");
    crossReportIntake.setCountyId(null);

    CrossReport crossReport = new CrossReportResourceBuilder().setCountyId("34")
        .setInformDate("2017-03-15T00:00:00.000Z").setCountyId(null).createCrossReport();

    Set<CrossReportIntake> nsCrossReports =
        Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> expected = Stream.of(crossReport).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports,
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);
    assertEquals(actual, expected);
  }
}
