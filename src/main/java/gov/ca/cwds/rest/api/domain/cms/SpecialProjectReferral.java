package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
public class SpecialProjectReferral extends ReportingDomain implements Request, Response{
  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "County specific code", example = "99")
  private String countySpecificCode;
  
  @NotEmpty
  @NotBlank
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "named in referral",
      example = "ABC1234567")
  private String referralId;
  
  @NotEmpty
  @NotBlank
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "special project ID",
      example = "ABC1234567")
  private String specialProjectId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT)
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String participationEndDate;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT)
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT, required = false)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String participationStartDate;
  
  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean safelySurrenderedBabiesIndicator;
  
  /**
   * Constructor
   */
  public SpecialProjectReferral() {
    super();
  }
  
  /**
   * Constructor
   * 
   * @param countySpecificCode - county specific code
   * @param referralId - referral ID
   * @param spcialProjectId - special project ID
   * @param participationEndDate - participation end date
   * @param participationStartDate - participation start date
   * @param safelySurrenderedBabiesIndicator - safely surrendered babies indicator
   */
  public SpecialProjectReferral(String countySpecificCode, String referralId, String specialProjectId, 
      String participationEndDate, String participationStartDate, Boolean safelySurrenderedBabiesIndicator) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.referralId = referralId;
    this.specialProjectId = specialProjectId;
    this.participationEndDate = participationEndDate;
    this.participationStartDate = participationStartDate;
    this.safelySurrenderedBabiesIndicator = safelySurrenderedBabiesIndicator;
  }
  
  /**
   * Constructor
   * 
   * @param specialProjectReferral - persisted special project referral
   * 
   */
  public SpecialProjectReferral(gov.ca.cwds.data.persistence.cms.SpecialProjectReferral persistent) {
    super();
    this.countySpecificCode = persistent.getCountySpecificCode();
    this.referralId = persistent.getReferralId();
    this.specialProjectId = persistent.getSpecialProjectId();
    this.participationEndDate = DomainChef.cookDate(persistent.getParticipationEndDate());
    this.participationStartDate = DomainChef.cookDate(persistent.getParticipationStartDate());
    this.safelySurrenderedBabiesIndicator = DomainChef.uncookBooleanString(persistent.getSafelySurrenderedBabiesIndicator());
  }

  /**
   * 
   * @return - countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * 
   * @return - referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * 
   * @return - specialProjectId
   */
  public String getSpecialProjectId() {
    return specialProjectId;
  }

  /**
   * 
   * @return - participationEndDate
   */
  public String getParticipationEndDate() {
    return participationEndDate;
  }

  /** 
   * 
   * @return - participationStartDate
   */
  public String getParticipationStartDate() {
    return participationStartDate;
  }

  /**
   * 
   * @return - safelySurrenderedBabiesIndicator
   */
  public Boolean getSafelySurrenderedBabiesIndicator() {
    return safelySurrenderedBabiesIndicator;
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
