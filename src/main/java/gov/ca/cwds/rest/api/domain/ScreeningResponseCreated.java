package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Response} adding an id to the {@link ScreeningResponse}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonInclude(Include.ALWAYS)
public class ScreeningResponseCreated extends ScreeningResponse {

  @JsonProperty("id")
  private long id;

  /**
   * 
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
   * @param participants The {@link List}
   */
  public ScreeningResponseCreated(long id, String reference, String ended_at,
      String incident_county, String incident_date, String location_type,
      String communication_method, String name, String response_time, String screening_decision,
      String started_at, String narrative, Address address, List<Person> participants) {
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
  public ScreeningResponseCreated(long id, String reference) {
    super(reference);
    this.id = id;
  }

  public ScreeningResponseCreated(gov.ca.cwds.rest.api.persistence.ns.Screening screening,
      gov.ca.cwds.rest.api.persistence.ns.Address address) {
    super(screening, address);
    this.id = screening.getId();
  }


  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

}
