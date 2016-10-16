package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.validation.Date;

public class Screening extends DomainObject implements Request {
  @JsonProperty("reference")
  private String reference;

  @Date
  @JsonProperty("ended_at")
  private String ended_at;

  @JsonProperty("incident_county")
  private String incident_county;

  @Date
  @JsonProperty("incident_date")
  private String incident_date;

  @JsonProperty("location_type")
  private String location_type;

  @JsonProperty("communication_method")
  private String communication_method;

  @JsonProperty("name")
  private String name;

  @JsonProperty("response_time")
  private String response_time;

  @JsonProperty("screening_decision")
  private String screening_decision;

  @Date
  @JsonProperty("started_at")
  private String started_at;

  @JsonProperty("narrative")
  private String narrative;

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
   */
  @JsonCreator
  public Screening(@JsonProperty("reference") String reference,
      @JsonProperty("ended_at") String ended_at,
      @JsonProperty("incident_county") String incident_county,
      @JsonProperty("incident_date") String incident_date,
      @JsonProperty("location_type") String location_type,
      @JsonProperty("communication_method") String communication_method,
      @JsonProperty("name") String name, @JsonProperty("response_time") String response_time,
      @JsonProperty("screening_decision") String screening_decision,
      @JsonProperty("started_at") String started_at, @JsonProperty("narrative") String narrative) {
    super();
    this.reference = reference;
    this.ended_at = ended_at;
    this.incident_county = incident_county;
    this.incident_date = incident_date;
    this.location_type = location_type;
    this.communication_method = communication_method;
    this.name = name;
    this.response_time = response_time;
    this.screening_decision = screening_decision;
    this.started_at = started_at;
    this.narrative = narrative;
  }

  /**
   * Constructor
   * 
   * @param reference the reference for this screening
   */
  public Screening(String reference) {
    super();
    this.reference = reference;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the ended_at
   */
  public String getEnded_at() {
    return ended_at;
  }

  /**
   * @return the incident_county
   */
  public String getIncident_county() {
    return incident_county;
  }

  /**
   * @return the incident_date
   */
  public String getIncident_date() {
    return incident_date;
  }

  /**
   * @return the location_type
   */
  public String getLocation_type() {
    return location_type;
  }

  /**
   * @return the communication_method
   */
  public String getCommunication_method() {
    return communication_method;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the response_time
   */
  public String getResponse_time() {
    return response_time;
  }

  /**
   * @return the screening_decision
   */
  public String getScreening_decision() {
    return screening_decision;
  }

  /**
   * @return the started_at
   */
  public String getStarted_at() {
    return started_at;
  }

  /**
   * @return the narrative
   */
  public String getNarrative() {
    return narrative;
  }
}
