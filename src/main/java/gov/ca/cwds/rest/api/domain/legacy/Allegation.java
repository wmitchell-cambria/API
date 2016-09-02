package gov.ca.cwds.rest.api.domain.legacy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.core.Api;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
@ApiModel
@InjectLinks(
        { 
        	@InjectLink(value="/{resource}/{id}", rel="self", style=Style.ABSOLUTE, bindings={ @Binding(name="id", value="${instance.id}"), @Binding(name="resource", value=Api.RESOURCE_ALLEGATION) } ),
        	@InjectLink(value="/{resource}/{id}", rel="fkClient0", style=Style.ABSOLUTE, bindings={ @Binding(name="id", value="${instance.fkClient0}"), @Binding(name="resource", value=Api.RESOURCE_CLIENT) }, condition="${not empty instance.fkClient0 }" ),
        	@InjectLink(value="/{resource}/{id}", rel="fkClientT", style=Style.ABSOLUTE, bindings={ @Binding(name="id", value="${instance.fkClientT}"), @Binding(name="resource", value=Api.RESOURCE_CLIENT) } ),
        	@InjectLink(value="/{resource}/{id}", rel="fkReferralT", style=Style.ABSOLUTE, bindings={ @Binding(name="id", value="${instance.fkReferralT}"), @Binding(name="resource", value=Api.RESOURCE_REFERRAL) } )
        })
public class Allegation extends DomainObject {

	@NotEmpty
	@Size(min = 1, max = 10, message = "Size must be between 1 and 10")
	private String id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonProperty(value = "abuseEndDate")
	@gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
	private String abuseEndDate;

	@gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonProperty(value = "abuseStartDate")
	private String abuseStartDate;

	@NotNull
	private Integer abuseFrequency;

	@NotEmpty
	@Size(min = 1, max = 1, message = "Size must be 1")
	@OneOf(value = {"D", "M", "W", "Y"}, ignoreCase = true, ignoreWhitespace = true)
	@ApiModelProperty(required = true, readOnly = false, value = "Size must be 1", example = "D", allowableValues="D,M,W,Y")
	private String abuseFrequencyPeriodCode;

	@NotEmpty
	@Size(min = 1, max = 75)
	private String abuseLocationDescription;

	@NotNull
	private Integer allegationDispositionType;

	@NotNull
	private Integer allegationType;

	@NotEmpty
	@Size(min = 1, max = 256)
	private String dispositionDescription;

	@gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonProperty(value = "dispositionDate")
	private String dispositionDate;

	@NotNull
	private Boolean injuryHarmDetailIndicator;

	@NotEmpty
	@Size(min = 1, max = 1, message = "Size must be 1")
	@OneOf(value = {"U", "P", "Y", "N"}, ignoreCase = true, ignoreWhitespace = true)
	@ApiModelProperty(required = true, readOnly = false, value = "Default value is U", example = "U", allowableValues="U,P,Y,N")
	private String nonProtectingParentCode;

	@NotNull
	private Boolean staffPersonAddedIndicator;

	@NotEmpty
	@Size(min = 1, max = 10)
	private String fkClientT;

	@Size(min = 0, max = 10)
	private String fkClient0;

	@NotEmpty
	@Size(min = 1, max = 10)
	private String fkReferralT;

	@NotEmpty
	@Size(min = 1, max = 2, message = "Size must be between 1 and 2")
	private String countySpecificCode;

	@NotNull
	private Boolean zippyCrestedIndicator;

	private Integer placementFacilityType;

	@JsonCreator
	public Allegation(@JsonProperty("id") String id, 
			@JsonProperty("abuseEndDate") String abuseEndDate,
			@JsonProperty("abuseFrequency") Integer abuseFrequency,
			@JsonProperty("abuseFrequencyPeriodCode") String abuseFrequencyPeriodCode,
			@JsonProperty("abuseLocationDescription") String abuseLocationDescription,
			@JsonProperty("abuseStartDate") String abuseStartDate,
			@JsonProperty("allegationDispositionType") Integer allegationDispositionType,
			@JsonProperty("allegationType") Integer allegationType,
			@JsonProperty("dispositionDescription") String dispositionDescription,
			@JsonProperty("dispositionDate") String dispositionDate,
			@JsonProperty("injuryHarmDetailIndicator") Boolean injuryHarmDetailIndicator,
			@JsonProperty("nonProtectingParentCode") String nonProtectingParentCode,
			@JsonProperty("staffPersonAddedIndicator") Boolean staffPersonAddedIndicator,
			@JsonProperty("fkClientT") String fkClientT,
			@JsonProperty("fkClient0") String fkClient0, 
			@JsonProperty("fkReferralT") String fkReferralT,
			@JsonProperty("countySpecificCode") String countySpecificCode,
			@JsonProperty("zippyCrestedIndicator") Boolean zippyCrestedIndicator,
			@JsonProperty("placementFacilityType") Integer placementFacilityType) {
		super();
		this.id = id;
		this.abuseFrequency = abuseFrequency;
		this.abuseEndDate = abuseEndDate;
		this.abuseStartDate = abuseStartDate;
		this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
		this.abuseLocationDescription = abuseLocationDescription;
		this.allegationDispositionType = allegationDispositionType;
		this.allegationType = allegationType;
		this.dispositionDescription = dispositionDescription;
		this.dispositionDate = dispositionDate;
		this.injuryHarmDetailIndicator = injuryHarmDetailIndicator;
		this.nonProtectingParentCode = nonProtectingParentCode;
		this.staffPersonAddedIndicator = staffPersonAddedIndicator;
		this.fkClientT = fkClientT;
		this.fkClient0 = fkClient0;
		this.fkReferralT = fkReferralT;
		this.countySpecificCode = countySpecificCode;
		this.zippyCrestedIndicator = zippyCrestedIndicator;
		this.placementFacilityType = placementFacilityType;
	}
	
	public Allegation(gov.ca.cwds.rest.api.persistence.legacy.Allegation persistedAllegation) {
		this.id = persistedAllegation.getId();
		this.abuseFrequency = persistedAllegation.getAbuseFrequency();
		this.abuseEndDate = DomainObject.cookDate(persistedAllegation.getAbuseEndDate());
		this.abuseStartDate = DomainObject.cookDate(persistedAllegation.getAbuseStartDate());
		this.abuseFrequencyPeriodCode = persistedAllegation.getAbuseFrequencyPeriodCode();
		this.abuseLocationDescription = persistedAllegation.getAbuseLocationDescription();
		this.allegationDispositionType = persistedAllegation.getAllegationDispositionType();
		this.allegationType = persistedAllegation.getAllegationType();
		this.dispositionDescription = persistedAllegation.getDispositionDescription();
		this.dispositionDate = DomainObject.cookDate(persistedAllegation.getDispositionDate());
		this.injuryHarmDetailIndicator = DomainObject.uncookBooleanString(persistedAllegation.getInjuryHarmDetailIndicator());
		this.nonProtectingParentCode = persistedAllegation.getNonProtectingParentCode();
		this.staffPersonAddedIndicator = DomainObject.uncookBooleanString(persistedAllegation.getStaffPersonAddedIndicator());
		this.fkClientT = persistedAllegation.getFkClientT();
		this.fkClient0 = persistedAllegation.getFkClient0();
		this.fkReferralT = persistedAllegation.getFkReferralT();
		this.countySpecificCode = persistedAllegation.getCountySpecificCode();
		this.zippyCrestedIndicator = DomainObject.uncookBooleanString(persistedAllegation.getZippyCrestedIndicator());
		this.placementFacilityType = persistedAllegation.getPlacementFacilityType();
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
		return abuseEndDate;
	}

	@ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd", example = "1963-11-22")
	public String getAbuseStartDate() {
		return abuseStartDate;
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
		return dispositionDate;
	}

	/**
	 * @return the injuryHarmDetailIndicator
	 */
	@ApiModelProperty(required = true, readOnly = false, example = "true")
	public Boolean getInjuryHarmDetailIndicator() {
		return injuryHarmDetailIndicator;
	}

	/**
	 * @return the nonProtectingParentCode
	 */
	public String getNonProtectingParentCode() {
		return nonProtectingParentCode;
	}

	/**
	 * @return the staffPersonAddedInd
	 */
	@ApiModelProperty(required = true, readOnly = false, example = "true")
	public Boolean getStaffPersonAddedIndicator() {
		return staffPersonAddedIndicator;
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
	@ApiModelProperty(required = true, readOnly = false, example = "true")
	public Boolean getZippyCrestedIndicator() {
		return zippyCrestedIndicator;
	}

	/**
	 * @return the placementFacilityType
	 */
	@ApiModelProperty(required = false, readOnly = true, value = "Size must be between 1 and 4", example = "6574")
	public Integer getPlacementFacilityType() {
		return placementFacilityType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abuseEndDate == null) ? 0 : abuseEndDate.hashCode());
		result = prime * result + ((abuseFrequency == null) ? 0 : abuseFrequency.hashCode());
		result = prime * result + ((abuseFrequencyPeriodCode == null) ? 0 : abuseFrequencyPeriodCode.hashCode());
		result = prime * result + ((abuseLocationDescription == null) ? 0 : abuseLocationDescription.hashCode());
		result = prime * result + ((abuseStartDate == null) ? 0 : abuseStartDate.hashCode());
		result = prime * result + ((allegationDispositionType == null) ? 0 : allegationDispositionType.hashCode());
		result = prime * result + ((allegationType == null) ? 0 : allegationType.hashCode());
		result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
		result = prime * result + ((dispositionDate == null) ? 0 : dispositionDate.hashCode());
		result = prime * result + ((dispositionDescription == null) ? 0 : dispositionDescription.hashCode());
		result = prime * result + ((fkClient0 == null) ? 0 : fkClient0.hashCode());
		result = prime * result + ((fkClientT == null) ? 0 : fkClientT.hashCode());
		result = prime * result + ((fkReferralT == null) ? 0 : fkReferralT.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((injuryHarmDetailIndicator == null) ? 0 : injuryHarmDetailIndicator.hashCode());
		result = prime * result + ((nonProtectingParentCode == null) ? 0 : nonProtectingParentCode.hashCode());
		result = prime * result + ((placementFacilityType == null) ? 0 : placementFacilityType.hashCode());
		result = prime * result + ((staffPersonAddedIndicator == null) ? 0 : staffPersonAddedIndicator.hashCode());
		result = prime * result + ((zippyCrestedIndicator == null) ? 0 : zippyCrestedIndicator.hashCode());
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
		Allegation other = (Allegation) obj;
		if (abuseEndDate == null) {
			if (other.abuseEndDate != null)
				return false;
		} else if (!abuseEndDate.equals(other.abuseEndDate))
			return false;
		if (abuseFrequency == null) {
			if (other.abuseFrequency != null)
				return false;
		} else if (!abuseFrequency.equals(other.abuseFrequency))
			return false;
		if (abuseFrequencyPeriodCode == null) {
			if (other.abuseFrequencyPeriodCode != null)
				return false;
		} else if (!abuseFrequencyPeriodCode.equals(other.abuseFrequencyPeriodCode))
			return false;
		if (abuseLocationDescription == null) {
			if (other.abuseLocationDescription != null)
				return false;
		} else if (!abuseLocationDescription.equals(other.abuseLocationDescription))
			return false;
		if (abuseStartDate == null) {
			if (other.abuseStartDate != null)
				return false;
		} else if (!abuseStartDate.equals(other.abuseStartDate))
			return false;
		if (allegationDispositionType == null) {
			if (other.allegationDispositionType != null)
				return false;
		} else if (!allegationDispositionType.equals(other.allegationDispositionType))
			return false;
		if (allegationType == null) {
			if (other.allegationType != null)
				return false;
		} else if (!allegationType.equals(other.allegationType))
			return false;
		if (countySpecificCode == null) {
			if (other.countySpecificCode != null)
				return false;
		} else if (!countySpecificCode.equals(other.countySpecificCode))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (injuryHarmDetailIndicator == null) {
			if (other.injuryHarmDetailIndicator != null)
				return false;
		} else if (!injuryHarmDetailIndicator.equals(other.injuryHarmDetailIndicator))
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
		if (staffPersonAddedIndicator == null) {
			if (other.staffPersonAddedIndicator != null)
				return false;
		} else if (!staffPersonAddedIndicator.equals(other.staffPersonAddedIndicator))
			return false;
		if (zippyCrestedIndicator == null) {
			if (other.zippyCrestedIndicator != null)
				return false;
		} else if (!zippyCrestedIndicator.equals(other.zippyCrestedIndicator))
			return false;
		return true;
	}
}
