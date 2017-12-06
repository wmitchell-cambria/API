package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.PersonHOI;

/**
 * 
 * @author CWDS API Team
 */
public class PersonHOIResourceBuilder {

  private String id = "jhdgfkhaj";
  private String firstName = "Barney";
  private String lastName = "Dino";
  private LegacyDescriptor legacyDescriptor;

  public PersonHOIResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


  public PersonHOIResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }


  public PersonHOIResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public PersonHOIResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }


  public PersonHOIResourceBuilder() {

    legacyDescriptor = new LegacyDescriptor("jhdgfkhaj", "jhdgfkhaj-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());
  }


  /**
   * @return the PersonHOI
   */
  public gov.ca.cwds.rest.api.domain.hoi.PersonHOI createPersonHOI() {
    return new PersonHOI(id, firstName, lastName, legacyDescriptor);
  }
}
