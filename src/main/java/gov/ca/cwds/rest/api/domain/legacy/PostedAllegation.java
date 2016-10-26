package gov.ca.cwds.rest.api.domain.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link Allegation}
 * 
 * @author CWDS API Team
 */
public class PostedAllegation extends Allegation {
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param allegation The persisted allegation
   */
  public PostedAllegation(gov.ca.cwds.rest.api.persistence.cms.Allegation allegation) {
    super(allegation);
    assert (allegation.getId() != null);

    this.id = allegation.getId();
  }

  /**
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }

}
