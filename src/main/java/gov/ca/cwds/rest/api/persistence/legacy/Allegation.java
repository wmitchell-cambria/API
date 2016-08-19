package gov.ca.cwds.rest.api.persistence.legacy;

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

@Entity
@Table(schema = "CWSINT", name = "ALLGTN_T")
@ApiModel
public class Allegation extends PersistentObject {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

	@Id
	@Column(name = "IDENTIFIER")
	@NotEmpty
	@Size(min = 1, max = 10, message = "Size must be between 1 and 10")
	private String id;

	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonProperty(value = "abuseEndDate")
	@Type(type = "date")
	@gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
	private String abuseEndDate;

	@Type(type = "date")
	@Column(name = "ABUSE_ENDT")
	private Date abuseEndDatePersistable;

	@Type(type = "date")
	@Column(name = "ABUSE_STDT")
	private Date abuseStartDatePersistable;

	@Transient
	@gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonProperty(value = "abuseStartDate")
	private String abuseStartDate;

	@Column(name = "ABUSE_FREQ")
	@NotNull
	private Integer abuseFrequency;

	@Column(name = "LST_UPD_ID")
	@NotEmpty
	@Size(min = 1, max = 3)
	private String lastUpdatedId;

	@Transient
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIMESTAMP_FORMAT)
	@gov.ca.cwds.rest.validation.Date(format = TIMESTAMP_FORMAT, required = true)
	private String lastUpdatedTime;

	@Type(type = "timestamp")
	@Column(name = "LST_UPD_TS")
	private Date lastUpdatedTimePersistable;

	@Column(name = "ABUSE_PDCD")
	@NotEmpty
	@Size(min = 1, max = 1, message = "Size must be 1")
	private String abuseFrequencyPeriodCode;

	@Column(name = "LOC_DSC")
	@NotEmpty
	@Size(min = 1, max = 75)
	private String abuseLocationDescription;

	@Column(name = "ALG_DSPC")
	@NotNull
	private Integer allegationDispositionType;

	@Column(name = "ALG_TPC")
	@NotNull
	private Integer allegationType;

	@Column(name = "DISPSN_DSC")
	@NotEmpty
	@Size(min = 1, max = 256)
	private String dispositionDescription;

	@Type(type = "date")
	@Column(name = "DISPSN_DT")
	private Date dispositionDatePersistable;

	@Transient
	@gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonProperty(value = "dispositionDate")
	private String dispositionDate;

	@Column(name = "IJHM_DET_B")
	@NotEmpty
	@Size(min = 1, max = 1, message = "Size must be 1")
	private String injuryHarmDetailIndVar;

	@Column(name = "NON_PRT_CD")
	@NotEmpty
	@Size(min = 1, max = 1, message = "Size must be 1")
	private String nonProtectingParentCode;

	@Column(name = "STFADD_IND")
	@NotEmpty
	@Size(min = 1, max = 1, message = "Size must be 1")
	private String staffPersonAddedInd;

	@Column(name = "FKCLIENT_T")
	@NotEmpty
	@Size(min = 1, max = 10)
	private String fkClientT;

	@Column(name = "FKCLIENT_0")
	@Size(min = 0, max = 10)
	private String fkClient0;

	@Column(name = "FKREFERL_T")
	@NotEmpty
	@Size(min = 1, max = 10)
	private String fkReferralT;

	@Column(name = "CNTY_SPFCD")
	@NotEmpty
	@Size(min = 1, max = 2, message = "Size must be between 1 and 2")
	private String countySpecificCode;

	@Column(name = "ZIPPY_IND")
	@NotEmpty
	@Size(min = 1, max = 1, message = "Size must be 1")
	private String zippyCrestedInd;

	@Column(name = "PLC_FCLC")
	private Integer placementFacilityType;

	public Allegation() {
		super();
	}

	@JsonCreator
	public Allegation(@JsonProperty("id") String id, @JsonProperty("abuseEndDate") String abuseEndDate,
			@JsonProperty("abuseFrequency") Integer abuseFrequency,
			@JsonProperty("abuseFrequencyPeriodCode") String abuseFrequencyPeriodCode,
			@JsonProperty("abuseLocationDescription") String abuseLocationDescription,
			@JsonProperty("abuseStartDate") String abuseStartDate,
			@JsonProperty("allegationDispositionType") Integer allegationDispositionType,
			@JsonProperty("allegationType") Integer allegationType,
			@JsonProperty("dispositionDescription") String dispositionDescription,
			@JsonProperty("dispositionDate") String dispositionDate,
			@JsonProperty("injuryHarmDetailIndVar") String injuryHarmDetailIndVar,
			@JsonProperty("nonProtectingParentCode") String nonProtectingParentCode,
			@JsonProperty("staffPersonAddedInd") String staffPersonAddedInd,
			@JsonProperty("lastUpdatedId") String lastUpdatedId,
			@JsonProperty("lastUpdatedTime") String lastUpdatedTime, @JsonProperty("fkClientT") String fkClientT,
			@JsonProperty("fkClient0") String fkClient0, @JsonProperty("fkReferralT") String fkReferralT,
			@JsonProperty("countySpecificCode") String countySpecificCode,
			@JsonProperty("zippyCrestedInd") String zippyCrestedInd,
			@JsonProperty("placementFacilityType") Integer placementFacilityType) {
		super();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		DateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);

		this.id = id;
		this.abuseFrequency = abuseFrequency;
		this.abuseEndDate = abuseEndDate;
		try {
			this.abuseEndDatePersistable = dateFormat.parse(abuseEndDate);
		} catch (Throwable e) {
		}
		this.lastUpdatedId = lastUpdatedId;
		this.lastUpdatedTime = lastUpdatedTime;

		try {
			this.lastUpdatedTimePersistable = timestampFormat.parse(lastUpdatedTime);
		} catch (Throwable e) {
		}
		this.abuseStartDate = abuseStartDate;

		try {
			if (null != abuseStartDate)
				this.abuseStartDatePersistable = dateFormat.parse(abuseStartDate);
		} catch (Throwable e) {
		}
		this.abuseEndDate = abuseEndDate;

		try {
			this.abuseEndDatePersistable = dateFormat.parse(abuseEndDate);
		} catch (Throwable e) {
		}
		this.abuseFrequency = abuseFrequency;
		this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
		this.abuseLocationDescription = abuseLocationDescription;
		this.allegationDispositionType = allegationDispositionType;
		this.allegationType = allegationType;
		this.dispositionDescription = dispositionDescription;
		this.dispositionDate = dispositionDate;

		try {
			this.dispositionDatePersistable = dateFormat.parse(dispositionDate);
		} catch (Throwable e) {
		}
		this.injuryHarmDetailIndVar = injuryHarmDetailIndVar;
		this.nonProtectingParentCode = nonProtectingParentCode;
		this.staffPersonAddedInd = staffPersonAddedInd;
		this.fkClientT = fkClientT;
		this.fkClient0 = fkClient0;
		this.fkReferralT = fkReferralT;
		this.countySpecificCode = countySpecificCode;
		this.zippyCrestedInd = zippyCrestedInd;
		this.placementFacilityType = placementFacilityType;

	}

	@Override
	public String getPrimaryKey() {
		return getId();
	}

	/**
	 * @return the id
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "Size must be between 1 and 10", example = "H6gHq1hN1T")
	public String getId() {
		return id;
	}

	@ApiModelProperty(required = true, readOnly = false, example = "1")
	public Integer getAbuseFrequency() {
		return abuseFrequency;
	}

	/**
	 * @return the abuseFrequencyPeriodCode
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "Size must be 1", example = "D,M,W,Y")
	public String getAbuseFrequencyPeriodCode() {
		return abuseFrequencyPeriodCode;
	}

	/**
	 * @return the abuseLocationDescrption
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "Size must be between 1 and 75", example = "1")
	public String getAbuseLocationDescription() {
		return abuseLocationDescription;
	}

	@ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd", example = "2016-05-22")
	public String getAbuseEndDate() {
		return !Strings.isNullOrEmpty(abuseEndDate) ? abuseEndDate
				: abuseEndDatePersistable != null
						? ((new SimpleDateFormat(DATE_FORMAT)).format(abuseEndDatePersistable)) : "";
	}

	@ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd", example = "1963-11-22")
	public String getAbuseStartDate() {
		return !Strings.isNullOrEmpty(abuseStartDate) ? abuseStartDate
				: abuseStartDatePersistable != null
						? ((new SimpleDateFormat(DATE_FORMAT)).format(abuseStartDatePersistable)) : "";
	}

	/**
	 * @return the allegationDispositionType
	 */
	@ApiModelProperty(required = true, readOnly = true, value = "Size must be between 1 and 4", example = "1234")
	public Integer getAllegationDispositionType() {
		return allegationDispositionType;
	}

	/**
	 * @return the allegationType
	 */
	@ApiModelProperty(required = true, readOnly = true, value = "Size must be between 1 and 4", example = "1234")
	public Integer getAllegationType() {
		return allegationType;
	}

	/**
	 * @return the dispositionDescription
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "Size must be between 1 and 254", example = "The first column will march to so and so etc")
	public String getDispositionDescription() {
		return dispositionDescription;
	}

	/**
	 * @return the dispositionDate
	 */
	@ApiModelProperty(required = false, readOnly = false, example = "2016-18-06")
	public String getDispositionDate() {
		return !Strings.isNullOrEmpty(dispositionDate) ? dispositionDate
				: dispositionDatePersistable != null
						? ((new SimpleDateFormat(DATE_FORMAT)).format(dispositionDatePersistable)) : "";
	}

	/**
	 * @return the injuryHarmDetailIndVar
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "Size must be 1", example = "K")
	public String getInjuryHarmDetailIndVar() {
		return injuryHarmDetailIndVar;
	}

	/**
	 * @return the nonProtectingParentCode
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "U", example = "U")
	public String getNonProtectingParentCode() {
		return nonProtectingParentCode;
	}

	/**
	 * @return the staffPersonAddedInd
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "N", example = "N")
	public String getStaffPersonAddedInd() {
		return staffPersonAddedInd;
	}

	@ApiModelProperty(readOnly = true, value = "remove this from view of client, generated at business layer")
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@ApiModelProperty(required = true, readOnly = true, value = "remove from view of user", example = "1963-11-22")
	public String getLastUpdatedTime() {
		return !Strings.isNullOrEmpty(lastUpdatedTime) ? lastUpdatedTime
				: lastUpdatedTimePersistable != null
						? ((new SimpleDateFormat(TIMESTAMP_FORMAT)).format(lastUpdatedTimePersistable)) : "";
	}

	/**
	 * @return the fkClientT
	 */
	@ApiModelProperty(required = true, readOnly = true, value = "Size must be between 1 and 10", example = "xydvd")
	public String getFkClientT() {
		return fkClientT;
	}

	/**
	 * @return the fkClient0
	 */
	@ApiModelProperty(required = true, readOnly = true, value = "Size must be between 0 and 10", example = "xydvd")
	public String getFkClient0() {
		return fkClient0;
	}

	/**
	 * @return the fkReferralT
	 */
	@ApiModelProperty(required = true, readOnly = true, value = "Size must be between 1 and 10", example = "xydvd")
	public String getFkReferralT() {
		return fkReferralT;
	}

	/**
	 * @return the countySpecificCode
	 */
	@ApiModelProperty(required = true, readOnly = true, value = "Size must be between 1 and 2", example = "CA")
	public String getCountySpecificCode() {
		return countySpecificCode;
	}

	/**
	 * @return the zippyCrestedInd
	 */
	@ApiModelProperty(required = true, readOnly = false, value = "N", example = "N")
	public String getZippyCrestedInd() {
		return zippyCrestedInd;
	}

	/**
	 * @return the placementFacilityType
	 */
	@ApiModelProperty(required = false, readOnly = true, value = "Size must be between 1 and 4", example = "6574")
	public Integer getPlacementFacilityType() {
		return placementFacilityType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((abuseFrequency == null) ? 0 : abuseFrequency.hashCode());
		result = prime * result + ((lastUpdatedId == null) ? 0 : lastUpdatedId.hashCode());
		result = prime * result + ((lastUpdatedTime == null) ? 0 : lastUpdatedTime.hashCode());
		result = prime * result + ((abuseStartDate == null) ? 0 : abuseStartDate.hashCode());
		result = prime * result + ((allegationDispositionType == null) ? 0 : allegationDispositionType.hashCode());
		result = prime * result + ((dispositionDescription == null) ? 0 : dispositionDescription.hashCode());
		result = prime * result + ((fkClientT == null) ? 0 : fkClientT.hashCode());
		result = prime * result + ((fkReferralT == null) ? 0 : fkReferralT.hashCode());
		result = prime * result + ((placementFacilityType == null) ? 0 : placementFacilityType.hashCode());
		result = prime * result + ((staffPersonAddedInd == null) ? 0 : staffPersonAddedInd.hashCode());
		result = prime * result + ((zippyCrestedInd == null) ? 0 : zippyCrestedInd.hashCode());
		result = prime * result + ((allegationType == null) ? 0 : allegationType.hashCode());
		result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Allegation other = (Allegation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (abuseFrequency == null) {
			if (other.abuseFrequency != null)
				return false;
		} else if (!abuseFrequency.equals(other.abuseFrequency))
			return false;
		if (abuseEndDate == null) {
			if (other.abuseEndDate != null)
				return false;
		} else if (!abuseEndDate.equals(other.abuseEndDate))
			return false;
		if (dispositionDate == null) {
			if (other.dispositionDate != null)
				return false;
		} else if (!dispositionDate.equals(other.dispositionDate))
			return false;
		if (dispositionDescription == null) {
			if (other.dispositionDescription != null)
				return false;
		} else if (!dispositionDescription.equals(other.dispositionDescription))
			return false;
		if (abuseStartDate == null) {
			if (other.abuseStartDate != null)
				return false;
		} else if (!abuseStartDate.equals(other.abuseStartDate))
			return false;
		if (fkClient0 == null) {
			if (other.fkClient0 != null)
				return false;
		} else if (!fkClient0.equals(other.fkClient0))
			return false;
		if (fkClientT == null) {
			if (other.fkClientT != null)
				return false;
		} else if (!fkClientT.equals(other.fkClientT))
			return false;
		if (fkReferralT == null) {
			if (other.fkReferralT != null)
				return false;
		} else if (!fkReferralT.equals(other.fkReferralT))
			return false;
		if (nonProtectingParentCode == null) {
			if (other.nonProtectingParentCode != null)
				return false;
		} else if (!nonProtectingParentCode.equals(other.nonProtectingParentCode))
			return false;
		if (placementFacilityType == null) {
			if (other.placementFacilityType != null)
				return false;
		} else if (!placementFacilityType.equals(other.placementFacilityType))
			return false;
		if (staffPersonAddedInd == null) {
			if (other.staffPersonAddedInd != null)
				return false;
		} else if (!staffPersonAddedInd.equals(other.staffPersonAddedInd))
			return false;
		if (zippyCrestedInd == null) {
			if (other.zippyCrestedInd != null)
				return false;
		} else if (!zippyCrestedInd.equals(other.zippyCrestedInd))
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
		return true;
	}
}
