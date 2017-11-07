package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.CrossReportAgency;

@SuppressWarnings("javadoc")
public class CrossReportAgencyEntityBuilder {
  protected String type = "DEPARTMENT_OF_JUSTICE";
  protected String name = "County Sheriff Dept";

  public CrossReportAgency build() {
    return new CrossReportAgency(type, name);
  }

  public CrossReportAgencyEntityBuilder setType(String type) {
    this.type = type;
    return this;
  }

  public CrossReportAgencyEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }

}
