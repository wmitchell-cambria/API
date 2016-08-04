package gov.ca.cwds.rest.api.persistence;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Base class for objects in the persistence layer.
 * 
 * @author CWDS API Team 
 */
@MappedSuperclass
public abstract class PersistentObject {
	
	/*
	 * There are no common elements across every persistent objects
	 */

	 protected PersistentObject() {
	}

	/**
	 * @return the primaryKey
	 */
	@JsonIgnore
	public abstract String getPrimaryKey() ;
}
