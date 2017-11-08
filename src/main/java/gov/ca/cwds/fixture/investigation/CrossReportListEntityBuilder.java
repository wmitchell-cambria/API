package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.CrossReport;
import gov.ca.cwds.rest.api.domain.investigation.CrossReportList;

@SuppressWarnings("javadoc")
public class CrossReportListEntityBuilder {

  private CrossReport crossReport = new CrossReportEntityBuilder().build();
  private Set<CrossReport> crossReports = new HashSet<>();

  public CrossReportList build() {
    crossReports.add(crossReport);
    return new CrossReportList(crossReports);
  }

  public Set<CrossReport> getCrossReports() {
    return crossReports;
  }

  public CrossReportListEntityBuilder setCrossReports(Set<CrossReport> crossReports) {
    this.crossReports = crossReports;
    return this;
  }

  public CrossReportListEntityBuilder addCrossReport(CrossReport crossReport) {
    this.crossReports.add(crossReport);
    return this;
  }
}
