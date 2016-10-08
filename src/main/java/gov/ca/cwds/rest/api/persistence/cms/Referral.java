package gov.ca.cwds.rest.api.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Referral
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "REFERL_T")
public class Referral extends PersistentObject {
	@Id
	@Column(name = "IDENTIFIER")
	private String id;

	@Column(name = "ADD_INF_CD")
	private String additionalInfoIncludedCode;

	@Column(name = "ANRPTR_IND")
	private String anonymousReporterIndicator;

	@Column(name = "PETAPL_IND")
	private String applicationForPetitionIndicator;

	@Column(name = "APRVL_NO")
	private String approvalNumber;

	@Type(type = "short")
	@Column(name = "APV_STC")
	private Short approvalStatusType;

	@Column(name = "CR_PERP_CD")
	private String caretakersPerpetratorCode;

	@Type(type = "date")
	@Column(name = "REFCLSR_DT")
	private Date closureDate;

	@Type(type = "short")
	@Column(name = "CMM_MTHC")
	private Short communicationMethodType;

	@Column(name = "CHILOC_TXT")
	private String currentLocationOfChildren;

	@Column(name = "ALGDSC_DOC")
	private String drmsAllegationDescriptionDoc;

	@Column(name = "ER_REF_DOC")
	private String drmsErReferralDoc;

	@Column(name = "INVSTG_DOC")
	private String drmsInvestigationDoc;

	@Column(name = "XRPT_LWIND")
	private String filedSuspectedChildAbuseReporttoLawEnforcementIndicator;

	@Column(name = "FMY_AW_IND")
	private String familyAwarenessIndicator;

	@Type(type = "short")
	@Column(name = "GVR_ENTC")
	private Short govtEntityType;

	@Column(name = "LGL_DEF_CD")
	private String legalDefinitionCode;

	@Column(name = "LGLRGT_IND")
	private String legalRightsNoticeIndicator;

	@Column(name = "LMT_ACSSCD")
	private String limitedAccessCode;

	@Type(type = "date")
	@Column(name = "XRPT_RCVDT")
	private Date mandatedCrossReportReceivedDate;

	@Column(name = "REFERRL_NM")
	private String referralName;

	@Column(name = "ADQT_CS_CD")
	private String openAdequateCaseCode;

	@Type(type = "date")
	@Column(name = "REF_RCV_DT")
	private Date receivedDate;

	@Type(type = "time")
	@Column(name = "REF_RCV_TM")
	private Date receivedTime;

	@Type(type = "short")
	@Column(name = "RFR_RSPC")
	private Short referralResponseType;

	@Type(type = "short")
	@Column(name = "RFRD_RSC")
	private Short referredToResourceType;

	@Type(type = "date")
	@Column(name = "RSP_DTR_DT")
	private Date responseDeterminationDate;

	@Type(type = "time")
	@Column(name = "RSP_DTR_TM")
	private Date responseDeterminationTime;

	@Column(name = "RSP_RTNTXT")
	private String responseRationaleText;

	@Column(name = "SCN_NT_TXT")
	private String screenerNoteText;

	@Column(name = "SP_INCL_CD")
	private String specificsIncludedCode;

	@Column(name = "SFC_INF_CD")
	private String sufficientInformationCode;

	@Column(name = "UNFD_SR_CD")
	private String unfoundedSeriesCode;

	@Column(name = "FKREFERL_T")
	private String linkToPrimaryReferralId;

	@Column(name = "FKADDRS_T")
	private String allegesAbuseOccurredAtAddressId;

	@Column(name = "FKSTFPERS0")
	private String firstResponseDeterminedByStaffPersonId;

	@Column(name = "FKSTFPERST")
	private String primaryContactStaffPersonId;

	@Column(name = "CNTY_SPFCD")
	private String countySpecificCode;

	@Column(name = "SPRJRF_IND")
	private String specialProjectReferralIndicator;

	@Column(name = "ZIPPY_IND")
	private String zippyCreatedIndicator;

	@Column(name = "HOMLES_IND")
	private String homelessIndicator;

	@Column(name = "FAMREF_IND")
	private String familyRefusedServicesIndicator;

	@Type(type = "date")
	@Column(name = "FIRST_EODT")
	private Date firstEvaluatedOutApprovalDate;

	@Column(name = "RSP_AGY_CD")
	private String responsibleAgencyCode;

	@Type(type = "short")
	@Column(name = "L_GVR_ENTC")
	private Short limitedAccessGovtAgencyType;

	@Type(type = "date")
	@Column(name = "LMT_ACS_DT")
	private Date limitedAccessDate;

	@Column(name = "LMT_ACSDSC")
	private String limitedAccessDesc;

	@Type(type = "date")
	@Column(name = "ORIGCLS_DT")
	private Date originalClosureDate;

	/**
	 * Default constructor
	 * 
	 * Required for Hibernate
	 */
	public Referral() {
		super();
	}

	public Referral(String id, String additionalInfoIncludedCode, String anonymousReporterIndicator, String applicationForPetitionIndicator, String approvalNumber, Short approvalStatusType,
			
			String caretakersPerpetratorCode, Date closureDate, Short communicationMethodType,String currentLocationOfChildren, String drmsAllegationDescriptionDoc, String drmsErReferralDoc,
			
			String drmsInvestigationDoc, String filedSuspectedChildAbuseReporttoLawEnforcementIndicator, String familyAwarenessIndicator, Short govtEntityType, String legalDefinitionCode, String legalRightsNoticeIndicator, 
			
			String limitedAccessCode, Date mandatedCrossReportReceivedDate, String referralName, String openAdequateCaseCode, Date receivedDate, Date receivedTime,
			
			Short referralResponseType, Short referredToResourceType, Date responseDeterminationDate, Date responseDeterminationTime, String responseRationaleText, String screenerNoteText,
			
			String specificsIncludedCode, String sufficientInformationCode, String unfoundedSeriesCode, String linkToPrimaryReferralId, String allegesAbuseOccurredAtAddressId, String firstResponseDeterminedByStaffPersonId, 
			
			String primaryContactStaffPersonId, String countySpecificCode, String specialProjectReferralIndicator, String zippyCreatedIndicator, String homelessIndicator, String familyRefusedServicesIndicator, 
			
			Date firstEvaluatedOutApprovalDate, String responsibleAgencyCode, Short limitedAccessGovtAgencyType, Date limitedAccessDate, String limitedAccessDesc, Date originalClosureDate) {
		super();
		this.id = id;
		this.additionalInfoIncludedCode = additionalInfoIncludedCode;
		this.anonymousReporterIndicator = anonymousReporterIndicator;
		this.applicationForPetitionIndicator = applicationForPetitionIndicator;
		this.approvalNumber = approvalNumber;
		this.approvalStatusType = approvalStatusType;
		this.caretakersPerpetratorCode = caretakersPerpetratorCode;
		this.closureDate = closureDate;
		this.communicationMethodType = communicationMethodType;
		this.currentLocationOfChildren = currentLocationOfChildren;
		this.drmsAllegationDescriptionDoc = drmsAllegationDescriptionDoc;
		this.drmsErReferralDoc = drmsErReferralDoc;
		this.drmsInvestigationDoc = drmsInvestigationDoc;
		this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator = filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
		this.familyAwarenessIndicator = familyAwarenessIndicator;
		this.govtEntityType = govtEntityType;
		this.legalDefinitionCode = legalDefinitionCode;
		this.legalRightsNoticeIndicator = legalRightsNoticeIndicator;
		this.limitedAccessCode = limitedAccessCode;
		this.mandatedCrossReportReceivedDate = mandatedCrossReportReceivedDate;
		this.referralName = referralName;
		this.openAdequateCaseCode = openAdequateCaseCode;
		this.receivedDate = receivedDate;
		this.receivedTime = receivedTime;
		this.referralResponseType = referralResponseType;
		this.referredToResourceType = referredToResourceType;
		this.responseDeterminationDate = responseDeterminationDate;
		this.responseDeterminationTime = responseDeterminationTime;
		this.responseRationaleText = responseRationaleText;
		this.screenerNoteText = screenerNoteText;
		this.specificsIncludedCode = specificsIncludedCode;
		this.sufficientInformationCode = sufficientInformationCode;
		this.unfoundedSeriesCode = unfoundedSeriesCode;
		this.linkToPrimaryReferralId = linkToPrimaryReferralId;
		this.allegesAbuseOccurredAtAddressId = allegesAbuseOccurredAtAddressId;
		this.firstResponseDeterminedByStaffPersonId = firstResponseDeterminedByStaffPersonId;
		this.primaryContactStaffPersonId = primaryContactStaffPersonId;
		this.countySpecificCode = countySpecificCode;
		this.specialProjectReferralIndicator = specialProjectReferralIndicator;
		this.zippyCreatedIndicator = zippyCreatedIndicator;
		this.homelessIndicator = homelessIndicator;
		this.familyRefusedServicesIndicator = familyRefusedServicesIndicator;
		this.firstEvaluatedOutApprovalDate = firstEvaluatedOutApprovalDate;
		this.responsibleAgencyCode = responsibleAgencyCode;
		this.limitedAccessGovtAgencyType = limitedAccessGovtAgencyType;
		this.limitedAccessDate = limitedAccessDate;
		this.limitedAccessDesc = limitedAccessDesc;
		this.originalClosureDate = originalClosureDate;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		return StringUtils.trimToEmpty(id);
	}

	/**
	 * @return the additionalInfoIncludedCode
	 */
	public String getAdditionalInfoIncludedCode() {
		return StringUtils.trimToEmpty(additionalInfoIncludedCode);
	}

	/**
	 * @return the anonymousReporterIndicator
	 */
	public String getAnonymousReporterIndicator() {
		return StringUtils.trimToEmpty(anonymousReporterIndicator);
	}

	/**
	 * @return the applicationForPetitionIndicator
	 */
	public String getApplicationForPetitionIndicator() {
		return StringUtils.trimToEmpty(applicationForPetitionIndicator);
	}

	/**
	 * @return the approvalNumber
	 */
	public String getApprovalNumber() {
		return StringUtils.trimToEmpty(approvalNumber);
	}

	/**
	 * @return the approvalStatusType
	 */
	public Short getApprovalStatusType() {
		return approvalStatusType;
	}

	/**
	 * @return the caretakersPerpetratorCode
	 */
	public String getCaretakersPerpetratorCode() {
		return StringUtils.trimToEmpty(caretakersPerpetratorCode);
	}

	/**
	 * @return the closureDate
	 */
	public Date getClosureDate() {
		return closureDate;
	}

	/**
	 * @return the communicationMethodType
	 */
	public Short getCommunicationMethodType() {
		return communicationMethodType;
	}

	/**
	 * @return the currentLocationOfChildren
	 */
	public String getCurrentLocationOfChildren() {
		return StringUtils.trimToEmpty(currentLocationOfChildren);
	}

	/**
	 * @return the drmsAllegationDescriptionDoc
	 */
	public String getDrmsAllegationDescriptionDoc() {
		return StringUtils.trimToEmpty(drmsAllegationDescriptionDoc);
	}

	/**
	 * @return the drmsErReferralDoc
	 */
	public String getDrmsErReferralDoc() {
		return StringUtils.trimToEmpty(drmsErReferralDoc);
	}

	/**
	 * @return the drmsInvestigationDoc
	 */
	public String getDrmsInvestigationDoc() {
		return StringUtils.trimToEmpty(drmsInvestigationDoc);
	}

	/**
	 * @return the filedSuspectedChildAbuseReporttoLawEnforcementIndicator
	 */
	public String getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator() {
		return StringUtils.trimToEmpty(filedSuspectedChildAbuseReporttoLawEnforcementIndicator);
	}

	/**
	 * @return the familyAwarenessIndicator
	 */
	public String getFamilyAwarenessIndicator() {
		return StringUtils.trimToEmpty(familyAwarenessIndicator);
	}

	/**
	 * @return the govtEntityType
	 */
	public Short getGovtEntityType() {
		return govtEntityType;
	}

	/**
	 * @return the legalDefinitionCode
	 */
	public String getLegalDefinitionCode() {
		return StringUtils.trimToEmpty(legalDefinitionCode);
	}

	/**
	 * @return the legalRightsNoticeIndicator
	 */
	public String getLegalRightsNoticeIndicator() {
		return StringUtils.trimToEmpty(legalRightsNoticeIndicator);
	}

	/**
	 * @return the limitedAccessCode
	 */
	public String getLimitedAccessCode() {
		return StringUtils.trimToEmpty(limitedAccessCode);
	}

	/**
	 * @return the mandatedCrossReportReceivedDate
	 */
	public Date getMandatedCrossReportReceivedDate() {
		return mandatedCrossReportReceivedDate;
	}

	/**
	 * @return the referralName
	 */
	public String getReferralName() {
		return StringUtils.trimToEmpty(referralName);
	}

	/**
	 * @return the openAdequateCaseCode
	 */
	public String getOpenAdequateCaseCode() {
		return StringUtils.trimToEmpty(openAdequateCaseCode);
	}

	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @return the receivedTime
	 */
	public Date getReceivedTime() {
		return receivedTime;
	}

	/**
	 * @return the referralResponseType
	 */
	public Short getReferralResponseType() {
		return referralResponseType;
	}

	/**
	 * @return the referredToResourceType
	 */
	public Short getReferredToResourceType() {
		return referredToResourceType;
	}

	/**
	 * @return the responseDeterminationDate
	 */
	public Date getResponseDeterminationDate() {
		return responseDeterminationDate;
	}

	/**
	 * @return the responseDeterminationTime
	 */
	public Date getResponseDeterminationTime() {
		return responseDeterminationTime;
	}

	/**
	 * @return the responseRationaleText
	 */
	public String getResponseRationaleText() {
		return StringUtils.trimToEmpty(responseRationaleText);
	}

	/**
	 * @return the screenerNoteText
	 */
	public String getScreenerNoteText() {
		return StringUtils.trimToEmpty(screenerNoteText);
	}

	/**
	 * @return the specificsIncludedCode
	 */
	public String getSpecificsIncludedCode() {
		return StringUtils.trimToEmpty(specificsIncludedCode);
	}

	/**
	 * @return the sufficientInformationCode
	 */
	public String getSufficientInformationCode() {
		return StringUtils.trimToEmpty(sufficientInformationCode);
	}

	/**
	 * @return the unfoundedSeriesCode
	 */
	public String getUnfoundedSeriesCode() {
		return StringUtils.trimToEmpty(unfoundedSeriesCode);
	}

	/**
	 * @return the linkToPrimaryReferralId
	 */
	public String getLinkToPrimaryReferralId() {
		return StringUtils.trimToEmpty(linkToPrimaryReferralId);
	}

	/**
	 * @return the allegesAbuseOccurredAtAddressId
	 */
	public String getAllegesAbuseOccurredAtAddressId() {
		return StringUtils.trimToEmpty(allegesAbuseOccurredAtAddressId);
	}

	/**
	 * @return the firstResponseDeterminedByStaffPersonId
	 */
	public String getFirstResponseDeterminedByStaffPersonId() {
		return StringUtils.trimToEmpty(firstResponseDeterminedByStaffPersonId);
	}

	/**
	 * @return the primaryContactStaffPersonId
	 */
	public String getPrimaryContactStaffPersonId() {
		return StringUtils.trimToEmpty(primaryContactStaffPersonId);
	}

	/**
	 * @return the countySpecificCode
	 */
	public String getCountySpecificCode() {
		return StringUtils.trimToEmpty(countySpecificCode);
	}

	/**
	 * @return the specialProjectReferralIndicator
	 */
	public String getSpecialProjectReferralIndicator() {
		return StringUtils.trimToEmpty(specialProjectReferralIndicator);
	}

	/**
	 * @return the zippyCreatedIndicator
	 */
	public String getZippyCreatedIndicator() {
		return StringUtils.trimToEmpty(zippyCreatedIndicator);
	}

	/**
	 * @return the homelessIndicator
	 */
	public String getHomelessIndicator() {
		return StringUtils.trimToEmpty(homelessIndicator);
	}

	/**
	 * @return the familyRefusedServicesIndicator
	 */
	public String getFamilyRefusedServicesIndicator() {
		return StringUtils.trimToEmpty(familyRefusedServicesIndicator);
	}

	/**
	 * @return the firstEvaluatedOutApprovalDate
	 */
	public Date getFirstEvaluatedOutApprovalDate() {
		return firstEvaluatedOutApprovalDate;
	}

	/**
	 * @return the responsibleAgencyCode
	 */
	public String getResponsibleAgencyCode() {
		return StringUtils.trimToEmpty(responsibleAgencyCode);
	}

	/**
	 * @return the limitedAccessGovtAgencyType
	 */
	public Short getLimitedAccessGovtAgencyType() {
		return limitedAccessGovtAgencyType;
	}

	/**
	 * @return the limitedAccessDate
	 */
	public Date getLimitedAccessDate() {
		return limitedAccessDate;
	}

	/**
	 * @return the limitedAccessDesc
	 */
	public String getLimitedAccessDesc() {
		return StringUtils.trimToEmpty(limitedAccessDesc);
	}

	/**
	 * @return the originalClosureDate
	 */
	public Date getOriginalClosureDate() {
		return originalClosureDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalInfoIncludedCode == null) ? 0 : additionalInfoIncludedCode.hashCode());
		result = prime * result
				+ ((allegesAbuseOccurredAtAddressId == null) ? 0 : allegesAbuseOccurredAtAddressId.hashCode());
		result = prime * result + ((anonymousReporterIndicator == null) ? 0 : anonymousReporterIndicator.hashCode());
		result = prime * result
				+ ((applicationForPetitionIndicator == null) ? 0 : applicationForPetitionIndicator.hashCode());
		result = prime * result + ((approvalNumber == null) ? 0 : approvalNumber.hashCode());
		result = prime * result + ((approvalStatusType == null) ? 0 : approvalStatusType.hashCode());
		result = prime * result + ((caretakersPerpetratorCode == null) ? 0 : caretakersPerpetratorCode.hashCode());
		result = prime * result + ((closureDate == null) ? 0 : closureDate.hashCode());
		result = prime * result + ((communicationMethodType == null) ? 0 : communicationMethodType.hashCode());
		result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
		result = prime * result + ((currentLocationOfChildren == null) ? 0 : currentLocationOfChildren.hashCode());
		result = prime * result
				+ ((drmsAllegationDescriptionDoc == null) ? 0 : drmsAllegationDescriptionDoc.hashCode());
		result = prime * result + ((drmsErReferralDoc == null) ? 0 : drmsErReferralDoc.hashCode());
		result = prime * result + ((drmsInvestigationDoc == null) ? 0 : drmsInvestigationDoc.hashCode());
		result = prime * result + ((familyAwarenessIndicator == null) ? 0 : familyAwarenessIndicator.hashCode());
		result = prime * result
				+ ((familyRefusedServicesIndicator == null) ? 0 : familyRefusedServicesIndicator.hashCode());
		result = prime * result + ((filedSuspectedChildAbuseReporttoLawEnforcementIndicator == null) ? 0
				: filedSuspectedChildAbuseReporttoLawEnforcementIndicator.hashCode());
		result = prime * result
				+ ((firstEvaluatedOutApprovalDate == null) ? 0 : firstEvaluatedOutApprovalDate.hashCode());
		result = prime * result + ((firstResponseDeterminedByStaffPersonId == null) ? 0
				: firstResponseDeterminedByStaffPersonId.hashCode());
		result = prime * result + ((govtEntityType == null) ? 0 : govtEntityType.hashCode());
		result = prime * result + ((homelessIndicator == null) ? 0 : homelessIndicator.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((legalDefinitionCode == null) ? 0 : legalDefinitionCode.hashCode());
		result = prime * result + ((legalRightsNoticeIndicator == null) ? 0 : legalRightsNoticeIndicator.hashCode());
		result = prime * result + ((limitedAccessCode == null) ? 0 : limitedAccessCode.hashCode());
		result = prime * result + ((limitedAccessDate == null) ? 0 : limitedAccessDate.hashCode());
		result = prime * result + ((limitedAccessDesc == null) ? 0 : limitedAccessDesc.hashCode());
		result = prime * result + ((limitedAccessGovtAgencyType == null) ? 0 : limitedAccessGovtAgencyType.hashCode());
		result = prime * result + ((linkToPrimaryReferralId == null) ? 0 : linkToPrimaryReferralId.hashCode());
		result = prime * result
				+ ((mandatedCrossReportReceivedDate == null) ? 0 : mandatedCrossReportReceivedDate.hashCode());
		result = prime * result + ((openAdequateCaseCode == null) ? 0 : openAdequateCaseCode.hashCode());
		result = prime * result + ((originalClosureDate == null) ? 0 : originalClosureDate.hashCode());
		result = prime * result + ((primaryContactStaffPersonId == null) ? 0 : primaryContactStaffPersonId.hashCode());
		result = prime * result + ((receivedDate == null) ? 0 : receivedDate.hashCode());
		result = prime * result + ((receivedTime == null) ? 0 : receivedTime.hashCode());
		result = prime * result + ((referralName == null) ? 0 : referralName.hashCode());
		result = prime * result + ((referralResponseType == null) ? 0 : referralResponseType.hashCode());
		result = prime * result + ((referredToResourceType == null) ? 0 : referredToResourceType.hashCode());
		result = prime * result + ((responseDeterminationDate == null) ? 0 : responseDeterminationDate.hashCode());
		result = prime * result + ((responseDeterminationTime == null) ? 0 : responseDeterminationTime.hashCode());
		result = prime * result + ((responseRationaleText == null) ? 0 : responseRationaleText.hashCode());
		result = prime * result + ((responsibleAgencyCode == null) ? 0 : responsibleAgencyCode.hashCode());
		result = prime * result + ((screenerNoteText == null) ? 0 : screenerNoteText.hashCode());
		result = prime * result
				+ ((specialProjectReferralIndicator == null) ? 0 : specialProjectReferralIndicator.hashCode());
		result = prime * result + ((specificsIncludedCode == null) ? 0 : specificsIncludedCode.hashCode());
		result = prime * result + ((sufficientInformationCode == null) ? 0 : sufficientInformationCode.hashCode());
		result = prime * result + ((unfoundedSeriesCode == null) ? 0 : unfoundedSeriesCode.hashCode());
		result = prime * result + ((zippyCreatedIndicator == null) ? 0 : zippyCreatedIndicator.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Referral other = (Referral) obj;
		if (additionalInfoIncludedCode == null) {
			if (other.additionalInfoIncludedCode != null)
				return false;
		} else if (!additionalInfoIncludedCode.equals(other.additionalInfoIncludedCode))
			return false;
		if (allegesAbuseOccurredAtAddressId == null) {
			if (other.allegesAbuseOccurredAtAddressId != null)
				return false;
		} else if (!allegesAbuseOccurredAtAddressId.equals(other.allegesAbuseOccurredAtAddressId))
			return false;
		if (anonymousReporterIndicator == null) {
			if (other.anonymousReporterIndicator != null)
				return false;
		} else if (!anonymousReporterIndicator.equals(other.anonymousReporterIndicator))
			return false;
		if (applicationForPetitionIndicator == null) {
			if (other.applicationForPetitionIndicator != null)
				return false;
		} else if (!applicationForPetitionIndicator.equals(other.applicationForPetitionIndicator))
			return false;
		if (approvalNumber == null) {
			if (other.approvalNumber != null)
				return false;
		} else if (!approvalNumber.equals(other.approvalNumber))
			return false;
		if (approvalStatusType == null) {
			if (other.approvalStatusType != null)
				return false;
		} else if (!approvalStatusType.equals(other.approvalStatusType))
			return false;
		if (caretakersPerpetratorCode == null) {
			if (other.caretakersPerpetratorCode != null)
				return false;
		} else if (!caretakersPerpetratorCode.equals(other.caretakersPerpetratorCode))
			return false;
		if (closureDate == null) {
			if (other.closureDate != null)
				return false;
		} else if (!closureDate.equals(other.closureDate))
			return false;
		if (communicationMethodType == null) {
			if (other.communicationMethodType != null)
				return false;
		} else if (!communicationMethodType.equals(other.communicationMethodType))
			return false;
		if (countySpecificCode == null) {
			if (other.countySpecificCode != null)
				return false;
		} else if (!countySpecificCode.equals(other.countySpecificCode))
			return false;
		if (currentLocationOfChildren == null) {
			if (other.currentLocationOfChildren != null)
				return false;
		} else if (!currentLocationOfChildren.equals(other.currentLocationOfChildren))
			return false;
		if (drmsAllegationDescriptionDoc == null) {
			if (other.drmsAllegationDescriptionDoc != null)
				return false;
		} else if (!drmsAllegationDescriptionDoc.equals(other.drmsAllegationDescriptionDoc))
			return false;
		if (drmsErReferralDoc == null) {
			if (other.drmsErReferralDoc != null)
				return false;
		} else if (!drmsErReferralDoc.equals(other.drmsErReferralDoc))
			return false;
		if (drmsInvestigationDoc == null) {
			if (other.drmsInvestigationDoc != null)
				return false;
		} else if (!drmsInvestigationDoc.equals(other.drmsInvestigationDoc))
			return false;
		if (familyAwarenessIndicator == null) {
			if (other.familyAwarenessIndicator != null)
				return false;
		} else if (!familyAwarenessIndicator.equals(other.familyAwarenessIndicator))
			return false;
		if (familyRefusedServicesIndicator == null) {
			if (other.familyRefusedServicesIndicator != null)
				return false;
		} else if (!familyRefusedServicesIndicator.equals(other.familyRefusedServicesIndicator))
			return false;
		if (filedSuspectedChildAbuseReporttoLawEnforcementIndicator == null) {
			if (other.filedSuspectedChildAbuseReporttoLawEnforcementIndicator != null)
				return false;
		} else if (!filedSuspectedChildAbuseReporttoLawEnforcementIndicator
				.equals(other.filedSuspectedChildAbuseReporttoLawEnforcementIndicator))
			return false;
		if (firstEvaluatedOutApprovalDate == null) {
			if (other.firstEvaluatedOutApprovalDate != null)
				return false;
		} else if (!firstEvaluatedOutApprovalDate.equals(other.firstEvaluatedOutApprovalDate))
			return false;
		if (firstResponseDeterminedByStaffPersonId == null) {
			if (other.firstResponseDeterminedByStaffPersonId != null)
				return false;
		} else if (!firstResponseDeterminedByStaffPersonId.equals(other.firstResponseDeterminedByStaffPersonId))
			return false;
		if (govtEntityType == null) {
			if (other.govtEntityType != null)
				return false;
		} else if (!govtEntityType.equals(other.govtEntityType))
			return false;
		if (homelessIndicator == null) {
			if (other.homelessIndicator != null)
				return false;
		} else if (!homelessIndicator.equals(other.homelessIndicator))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (legalDefinitionCode == null) {
			if (other.legalDefinitionCode != null)
				return false;
		} else if (!legalDefinitionCode.equals(other.legalDefinitionCode))
			return false;
		if (legalRightsNoticeIndicator == null) {
			if (other.legalRightsNoticeIndicator != null)
				return false;
		} else if (!legalRightsNoticeIndicator.equals(other.legalRightsNoticeIndicator))
			return false;
		if (limitedAccessCode == null) {
			if (other.limitedAccessCode != null)
				return false;
		} else if (!limitedAccessCode.equals(other.limitedAccessCode))
			return false;
		if (limitedAccessDate == null) {
			if (other.limitedAccessDate != null)
				return false;
		} else if (!limitedAccessDate.equals(other.limitedAccessDate))
			return false;
		if (limitedAccessDesc == null) {
			if (other.limitedAccessDesc != null)
				return false;
		} else if (!limitedAccessDesc.equals(other.limitedAccessDesc))
			return false;
		if (limitedAccessGovtAgencyType == null) {
			if (other.limitedAccessGovtAgencyType != null)
				return false;
		} else if (!limitedAccessGovtAgencyType.equals(other.limitedAccessGovtAgencyType))
			return false;
		if (linkToPrimaryReferralId == null) {
			if (other.linkToPrimaryReferralId != null)
				return false;
		} else if (!linkToPrimaryReferralId.equals(other.linkToPrimaryReferralId))
			return false;
		if (mandatedCrossReportReceivedDate == null) {
			if (other.mandatedCrossReportReceivedDate != null)
				return false;
		} else if (!mandatedCrossReportReceivedDate.equals(other.mandatedCrossReportReceivedDate))
			return false;
		if (openAdequateCaseCode == null) {
			if (other.openAdequateCaseCode != null)
				return false;
		} else if (!openAdequateCaseCode.equals(other.openAdequateCaseCode))
			return false;
		if (originalClosureDate == null) {
			if (other.originalClosureDate != null)
				return false;
		} else if (!originalClosureDate.equals(other.originalClosureDate))
			return false;
		if (primaryContactStaffPersonId == null) {
			if (other.primaryContactStaffPersonId != null)
				return false;
		} else if (!primaryContactStaffPersonId.equals(other.primaryContactStaffPersonId))
			return false;
		if (receivedDate == null) {
			if (other.receivedDate != null)
				return false;
		} else if (!receivedDate.equals(other.receivedDate))
			return false;
		if (receivedTime == null) {
			if (other.receivedTime != null)
				return false;
		} else if (!receivedTime.equals(other.receivedTime))
			return false;
		if (referralName == null) {
			if (other.referralName != null)
				return false;
		} else if (!referralName.trim().equals(other.referralName.trim()))
			return false;
		if (referralResponseType == null) {
			if (other.referralResponseType != null)
				return false;
		} else if (!referralResponseType.equals(other.referralResponseType))
			return false;
		if (referredToResourceType == null) {
			if (other.referredToResourceType != null)
				return false;
		} else if (!referredToResourceType.equals(other.referredToResourceType))
			return false;
		if (responseDeterminationDate == null) {
			if (other.responseDeterminationDate != null)
				return false;
		} else if (!responseDeterminationDate.equals(other.responseDeterminationDate))
			return false;
		if (responseDeterminationTime == null) {
			if (other.responseDeterminationTime != null)
				return false;
		} else if (!responseDeterminationTime.equals(other.responseDeterminationTime))
			return false;
		if (responseRationaleText == null) {
			if (other.responseRationaleText != null)
				return false;
		} else if (!responseRationaleText.equals(other.responseRationaleText))
			return false;
		if (responsibleAgencyCode == null) {
			if (other.responsibleAgencyCode != null)
				return false;
		} else if (!responsibleAgencyCode.equals(other.responsibleAgencyCode))
			return false;
		if (screenerNoteText == null) {
			if (other.screenerNoteText != null)
				return false;
		} else if (!screenerNoteText.equals(other.screenerNoteText))
			return false;
		if (specialProjectReferralIndicator == null) {
			if (other.specialProjectReferralIndicator != null)
				return false;
		} else if (!specialProjectReferralIndicator.equals(other.specialProjectReferralIndicator))
			return false;
		if (specificsIncludedCode == null) {
			if (other.specificsIncludedCode != null)
				return false;
		} else if (!specificsIncludedCode.equals(other.specificsIncludedCode))
			return false;
		if (sufficientInformationCode == null) {
			if (other.sufficientInformationCode != null)
				return false;
		} else if (!sufficientInformationCode.equals(other.sufficientInformationCode))
			return false;
		if (unfoundedSeriesCode == null) {
			if (other.unfoundedSeriesCode != null)
				return false;
		} else if (!unfoundedSeriesCode.equals(other.unfoundedSeriesCode))
			return false;
		if (zippyCreatedIndicator == null) {
			if (other.zippyCreatedIndicator != null)
				return false;
		} else if (!zippyCreatedIndicator.equals(other.zippyCreatedIndicator))
			return false;
		return true;
	}

}
