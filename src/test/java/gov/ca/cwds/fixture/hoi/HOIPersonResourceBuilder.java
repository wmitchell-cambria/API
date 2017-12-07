package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;

/**
 * 
 * @author CWDS API Team
 */
public class HOIPersonResourceBuilder {

  private String id = "jhdgfkhaj";
  private String firstName = "Barney";
  private String lastName = "Dino";
  private LegacyDescriptor legacyDescriptor;

  public HOIPersonResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


  public HOIPersonResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }


  public HOIPersonResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public HOIPersonResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }


  public HOIPersonResourceBuilder() {

    legacyDescriptor = new LegacyDescriptor("jhdgfkhaj", "jhdgfkhaj-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());
  }


  /**
   * @return the PersonHOI
   */
  public gov.ca.cwds.rest.api.domain.hoi.HOIPerson createHOIPerson() {
    return new HOIPerson(id, firstName, lastName, legacyDescriptor);
  }
}
