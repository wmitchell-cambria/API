package gov.ca.cwds.rest.api.persistence.legacy;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import io.swagger.annotations.ApiModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link PersistentObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "STFPERST")
@ApiModel
public class StaffPerson extends PersistentObject {

	@Id
	@Column(name = "IDENTIFIER")
	private String id;
	@Type(type = "date")
	@Column(name = "END_DT")
	private Date endDate;
	@Column(name = "FIRST_NM")
	private String firstName;
	@Column(name = "JOB_TL_DSC")
	private String jobTitle;
	@Column(name = "LAST_NM")
	private String lastName;
	@Column(name = "MID_INI_NM")
	private String middleInitial;

	@Column(name = "NMPRFX_DSC")
	private String namePrefix;
	@Column(name = "PHONE_NO")
	private long phoneNumber;
	@Column(name = "TEL_EXT_NO")
	private int phoneExt;
	@Type(type = "date")
	@Column(name = "START_DT")
	private Date startDate;
	@Column(name = "SUFX_TLDSC")
	private String sufxTldsc;
	@Column(name = "TLCMTR_IND")
	private String tlcmtrInd;

	@Column(name = "LST_UPD_ID")
	private String lastUpdatedId;
	@Type(type = "timestamp")
	@Column(name = "LST_UPD_TS")
	private Date lastUpdatedTime;

	@Column(name = "FKCWS_OFFT")
	private String fkcwsOfft;
	@Column(name = "AVLOC_DSC")
	private String avlocDsc;
	@Column(name = "SSRS_WKRID")
	private String ssrsWkrid;
	@Column(name = "CNTY_SPFCD")
	private String countySpfcd;
	@Column(name = "DTYWKR_IND")
	private String dutyWorkerInd;
	@Column(name = "FKCWSADDRT")
	private String fkcwsaddrt;
	@Column(name = "EMAIL_ADDR")
	private String emailAddress;

	public StaffPerson() {
		super();
	}
	


	@JsonCreator
	public StaffPerson(
			@JsonProperty("id") String id, 
			@JsonProperty("endDate") Date endDate, 
			@JsonProperty("firstName") String firstName,
			@JsonProperty("jobTitle") String jobTitle, 
			@JsonProperty("lastName") String lastName, 
			@JsonProperty("middleInitial") String middleInitial,
			@JsonProperty("namePrefix") String namePrefix, 
			@JsonProperty("phoneNumber") long phoneNumber, 
			@JsonProperty("phoneExt") int phoneExt, 
			@JsonProperty("startDate") Date startDate,
			@JsonProperty("sufxTldsc") String sufxTldsc, 
			@JsonProperty("tlcmtrInd") String tlcmtrInd, 
			@JsonProperty("lastUpdatedId") String lastUpdatedId,
			@JsonProperty("lastUpdatedTime") Date lastUpdatedTime, 
			@JsonProperty("fkcwsOfft") String fkcwsOfft, 
			@JsonProperty("avlocDsc") String avlocDsc,
			@JsonProperty("ssrsWkrid") String ssrsWkrid, 
			@JsonProperty("countySpfcd") String countySpfcd, 
			@JsonProperty("dutyWorkerInd") String dutyWorkerInd,
			@JsonProperty("fkcwsaddrt") String fkcwsaddrt, 
			@JsonProperty("emailAddress") String emailAddress) {
		super();
		this.id = id;
		this.endDate = endDate;
		this.firstName = firstName;
		this.jobTitle = jobTitle;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.namePrefix = namePrefix;
		this.phoneNumber = phoneNumber;
		this.phoneExt = phoneExt;
		this.startDate = startDate;
		this.sufxTldsc = sufxTldsc;
		this.tlcmtrInd = tlcmtrInd;
		this.lastUpdatedId = lastUpdatedId;
		this.lastUpdatedTime = lastUpdatedTime;
		this.fkcwsOfft = fkcwsOfft;
		this.avlocDsc = avlocDsc;
		this.ssrsWkrid = ssrsWkrid;
		this.countySpfcd = countySpfcd;
		this.dutyWorkerInd = dutyWorkerInd;
		this.fkcwsaddrt = fkcwsaddrt;
		this.emailAddress = emailAddress;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		return getId();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
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

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @return the namePrefix
	 */
	public String getNamePrefix() {
		return namePrefix;
	}

	/**
	 * @return the phoneNumber
	 */
	public long getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the phoneExt
	 */
	public int getPhoneExt() {
		return phoneExt;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the sufxTldsc
	 */
	public String getSufxTldsc() {
		return sufxTldsc;
	}

	/**
	 * @return the tlcmtrInd
	 */
	public String getTlcmtrInd() {
		return tlcmtrInd;
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
	 * @return the fkcwsOfft
	 */
	public String getFkcwsOfft() {
		return fkcwsOfft;
	}

	/**
	 * @return the avlocDsc
	 */
	public String getAvlocDsc() {
		return avlocDsc;
	}

	/**
	 * @return the ssrsWkrid
	 */
	public String getSsrsWkrid() {
		return ssrsWkrid;
	}

	/**
	 * @return the countySpfcd
	 */
	public String getCountySpfcd() {
		return countySpfcd;
	}

	/**
	 * @return the dutyWorkerInd
	 */
	public String getDutyWorkerInd() {
		return dutyWorkerInd;
	}

	/**
	 * @return the fkcwsaddrt
	 */
	public String getFkcwsaddrt() {
		return fkcwsaddrt;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
}
