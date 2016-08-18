package gov.ca.cwds.rest.api.persistence.legacy;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

/**
 * {@link PersistentObject} representing a Referral
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "REFERL_T")
@ApiModel
public class Referral extends PersistentObject {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

	@Id
	@Column(name = "IDENTIFIER")
	@NotEmpty
	@Size(min=1, max=10)
	private String id;
	
	@Column(name = "ADD_INF_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String additionalInfoIncludedCode;
	
	@Column(name = "ANRPTR_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String anonymousReporter;
	
	@Column(name = "PETAPL_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String applicationForPetition;
	
	@Column(name = "APRVL_NO")
	@Size(max=10)	
	private String approvalNumber;
	
	@Type(type = "short")
	@Column(name = "APV_STC")
	@NotNull	
	private Short approvalStatusType;
	
	@Column(name = "CR_PERP_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String caretakersPerpetratorCode;
	
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="closureDate")
	@Type(type = "date")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String closureDate;
	
	@Type(type = "date")
	@Column(name = "REFCLSR_DT")
	private Date closureDatePersistable;
	
	@Type(type = "short")
	@Column(name = "CMM_MTHC")
	@NotNull
	private Short communicationMethodType;
	
	@Column(name = "CHILOC_TXT")
	@Size(max=10)	
	private String currentLocationOfChildren;
	
	@Column(name = "ALGDSC_DOC")
	@Size(max=10)
	private String drmsAllegationDescriptionDoc;
	
	@Column(name = "ER_REF_DOC")
	@Size(max=10)
	private String drmsErReferralDoc;
	
	@Column(name = "INVSTG_DOC")
	@Size(max=10)	
	private String drmsInvestigationDoc;

	@Column(name = "XRPT_LWIND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String filedSuspectedChildAbuseReporttoLawEnforcement;
	
	@Column(name = "FMY_AW_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String familyAwarenessIndicator;
	
	@Type(type = "short")
	@Column(name = "GVR_ENTC")
	@NotNull
	private Short govtEntityType;
	
	@Column(name = "LGL_DEF_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String legalDefinitionCode;
	
	@Column(name = "LGLRGT_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String legalRightsNoticeIndicator;
	
	@Column(name = "LMT_ACSSCD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String limitedAccessCode;

	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="mandatedCrossReportReceivedDate")
	@Type(type = "date")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String mandatedCrossReportReceivedDate;
	
	@Type(type = "date")
	@Column(name = "XRPT_RCVDT")
	private Date mandatedCrossReportReceivedDatePersistable;
	
	@Column(name = "REFERRL_NM")
	@NotEmpty
	@Size(min=1, max=35)	
	private String referralName;
	
	@Column(name = "ADQT_CS_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String openAdequateCaseCode;

	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="receivedDate")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=true)
	private String receivedDate;
	
	@Type(type = "date")
	@Column(name = "REF_RCV_DT")
	private Date receivedDatePersistable;
	
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=TIMESTAMP_FORMAT)
	@gov.ca.cwds.rest.validation.Date(format=TIMESTAMP_FORMAT, required=true)
	private String receivedTime;
		
	@Type(type = "time")
	@Column(name = "REF_RCV_TM")
	private Date receivedTimePersistable;
	
	@Type(type = "short")
	@Column(name = "RFR_RSPC")
	@NotNull
	private Short referralResponseType;

	@Type(type = "short")
	@Column(name = "RFRD_RSC")
	@NotNull
	private Short referredToResourceType;

	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="responseDeterminationDate")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String responseDeterminationDate;
	
	@Type(type = "date")
	@Column(name = "RSP_DTR_DT")
	private Date responseDeterminationDatePersistable;
	
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=TIMESTAMP_FORMAT)
	@gov.ca.cwds.rest.validation.Date(format=TIMESTAMP_FORMAT, required=false)
	private String responseDeterminationTime;
		
	@Type(type = "time")
	@Column(name = "RSP_DTR_TM")
	private Date responseDeterminationTimePersistable;

	@Column(name = "RSP_RTNTXT")
	@Size(max=10)	
	private String responseRationaleText;

	@Column(name = "SCN_NT_TXT")
	@Size(max=10)	
	private String screenerNoteText;
	
	@Column(name = "SP_INCL_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String specificsIncludedCode;
	
	@Column(name = "SFC_INF_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String sufficientInformationCode;
	
	@Column(name = "UNFD_SR_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String unfoundedSeriesCode;

	@Column(name = "LST_UPD_ID")
	@NotEmpty
	@Size(min=1, max=3)	
	private String lastUpdateId;
	
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=TIMESTAMP_FORMAT)
	@gov.ca.cwds.rest.validation.Date(format=TIMESTAMP_FORMAT, required=true)
	private String lastUpdateTimeStamp;
	
	@Type(type = "timestamp")
	@Column(name = "LST_UPD_TS")	
	private Date lastUpdateTimeStampPersistable;

	@Column(name = "FKREFERL_T")
	@Size(max=10)	
	private String foreignKeyFromReferral;

	@Column(name = "FKADDRS_T")
	@Size(max=10)	
	private String fkAddrsT;

	@Column(name = "FKSTFPERS0")
	@Size(max=3)	
	private String fkStaffPerso;

	@Column(name = "FKSTFPERST")
	@NotEmpty
	@Size(min=1, max=3)	
	private String foreignKeyStaffPerson;

	@Column(name = "CNTY_SPFCD")
	@NotEmpty
	@Size(min=1, max=2)	
	private String countySpecificCode;
	
	@Column(name = "SPRJRF_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String specialProjectReferralIndicator;

	@Column(name = "ZIPPY_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String zippyCreatedIndicator;

	@Column(name = "HOMLES_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String homelessIndicator;

	@Column(name = "FAMREF_IND")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String familyRefusedServicesIndicator;

	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="firstEvaluatedOutApprovalDate")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String firstEvaluatedOutApprovalDate;
	
	@Type(type = "date")
	@Column(name = "FIRST_EODT")
	private Date firstEvaluatedOutApprovalDatePersistable;
	
	@Column(name = "RSP_AGY_CD")
	@NotEmpty
	@Size(min=1, max=1, message="size must be 1")
	private String responsibleAgencyCode;
	
	@Type(type = "short")
	@Column(name = "L_GVR_ENTC")
	private Short limitedAccessGovtAgencyType;
	
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="limitedAccessDate")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String limitedAccessDate;

	@Type(type = "date")
	@Column(name = "LMT_ACS_DT")
	private Date limitedAccessDatePersistable;
	
	@Column(name = "LMT_ACSDSC")
	@Size(max=254)	
	private String limitedAccessDesc;

	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATE_FORMAT)
	@JsonProperty(value="originalClosureDate")
	@gov.ca.cwds.rest.validation.Date(format=DATE_FORMAT, required=false)
	private String originalClosureDate;

	@Type(type = "date")
	@Column(name = "ORIGCLS_DT")
	private Date originalClosureDatePersistable;
		
	public Referral() {
		super();
	}

	
	/**
	 * @param id
	 * @param additionalInfoIncludedCode
	 * @param anonymousReporter
	 * @param applicationForPetition
	 * @param approvalNumber
	 * @param approvalStatusType
	 * @param caretakersPerpetratorCode
	 * @param closureDate
	 * @param communicationMethodType
	 * @param currentLocationOfChildren
	 * @param drmsAllegationDescriptionDoc
	 * @param drmsErReferralDoc
	 * @param drmsInvestigationDoc
	 * @param filedSuspectedChildAbuseReporttoLawEnforcement
	 * @param familyAwarenessIndicator
	 * @param govtEntityType
	 * @param legalDefinitionCode
	 * @param legalRightsNoticeIndicator
	 * @param limitedAccessCode
	 * @param mandatedCrossReportReceivedDate
	 * @param referralName
	 * @param openAdequateCaseCode
	 * @param receivedDate
	 * @param receivedTime
	 * @param referralResponseType
	 * @param referredToResourceType
	 * @param responseDeterminationDate
	 * @param responseDeterminationTime
	 * @param responseRationaleText
	 * @param screenerNoteText
	 * @param specificsIncludedCode
	 * @param sufficientInformationCode
	 * @param unfoundedSeriesCode
	 * @param lastUpdateId
	 * @param lastUpdateTimeStamp
	 * @param foreignKeyFromReferral
	 * @param fkAddrsT
	 * @param fkStaffPerso
	 * @param foreignKeyStaffPerson
	 * @param countySpecificCode
	 * @param specialProjectReferralIndicator
	 * @param zippyCreatedIndicator
	 * @param homelessIndicator
	 * @param familyRefusedServicesIndicator
	 * @param firstEvaluatedOutApprovalDate
	 * @param responsibleAgencyCode
	 * @param limitedAccessGovtAgencyType
	 * @param limitedAccessDate
	 * @param limitedAccessDesc
	 * @param originalClosureDate
	 */
	@JsonCreator
	public Referral(
			@JsonProperty("id") String id, 
			@JsonProperty("additionalInfoIncludedCode") String additionalInfoIncludedCode,
			@JsonProperty("anonymousReporter") String anonymousReporter, 
			@JsonProperty("applicationForPetition") String applicationForPetition,
			@JsonProperty("approvalNumber") String approvalNumber, 
			@JsonProperty("approvalStatusType") Short approvalStatusType,
			@JsonProperty("caretakersPerpetratorCode") String caretakersPerpetratorCode, 
			@JsonProperty("closureDate") String closureDate,
			@JsonProperty("communicationMethodType") Short communicationMethodType, 
			@JsonProperty("currentLocationOfChildren") String currentLocationOfChildren,
			@JsonProperty("drmsAllegationDescriptionDoc") String drmsAllegationDescriptionDoc,
			@JsonProperty("drmsErReferralDoc") String drmsErReferralDoc,
			@JsonProperty("drmsInvestigationDoc") String drmsInvestigationDoc,
			@JsonProperty("filedSuspectedChildAbuseReporttoLawEnforcement") String filedSuspectedChildAbuseReporttoLawEnforcement,
			@JsonProperty("familyAwarenessIndicator") String familyAwarenessIndicator, 
			@JsonProperty("govtEntityType") Short govtEntityType,
			@JsonProperty("legalDefinitionCode") String legalDefinitionCode, 
			@JsonProperty("legalRightsNoticeIndicator") String legalRightsNoticeIndicator,
			@JsonProperty("limitedAccessCode") String limitedAccessCode, 
			@JsonProperty("mandatedCrossReportReceivedDate") String mandatedCrossReportReceivedDate,
			@JsonProperty("referralName") String referralName, 
			@JsonProperty("openAdequateCaseCode") String openAdequateCaseCode,
			@JsonProperty("receivedDate") String receivedDate , 
			@JsonProperty("receivedTime") String  receivedTime, 
			@JsonProperty("referralResponseType") Short referralResponseType,
			@JsonProperty("referredToResourceType") Short referredToResourceType, 
			@JsonProperty("responseDeterminationDate") String responseDeterminationDate,
			@JsonProperty("responseDeterminationTime") String responseDeterminationTime, 
			@JsonProperty("responseRationaleText") String responseRationaleText,
			@JsonProperty("screenerNoteText") String screenerNoteText, 
			@JsonProperty("specificsIncludedCode") String specificsIncludedCode,
			@JsonProperty("sufficientInformationCode") String sufficientInformationCode, 
			@JsonProperty("unfoundedSeriesCode") String unfoundedSeriesCode,
			@JsonProperty("lastUpdateId") String lastUpdateId,
			@JsonProperty("lastUpdateTimeStamp") String lastUpdateTimeStamp,
			@JsonProperty("foreignKeyFromReferral") String foreignKeyFromReferral,
			@JsonProperty("fkAddrsT") String fkAddrsT,
			@JsonProperty("fkStaffPerso") String fkStaffPerso, 
			@JsonProperty("foreignKeyStaffPerson") String foreignKeyStaffPerson,
			@JsonProperty("countySpecificCode") String countySpecificCode, 
			@JsonProperty("specialProjectReferralIndicator") String specialProjectReferralIndicator,
			@JsonProperty("zippyCreatedIndicator") String zippyCreatedIndicator, 
			@JsonProperty("homelessIndicator") String homelessIndicator,
			@JsonProperty("familyRefusedServicesIndicator") String familyRefusedServicesIndicator,
			@JsonProperty("firstEvaluatedOutApprovalDate") String firstEvaluatedOutApprovalDate, 
			@JsonProperty("responsibleAgencyCode") String responsibleAgencyCode,
			@JsonProperty("limitedAccessGovtAgencyType") Short limitedAccessGovtAgencyType, 
			@JsonProperty("limitedAccessDate") String limitedAccessDate,
			@JsonProperty("limitedAccessDesc") String limitedAccessDesc, 
			@JsonProperty("originalClosureDate") String originalClosureDate) {
		super();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		DateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		
		this.id = id;
		this.additionalInfoIncludedCode = additionalInfoIncludedCode;
		this.anonymousReporter = anonymousReporter;
		this.applicationForPetition = applicationForPetition;
		this.approvalNumber = approvalNumber;
		this.approvalStatusType = approvalStatusType;
		this.caretakersPerpetratorCode = caretakersPerpetratorCode;
		this.closureDate = closureDate;
		try { this.closureDatePersistable = dateFormat.parse(closureDate); } catch (Throwable e) {}
		this.communicationMethodType = communicationMethodType;
		this.currentLocationOfChildren = currentLocationOfChildren;
		this.drmsAllegationDescriptionDoc = drmsAllegationDescriptionDoc;
		this.drmsErReferralDoc = drmsErReferralDoc;
		this.drmsInvestigationDoc = drmsInvestigationDoc;
		this.filedSuspectedChildAbuseReporttoLawEnforcement = filedSuspectedChildAbuseReporttoLawEnforcement;
		this.familyAwarenessIndicator = familyAwarenessIndicator;
		this.govtEntityType = govtEntityType;
		this.legalDefinitionCode = legalDefinitionCode;
		this.legalRightsNoticeIndicator = legalRightsNoticeIndicator;
		this.limitedAccessCode = limitedAccessCode;
		this.mandatedCrossReportReceivedDate = mandatedCrossReportReceivedDate;
		try { this.mandatedCrossReportReceivedDatePersistable = dateFormat.parse(mandatedCrossReportReceivedDate); } catch (Throwable e) {}
		this.referralName = referralName;
		this.openAdequateCaseCode = openAdequateCaseCode;
		this.receivedDate = receivedDate;
		try { this.receivedDatePersistable = dateFormat.parse(receivedDate); } catch (Throwable e) {}
		this.receivedTime = receivedTime;
		try { this.receivedTimePersistable = timestampFormat.parse(receivedTime); } catch (Throwable e) {}
		this.referralResponseType = referralResponseType;
		this.referredToResourceType = referredToResourceType;
		this.responseDeterminationDate = responseDeterminationDate;
		try { this.responseDeterminationDatePersistable = dateFormat.parse(responseDeterminationDate); } catch (Throwable e) {}
		this.responseDeterminationTime = responseDeterminationTime;
		try { this.responseDeterminationTimePersistable = timestampFormat.parse(responseDeterminationTime); } catch (Throwable e) {}
		this.responseRationaleText = responseRationaleText;
		this.screenerNoteText = screenerNoteText;
		this.specificsIncludedCode = specificsIncludedCode;
		this.sufficientInformationCode = sufficientInformationCode;
		this.unfoundedSeriesCode = unfoundedSeriesCode;
		this.lastUpdateId = lastUpdateId;
		this.lastUpdateTimeStamp = lastUpdateTimeStamp;
		try { this.lastUpdateTimeStampPersistable = timestampFormat.parse(lastUpdateTimeStamp); } catch (Throwable e) {}
		this.foreignKeyFromReferral = foreignKeyFromReferral;
		this.fkAddrsT = fkAddrsT;
		this.fkStaffPerso = fkStaffPerso;
		this.foreignKeyStaffPerson = foreignKeyStaffPerson;
		this.countySpecificCode = countySpecificCode;
		this.specialProjectReferralIndicator = specialProjectReferralIndicator;
		this.zippyCreatedIndicator = zippyCreatedIndicator;
		this.homelessIndicator = homelessIndicator;
		this.familyRefusedServicesIndicator = familyRefusedServicesIndicator;
		this.firstEvaluatedOutApprovalDate = firstEvaluatedOutApprovalDate;
		try { this.firstEvaluatedOutApprovalDatePersistable = dateFormat.parse(firstEvaluatedOutApprovalDate); } catch (Throwable e) {}
		this.responsibleAgencyCode = responsibleAgencyCode;
		this.limitedAccessGovtAgencyType = limitedAccessGovtAgencyType;
		this.limitedAccessDate = limitedAccessDate;
		try { this.limitedAccessDatePersistable = dateFormat.parse(limitedAccessDate); } catch (Throwable e) {}
		this.limitedAccessDesc = limitedAccessDesc;
		this.originalClosureDate = originalClosureDate;
		try { this.originalClosureDatePersistable = dateFormat.parse(originalClosureDate); } catch (Throwable e) {}
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
	@ApiModelProperty(required=true, readOnly=false, value="", example="ABC123")
	public String getId() {
		return id;
	}

	/**
	 * @return the additionalInfoIncludedCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getAdditionalInfoIncludedCode() {
		return additionalInfoIncludedCode;
	}

	/**
	 * @return the anonymousReporter
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="N")
	public String getAnonymousReporter() {
		return anonymousReporter;
	}

	/**
	 * @return the applicationForPetition
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getApplicationForPetition() {
		return applicationForPetition;
	}

	/**
	 * @return the approvalNumber
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="ABC123")
	public String getApprovalNumber() {
		return approvalNumber;
	}

	/**
	 * @return the approvalStatusType
	 */
	@ApiModelProperty(required=true, readOnly=false, example="123")
	public Short getApprovalStatusType() {
		return approvalStatusType;
	}

	/**
	 * @return the caretakersPerpetratorCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getCaretakersPerpetratorCode() {
		return caretakersPerpetratorCode;
	}

	/**
	 * @return the closureDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-05-22")
	public String getClosureDate() {
		return !Strings.isNullOrEmpty(closureDate) ? closureDate : closureDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(closureDatePersistable)) : "";
	}

	/**
	 * @return the communicationMethodType
	 */
	@ApiModelProperty(required=true, readOnly=false, example="123")
	public Short getCommunicationMethodType() {
		return communicationMethodType;
	}

	/**
	 * @return the currentLocationOfChildren
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="QABC12")
	public String getCurrentLocationOfChildren() {
		return currentLocationOfChildren;
	}

	/**
	 * @return the drmsAllegationDescriptionDoc
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="QABC12")
	public String getDrmsAllegationDescriptionDoc() {
		return drmsAllegationDescriptionDoc;
	}

	/**
	 * @return the drmsErReferralDoc
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="QABC12")
	public String getDrmsErReferralDoc() {
		return drmsErReferralDoc;
	}

	/**
	 * @return the drmsInvestigationDoc
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="QABC12")
	public String getDrmsInvestigationDoc() {
		return drmsInvestigationDoc;
	}

	/**
	 * @return the filedSuspectedChildAbuseReporttoLawEnforcement
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getFiledSuspectedChildAbuseReporttoLawEnforcement() {
		return filedSuspectedChildAbuseReporttoLawEnforcement;
	}

	/**
	 * @return the familyAwarenessIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getFamilyAwarenessIndicator() {
		return familyAwarenessIndicator;
	}

	/**
	 * @return the govtEntityType
	 */
	@ApiModelProperty(required=true, readOnly=false, example="123")
	public Short getGovtEntityType() {
		return govtEntityType;
	}

	/**
	 * @return the legalDefinitionCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getLegalDefinitionCode() {
		return legalDefinitionCode;
	}

	/**
	 * @return the legalRightsNoticeIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getLegalRightsNoticeIndicator() {
		return legalRightsNoticeIndicator;
	}

	/**
	 * @return the limitedAccessCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getLimitedAccessCode() {
		return limitedAccessCode;
	}

	/**
	 * @return the mandatedCrossReportReceivedDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-01-01")
	public String getMandatedCrossReportReceivedDate() {
		return !Strings.isNullOrEmpty(mandatedCrossReportReceivedDate) ? mandatedCrossReportReceivedDate : mandatedCrossReportReceivedDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(mandatedCrossReportReceivedDatePersistable)) : "";
	}

	/**
	 * @return the referralName
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getReferralName() {
		return referralName;
	}

	/**
	 * @return the openAdequateCaseCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getOpenAdequateCaseCode() {
		return openAdequateCaseCode;
	}

	/**
	 * @return the receivedDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-01-01")
	public String getReceivedDate() {
		return !Strings.isNullOrEmpty(receivedDate) ? receivedDate : receivedDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(receivedDatePersistable)) : "";
	}

	/**
	 * @return the receivedTime
	 */
	@ApiModelProperty(required=false, readOnly=true, example="2015-11-22")
	public String getReceivedTime() {
		return !Strings.isNullOrEmpty(receivedTime) ? receivedTime : receivedTimePersistable != null ? ( (new SimpleDateFormat(TIMESTAMP_FORMAT)).format(receivedTimePersistable)) : "";
	}

	/**
	 * @return the referralResponseType
	 */
	@ApiModelProperty(required=true, readOnly=false, example="123")
	public Short getReferralResponseType() {
		return referralResponseType;
	}

	/**
	 * @return the referredToResourceType
	 */
	@ApiModelProperty(required=true, readOnly=false, example="123")
	public Short getReferredToResourceType() {
		return referredToResourceType;
	}

	/**
	 * @return the responseDeterminationDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-01-01")
	public String getResponseDeterminationDate() {
		return !Strings.isNullOrEmpty(responseDeterminationDate) ? responseDeterminationDate : responseDeterminationDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(responseDeterminationDatePersistable)) : "";
	}

	/**
	 * @return the responseDeterminationTime
	 */
	@ApiModelProperty(required=false, readOnly=true, example="2015-11-22")
	public String getResponseDeterminationTime() {
		return !Strings.isNullOrEmpty(responseDeterminationTime) ? responseDeterminationTime : responseDeterminationTimePersistable != null ? ( (new SimpleDateFormat(TIMESTAMP_FORMAT)).format(responseDeterminationTimePersistable)) : "";
	}

	/**
	 * @return the responseRationaleText
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Qabcd")
	public String getResponseRationaleText() {
		return responseRationaleText;
	}

	/**
	 * @return the screenerNoteText
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="QABCD")
	public String getScreenerNoteText() {
		return screenerNoteText;
	}

	/**
	 * @return the specificsIncludedCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getSpecificsIncludedCode() {
		return specificsIncludedCode;
	}


	/**
	 * @return the sufficientInformationCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getSufficientInformationCode() {
		return sufficientInformationCode;
	}

	/**
	 * @return the unfoundedSeriesCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getUnfoundedSeriesCode() {
		return unfoundedSeriesCode;
	}

	/**
	 * @return the lastUpdateId
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	/**
	 * @return the lastUpdateTimeStamp
	 */
	@ApiModelProperty(required=false, readOnly=true, example="2015-11-22")
	public String getLastUpdateTimeStamp() {
		return !Strings.isNullOrEmpty(lastUpdateTimeStamp) ? lastUpdateTimeStamp : lastUpdateTimeStampPersistable != null ? ( (new SimpleDateFormat(TIMESTAMP_FORMAT)).format(lastUpdateTimeStampPersistable)) : "";
	}

	/**
	 * @return the foreignKeyFromReferral
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="Q")
	public String getForeignKeyFromReferral() {
		return foreignKeyFromReferral;
	}

	/**
	 * @return the fkAddrsT
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="Q")
	public String getFkAddrsT() {
		return fkAddrsT;
	}

	/**
	 * @return the fkStaffPerso
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="Q")
	public String getFkStaffPerso() {
		return fkStaffPerso;
	}

	/**
	 * @return the foreignKeyStaffPerson
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getForeignKeyStaffPerson() {
		return foreignKeyStaffPerson;
	}

	/**
	 * @return the countySpecificCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getCountySpecificCode() {
		return countySpecificCode;
	}

	/**
	 * @return the specialProjectReferralIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getSpecialProjectReferralIndicator() {
		return specialProjectReferralIndicator;
	}

	/**
	 * @return the zippyCreatedIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getZippyCreatedIndicator() {
		return zippyCreatedIndicator;
	}

	/**
	 * @return the homelessIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getHomelessIndicator() {
		return homelessIndicator;
	}

	/**
	 * @return the familyRefusedServicesIndicator
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getFamilyRefusedServicesIndicator() {
		return familyRefusedServicesIndicator;
	}

	/**
	 * @return the firstEvaluatedOutApprovalDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-01-01")
	public String getFirstEvaluatedOutApprovalDate() {
		return !Strings.isNullOrEmpty(firstEvaluatedOutApprovalDate) ? firstEvaluatedOutApprovalDate : firstEvaluatedOutApprovalDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(firstEvaluatedOutApprovalDatePersistable)) : "";
	}

	/**
	 * @return the responsibleAgencyCode
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Q")
	public String getResponsibleAgencyCode() {
		return responsibleAgencyCode;
	}

	/**
	 * @return the limitedAccessGovtAgencyType
	 */
	@ApiModelProperty(required=false, readOnly=false, example="123")
	public Short getLimitedAccessGovtAgencyType() {
		return limitedAccessGovtAgencyType;
	}

	/**
	 * @return the limitedAccessDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-01-01")
	public String getLimitedAccessDate() {
		return !Strings.isNullOrEmpty(limitedAccessDate) ? limitedAccessDate : limitedAccessDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(limitedAccessDatePersistable)) : "";
		
		//return limitedAccessDate;
	}

	/**
	 * @return the limitedAccessDesc
	 */
	@ApiModelProperty(required=false, readOnly=false, value="", example="QASW")
	public String getLimitedAccessDesc() {
		return limitedAccessDesc;
	}
	
	/**
	 * @return the originalClosureDate
	 */
	@ApiModelProperty(required=false, readOnly=false, value="yyyy-MM-dd", example="2016-01-01")
	public String getOriginalClosureDate() {
		return !Strings.isNullOrEmpty(originalClosureDate) ? originalClosureDate : originalClosureDatePersistable != null ? ( (new SimpleDateFormat(DATE_FORMAT)).format(originalClosureDatePersistable)) : "";
		
//		return originalClosureDate;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((additionalInfoIncludedCode == null) ? 0
						: additionalInfoIncludedCode.hashCode());
		result = prime
				* result
				+ ((anonymousReporter == null) ? 0 : anonymousReporter
						.hashCode());
		result = prime
				* result
				+ ((applicationForPetition == null) ? 0
						: applicationForPetition.hashCode());
		result = prime * result
				+ ((approvalNumber == null) ? 0 : approvalNumber.hashCode());
		result = prime
				* result
				+ ((approvalStatusType == null) ? 0 : approvalStatusType
						.hashCode());
		result = prime
				* result
				+ ((caretakersPerpetratorCode == null) ? 0
						: caretakersPerpetratorCode.hashCode());
		result = prime * result
				+ ((closureDate == null) ? 0 : closureDate.hashCode());
		result = prime
				* result
				+ ((communicationMethodType == null) ? 0
						: communicationMethodType.hashCode());
		result = prime
				* result
				+ ((countySpecificCode == null) ? 0 : countySpecificCode
						.hashCode());
		result = prime
				* result
				+ ((currentLocationOfChildren == null) ? 0
						: currentLocationOfChildren.hashCode());
		result = prime
				* result
				+ ((drmsAllegationDescriptionDoc == null) ? 0
						: drmsAllegationDescriptionDoc.hashCode());
		result = prime
				* result
				+ ((drmsErReferralDoc == null) ? 0 : drmsErReferralDoc
						.hashCode());
		result = prime
				* result
				+ ((drmsInvestigationDoc == null) ? 0 : drmsInvestigationDoc
						.hashCode());
		result = prime
				* result
				+ ((familyAwarenessIndicator == null) ? 0
						: familyAwarenessIndicator.hashCode());
		result = prime
				* result
				+ ((familyRefusedServicesIndicator == null) ? 0
						: familyRefusedServicesIndicator.hashCode());
		result = prime
				* result
				+ ((filedSuspectedChildAbuseReporttoLawEnforcement == null) ? 0
						: filedSuspectedChildAbuseReporttoLawEnforcement
								.hashCode());
		result = prime
				* result
				+ ((firstEvaluatedOutApprovalDate == null) ? 0
						: firstEvaluatedOutApprovalDate.hashCode());
		result = prime * result
				+ ((fkAddrsT == null) ? 0 : fkAddrsT.hashCode());
		result = prime * result
				+ ((fkStaffPerso == null) ? 0 : fkStaffPerso.hashCode());
		result = prime
				* result
				+ ((foreignKeyFromReferral == null) ? 0
						: foreignKeyFromReferral.hashCode());
		result = prime
				* result
				+ ((foreignKeyStaffPerson == null) ? 0 : foreignKeyStaffPerson
						.hashCode());
		result = prime * result
				+ ((govtEntityType == null) ? 0 : govtEntityType.hashCode());
		result = prime
				* result
				+ ((homelessIndicator == null) ? 0 : homelessIndicator
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdateId == null) ? 0 : lastUpdateId.hashCode());
		result = prime
				* result
				+ ((lastUpdateTimeStamp == null) ? 0 : lastUpdateTimeStamp
						.hashCode());
		result = prime
				* result
				+ ((legalDefinitionCode == null) ? 0 : legalDefinitionCode
						.hashCode());
		result = prime
				* result
				+ ((legalRightsNoticeIndicator == null) ? 0
						: legalRightsNoticeIndicator.hashCode());
		result = prime
				* result
				+ ((limitedAccessCode == null) ? 0 : limitedAccessCode
						.hashCode());
		result = prime
				* result
				+ ((limitedAccessDate == null) ? 0 : limitedAccessDate
						.hashCode());
		result = prime
				* result
				+ ((limitedAccessDesc == null) ? 0 : limitedAccessDesc
						.hashCode());
		result = prime
				* result
				+ ((limitedAccessGovtAgencyType == null) ? 0
						: limitedAccessGovtAgencyType.hashCode());
		result = prime
				* result
				+ ((mandatedCrossReportReceivedDate == null) ? 0
						: mandatedCrossReportReceivedDate.hashCode());
		result = prime
				* result
				+ ((openAdequateCaseCode == null) ? 0 : openAdequateCaseCode
						.hashCode());
		result = prime
				* result
				+ ((originalClosureDate == null) ? 0 : originalClosureDate
						.hashCode());
		result = prime * result
				+ ((receivedDate == null) ? 0 : receivedDate.hashCode());
		result = prime * result
				+ ((receivedTime == null) ? 0 : receivedTime.hashCode());
		result = prime * result
				+ ((referralName == null) ? 0 : referralName.hashCode());
		result = prime
				* result
				+ ((referralResponseType == null) ? 0 : referralResponseType
						.hashCode());
		result = prime
				* result
				+ ((referredToResourceType == null) ? 0
						: referredToResourceType.hashCode());
		result = prime
				* result
				+ ((responseDeterminationDate == null) ? 0
						: responseDeterminationDate.hashCode());
		result = prime
				* result
				+ ((responseDeterminationTime == null) ? 0
						: responseDeterminationTime.hashCode());
		result = prime
				* result
				+ ((responseRationaleText == null) ? 0 : responseRationaleText
						.hashCode());
		result = prime
				* result
				+ ((responsibleAgencyCode == null) ? 0 : responsibleAgencyCode
						.hashCode());
		result = prime
				* result
				+ ((screenerNoteText == null) ? 0 : screenerNoteText.hashCode());
		result = prime
				* result
				+ ((specialProjectReferralIndicator == null) ? 0
						: specialProjectReferralIndicator.hashCode());
		result = prime
				* result
				+ ((specificsIncludedCode == null) ? 0 : specificsIncludedCode
						.hashCode());
		result = prime
				* result
				+ ((sufficientInformationCode == null) ? 0
						: sufficientInformationCode.hashCode());
		result = prime
				* result
				+ ((unfoundedSeriesCode == null) ? 0 : unfoundedSeriesCode
						.hashCode());
		result = prime
				* result
				+ ((zippyCreatedIndicator == null) ? 0 : zippyCreatedIndicator
						.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Referral other = (Referral) obj;
		if (additionalInfoIncludedCode == null) {
			if (other.additionalInfoIncludedCode != null) {
				return false;
			}
		} else if (!additionalInfoIncludedCode
				.equals(other.additionalInfoIncludedCode)) {
			return false;
		}
		if (anonymousReporter == null) {
			if (other.anonymousReporter != null) {
				return false;
			}
		} else if (!anonymousReporter.equals(other.anonymousReporter)) {
			return false;
		}
		if (applicationForPetition == null) {
			if (other.applicationForPetition != null) {
				return false;
			}
		} else if (!applicationForPetition.equals(other.applicationForPetition)) {
			return false;
		}
		if (approvalNumber == null) {
			if (other.approvalNumber != null) {
				return false;
			}
		} else if (!approvalNumber.equals(other.approvalNumber)) {
			return false;
		}
		if (approvalStatusType == null) {
			if (other.approvalStatusType != null) {
				return false;
			}
		} else if (!approvalStatusType.equals(other.approvalStatusType)) {
			return false;
		}
		if (caretakersPerpetratorCode == null) {
			if (other.caretakersPerpetratorCode != null) {
				return false;
			}
		} else if (!caretakersPerpetratorCode
				.equals(other.caretakersPerpetratorCode)) {
			return false;
		}
		if (closureDate == null) {
			if (other.closureDate != null) {
				return false;
			}
		} else if (!closureDate.equals(other.closureDate)) {
			return false;
		}
		if (communicationMethodType == null) {
			if (other.communicationMethodType != null) {
				return false;
			}
		} else if (!communicationMethodType
				.equals(other.communicationMethodType)) {
			return false;
		}
		if (countySpecificCode == null) {
			if (other.countySpecificCode != null) {
				return false;
			}
		} else if (!countySpecificCode.equals(other.countySpecificCode)) {
			return false;
		}
		if (currentLocationOfChildren == null) {
			if (other.currentLocationOfChildren != null) {
				return false;
			}
		} else if (!currentLocationOfChildren
				.equals(other.currentLocationOfChildren)) {
			return false;
		}
		if (drmsAllegationDescriptionDoc == null) {
			if (other.drmsAllegationDescriptionDoc != null) {
				return false;
			}
		} else if (!drmsAllegationDescriptionDoc
				.equals(other.drmsAllegationDescriptionDoc)) {
			return false;
		}
		if (drmsErReferralDoc == null) {
			if (other.drmsErReferralDoc != null) {
				return false;
			}
		} else if (!drmsErReferralDoc.equals(other.drmsErReferralDoc)) {
			return false;
		}
		if (drmsInvestigationDoc == null) {
			if (other.drmsInvestigationDoc != null) {
				return false;
			}
		} else if (!drmsInvestigationDoc.equals(other.drmsInvestigationDoc)) {
			return false;
		}
		if (familyAwarenessIndicator == null) {
			if (other.familyAwarenessIndicator != null) {
				return false;
			}
		} else if (!familyAwarenessIndicator
				.equals(other.familyAwarenessIndicator)) {
			return false;
		}
		if (familyRefusedServicesIndicator == null) {
			if (other.familyRefusedServicesIndicator != null) {
				return false;
			}
		} else if (!familyRefusedServicesIndicator
				.equals(other.familyRefusedServicesIndicator)) {
			return false;
		}
		if (filedSuspectedChildAbuseReporttoLawEnforcement == null) {
			if (other.filedSuspectedChildAbuseReporttoLawEnforcement != null) {
				return false;
			}
		} else if (!filedSuspectedChildAbuseReporttoLawEnforcement
				.equals(other.filedSuspectedChildAbuseReporttoLawEnforcement)) {
			return false;
		}
		if (firstEvaluatedOutApprovalDate == null) {
			if (other.firstEvaluatedOutApprovalDate != null) {
				return false;
			}
		} else if (!firstEvaluatedOutApprovalDate
				.equals(other.firstEvaluatedOutApprovalDate)) {
			return false;
		}
		if (fkAddrsT == null) {
			if (other.fkAddrsT != null) {
				return false;
			}
		} else if (!fkAddrsT.equals(other.fkAddrsT)) {
			return false;
		}
		if (fkStaffPerso == null) {
			if (other.fkStaffPerso != null) {
				return false;
			}
		} else if (!fkStaffPerso.equals(other.fkStaffPerso)) {
			return false;
		}
		if (foreignKeyFromReferral == null) {
			if (other.foreignKeyFromReferral != null) {
				return false;
			}
		} else if (!foreignKeyFromReferral.equals(other.foreignKeyFromReferral)) {
			return false;
		}
		if (foreignKeyStaffPerson == null) {
			if (other.foreignKeyStaffPerson != null) {
				return false;
			}
		} else if (!foreignKeyStaffPerson.equals(other.foreignKeyStaffPerson)) {
			return false;
		}
		if (govtEntityType == null) {
			if (other.govtEntityType != null) {
				return false;
			}
		} else if (!govtEntityType.equals(other.govtEntityType)) {
			return false;
		}
		if (homelessIndicator == null) {
			if (other.homelessIndicator != null) {
				return false;
			}
		} else if (!homelessIndicator.equals(other.homelessIndicator)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastUpdateId == null) {
			if (other.lastUpdateId != null) {
				return false;
			}
		} else if (!lastUpdateId.equals(other.lastUpdateId)) {
			return false;
		}
		if (lastUpdateTimeStamp == null) {
			if (other.lastUpdateTimeStamp != null) {
				return false;
			}
		} else if (!lastUpdateTimeStamp.equals(other.lastUpdateTimeStamp)) {
			return false;
		}
		if (legalDefinitionCode == null) {
			if (other.legalDefinitionCode != null) {
				return false;
			}
		} else if (!legalDefinitionCode.equals(other.legalDefinitionCode)) {
			return false;
		}
		if (legalRightsNoticeIndicator == null) {
			if (other.legalRightsNoticeIndicator != null) {
				return false;
			}
		} else if (!legalRightsNoticeIndicator
				.equals(other.legalRightsNoticeIndicator)) {
			return false;
		}
		if (limitedAccessCode == null) {
			if (other.limitedAccessCode != null) {
				return false;
			}
		} else if (!limitedAccessCode.equals(other.limitedAccessCode)) {
			return false;
		}
		if (limitedAccessDate == null) {
			if (other.limitedAccessDate != null) {
				return false;
			}
		} else if (!limitedAccessDate.equals(other.limitedAccessDate)) {
			return false;
		}
		if (limitedAccessDesc == null) {
			if (other.limitedAccessDesc != null) {
				return false;
			}
		} else if (!limitedAccessDesc.equals(other.limitedAccessDesc)) {
			return false;
		}
		if (limitedAccessGovtAgencyType == null) {
			if (other.limitedAccessGovtAgencyType != null) {
				return false;
			}
		} else if (!limitedAccessGovtAgencyType
				.equals(other.limitedAccessGovtAgencyType)) {
			return false;
		}
		if (mandatedCrossReportReceivedDate == null) {
			if (other.mandatedCrossReportReceivedDate != null) {
				return false;
			}
		} else if (!mandatedCrossReportReceivedDate
				.equals(other.mandatedCrossReportReceivedDate)) {
			return false;
		}
		if (openAdequateCaseCode == null) {
			if (other.openAdequateCaseCode != null) {
				return false;
			}
		} else if (!openAdequateCaseCode.equals(other.openAdequateCaseCode)) {
			return false;
		}
		if (originalClosureDate == null) {
			if (other.originalClosureDate != null) {
				return false;
			}
		} else if (!originalClosureDate.equals(other.originalClosureDate)) {
			return false;
		}
		if (receivedDate == null) {
			if (other.receivedDate != null) {
				return false;
			}
		} else if (!receivedDate.equals(other.receivedDate)) {
			return false;
		}
		if (receivedTime == null) {
			if (other.receivedTime != null) {
				return false;
			}
		} else if (!receivedTime.equals(other.receivedTime)) {
			return false;
		}
		if (referralName == null) {
			if (other.referralName != null) {
				return false;
			}
		} else if (!referralName.equals(other.referralName)) {
			return false;
		}
		if (referralResponseType == null) {
			if (other.referralResponseType != null) {
				return false;
			}
		} else if (!referralResponseType.equals(other.referralResponseType)) {
			return false;
		}
		if (referredToResourceType == null) {
			if (other.referredToResourceType != null) {
				return false;
			}
		} else if (!referredToResourceType.equals(other.referredToResourceType)) {
			return false;
		}
		if (responseDeterminationDate == null) {
			if (other.responseDeterminationDate != null) {
				return false;
			}
		} else if (!responseDeterminationDate
				.equals(other.responseDeterminationDate)) {
			return false;
		}
		if (responseDeterminationTime == null) {
			if (other.responseDeterminationTime != null) {
				return false;
			}
		} else if (!responseDeterminationTime
				.equals(other.responseDeterminationTime)) {
			return false;
		}
		if (responseRationaleText == null) {
			if (other.responseRationaleText != null) {
				return false;
			}
		} else if (!responseRationaleText.equals(other.responseRationaleText)) {
			return false;
		}
		if (responsibleAgencyCode == null) {
			if (other.responsibleAgencyCode != null) {
				return false;
			}
		} else if (!responsibleAgencyCode.equals(other.responsibleAgencyCode)) {
			return false;
		}
		if (screenerNoteText == null) {
			if (other.screenerNoteText != null) {
				return false;
			}
		} else if (!screenerNoteText.equals(other.screenerNoteText)) {
			return false;
		}
		if (specialProjectReferralIndicator == null) {
			if (other.specialProjectReferralIndicator != null) {
				return false;
			}
		} else if (!specialProjectReferralIndicator
				.equals(other.specialProjectReferralIndicator)) {
			return false;
		}
		if (specificsIncludedCode == null) {
			if (other.specificsIncludedCode != null) {
				return false;
			}
		} else if (!specificsIncludedCode.equals(other.specificsIncludedCode)) {
			return false;
		}
		if (sufficientInformationCode == null) {
			if (other.sufficientInformationCode != null) {
				return false;
			}
		} else if (!sufficientInformationCode
				.equals(other.sufficientInformationCode)) {
			return false;
		}
		if (unfoundedSeriesCode == null) {
			if (other.unfoundedSeriesCode != null) {
				return false;
			}
		} else if (!unfoundedSeriesCode.equals(other.unfoundedSeriesCode)) {
			return false;
		}
		if (zippyCreatedIndicator == null) {
			if (other.zippyCreatedIndicator != null) {
				return false;
			}
		} else if (!zippyCreatedIndicator.equals(other.zippyCreatedIndicator)) {
			return false;
		}
		return true;
	}

}
