package gov.ca.cwds.rest.api.persistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

import io.swagger.annotations.ApiModelProperty;

/**
 * Base class for objects in the persistence layer.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class PersistentObject {
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";
	
	@Column(name = "LST_UPD_ID")
	@NotEmpty
	@Size(min=1, max=3)
	private String lastUpdatedId;
	
	@Transient
	@NotNull
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=TIMESTAMP_FORMAT)
	@gov.ca.cwds.rest.validation.Date(format=TIMESTAMP_FORMAT, required=true)
	private String lastUpdatedTime;
	
	@Type(type = "timestamp")
	@Column(name = "LST_UPD_TS")	
	private Date lastUpdatedTimePersistable;
	
	/*
	 * There are no common elements across every persistent objects
	 */

	protected PersistentObject(String lastUpdatedId, String lastUpdatedTime) {
		DateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		
		this.lastUpdatedId = lastUpdatedId;
		this.lastUpdatedTime = lastUpdatedTime;
		//we are validating this.startDate so we can swallow this ParseException - should never happen
		try { this.lastUpdatedTimePersistable = timestampFormat.parse(lastUpdatedTime); } catch (Throwable e) {}
	}
	
	/**
	 * @return the lastUpdatedId
	 */
	@ApiModelProperty(readOnly=true, value="remove this from view of client, generated at business layer", example="tob")
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@ApiModelProperty(required=true, readOnly=true, value="remove from view of user", example="1963-11-22-13.51.39.247")
	public String getLastUpdatedTime() {
		return !Strings.isNullOrEmpty(lastUpdatedTime) ? lastUpdatedTime : lastUpdatedTimePersistable != null ? ( (new SimpleDateFormat(TIMESTAMP_FORMAT)).format(lastUpdatedTimePersistable)) : "";
	}

	/**
	 * @return the primaryKey
	 */
	@JsonIgnore
	public abstract String getPrimaryKey();

	protected Boolean cookedBoolean(Boolean transientBoolean, String persistableBoolean) {
		if (transientBoolean != null) {
			return transientBoolean;
		}
		if ("N".equalsIgnoreCase(persistableBoolean)) {
			return Boolean.FALSE;
		}
		if ("Y".equalsIgnoreCase(persistableBoolean)) {
			return Boolean.TRUE;
		}
		return null;
	}

	protected String persistableBoolean(Boolean transientBoolean) {
		if (transientBoolean != null) {
			return Boolean.TRUE.equals(transientBoolean) ? "Y" : "N";
		}
		return "";
	}
}
