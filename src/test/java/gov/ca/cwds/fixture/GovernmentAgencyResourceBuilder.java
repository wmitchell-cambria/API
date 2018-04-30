package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.GovernmentAgency;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentAgencyResourceBuilder {

  String id = "ABC1234567";
  String type = "LAW_ENFORCEMENT";

  /**
   * @return the agencies
   */
  public GovernmentAgency build() {
    return new GovernmentAgency(id, type);
  }

  /**
   * @param id - id
   * @return the id
   */
  public GovernmentAgencyResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   * @param type - type
   * @return the type
   */
  public GovernmentAgencyResourceBuilder setType(String type) {
    this.type = type;
    return this;
  }

}
