package gov.ca.cwds.rest.api.persistence;

import io.swagger.annotations.ApiModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * {@link PersistentObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "STFPERST")
@ApiModel
public class StaffPerson extends PersistentObject {

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
	@Column(name = "START_DT")
	private Date startDate;
	@Column(name = "SUFX_TLDSC")
	private String sufxTldsc;
	@Column(name = "TLCMTR_IND")
	private String tlcmtrInd;

	//
	@Column(name = "LST_UPD_ID")
	private String lastUpdatedId;
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
		super(null);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param endDate
	 * @param firstName
	 * @param jobTitle
	 * @param lastName
	 * @param middleInitial
	 * @param namePrefix
	 * @param phoneNumber
	 * @param phoneExt
	 * @param startDate
	 * @param sufxTldsc
	 * @param tlcmtrInd
	 * @param lastUpdatedId
	 * @param lastUpdatedTime
	 * @param fkcwsOfft
	 * @param avlocDsc
	 * @param ssrsWkrid
	 * @param countySpfcd
	 * @param dutyWorkerInd
	 * @param fkcwsaddrt
	 * @param emailAddress
	 */
	public StaffPerson(String id, Date endDate, String firstName,
			String jobTitle, String lastName, String middleInitial,
			String namePrefix, long phoneNumber, int phoneExt, Date startDate,
			String sufxTldsc, String tlcmtrInd, String lastUpdatedId,
			Date lastUpdatedTime, String fkcwsOfft, String avlocDsc,
			String ssrsWkrid, String countySpfcd, String dutyWorkerInd,
			String fkcwsaddrt, String emailAddress) {
		super(id);
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
