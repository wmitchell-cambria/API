package gov.ca.cwds.fixture.hoi;

import static gov.ca.cwds.fixture.ParticipantEntityBuilder.DEFAULT_PERSON_ID;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;

/**
 * @author CWDS API Team
 */
public class HOIPersonResourceBuilder {

  private String id = DEFAULT_PERSON_ID;
  private String firstName = "Barney";
  private String lastName = "Dino";
  private String nameSuffix = "Jr.";
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

  public HOIPersonResourceBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  HOIPersonResourceBuilder() {
    this(new DateTime());
  }

  public HOIPersonResourceBuilder(DateTime legacyDescriptorlastUpdated) {
    legacyDescriptor = new LegacyDescriptor(id, "jhdgfkhaj-hohj-jkj",
        legacyDescriptorlastUpdated, LegacyTable.CLIENT.getName(),
        LegacyTable.CLIENT.getDescription());
  }

  /**
   * @return the HOIPerson
   */
  public HOIPerson createHOIPerson() {
    return new HOIPerson(id, firstName, lastName, nameSuffix, legacyDescriptor);
  }
}
