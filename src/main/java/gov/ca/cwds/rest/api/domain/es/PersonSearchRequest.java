package gov.ca.cwds.rest.api.domain.es;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link DomainObject} representing a Person Search request for ElasticSearch or similar search
 * engine.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class PersonSearchRequest extends DomainObject implements Request {
  @JsonProperty("participant_ids")
  private List<Long> participant_ids;

  /**
   * Constructor
   * 
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
   * @param participant_ids The {@link List}
   */
  @JsonCreator
  public PersonSearchRequest(@JsonProperty("reference") String reference,
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
    super();

    this.participant_ids = participant_ids;
  }

  /**
   * @return the participant_ids
   */
  public List<Long> getParticipant_ids() {
    return participant_ids;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((participant_ids == null) ? 0 : participant_ids.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
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
    PersonSearchRequest other = (PersonSearchRequest) obj;

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
