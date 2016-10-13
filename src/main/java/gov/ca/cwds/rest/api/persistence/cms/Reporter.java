package gov.ca.cwds.rest.api.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Reporter
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "REPTR_T")
public class Reporter extends PersistentObject {

	@Id
	@Column(name = "FKREFERL_T")
	private String referralId;

	@Column(name = "RPTR_BDGNO")
	private String badgeNumber;

	@Column(name = "RPTR_CTYNM")
	private String cityName;

	@Type(type = "short")
	@Column(name = "COL_RELC")
	private Short colltrClientRptrReltnshpType;

	@Type(type = "short")
	@Column(name = "CMM_MTHC")
	private Short communicationMethodType;

	@Column(name = "CNFWVR_IND")
	private String confidentialWaiverIndicator;

	@Column(name = "FDBACK_DOC")
	private String drmsMandatedRprtrFeedback;

	@Column(name = "RPTR_EMPNM")
	private String employerName;

	@Type(type = "date")
	@Column(name = "FEEDBCK_DT")
	private Date feedbackDate;

	@Column(name = "FB_RQR_IND")
	private String feedbackRequiredIndicator;

	@Column(name = "RPTR_FSTNM")
	private String firstName;

	@Column(name = "RPTR_LSTNM")
	private String lastName;

	@Column(name = "MNRPTR_IND")
	private String mandatedReporterIndicator;

	@Type(type = "integer")
	@Column(name = "MSG_EXT_NO")
	private Integer messagePhoneExtensionNumber;

	@Column(name = "MSG_TEL_NO")
	private BigDecimal messagePhoneNumber;

	@Column(name = "MID_INI_NM")
	private String middleInitialName;

	@Column(name = "NMPRFX_DSC")
	private String namePrefixDescription;

	@Column(name = "PRM_TEL_NO")
	private BigDecimal primaryPhoneNumber;

	@Type(type = "integer")
	@Column(name = "PRM_EXT_NO")
	private Integer primaryPhoneExtensionNumber;

	@Type(type = "short")
	@Column(name = "STATE_C")
	private Short stateCodeType;

	@Column(name = "RPTR_ST_NM")
	private String streetName;

	@Column(name = "RPTR_ST_NO")
	private String streetNumber;

	@Column(name = "SUFX_TLDSC")
	private String suffixTitleDescription;

	@Type(type = "integer")
	@Column(name = "RPTR_ZIPNO")
	private Integer zipNumber;

	@Column(name = "FKLAW_ENFT")
	private String lawEnforcementId;

	@Type(type = "short")
	@Column(name = "ZIP_SFX_NO")
	private Short zipSuffixNumber;

	@Column(name = "CNTY_SPFCD")
	private String countySpecificCode;

	/**
	 * Default constructor
	 * 
	 * Required for Hibernate
	 */
	public Reporter() {
		super();
	}

	public Reporter(String referralId, String badgeNumber, String cityName, Short colltrClientRptrReltnshpType,
			Short communicationMethodType, String confidentialWaiverIndicator, String drmsMandatedRprtrFeedback,
			String employerName, Date feedbackDate, String feedbackRequiredIndicator, String firstName, String lastName,
			String mandatedReporterIndicator, Integer messagePhoneExtensionNumber, BigDecimal messagePhoneNumber,
			String middleInitialName, String namePrefixDescription, BigDecimal primaryPhoneNumber,
			Integer primaryPhoneExtensionNumber, Short stateCodeType, String streetName, String streetNumber,
			String suffixTitleDescription, Integer zipNumber, String lawEnforcementId, Short zipSuffixNumber,
			String countySpecificCode) {
		super();
		this.referralId = referralId;
		this.badgeNumber = badgeNumber;
		this.cityName = cityName;
		this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
		this.communicationMethodType = communicationMethodType;
		this.confidentialWaiverIndicator = confidentialWaiverIndicator;
		this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
		this.employerName = employerName;
		this.feedbackDate = feedbackDate;
		this.feedbackRequiredIndicator = feedbackRequiredIndicator;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mandatedReporterIndicator = mandatedReporterIndicator;
		this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
		this.messagePhoneNumber = messagePhoneNumber;
		this.middleInitialName = middleInitialName;
		this.namePrefixDescription = namePrefixDescription;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
		this.stateCodeType = stateCodeType;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.suffixTitleDescription = suffixTitleDescription;
		this.zipNumber = zipNumber;
		this.lawEnforcementId = lawEnforcementId;
		this.zipSuffixNumber = zipSuffixNumber;
		this.countySpecificCode = countySpecificCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		return getReferralId();
	}

	/**
	 * @return the referralId
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * @return the badgeNumber
	 */
	public String getBadgeNumber() {
		return badgeNumber;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @return the colltrClientRptrReltnshpType
	 */
	public Short getColltrClientRptrReltnshpType() {
		return colltrClientRptrReltnshpType;
	}

	/**
	 * @return the communicationMethodType
	 */
	public Short getCommunicationMethodType() {
		return communicationMethodType;
	}

	/**
	 * @return the confidentialWaiverIndicator
	 */
	public String getConfidentialWaiverIndicator() {
		return confidentialWaiverIndicator;
	}

	/**
	 * @return the drmsMandatedRprtrFeedback
	 */
	public String getDrmsMandatedRprtrFeedback() {
		return drmsMandatedRprtrFeedback;
	}

	/**
	 * @return the employerName
	 */
	public String getEmployerName() {
		return employerName;
	}

	/**
	 * @return the feedbackDate
	 */
	public Date getFeedbackDate() {
		return feedbackDate;
	}

	/**
	 * @return the feedbackRequiredIndicator
	 */
	public String getFeedbackRequiredIndicator() {
		return feedbackRequiredIndicator;
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
	 * @return the mandatedReporterIndicator
	 */
	public String getMandatedReporterIndicator() {
		return mandatedReporterIndicator;
	}

	/**
	 * @return the messagePhoneExtensionNumber
	 */
	public Integer getMessagePhoneExtensionNumber() {
		return messagePhoneExtensionNumber;
	}

	/**
	 * @return the messagePhoneNumber
	 */
	public BigDecimal getMessagePhoneNumber() {
		return messagePhoneNumber;
	}

	/**
	 * @return the middleInitialName
	 */
	public String getMiddleInitialName() {
		return middleInitialName;
	}

	/**
	 * @return the namePrefixDescription
	 */
	public String getNamePrefixDescription() {
		return namePrefixDescription;
	}

	/**
	 * @return the primaryPhoneNumber
	 */
	public BigDecimal getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}

	/**
	 * @return the primaryPhoneExtensionNumber
	 */
	public Integer getPrimaryPhoneExtensionNumber() {
		return primaryPhoneExtensionNumber;
	}

	/**
	 * @return the stateCodeType
	 */
	public Short getStateCodeType() {
		return stateCodeType;
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @return the streetNumber
	 */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * @return the suffixTitleDescription
	 */
	public String getSuffixTitleDescription() {
		return suffixTitleDescription;
	}

	/**
	 * @return the zipNumber
	 */
	public Integer getZipNumber() {
		return zipNumber;
	}

	/**
	 * @return the lawEnforcementId
	 */
	public String getLawEnforcementId() {
		return lawEnforcementId;
	}

	/**
	 * @return the zipSuffixNumber
	 */
	public Short getZipSuffixNumber() {
		return zipSuffixNumber;
	}

	/**
	 * @return the countySpecificCode
	 */
	public String getCountySpecificCode() {
		return countySpecificCode;
	}
}
