package gov.ca.cwds.rest.api.domain;

import java.util.Set;

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

  @JsonProperty("id")
  private long id;

  /**
   * Default no-op constructor
   */
  public PostedScreening() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param reference The reference
   * @param ended_at The ended at
   * @param incident_county The incident county
   * @param incident_date The incident date
   * @param location_type The location type
   * @param communication_method The communication method
   * @param name The name
   * @param response_time The response time
   * @param screening_decision The screening decision
   * @param started_at The started at
   * @param narrative The narrative
   * @param address The {@link Address}
   * @param participants The {@link Set}
   */
  public PostedScreening(long id, String reference, String ended_at, String incident_county,
      String incident_date, String location_type, String communication_method, String name,
      String response_time, String screening_decision, String started_at, String narrative,
      Address address, Set<Person> participants) {
    super(reference, ended_at, incident_county, incident_date, location_type, communication_method,
        name, response_time, screening_decision, started_at, narrative, address, participants);
    this.id = id;
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param reference The reference
   */
  public PostedScreening(long id, String reference) {
    super(reference);
    this.id = id;
  }

  // public ScreeningResponseCreated(gov.ca.cwds.rest.api.persistence.ns.Screening screening,
  // gov.ca.cwds.rest.api.persistence.ns.Address address) {
  // super(screening, address);
  // this.id = screening.getId();
  // }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;

    // if (getClass() != obj.getClass())
    // return false;

    if (!(obj instanceof PostedScreening)) {
      return false;
    }

    PostedScreening other = (PostedScreening) obj;
    if (id != other.id)
      return false;
    return true;
  }

}
