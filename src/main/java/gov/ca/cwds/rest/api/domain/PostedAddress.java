package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link Response} adding an id to the {@link Address}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class PostedAddress extends Address {

  @JsonProperty("id")
  private long id;

  public PostedAddress(long id, String street_address, String city, String state, Integer zip) {
    super(street_address, city, state, zip);
    this.id = id;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

}
