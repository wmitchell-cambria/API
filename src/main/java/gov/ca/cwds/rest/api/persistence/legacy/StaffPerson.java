package gov.ca.cwds.rest.api.persistence.legacy;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link PersistentObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "STFPERST")
@ApiModel
public class StaffPerson extends PersistentObject {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

	@Id
	@Column(name = "IDENTIFIER")
	@NotEmpty
	@Size(min=3, max=3, message="size must be 3")
 	private String id;
	
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="endDate")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String endDate;

	@Type(type = "date")
	@Column(name = "END_DT")
	private Date endDatePersistable;
	
	@Column(name = "FIRST_NM")
	@NotEmpty
    @Size(min=1, max=20)
	private String firstName;
	
	@Column(name = "JOB_TL_DSC")
	@NotEmpty
	@Size(min=1, max=30)
	private String jobTitle;
	
	@Column(name = "LAST_NM")
	@NotEmpty
	@Size(min=1, max=25)
	private String lastName;
	
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	@Column(name = "MID_INI_NM")
	private String middleInitial;
	
	@Column(name = "NMPRFX_DSC")
	@NotEmpty
	@Size(min=1, max=6)
	private String namePrefix;
	
	@Column(name = "PHONE_NO")
	@NotNull
	private BigDecimal phoneNumber;
	
	@Column(name = "TEL_EXT_NO")
	private int phoneExt = 0;

	@Type(type = "date")
	@Column(name = "START_DT")
	private Date startDatePersistable;
	
	@Transient
	@NotNull
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="startDate")
	private String startDate;
	
	@Column(name = "SUFX_TLDSC")
	@NotEmpty
	@Size(min=1, max=4)
	private String nameSuffix;
	
	@NotNull
	@Transient
	private Boolean tlcmtrInd;

	@Column(name = "TLCMTR_IND")
	private String tlcmtrIndPersistable;
	
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
	
	@Column(name = "FKCWS_OFFT")
	@NotEmpty
	@Size(min=1, max=10)
	private String fkcwsOfft;
	
	@Column(name = "AVLOC_DSC")
	@NotEmpty
	@Size(min=1, max=160)
	private String avlocDsc;

	@Column(name = "SSRS_WKRID")
	@NotEmpty
	@Size(min=1, max=4)
	private String ssrsWkrid;
	
	@Column(name = "CNTY_SPFCD")
	@NotEmpty
	@Size(min=1, max=2)
	private String countySpfcd;
	
	@NotNull
	@Transient
	private Boolean dutyWorkerInd;

	@Column(name = "DTYWKR_IND")
	private String dutyWorkerIndPersistable;
	
	@Column(name = "FKCWSADDRT")
	@NotEmpty
	@Size(min=1, max=10)
	private String fkcwsaddrt;
	
	@Column(name = "EMAIL_ADDR")
	@Size(min=1, max=50)
	//NOTE : The legacy system doesn't seem to enforce valid email addresses
	//@Email
	private String emailAddress;

	public StaffPerson() {
		super();
	}
	
	@JsonCreator
	public StaffPerson(
			@JsonProperty("id") String id, 
			@JsonProperty("endDate") String endDate, 
			@JsonProperty("firstName") String firstName,
			@JsonProperty("jobTitle") String jobTitle, 
			@JsonProperty("lastName") String lastName, 
			@JsonProperty("middleInitial") String middleInitial,
			@JsonProperty("namePrefix") String namePrefix, 
			@JsonProperty("phoneNumber") BigDecimal phoneNumber, 
			@JsonProperty("phoneExt") int phoneExt, 
			@JsonProperty("startDate") String startDate,
			@JsonProperty("nameSuffix") String nameSuffix, 
			@JsonProperty("tlcmtrInd") Boolean tlcmtrInd, 
			@JsonProperty("lastUpdatedId") String lastUpdatedId,
			@JsonProperty("lastUpdatedTime") String lastUpdatedTime, 
			@JsonProperty("fkcwsOfft") String fkcwsOfft, 
			@JsonProperty("avlocDsc") String avlocDsc,
			@JsonProperty("ssrsWkrid") String ssrsWkrid, 
			@JsonProperty("countySpfcd") String countySpfcd, 
			@JsonProperty("dutyWorkerInd") Boolean dutyWorkerInd,
			@JsonProperty("fkcwsaddrt") String fkcwsaddrt, 
			@JsonProperty("emailAddress") String emailAddress) {
		super();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		DateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		
		this.id = id;
		
		this.firstName = firstName;
		this.jobTitle = jobTitle;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.namePrefix = namePrefix;
		this.phoneNumber = phoneNumber;
		this.phoneExt = phoneExt;
		this.endDate = endDate;
		//we are validating this.startDate so we can swallow this ParseException - should never happen
		try { this.endDatePersistable = dateFormat.parse(endDate); } catch (Throwable e) {}

		this.startDate = startDate;
		//we are validating this.startDate so we can swallow this ParseException - should never happen
		try { this.startDatePersistable = dateFormat.parse(startDate); } catch (Throwable e) {}
		this.nameSuffix = nameSuffix;
		this.tlcmtrInd = tlcmtrInd;
		this.tlcmtrIndPersistable = persistableBoolean(tlcmtrInd);
		this.lastUpdatedId = lastUpdatedId;
		this.lastUpdatedTime = lastUpdatedTime;
		//we are validating this.startDate so we can swallow this ParseException - should never happen
		try { this.lastUpdatedTimePersistable = timestampFormat.parse(lastUpdatedTime); } catch (Throwable e) {}
		this.fkcwsOfft = fkcwsOfft;
		this.avlocDsc = avlocDsc;
		this.ssrsWkrid = ssrsWkrid;
		this.countySpfcd = countySpfcd;
		this.dutyWorkerInd = dutyWorkerInd;
		this.dutyWorkerIndPersistable = persistableBoolean(dutyWorkerInd);
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
	@ApiModelProperty(required=true, readOnly=false, value="", example="abc")
	public String getId() {
		return id;
	}

	/**
	 * @return the firstName
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="John")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Smith")
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the middleInitial
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getMiddleInitial() {
		return middleInitial;
	}

	/**
	 * @return the endDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-05-22")
	public String getEndDate() {
		return !Strings.isNullOrEmpty(endDate) ? endDate : endDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(endDatePersistable)) : "";
	}

	/**
	 * @return the jobTitle
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Case Worker")
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @return the namePrefix
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="MR.")
	public String getNamePrefix() {
		return namePrefix;
	}

	/**
	 * @return the phoneNumber
	 */
	@ApiModelProperty(required=true, readOnly=false, example="9165551212")
	public BigDecimal getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the phoneExt
	 */
	@ApiModelProperty(required=true, readOnly=false, example="123")
	public int getPhoneExt() {
		return phoneExt;
	}

	/**
	 * @return the startDate
	 */
	@ApiModelProperty(required=true, readOnly=false, value="yyyy-MM-dd", example="1963-11-22")
	public String getStartDate() {
		return !Strings.isNullOrEmpty(startDate) ? startDate : startDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(startDatePersistable)) : "";
	}
	
	/**
	 * @return the nameSuffix
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="SR.")
	public String getNameSuffix() {
		return nameSuffix;
	}

	/**
	 * @return the tlcmtrInd
	 */
	@ApiModelProperty(required=true, readOnly=false)
	public Boolean getTlcmtrInd() {
		return super.cookedBoolean(tlcmtrInd, tlcmtrIndPersistable);
	}

	/**
	 * @return the lastUpdatedId
	 */
	@ApiModelProperty(readOnly=true, value="remove this from view of client, generated at business layer")
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@ApiModelProperty(required=true, readOnly=true, value="remove from view of user", example="1963-11-22")
	public String getLastUpdatedTime() {
		return !Strings.isNullOrEmpty(lastUpdatedTime) ? lastUpdatedTime : lastUpdatedTimePersistable != null ? ( (new SimpleDateFormat(TIMESTAMP_FORMAT)).format(lastUpdatedTimePersistable)) : "";
	}

	/**
	 * @return the fkcwsOfft
	 */
	@ApiModelProperty(required=true, readOnly=true, value="IDENTIFIER of CWS_OFFT", example="def")
	public String getFkcwsOfft() {
		return fkcwsOfft;
	}

	/**
	 * @return the avlocDsc
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="some free form text")
	public String getAvlocDsc() {
		return avlocDsc;
	}

	/**
	 * @return the ssrsWkrid
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="9021")
	public String getSsrsWkrid() {
		return ssrsWkrid;
	}

	/**
	 * @return the countySpfcd
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="13")
	public String getCountySpfcd() {
		return countySpfcd;
	}

	/**
	 * @return the dutyWorkerInd
	 */
	@ApiModelProperty(required=true, readOnly=false)
	public Boolean getDutyWorkerInd() {
		return super.cookedBoolean(dutyWorkerInd, dutyWorkerIndPersistable);
	}

	/**
	 * @return the fkcwsaddrt
	 */
	@ApiModelProperty(required=true, readOnly=true, value="IDENTIFIER of CWSADDRT", example="ghi")
	public String getFkcwsaddrt() {
		return fkcwsaddrt;
	}

	/**
	 * @return the emailAddress
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="john.q.smith@somedomain.com")
	public String getEmailAddress() {
		return emailAddress;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avlocDsc == null) ? 0 : avlocDsc.hashCode());
		result = prime * result
				+ ((countySpfcd == null) ? 0 : countySpfcd.hashCode());
		result = prime * result
				+ ((dutyWorkerInd == null) ? 0 : dutyWorkerInd.hashCode());
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((fkcwsOfft == null) ? 0 : fkcwsOfft.hashCode());
		result = prime * result
				+ ((fkcwsaddrt == null) ? 0 : fkcwsaddrt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((lastUpdatedId == null) ? 0 : lastUpdatedId.hashCode());
		result = prime * result
				+ ((lastUpdatedTime == null) ? 0 : lastUpdatedTime.hashCode());
		result = prime * result
				+ ((middleInitial == null) ? 0 : middleInitial.hashCode());
		result = prime * result
				+ ((namePrefix == null) ? 0 : namePrefix.hashCode());
		result = prime * result + phoneExt;
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((ssrsWkrid == null) ? 0 : ssrsWkrid.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((nameSuffix == null) ? 0 : nameSuffix.hashCode());
		result = prime * result
				+ ((tlcmtrInd == null) ? 0 : tlcmtrInd.hashCode());
		return result;
	}



	/* (non-Javadoc)
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
		StaffPerson other = (StaffPerson) obj;
		if (avlocDsc == null) {
			if (other.avlocDsc != null)
				return false;
		} else if (!avlocDsc.equals(other.avlocDsc))
			return false;
		if (countySpfcd == null) {
			if (other.countySpfcd != null)
				return false;
		} else if (!countySpfcd.equals(other.countySpfcd))
			return false;
		if (dutyWorkerInd == null) {
			if (other.dutyWorkerInd != null)
				return false;
		} else if (!dutyWorkerInd.equals(other.dutyWorkerInd))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fkcwsOfft == null) {
			if (other.fkcwsOfft != null)
				return false;
		} else if (!fkcwsOfft.equals(other.fkcwsOfft))
			return false;
		if (fkcwsaddrt == null) {
			if (other.fkcwsaddrt != null)
				return false;
		} else if (!fkcwsaddrt.equals(other.fkcwsaddrt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (lastUpdatedId == null) {
			if (other.lastUpdatedId != null)
				return false;
		} else if (!lastUpdatedId.equals(other.lastUpdatedId))
			return false;
		if (lastUpdatedTime == null) {
			if (other.lastUpdatedTime != null)
				return false;
		} else if (!lastUpdatedTime.equals(other.lastUpdatedTime))
			return false;
		if (middleInitial == null) {
			if (other.middleInitial != null)
				return false;
		} else if (!middleInitial.equals(other.middleInitial))
			return false;
		if (namePrefix == null) {
			if (other.namePrefix != null)
				return false;
		} else if (!namePrefix.equals(other.namePrefix))
			return false;
		if (phoneExt != other.phoneExt)
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (ssrsWkrid == null) {
			if (other.ssrsWkrid != null)
				return false;
		} else if (!ssrsWkrid.equals(other.ssrsWkrid))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (nameSuffix == null) {
			if (other.nameSuffix != null)
				return false;
		} else if (!nameSuffix.equals(other.nameSuffix))
			return false;
		if (tlcmtrInd == null) {
			if (other.tlcmtrInd != null)
				return false;
		} else if (!tlcmtrInd.equals(other.tlcmtrInd))
			return false;
		return true;
	}
	
	
}
