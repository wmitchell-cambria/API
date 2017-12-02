package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link Response} adding an id to the {@link ScreeningResponse}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonInclude(Include.ALWAYS)
public class PostedScreening extends ScreeningResponse {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private long id;

  /**
   * Default no-op constructor
   */
  public PostedScreening() {
    super();
  }

}
