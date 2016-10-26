package gov.ca.cwds.rest.api.domain.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class PostedReferral extends Referral {
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param staffPerson The persisted staffPerson
   */
  public PostedReferral(gov.ca.cwds.rest.api.persistence.cms.Referral referral) {

    super(referral);
    assert (referral.getId() != null);

    this.id = referral.getId();
  }

  /**
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }


}
