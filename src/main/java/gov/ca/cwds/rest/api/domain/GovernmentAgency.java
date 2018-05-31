package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a GovernmentAgency
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "type"})
public class GovernmentAgency implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, value = "", example = "ABC1234567")
  @NotEmpty
  @Size(max = CMS_ID_LEN)
  private String id;

  @JsonProperty("type")
  @ApiModelProperty(required = true, value = "", example = "COMMUNITY_CARE_LICENSING")
  @OneOf(value = {"COMMUNITY_CARE_LICENSING", "COUNTY_LICENSING", "DISTRICT_ATTORNEY",
      "DEPARTMENT_OF_JUSTICE", "LAW_ENFORCEMENT"})
  private String type;

  /**
   * default constructor
   */
  public GovernmentAgency() {
    // default
  }

  /**
   * @param id - id
   * @param type - type
   */
  public GovernmentAgency(String id, String type) {
    this.id = id;
    this.type = type;
  }

  /**
   * Get agency id
   * 
   * @return Agency id
   */
  public String getId() {
    return id;
  }

  /**
   * Get agency type
   * 
   * @return Agency type
   */
  public String getType() {
    return type;
  }

  /**
   * 
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
