package gov.ca.cwds.rest.api.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * Base class for objects in the persistence layer.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class PersistentObject {
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";
	
	@Column(name = "LST_UPD_ID")
	private String lastUpdatedId;
	
	@Type(type = "timestamp")
	@Column(name = "LST_UPD_TS")	
	private Date lastUpdatedTime;
	
	/*
	 * There are no common elements across every persistent objects
	 */

	protected PersistentObject(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
		this.lastUpdatedTime = new Date();
	}
	
	/**
	 * @return the lastUpdatedId
	 */
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	
	/**
	 * @return the primaryKey
	 */
	public abstract String getPrimaryKey() ;
}
