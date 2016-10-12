package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//TODO - RDB add constraints
/**
 * {@link DomainObject} representing a screening
 *  
 * @author CWDS API Team
 */
public class Screening extends DomainObject {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("reference")
	private String reference;

	@JsonProperty("ended_at")
	private String ended_at;

	@JsonProperty("incident_county")
	private String incident_county;
	
	@JsonProperty("incident_date")
	private String incident_date;
	
	@JsonProperty("location_type")
	private String location_type;
	
	@JsonProperty("method_of_referral")
	private String method_of_referral;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("response_time")
	private String response_time;
	
	@JsonProperty("screening_decision")
	private String screening_decision;
	
	@JsonProperty("started_at")
	private String started_at;
	
	@JsonProperty("narrative")
	private String narrative;

	/**
	 * Constructor 
	 * 
	 * @param id The id
	 * @param reference The reference
	 * @param ended_at The ended at
	 * @param incident_county The incident county
	 * @param incident_date The incident date
	 * @param location_type The location type
	 * @param method_of_referral The method of referral
	 * @param name The name
	 * @param response_time The response time
	 * @param screening_decision The screening decision
	 * @param started_at The started at
	 * @param narrative The narrative
	 */
	@JsonCreator
	public Screening(@JsonProperty("id") Long id, @JsonProperty("reference") String reference,
			@JsonProperty("ended_at") String ended_at, @JsonProperty("incident_county") String incident_county,
			@JsonProperty("incident_date") String incident_date, @JsonProperty("location_type") String location_type,
			@JsonProperty("method_of_referral") String method_of_referral, @JsonProperty("name") String name,
			@JsonProperty("response_time") String response_time, @JsonProperty("screening_decision") String screening_decision,
			@JsonProperty("started_at") String started_at, @JsonProperty("narrative") String narrative) {
		super();
		this.id = id;
		this.reference = reference;
		this.ended_at = ended_at;
		this.incident_county = incident_county;
		this.incident_date = incident_date;
		this.location_type = location_type;
		this.method_of_referral = method_of_referral;
		this.name = name;
		this.response_time = response_time;
		this.screening_decision = screening_decision;
		this.started_at = started_at;
		this.narrative = narrative;
	}

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * @return the method_of_referral
	 */
	public String getMethod_of_referral() {
		return method_of_referral;
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