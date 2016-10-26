package gov.ca.cwds.rest.api.domain.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class PostedReporter extends Reporter {
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param reporter The persisted reporter
   */
  public PostedReporter(gov.ca.cwds.rest.api.persistence.cms.Reporter reporter) {
    super(reporter);
    assert (reporter.getReferralId() != null);

    this.id = reporter.getReferralId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
