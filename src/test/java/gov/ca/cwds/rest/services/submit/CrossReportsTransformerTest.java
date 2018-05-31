package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CrossReportsTransformerTest {

  private CrossReportIntake crossReportIntake;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() throws Exception {
    Set<GovernmentAgencyIntake> agencies =
        Stream.of(new GovernmentAgencyIntake("12", "Ad4ATcY00E", "LAW_ENFORCEMENT"))
            .collect(Collectors.toSet());
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
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);
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
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);
    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsCrossReportsIntakeToCrossReportsWhenCountyEmpty() {
    crossReportIntake.setMethod("Electronic Report");
    crossReportIntake.setCountyId(null);

    CrossReport crossReport = new CrossReportResourceBuilder()
        .setInformDate("2017-03-15T00:00:00.000Z").setCountyId(null).createCrossReport();

    Set<CrossReportIntake> nsCrossReports =
        Stream.of(crossReportIntake).collect(Collectors.toSet());
    Set<CrossReport> expected = Stream.of(crossReport).collect(Collectors.toSet());
    Set<CrossReport> actual = new CrossReportsTransformer().transform(nsCrossReports);
    assertEquals(actual, expected);
  }
}
