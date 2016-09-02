 package gov.ca.cwds.rest.api.persistence.legacy;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "STFPERST")
public class StaffPerson extends PersistentObject {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";

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
	private BigDecimal phoneNumber;
	
	@Column(name = "TEL_EXT_NO")
	private int phoneExt = 0;

	@Type(type = "date")
	@Column(name = "START_DT")
	private Date startDate;
	
	@Column(name = "SUFX_TLDSC")
	private String nameSuffix;
	
	@Column(name = "TLCMTR_IND")
	private String telecommuterIndicator;
	
	@Column(name = "FKCWS_OFFT")
	private String cwsOffice;
	
	@Column(name = "AVLOC_DSC")
	private String availabilityAndLocationDescription;

	@Column(name = "SSRS_WKRID")
	private String ssrsLicensingWorkerId;
	
	@Column(name = "CNTY_SPFCD")
	private String countyCode;
	
	@Column(name = "DTYWKR_IND")
	private String dutyWorkerIndicator;
	
	@Column(name = "FKCWSADDRT")
	private String cwsOfficeAddress;
	
	@Column(name = "EMAIL_ADDR")
	private String emailAddress;
	
	/**
	 * Default constructor 
	 * 
	 * Required for Hibernate
	 */
	public StaffPerson() {
		super();
	}

	/**
	 * Constructor 
	 * 
	 * @param staffPerson The domain object to construct this object from 
	 * @param lastUpdatedId  the id of the last person to update this object
	 */
	public StaffPerson(gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson, String lastUpdatedId) {
		super(lastUpdatedId);
		
		try {
			this.id = staffPerson.getId();
			this.firstName = staffPerson.getFirstName();
			this.jobTitle = staffPerson.getJobTitle();
			this.lastName = staffPerson.getLastName();
			this.middleInitial = staffPerson.getMiddleInitial();
			this.namePrefix = staffPerson.getNamePrefix();
			this.phoneNumber = staffPerson.getPhoneNumber();
			this.phoneExt = staffPerson.getPhoneExt();
			this.endDate = DomainObject.uncookDateString(staffPerson.getEndDate());
			this.startDate = DomainObject.uncookDateString(staffPerson.getStartDate());
			this.nameSuffix = staffPerson.getNameSuffix();
			this.telecommuterIndicator = DomainObject.cookBoolean(staffPerson.getTelecommuterIndicator());
			this.cwsOffice = staffPerson.getCwsOffice();
			this.availabilityAndLocationDescription = staffPerson.getAvailabilityAndLocationDescription();
			this.ssrsLicensingWorkerId = staffPerson.getSsrsLicensingWorkerId();
			this.countyCode = staffPerson.getCountyCode();
			this.dutyWorkerIndicator = DomainObject.cookBoolean(staffPerson.getDutyWorkerIndicator());
			this.cwsOfficeAddress = staffPerson.getCwsOfficeAddress();
			this.emailAddress = staffPerson.getEmailAddress();
		} catch (DomainException e) {
			throw new PersistenceException(e);
		}
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * @return the nameSuffix
	 */
	public String getNameSuffix() {
		return nameSuffix;
	}

	/**
	 * @return the telecommuterIndicator
	 */
	public String getTelecommuterIndicator() {
		return telecommuterIndicator;
	}

	/**
	 * @return the cwsOffice
	 */
	public String getCwsOffice() {
		return cwsOffice;
	}

	/**
	 * @return the availabilityAndLocationDescription
	 */
	public String getAvailabilityAndLocationDescription() {
		return availabilityAndLocationDescription;
	}

	/**
	 * @return the ssrsLicensingWorkerId
	 */
	public String getSsrsLicensingWorkerId() {
		return ssrsLicensingWorkerId;
	}

	/**
	 * @return the countyCode
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * @return the dutyWorkerIndicator
	 */
	public String getDutyWorkerIndicator() {
		return dutyWorkerIndicator;
	}

	/**
	 * @return the cwsOfficeAddress
	 */
	public String getCwsOfficeAddress() {
		return cwsOfficeAddress;
	}

	/**
	 * @return the emailAddress
	 */
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
				+ ((availabilityAndLocationDescription == null) ? 0 : availabilityAndLocationDescription.hashCode());
		result = prime * result + ((countyCode == null) ? 0 : countyCode.hashCode());
		result = prime * result + ((cwsOffice == null) ? 0 : cwsOffice.hashCode());
		result = prime * result + ((cwsOfficeAddress == null) ? 0 : cwsOfficeAddress.hashCode());
		result = prime * result + ((dutyWorkerIndicator == null) ? 0 : dutyWorkerIndicator.hashCode());
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleInitial == null) ? 0 : middleInitial.hashCode());
		result = prime * result + ((namePrefix == null) ? 0 : namePrefix.hashCode());
		result = prime * result + ((nameSuffix == null) ? 0 : nameSuffix.hashCode());
		result = prime * result + phoneExt;
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((ssrsLicensingWorkerId == null) ? 0 : ssrsLicensingWorkerId.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((telecommuterIndicator == null) ? 0 : telecommuterIndicator.hashCode());
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
		if (availabilityAndLocationDescription == null) {
			if (other.availabilityAndLocationDescription != null)
				return false;
		} else if (!availabilityAndLocationDescription.equals(other.availabilityAndLocationDescription))
			return false;
		if (countyCode == null) {
			if (other.countyCode != null)
				return false;
		} else if (!countyCode.equals(other.countyCode))
			return false;
		if (cwsOffice == null) {
			if (other.cwsOffice != null)
				return false;
		} else if (!cwsOffice.equals(other.cwsOffice))
			return false;
		if (cwsOfficeAddress == null) {
			if (other.cwsOfficeAddress != null)
				return false;
		} else if (!cwsOfficeAddress.equals(other.cwsOfficeAddress))
			return false;
		if (dutyWorkerIndicator == null) {
			if (other.dutyWorkerIndicator != null)
				return false;
		} else if (!dutyWorkerIndicator.equals(other.dutyWorkerIndicator))
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
		if (nameSuffix == null) {
			if (other.nameSuffix != null)
				return false;
		} else if (!nameSuffix.equals(other.nameSuffix))
			return false;
		if (phoneExt != other.phoneExt)
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (ssrsLicensingWorkerId == null) {
			if (other.ssrsLicensingWorkerId != null)
				return false;
		} else if (!ssrsLicensingWorkerId.equals(other.ssrsLicensingWorkerId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (telecommuterIndicator == null) {
			if (other.telecommuterIndicator != null)
				return false;
		} else if (!telecommuterIndicator.equals(other.telecommuterIndicator))
			return false;
		return true;
	}	
}
