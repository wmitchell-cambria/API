package gov.ca.cwds.rest.api.persistence.legacy;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

	@Id
	@Column(name = "IDENTIFIER")
	@NotEmpty
	@Length(min=3, max=3, message="length must be 3")
	private String id;
	
	@Type(type = "date")
	@Column(name = "END_DT")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="endDate")
	private Date endDate;
	
	@Transient
	@NotNull
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT)
	private String endDateCooked;
	
	@Column(name = "FIRST_NM")
	@NotEmpty
	@Length(min=1, max=20)
	private String firstName;
	
	@Column(name = "JOB_TL_DSC")
	@NotEmpty
	@Length(min=1, max=30)
	private String jobTitle;
	
	@Column(name = "LAST_NM")
	@NotEmpty
	@Length(min=1, max=25)
	private String lastName;
	
	@NotEmpty
	@Length(min=1, max=1, message="length must be 1")
	@Column(name = "MID_INI_NM")
	private String middleInitial;
	
	@Column(name = "NMPRFX_DSC")
	@NotEmpty
	@Length(min=1, max=6)
	private String namePrefix;
	
	@Column(name = "PHONE_NO")
	@NotNull
	private BigDecimal phoneNumber;
	
	@Column(name = "TEL_EXT_NO")
	@NotNull
	private int phoneExt;

	@Type(type = "date")
	@Column(name = "START_DT")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="startDate")
	private Date startDate;
	
	@Transient
	@NotNull
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT)
	private String startDateCooked;
	
	@Column(name = "SUFX_TLDSC")
	@NotEmpty
	@Length(min=1, max=4)
	private String sufxTldsc;
	
	@Column(name = "TLCMTR_IND")
	@NotEmpty
	@Length(min=1, max=1, message="length must be 1")
	@OneOf(value = {"Y", "N"}, ignoreCase = true, ignoreWhitespace = true)
	private String tlcmtrInd;

	@Column(name = "LST_UPD_ID")
	private String lastUpdatedId;
	
	@Type(type = "timestamp")
	@Column(name = "LST_UPD_TS")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=TIMESTAMP_FORMAT)
	private Date lastUpdatedTime;
	
	@Transient
	@NotNull
	@gov.ca.cwds.rest.validation.Date(format=TIMESTAMP_FORMAT)
	private String lastUpdatedTimeCooked;
	

	@Column(name = "FKCWS_OFFT")
	@NotEmpty
	@Length(min=1, max=10)
	private String fkcwsOfft;
	
	@Column(name = "AVLOC_DSC")
	@NotEmpty
	@Length(min=1, max=160)
	private String avlocDsc;

	@Column(name = "SSRS_WKRID")
	@NotEmpty
	@Length(min=1, max=4)
	private String ssrsWkrid;
	
	@Column(name = "CNTY_SPFCD")
	@NotEmpty
	@Length(min=1, max=2)
	private String countySpfcd;
	
	@Column(name = "DTYWKR_IND")
	@NotEmpty
	@Length(min=1, max=1, message="length must be 1")
	@OneOf(value = {"Y", "N"}, ignoreCase = true, ignoreWhitespace = true)
	private String dutyWorkerInd;
	
	@Column(name = "FKCWSADDRT")
	@NotEmpty
	@Length(min=1, max=10)
	private String fkcwsaddrt;
	
	@Column(name = "EMAIL_ADDR")
	@Length(min=1, max=50)
	//NOTE : The legacy system doesn't seem to enforce valid email addresses
	//@Email
	private String emailAddress;

	public StaffPerson() {
		super();
	}
	


	@JsonCreator
	public StaffPerson(
			@JsonProperty("id") String id, 
			@JsonProperty("endDate") String endDateCooked, 
			@JsonProperty("firstName") String firstName,
			@JsonProperty("jobTitle") String jobTitle, 
			@JsonProperty("lastName") String lastName, 
			@JsonProperty("middleInitial") String middleInitial,
			@JsonProperty("namePrefix") String namePrefix, 
			@JsonProperty("phoneNumber") BigDecimal phoneNumber, 
			@JsonProperty("phoneExt") int phoneExt, 
			@JsonProperty("startDate") String startDateCooked,
			@JsonProperty("sufxTldsc") String sufxTldsc, 
			@JsonProperty("tlcmtrInd") String tlcmtrInd, 
			@JsonProperty("lastUpdatedId") String lastUpdatedId,
			@JsonProperty("lastUpdatedTime") String lastUpdatedTimeCooked, 
			@JsonProperty("fkcwsOfft") String fkcwsOfft, 
			@JsonProperty("avlocDsc") String avlocDsc,
			@JsonProperty("ssrsWkrid") String ssrsWkrid, 
			@JsonProperty("countySpfcd") String countySpfcd, 
			@JsonProperty("dutyWorkerInd") String dutyWorkerInd,
			@JsonProperty("fkcwsaddrt") String fkcwsaddrt, 
			@JsonProperty("emailAddress") String emailAddress) {
		super();
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		this.id = id;
		
		this.firstName = firstName;
		this.jobTitle = jobTitle;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.namePrefix = namePrefix;
		this.phoneNumber = phoneNumber;
		this.phoneExt = phoneExt;
		this.endDateCooked = endDateCooked;
		//we are validating this.startDate so we can swallow this ParseException - should never happen
		try { this.endDate = df.parse(endDateCooked); } catch (ParseException e) {}

		this.startDateCooked = startDateCooked;
		//we are validating this.startDate so we can swallow this ParseException - should never happen
		try { this.startDate = df.parse(startDateCooked); } catch (ParseException e) {}
		this.sufxTldsc = sufxTldsc;
		this.tlcmtrInd = tlcmtrInd;
		this.lastUpdatedId = lastUpdatedId;
		this.lastUpdatedTimeCooked = lastUpdatedTimeCooked;
		//we are validating this.startDate so we can swallow this ParseException - should never happen
		try { this.lastUpdatedTime = df.parse(lastUpdatedTimeCooked); } catch (ParseException e) {}
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
	public BigDecimal getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the phoneExt
	 */
	public int getPhoneExt() {
		return phoneExt;
	}

	/**
	 * @return the startDateCooked
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
