package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerpetrator;

/**
 * 
 * @author CWDS API Team
 */
public class HOIPerpetratorResourceBuilder {

  private String id = "perpabc";
  private String firstName = "Perpatrator1";
  private String lastName = "Dino";
  private String nameSuffix = "Sr.";
  private LegacyDescriptor legacyDescriptor;
  private LimitedAccessType limitedAccessType;

  public HOIPerpetratorResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


  public HOIPerpetratorResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }


  public HOIPerpetratorResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public HOIPerpetratorResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public HOIPerpetratorResourceBuilder setLimitationAccessType(
      LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
    return this;
  }


  public HOIPerpetratorResourceBuilder() {

    legacyDescriptor = new LegacyDescriptor("perpabc", "perpabc-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());

    limitedAccessType = LimitedAccessType.NONE;
  }


  /**
   * @return the HOIPerpetrator
   */
  public HOIPerpetrator createHOIPerpetrator() {
    HOIPerpetrator perpetrator = new HOIPerpetrator(id, firstName, lastName, nameSuffix, legacyDescriptor);
    perpetrator.setLimitedAccessType(limitedAccessType);
    return perpetrator;
  }
}
