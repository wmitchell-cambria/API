package gov.ca.cwds.fixture.investigation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.CrossReport;
import gov.ca.cwds.rest.api.domain.investigation.CrossReportAgencyTest;

@SuppressWarnings("javadoc")
public class CrossReportEntityBuilder {

  protected Boolean readOnly = false;
  protected Date reportedOn = DomainChef.uncookDateString("2017-10-31");
  protected String communicationMethod = "409";
  protected String county = "20";

  private CrossReportAgencyTest crossReportAgency = new CrossReportAgencyEntityBuilder().build();
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();

  private Set<CrossReportAgencyTest> crossReportAgencies = new HashSet<>();

  public CrossReport build() {
    crossReportAgencies.add(crossReportAgency);
    return new CrossReport(legacyDescriptor, readOnly, reportedOn, communicationMethod, county,
        crossReportAgencies);
  }

  public CrossReportEntityBuilder setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public CrossReportEntityBuilder setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
    return this;
  }

  public CrossReportEntityBuilder setReportedOn(Date reportedOn) {
    this.reportedOn = reportedOn;
    return this;
  }

  public CrossReportEntityBuilder setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }

  public CrossReportEntityBuilder setCounty(String county) {
    this.county = county;
    return this;
  }

  public CrossReportEntityBuilder setCrossReportAgencies(
      Set<CrossReportAgencyTest> crossReportAgencies) {
    this.crossReportAgencies = crossReportAgencies;
    return this;
  }

}
