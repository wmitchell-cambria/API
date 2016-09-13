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
@InjectLinks({
    @InjectLink(value = "/{resource}/{id}", rel = "self", style = Style.ABSOLUTE, bindings = {
        @Binding(name = "id", value = "${instance.id}"),
        @Binding(name = "resource", value = Api.RESOURCE_ALLEGATION)}),
    @InjectLink(value = "/{resource}/{id}", rel = "perpetratorClientId", style = Style.ABSOLUTE, bindings = {
        @Binding(name = "id", value = "${instance.perpetratorClientId}"),
        @Binding(name = "resource", value = Api.RESOURCE_CLIENT)},
        condition = "${not empty instance.perpetratorClientId }"),
    @InjectLink(value = "/{resource}/{id}", rel = "victimClientId", style = Style.ABSOLUTE, bindings = {
        @Binding(name = "id", value = "${instance.victimClientId}"),
        @Binding(name = "resource", value = Api.RESOURCE_CLIENT)}),
    @InjectLink(value = "/{resource}/{id}", rel = "referralId", style = Style.ABSOLUTE,
        bindings = {@Binding(name = "id", value = "${instance.referralId}"),
            @Binding(name = "resource", value = Api.RESOURCE_REFERRAL)})})
public class Allegation extends DomainObject {

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "Value overwritten on POST", example = "ABC123")
  private String id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "abuseEndDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String abuseEndDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short abuseFrequency;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"D", "M", "W", "Y"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "D",
      allowableValues = "D, M, W, Y")
  private String abuseFrequencyPeriodCode;

  @NotEmpty
  @Size(min = 1, max = 75)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String abuseLocationDescription;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "abuseStartDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String abuseStartDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short allegationDispositionType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short allegationType;

  @NotEmpty
  @Size(min = 1, max = 254)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String dispositionDescription;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "dispositionDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String dispositionDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean injuryHarmDetailIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"U", "P", "Y", "N"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U",
      allowableValues = "U, P, Y, N")
  private String nonProtectingParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean staffPersonAddedIndicator;

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String victimClientId;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC123")
  private String perpetratorClientId;

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String referralId;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "A1")
  private String countySpecificCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean zippyCreatedIndicator;

  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short placementFacilityType;

  @JsonCreator
  public Allegation(@JsonProperty("id") String id,
      @JsonProperty("abuseEndDate") String abuseEndDate,
      @JsonProperty("abuseFrequency") Short abuseFrequency,
      @JsonProperty("abuseFrequencyPeriodCode") String abuseFrequencyPeriodCode,
      @JsonProperty("abuseLocationDescription") String abuseLocationDescription,
      @JsonProperty("abuseStartDate") String abuseStartDate,
      @JsonProperty("allegationDispositionType") Short allegationDispositionType,
      @JsonProperty("allegationType") Short allegationType,
      @JsonProperty("dispositionDescription") String dispositionDescription,
      @JsonProperty("dispositionDate") String dispositionDate,
      @JsonProperty("injuryHarmDetailIndicator") Boolean injuryHarmDetailIndicator,
      @JsonProperty("nonProtectingParentCode") String nonProtectingParentCode,
      @JsonProperty("staffPersonAddedIndicator") Boolean staffPersonAddedIndicator,
      @JsonProperty("victimClientId") String victimClientId, @JsonProperty("perpetratorClientId") String perpetratorClientId,
      @JsonProperty("referralId") String referralId,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("zippyCreatedIndicator") Boolean zippyCreatedIndicator,
      @JsonProperty("placementFacilityType") Short placementFacilityType) {
    super();
    this.id = id;
    this.abuseEndDate = abuseEndDate;
    this.abuseFrequency = abuseFrequency;
    this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
    this.abuseLocationDescription = abuseLocationDescription;
    this.abuseStartDate = abuseStartDate;
    this.allegationDispositionType = allegationDispositionType;
    this.allegationType = allegationType;
    this.dispositionDescription = dispositionDescription;
    this.dispositionDate = dispositionDate;
    this.injuryHarmDetailIndicator = injuryHarmDetailIndicator;
    this.nonProtectingParentCode = nonProtectingParentCode;
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    this.victimClientId = victimClientId;
    this.perpetratorClientId = perpetratorClientId;
    this.referralId = referralId;
    this.countySpecificCode = countySpecificCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    this.placementFacilityType = placementFacilityType;
  }

  public Allegation(gov.ca.cwds.rest.api.persistence.legacy.Allegation persistedAllegation) {
    this.id = persistedAllegation.getId();
    this.abuseEndDate = DomainObject.cookDate(persistedAllegation.getAbuseEndDate());
    this.abuseFrequency = persistedAllegation.getAbuseFrequency();
    this.abuseFrequencyPeriodCode = persistedAllegation.getAbuseFrequencyPeriodCode();
    this.abuseLocationDescription = persistedAllegation.getAbuseLocationDescription();
    this.abuseStartDate = DomainObject.cookDate(persistedAllegation.getAbuseStartDate());
    this.allegationDispositionType = persistedAllegation.getAllegationDispositionType();
    this.allegationType = persistedAllegation.getAllegationType();
    this.dispositionDescription = persistedAllegation.getDispositionDescription();
    this.dispositionDate = DomainObject.cookDate(persistedAllegation.getDispositionDate());
    this.injuryHarmDetailIndicator =
        DomainObject.uncookBooleanString(persistedAllegation.getInjuryHarmDetailIndicator());
    this.nonProtectingParentCode = persistedAllegation.getNonProtectingParentCode();
    this.staffPersonAddedIndicator =
        DomainObject.uncookBooleanString(persistedAllegation.getStaffPersonAddedIndicator());
    this.victimClientId = persistedAllegation.getVictimClientId();
    this.perpetratorClientId = persistedAllegation.getPerpetratorClientId();
    this.referralId = persistedAllegation.getReferralId();
    this.countySpecificCode = persistedAllegation.getCountySpecificCode();
    this.zippyCreatedIndicator =
        DomainObject.uncookBooleanString(persistedAllegation.getZippyCreatedIndicator());
    this.placementFacilityType = persistedAllegation.getPlacementFacilityType();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the abuseEndDate
   */
  public String getAbuseEndDate() {
    return abuseEndDate;
  }

  /**
   * @return the abuseFrequency
   */
  public Short getAbuseFrequency() {
    return abuseFrequency;
  }

  /**
   * @return the abuseFrequencyPeriodCode
   */
  public String getAbuseFrequencyPeriodCode() {
    return abuseFrequencyPeriodCode;
  }

  /**
   * @return the abuseLocationDescription
   */
  public String getAbuseLocationDescription() {
    return abuseLocationDescription;
  }

  /**
   * @return the abuseStartDate
   */
  public String getAbuseStartDate() {
    return abuseStartDate;
  }

  /**
   * @return the allegationDispositionType
   */
  public Short getAllegationDispositionType() {
    return allegationDispositionType;
  }

  /**
   * @return the allegationType
   */
  public Short getAllegationType() {
    return allegationType;
  }

  /**
   * @return the dispositionDescription
   */
  public String getDispositionDescription() {
    return dispositionDescription;
  }

  /**
   * @return the dispositionDate
   */
  public String getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the injuryHarmDetailIndicator
   */
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
   * @return the staffPersonAddedIndicator
   */
  public Boolean getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  /**
   * @return the victimClientId
   */
  public String getVictimClientId() {
    return victimClientId;
  }

  /**
   * @return the perpetratorClientId
   */
  public String getPerpetratorClientId() {
    return perpetratorClientId;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  /**
   * @return the placementFacilityType
   */
  public Short getPlacementFacilityType() {
    return placementFacilityType;
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
    result = prime * result + ((abuseEndDate == null) ? 0 : abuseEndDate.hashCode());
    result = prime * result + ((abuseFrequency == null) ? 0 : abuseFrequency.hashCode());
    result =
        prime * result
            + ((abuseFrequencyPeriodCode == null) ? 0 : abuseFrequencyPeriodCode.hashCode());
    result =
        prime * result
            + ((abuseLocationDescription == null) ? 0 : abuseLocationDescription.hashCode());
    result = prime * result + ((abuseStartDate == null) ? 0 : abuseStartDate.hashCode());
    result =
        prime * result
            + ((allegationDispositionType == null) ? 0 : allegationDispositionType.hashCode());
    result = prime * result + ((allegationType == null) ? 0 : allegationType.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result + ((dispositionDate == null) ? 0 : dispositionDate.hashCode());
    result =
        prime * result + ((dispositionDescription == null) ? 0 : dispositionDescription.hashCode());
    result = prime * result + ((perpetratorClientId == null) ? 0 : perpetratorClientId.hashCode());
    result = prime * result + ((victimClientId == null) ? 0 : victimClientId.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result =
        prime * result
            + ((injuryHarmDetailIndicator == null) ? 0 : injuryHarmDetailIndicator.hashCode());
    result =
        prime * result
            + ((nonProtectingParentCode == null) ? 0 : nonProtectingParentCode.hashCode());
    result =
        prime * result + ((placementFacilityType == null) ? 0 : placementFacilityType.hashCode());
    result =
        prime * result
            + ((staffPersonAddedIndicator == null) ? 0 : staffPersonAddedIndicator.hashCode());
    result =
        prime * result + ((zippyCreatedIndicator == null) ? 0 : zippyCreatedIndicator.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
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
    Allegation other = (Allegation) obj;
    if (abuseEndDate == null) {
      if (other.abuseEndDate != null) {
        return false;
      }
    } else if (!abuseEndDate.equals(other.abuseEndDate)) {
      return false;
    }
    if (abuseFrequency == null) {
      if (other.abuseFrequency != null) {
        return false;
      }
    } else if (!abuseFrequency.equals(other.abuseFrequency)) {
      return false;
    }
    if (abuseFrequencyPeriodCode == null) {
      if (other.abuseFrequencyPeriodCode != null) {
        return false;
      }
    } else if (!abuseFrequencyPeriodCode.equals(other.abuseFrequencyPeriodCode)) {
      return false;
    }
    if (abuseLocationDescription == null) {
      if (other.abuseLocationDescription != null) {
        return false;
      }
    } else if (!abuseLocationDescription.equals(other.abuseLocationDescription)) {
      return false;
    }
    if (abuseStartDate == null) {
      if (other.abuseStartDate != null) {
        return false;
      }
    } else if (!abuseStartDate.equals(other.abuseStartDate)) {
      return false;
    }
    if (allegationDispositionType == null) {
      if (other.allegationDispositionType != null) {
        return false;
      }
    } else if (!allegationDispositionType.equals(other.allegationDispositionType)) {
      return false;
    }
    if (allegationType == null) {
      if (other.allegationType != null) {
        return false;
      }
    } else if (!allegationType.equals(other.allegationType)) {
      return false;
    }
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null) {
        return false;
      }
    } else if (!countySpecificCode.equals(other.countySpecificCode)) {
      return false;
    }
    if (dispositionDate == null) {
      if (other.dispositionDate != null) {
        return false;
      }
    } else if (!dispositionDate.equals(other.dispositionDate)) {
      return false;
    }
    if (dispositionDescription == null) {
      if (other.dispositionDescription != null) {
        return false;
      }
    } else if (!dispositionDescription.equals(other.dispositionDescription)) {
      return false;
    }
    if (perpetratorClientId == null) {
      if (other.perpetratorClientId != null) {
        return false;
      }
    } else if (!perpetratorClientId.equals(other.perpetratorClientId)) {
      return false;
    }
    if (victimClientId == null) {
      if (other.victimClientId != null) {
        return false;
      }
    } else if (!victimClientId.equals(other.victimClientId)) {
      return false;
    }
    if (referralId == null) {
      if (other.referralId != null) {
        return false;
      }
    } else if (!referralId.equals(other.referralId)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (injuryHarmDetailIndicator == null) {
      if (other.injuryHarmDetailIndicator != null) {
        return false;
      }
    } else if (!injuryHarmDetailIndicator.equals(other.injuryHarmDetailIndicator)) {
      return false;
    }
    if (nonProtectingParentCode == null) {
      if (other.nonProtectingParentCode != null) {
        return false;
      }
    } else if (!nonProtectingParentCode.equals(other.nonProtectingParentCode)) {
      return false;
    }
    if (placementFacilityType == null) {
      if (other.placementFacilityType != null) {
        return false;
      }
    } else if (!placementFacilityType.equals(other.placementFacilityType)) {
      return false;
    }
    if (staffPersonAddedIndicator == null) {
      if (other.staffPersonAddedIndicator != null) {
        return false;
      }
    } else if (!staffPersonAddedIndicator.equals(other.staffPersonAddedIndicator)) {
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
