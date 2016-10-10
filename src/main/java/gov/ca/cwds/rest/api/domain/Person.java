package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//TODO - RDB add constraints
/**
 * {@link DomainObject} representing an person
 * 
 * @author CWDS API Team
 */
public class Person extends DomainObject {
	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("date_of_birth")
	private String dob;

	@JsonProperty("ssn")
	private String ssn;

	@JsonProperty("address")
	private Address address;

	/**
	 * Constructor
	 * 
	 * @param firstName
	 *            the firstName
	 * @param lastName
	 *            the lastName
	 * @param gender
	 *            the gender
	 * @param dob
	 *            the dob
	 * @param ssn
	 *            the ssn
	 * @param address
	 *            the address
	 */
	@JsonCreator
	public Person(@JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName,
			@JsonProperty("gender") String gender, @JsonProperty("date_of_birth") String dob,
			@JsonProperty("ssn") String ssn, @JsonProperty("address") Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.ssn = ssn;
		this.address = address;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

}
