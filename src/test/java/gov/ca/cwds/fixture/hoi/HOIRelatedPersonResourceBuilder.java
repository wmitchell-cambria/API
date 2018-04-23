package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIRelatedPerson;

/**
 * 
 * @author CWDS API Team
 */
public class HOIRelatedPersonResourceBuilder {

  private String id = "motherabc";
  private String firstName = "Mother";
  private String lastName = "Dino";
  private String nameSuffix = "Sr.";
  private LegacyDescriptor legacyDescriptor;
  private LimitedAccessType limitedAccessType;
  private SystemCodeDescriptor relationship;

  public HOIRelatedPersonResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


  public HOIRelatedPersonResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }


  public HOIRelatedPersonResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public HOIRelatedPersonResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public HOIRelatedPersonResourceBuilder setLimitationAccessType(
      LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
    return this;
  }


  public HOIRelatedPersonResourceBuilder() {

    legacyDescriptor = new LegacyDescriptor("motherabc", "motherabc-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());

    limitedAccessType = LimitedAccessType.NONE;
  }


  /**
   * @return the HOIRelatedPerson
   */
  public HOIRelatedPerson createHOIRelatedPerson() {
    return new HOIRelatedPerson(id, firstName, lastName, nameSuffix, legacyDescriptor, relationship,
        limitedAccessType);

  }
}
