package gov.ca.cwds.rest.api.persistence;

import io.swagger.annotations.ApiModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link PersistentObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */

@ApiModel
public class StaffPerson extends PersistentObject {

	private String firstName;
	private String lastName;
	private String middleInitial;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The id of the Staff Person
	 * @param firstname
	 *            The first name
	 * @param lastname
	 *            The last name
	 * 
	 * @param middleInitial
	 *            The middle initial
	 */
	@JsonCreator
	public StaffPerson(@JsonProperty("id") String id,
			@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("middleInitial") String middleInitial) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
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
	 * @return the middleInitial
	 */
	public String getMiddleInitial() {
		return middleInitial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.ca.cwds.rest.api.persistence.PersistentObject#copy(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public PersistentObject copy(String id, Object from) {
		if (!(from instanceof StaffPerson)) {
			throw new IllegalArgumentException(from.getClass()
					+ " not of type " + StaffPerson.class);
		}
		StaffPerson fromCasted = (StaffPerson) from;
		return new StaffPerson(id, fromCasted.getFirstName(),
				fromCasted.getLastName(), fromCasted.getMiddleInitial());
	}

}
