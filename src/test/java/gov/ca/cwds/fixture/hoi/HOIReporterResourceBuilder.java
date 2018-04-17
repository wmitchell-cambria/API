package gov.ca.cwds.fixture.hoi;

import static gov.ca.cwds.fixture.ParticipantEntityBuilder.DEFAULT_REPORTER_ID;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;

/**
 * @author CWDS API Team
 */
public class HOIReporterResourceBuilder {

  private String id = DEFAULT_REPORTER_ID;
  private String firstName = "Reporter1";
  private String lastName = "Dino";
  private String nameSuffix = "Jr.";
  private LegacyDescriptor legacyDescriptor;
  private Role role;

  public HOIReporterResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public HOIReporterResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public HOIReporterResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public HOIReporterResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public HOIReporterResourceBuilder setRole(Role role) {
    this.role = role;
    return this;
  }

  public HOIReporterResourceBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  HOIReporterResourceBuilder() {
    this(new DateTime());
  }

  public HOIReporterResourceBuilder(DateTime legacyDescriptorlastUpdated) {
    legacyDescriptor = new LegacyDescriptor(id, "reporterabc-hohj-jkj",
        legacyDescriptorlastUpdated, LegacyTable.REPORTER.getName(),
        LegacyTable.REPORTER.getDescription());
    role = Role.ANONYMOUS_REPORTER;
  }

  /**
   * @return the HOIReporter
   */
  public gov.ca.cwds.rest.api.domain.hoi.HOIReporter createHOIReporter() {
    return new HOIReporter(role, id, firstName, lastName, nameSuffix, legacyDescriptor);
  }
}
