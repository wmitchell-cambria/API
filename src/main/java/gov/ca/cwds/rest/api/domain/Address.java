package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//TODO - RDB add constraints
/**
 * {@link DomainObject} representing an address
 *  
 * @author CWDS API Team
 */
public class Address extends DomainObject {
	@JsonProperty("street_address")
	private String streetAddress;

	@JsonProperty("city")
	private String city;

	@JsonProperty("state")
	private String state;

	@JsonProperty("zip")
	private Integer zip;

	/**
	 * Constructor 
	 * 
	 * @param streetAddress	the street address
	 * @param city	the city
	 * @param state	the state
	 * @param zip	the zip
	 */
	@JsonCreator
	public Address(@JsonProperty("street_address") String streetAddress, @JsonProperty("city") String city,
			@JsonProperty("state") String state, @JsonProperty("zip") Integer zip) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the zip
	 */
	public Integer getZip() {
		return zip;
	}
}