 package gov.ca.cwds.rest.api.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@ApiModel
public class StaffPerson extends DomainObject {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	@NotEmpty
	@Size(min=3, max=3, message="size must be 3")
 	private String id;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="endDate")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String endDate;

	@NotEmpty
    @Size(min=1, max=20)
	private String firstName;
	
	@NotEmpty
	@Size(min=1, max=30)
	private String jobTitle;
	
	@NotEmpty
	@Size(min=1, max=25)
	private String lastName;
	
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String middleInitial;
	
	@NotEmpty
	@Size(min=1, max=6)
	private String namePrefix;
	
	@NotNull
	private BigDecimal phoneNumber;
	
	private int phoneExt = 0;

	@NotNull
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="startDate")
	private String startDate;
	
	@NotEmpty
	@Size(min=1, max=4)
	private String nameSuffix;
	
	@NotNull
	private Boolean telecommuterIndicator;

	@NotEmpty
	@Size(min=1, max=10)
	private String cwsOffice;
	
	@NotEmpty
	@Size(min=1, max=160)
	private String availabilityAndLocationDescription;

	@NotEmpty
	@Size(min=1, max=4)
	private String ssrsLicensingWorkerId;
	
	@NotEmpty
	@Size(min=1, max=2)
	private String countyCode;
	
	@NotNull
	private Boolean dutyWorkerIndicator;

	@NotEmpty
	@Size(min=1, max=10)
	private String cwsOfficeAddress;
	
	@Size(min=1, max=50)
	//NOTE : The legacy system doesn't seem to enforce valid email addresses
	//@Email
	private String emailAddress;

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
			@JsonProperty("telecommuterIndicator") Boolean telecommuterIndicator, 
			@JsonProperty("cwsOffice") String cwsOffice, 
			@JsonProperty("availabilityAndLocationDescription") String availabilityAndLocationDescription,
			@JsonProperty("ssrsLicensingWorkerId") String ssrsLicensingWorkerId, 
			@JsonProperty("countyCode") String countyCode, 
			@JsonProperty("dutyWorkerIndicator") Boolean dutyWorkerIndicator,
			@JsonProperty("cwsOfficeAddress") String cwsOfficeAddress, 
			@JsonProperty("emailAddress") String emailAddress) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.jobTitle = jobTitle;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.namePrefix = namePrefix;
		this.phoneNumber = phoneNumber;
		this.phoneExt = phoneExt;
		this.endDate = endDate;
		this.startDate = startDate;
		this.nameSuffix = nameSuffix;
		this.telecommuterIndicator = telecommuterIndicator;
		this.cwsOffice = cwsOffice;
		this.availabilityAndLocationDescription = availabilityAndLocationDescription;
		this.ssrsLicensingWorkerId = ssrsLicensingWorkerId;
		this.countyCode = countyCode;
		this.dutyWorkerIndicator = dutyWorkerIndicator;
		this.cwsOfficeAddress = cwsOfficeAddress;
		this.emailAddress = emailAddress;
	}
	
	public StaffPerson(gov.ca.cwds.rest.api.persistence.legacy.StaffPerson persistentObject) {
		this.id = persistentObject.getId();
		this.firstName = persistentObject.getFirstName();
		this.jobTitle = persistentObject.getJobTitle();
		this.lastName = persistentObject.getLastName();
		this.middleInitial = persistentObject.getMiddleInitial();
		this.namePrefix = persistentObject.getNamePrefix();
		this.phoneNumber = persistentObject.getPhoneNumber();
		this.phoneExt = persistentObject.getPhoneExt();
		this.endDate = DomainObject.cookDateString(persistentObject.getEndDate());
		this.startDate = DomainObject.cookDateString(persistentObject.getStartDate());
		this.nameSuffix = persistentObject.getNameSuffix();
		this.telecommuterIndicator = DomainObject.uncookBoolean(persistentObject.getTelecommuterIndicator());
		this.cwsOffice = persistentObject.getCwsOffice();
		this.availabilityAndLocationDescription = persistentObject.getAvailabilityAndLocationDescription();
		this.ssrsLicensingWorkerId = persistentObject.getSsrsLicensingWorkerId();
		this.countyCode = persistentObject.getCountyCode();
		this.dutyWorkerIndicator = DomainObject.uncookBoolean(persistentObject.getDutyWorkerIndicator());
		this.cwsOfficeAddress = persistentObject.getCwsOfficeAddress();
		this.emailAddress = persistentObject.getEmailAddress();
	}

	/**
	 * @return the id
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Aaeae9r0F4")
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
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-05-22", dataType="Date")
	public String getEndDate() {
		return endDate;
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
		return startDate;
	}
	
	/**
	 * @return the nameSuffix
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="SR.")
	public String getNameSuffix() {
		return nameSuffix;
	}

	/**
	 * @return the telecommuterIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false)
	public Boolean getTelecommuterIndicator() {
		return telecommuterIndicator;
	}

	/**
	 * @return the cwsOffice
	 */
	@ApiModelProperty(required=true, readOnly=true, value="IDENTIFIER of CWS_OFFT", example="def")
	public String getCwsOffice() {
		return cwsOffice;
	}

	/**
	 * @return the availabilityAndLocationDescription
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="some free form text")
	public String getAvailabilityAndLocationDescription() {
		return availabilityAndLocationDescription;
	}

	/**
	 * @return the ssrsLicensingWorkerId
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="9021")
	public String getSsrsLicensingWorkerId() {
		return ssrsLicensingWorkerId;
	}

	/**
	 * @return the countyCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="99")
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * @return the dutyWorkerIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false)
	public Boolean getDutyWorkerIndicator() {
		return dutyWorkerIndicator;
	}

	/**
	 * @return the cwsOfficeAddress
	 */
	@ApiModelProperty(required=true, readOnly=true, value="IDENTIFIER of CWSADDRT", example="ghi")
	public String getCwsOfficeAddress() {
		return cwsOfficeAddress;
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
				+ ((availabilityAndLocationDescription == null) ? 0 : availabilityAndLocationDescription.hashCode());
		result = prime * result
				+ ((countyCode == null) ? 0 : countyCode.hashCode());
		result = prime * result
				+ ((dutyWorkerIndicator == null) ? 0 : dutyWorkerIndicator.hashCode());
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((cwsOffice == null) ? 0 : cwsOffice.hashCode());
		result = prime * result
				+ ((cwsOfficeAddress == null) ? 0 : cwsOfficeAddress.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((middleInitial == null) ? 0 : middleInitial.hashCode());
		result = prime * result
				+ ((namePrefix == null) ? 0 : namePrefix.hashCode());
		result = prime * result + phoneExt;
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((ssrsLicensingWorkerId == null) ? 0 : ssrsLicensingWorkerId.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((nameSuffix == null) ? 0 : nameSuffix.hashCode());
		result = prime * result
				+ ((telecommuterIndicator == null) ? 0 : telecommuterIndicator.hashCode());
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
		if (telecommuterIndicator == null) {
			if (other.telecommuterIndicator != null)
				return false;
		} else if (!telecommuterIndicator.equals(other.telecommuterIndicator))
			return false;
		return true;
	}
	
	
}
