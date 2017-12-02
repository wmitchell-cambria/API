package gov.ca.cwds.rest.api.domain.investigation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
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
public class AllegationSubType extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("injury_harm_type")
  @ApiModelProperty(required = true, readOnly = false, value = "Injury/Harm Type", example = "1372")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.INJURY_HARM_TYPE)
  @JsonSerialize(using = SysIdShortToStringSerializer.class)
  private Short injuryHarmType;

  @JsonProperty("injury_harm_sub_type")
  @ApiModelProperty(required = false, readOnly = false, value = "Injury/Harm sub-type")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.INJURY_TO_BODY_PART_TYPE)
  @JsonSerialize(using = SysIdShortToStringSerializer.class)
  private Short injuryHarmSubType;

  /**
   * empty constructor
   */
  public AllegationSubType() {
    super();
  }

  /**
   * @param injuryHarmType - Lov value of injury harm type
   * @param injuryHarmSubType - Lov value of body part
   */
  public AllegationSubType(Short injuryHarmType, Short injuryHarmSubType) {
    super();
    this.injuryHarmType = injuryHarmType;
    this.injuryHarmSubType = injuryHarmSubType;
  }

  /**
   * @return - the injury harm type code
   */
  public Short getInjuryHarmType() {
    return injuryHarmType;
  }

  /**
   * @return - the injury harm sub type
   */
  public Short getInjuryHarmSubType() {
    return injuryHarmSubType;
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
