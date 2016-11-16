package gov.ca.cwds.rest.api.domain.cms;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class PostedStaffPerson extends StaffPerson {
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param staffPerson The persisted staffPerson
   */
  public PostedStaffPerson(gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson) {
    super(staffPerson);
    assert (staffPerson.getId() != null);

    this.id = staffPerson.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
