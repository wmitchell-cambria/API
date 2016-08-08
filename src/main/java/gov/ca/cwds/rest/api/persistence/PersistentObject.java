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
	
	protected Boolean cookedBoolean(Boolean transientBoolean, String persistableBoolean) {
		if( transientBoolean != null ) {
			return transientBoolean;
		}
		if( "N".equalsIgnoreCase(persistableBoolean)) {
			return Boolean.FALSE;
		}
		if( "Y".equalsIgnoreCase(persistableBoolean)) {
			return Boolean.TRUE;
		}
		return null;
	}
	
	protected String persistableBoolean(Boolean transientBoolean) {
		if( transientBoolean != null ) {
			return Boolean.TRUE.equals(transientBoolean) ? "Y" : "N";
		}
		return "";
	}
}
