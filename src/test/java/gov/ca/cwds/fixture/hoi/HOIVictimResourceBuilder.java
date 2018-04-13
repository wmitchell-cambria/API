package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIVictim;

/**
 * 
 * @author CWDS API Team
 */
public class HOIVictimResourceBuilder {

  private String id = "victimabc";
  private String firstName = "Victim1";
  private String lastName = "Dino";
  private String nameSuffix = "Jr.";
  private LegacyDescriptor legacyDescriptor;
  private LimitedAccessType limitedAccessType;

  public HOIVictimResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


  public HOIVictimResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }


  public HOIVictimResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public HOIVictimResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public HOIVictimResourceBuilder setLimitationAccessType(LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
    return this;
  }


  public HOIVictimResourceBuilder() {

    legacyDescriptor = new LegacyDescriptor("victimabc", "victimabc-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());

    limitedAccessType = LimitedAccessType.NONE;
  }


  /**
   * @return the HOIVictim
   */
  public gov.ca.cwds.rest.api.domain.hoi.HOIVictim createHOIVictim() {

    HOIVictim victim = new HOIVictim(id, firstName, lastName, nameSuffix, legacyDescriptor);
    victim.setLimitedAccessType(limitedAccessType);
    return victim;
  }
}
