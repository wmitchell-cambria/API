package gov.ca.cwds.rest.api.domain;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link DomainObject} representing a screening request
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ScreeningRequest extends Screening implements Request {
  @JsonProperty("address")
  private Address address;

  @JsonProperty("participant_ids")
  private List<Long> participant_ids;


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
   * @param participants The {@link List<Long>}
   */
  @JsonCreator
  public ScreeningRequest(@JsonProperty("reference") String reference,
      @JsonProperty("ended_at") String ended_at,
      @JsonProperty("incident_county") String incident_county,
      @JsonProperty("incident_date") String incident_date,
      @JsonProperty("location_type") String location_type,
      @JsonProperty("communication_method") String communication_method,
      @JsonProperty("name") String name, @JsonProperty("response_time") String response_time,
      @JsonProperty("screening_decision") String screening_decision,
      @JsonProperty("started_at") String started_at, @JsonProperty("narrative") String narrative,
      @JsonProperty("address") Address address,
      @JsonProperty("participant_ids") List<Long> participant_ids) {
    super(reference, ended_at, incident_county, incident_date, location_type, communication_method,
        name, response_time, screening_decision, started_at, narrative);
    this.address = address;
    this.participant_ids = participant_ids;
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @return the participant_ids
   */
  public List<Long> getParticipant_ids() {
    return participant_ids;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((participant_ids == null) ? 0 : participant_ids.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(getClass().isInstance(obj)))
      return false;
    ScreeningRequest other = (ScreeningRequest) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (participant_ids == null) {
      if (other.participant_ids != null)
        return false;
    } else if (other.participant_ids == null) {
      return false;
    } else if (!Arrays.equals(participant_ids.toArray(), other.participant_ids.toArray())) {
      return false;
    }

    return true;
  }
}
