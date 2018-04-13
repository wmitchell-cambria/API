package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;

/**
 * 
 * @author CWDS API Team
 */
public class HOISocialWorkerResourceBuilder {

  private String id = "socialwkrz";
  private String firstName = "Jane";
  private String lastName = "Worker";
  private String nameSuffix = "Sr.";
  private LegacyDescriptor legacyDescriptor;

  public HOISocialWorkerResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public HOISocialWorkerResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }


  public HOISocialWorkerResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public HOISocialWorkerResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }


  public HOISocialWorkerResourceBuilder() {

    legacyDescriptor = new LegacyDescriptor("socialwkrz", "socialwkrz-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());
  }


  /**
   * @return the HOISocialWorker
   */
  public HOISocialWorker createHOISocialWorker() {
    return new HOISocialWorker(id, firstName, lastName, nameSuffix, legacyDescriptor);
  }
}
