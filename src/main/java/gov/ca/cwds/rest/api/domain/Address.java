package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing an address
 * 
 * @author CWDS API Team
 */
public class Address extends DomainObject {

	private String street_address;

	private String city;

	private String state;

	private Integer zip;

	/**
	 * Constructor
	 * 
	 * @param street_address
	 *            The street address
	 * @param city
	 *            The city
	 * @param state
	 *            The state
	 * @param zip
	 *            The zip
	 */
	@JsonCreator
	public Address(@JsonProperty("street_address") String street_address, @JsonProperty("city") String city,
			@JsonProperty("state") String state, @JsonProperty("zip") Integer zip) {
		super();
		this.street_address = street_address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * @return the street_address
	 */
	public String getStreet_address() {
		return street_address;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street_address == null) ? 0 : street_address.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (street_address == null) {
			if (other.street_address != null)
				return false;
		} else if (!street_address.equals(other.street_address))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

}