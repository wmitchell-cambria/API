package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.util.SysIdShortToStringSerializer;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"allegation_type", "created_by_screener", "allegation_sub_types", "disposition",
    "rationale", "legacy_descriptor", "victim", "perpetrator"})
public class Allegation extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("allegation_type")
  @JsonSerialize(using = SysIdShortToStringSerializer.class)
  @ApiModelProperty(required = true, readOnly = false, value = "Allegation Type", example = "2179",
      dataType = "string")
  @NotNull
  private Short allegationType;

  @JsonProperty("created_by_screener")
  @ApiModelProperty(required = false, readOnly = false,
      value = "allegation created during screening")
  private Boolean createdByScreener;

  @JsonProperty("allegation_sub_types")
  @ApiModelProperty(required = false, readOnly = false)
  // @Valid
  private Set<AllegationSubType> allegationSubType;

  @JsonProperty("disposition")
  @JsonSerialize(using = SysIdShortToStringSerializer.class)
  @ApiModelProperty(required = false, readOnly = false, dataType = "string")
  private Short dispositionType;

  @JsonProperty("rationale")
  @ApiModelProperty(required = false, readOnly = false)
  @Size(max = 254)
  private String rationale;

  @JsonProperty("legacy_descriptor")
  @ApiModelProperty(required = false, readOnly = false)
  private CmsRecordDescriptor legacyDescriptor;

  @JsonProperty("victim")
  @Valid
  @NotNull
  private AllegationPerson victim;

  @JsonProperty("perpetrator")
  @Valid
  private AllegationPerson perpetrator;

  /**
   * default constructor
   */
  public Allegation() {
    super();
  }

  /**
   * @param allegationType - LOV of allegation type
   * @param createdByScreener - is this created by a screener
   * @param allegationSubType - list of allegation sub-types
   * @param dispositionType - LOV of Allegation Disposition Type
   * @param rationale - rational of the disposition
   * @param legacyDescriptor - CMS record descriptor
   * @param victim - allegation victim (required)
   * @param perpetrator - allegation perpetrator
   */
  public Allegation(@ValidSystemCodeId(required = true, category = "INJR_HMC") Short allegationType,
      Boolean createdByScreener, Set<AllegationSubType> allegationSubType, Short dispositionType,
      String rationale, CmsRecordDescriptor legacyDescriptor, AllegationPerson victim,
      AllegationPerson perpetrator) {
    super();
    this.allegationType = allegationType;
    this.createdByScreener = createdByScreener;
    this.allegationSubType = allegationSubType;
    this.dispositionType = dispositionType;
    this.rationale = rationale;
    this.legacyDescriptor = legacyDescriptor;
    this.victim = victim;
    this.perpetrator = perpetrator;
  }

  /**
   * Constructing new Allegation object
   * 
   * @param persistedAllocation - persisted Allegation object
   * @param allegationSubType - allegation sub types
   */
  public Allegation(gov.ca.cwds.data.persistence.cms.Allegation persistedAllocation,
      Set<AllegationSubType> allegationSubType) {
    super();
    this.allegationType = persistedAllocation.getAllegationType();
    this.dispositionType = persistedAllocation.getAllegationDispositionType();
    this.victim = new AllegationPerson(persistedAllocation.getVictim());
    if (persistedAllocation.getPerpetrator() != null) {
      this.perpetrator = new AllegationPerson(persistedAllocation.getPerpetrator());
    }

    this.createdByScreener = Boolean.FALSE;

    this.allegationSubType = allegationSubType;
  }

  /**
   * @return - the Allegation type
   */
  public Short getAllegationType() {
    return allegationType;
  }


  /**
   * @param allegationType - LOV of Injury Harm Type
   */
  public void setAllegationType(Short allegationType) {
    this.allegationType = allegationType;
  }


  /**
   * @return - created by a screening
   */
  public Boolean getCreatedByScreener() {
    return createdByScreener;
  }


  /**
   * @param createdByScreener - true or false
   */
  public void setCreatedByScreener(Boolean createdByScreener) {
    this.createdByScreener = createdByScreener;
  }


  /**
   * @return - list of allegation sub-types
   */
  public Set<AllegationSubType> getAllegationSubType() {
    return allegationSubType;
  }


  /**
   * @param allegationSubType - list of allegation sub-types
   */
  public void setAllegationSubType(Set<AllegationSubType> allegationSubType) {
    this.allegationSubType = allegationSubType;
  }


  /**
   * @return - allegation disposition LOV
   */
  public Short getDispositionType() {
    return dispositionType;
  }


  /**
   * @param dispositionType - allegation disposition LOV
   */
  public void setDispositionType(Short dispositionType) {
    this.dispositionType = dispositionType;
  }


  /**
   * @return - allegation disposition rationale text
   */
  public String getRationale() {
    return rationale;
  }


  /**
   * @param rationale - allegation disposition rationale text
   */
  public void setRationale(String rationale) {
    this.rationale = rationale;
  }


  /**
   * @return - allegation victim
   */
  public AllegationPerson getVictim() {
    return victim;
  }

  /**
   * @param legacyDescriptor - CMS record description of ALLEGATION
   */
  public void setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * @return - CMS record description of ALLEGATION
   */
  public CmsRecordDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @param victim - allegation victim
   */
  public void setVictim(AllegationPerson victim) {
    this.victim = victim;
  }


  /**
   * @return - allegation perpetrator
   */
  public AllegationPerson getPerpetrator() {
    return perpetrator;
  }


  /**
   * @param perpetrator - allegation perpetrator
   */
  public void setPerpetrator(AllegationPerson perpetrator) {
    this.perpetrator = perpetrator;
  }


  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
