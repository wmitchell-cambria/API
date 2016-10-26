package gov.ca.cwds.rest.api.domain.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class PostedCrossReport extends CrossReport {
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param staffPerson The persisted staffPerson
   */
  public PostedCrossReport(gov.ca.cwds.rest.api.persistence.cms.CrossReport crossReport) {
    super(crossReport);
    assert (crossReport.getThirdId() != null);

    this.id = crossReport.getThirdId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
