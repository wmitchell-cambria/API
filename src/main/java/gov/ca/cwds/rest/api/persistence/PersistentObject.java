package gov.ca.cwds.rest.api.persistence;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class for objects in the persistence layer
 * 
 * @author CWDS API Team 
 */
@MappedSuperclass
public class PersistentObject {
	
	@Id
	@Column(name = "IDENTIFIER")
	private String id;
	
	/**
	 * Constructor
	 * 
	 * @param id The id of the object
	 */
	public PersistentObject(String id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
}
