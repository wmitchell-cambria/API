package gov.ca.cwds.rest.api.domain.investigation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Cross Report Agency
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class CrossReportAgency extends ReportingDomain implements Response {
  private static final long serialVersionUID = 1L;

  @JsonProperty("type")
  @ApiModelProperty(required = true, readOnly = false, value = "Agency Type",
      example = "DEPARTMENT_OF_JUSTICE")
  private String type;

  @JsonProperty("name")
  @ApiModelProperty(required = true, readOnly = false, value = "Agency Name",
      example = "County Sheriff Dept")
  private String name;

  /**
   * empty constructor
   */
  public CrossReportAgency() {
    super();
  }

  /**
   * @param type - cross report agency type
   * @param name - cross report agency name
   */
  public CrossReportAgency(String type, String name) {
    super();
    this.type = type;
    this.name = name;
  }

  /**
   * @return - cross report agency type
   */
  public String getType() {
    return type;
  }

  /**
   * @return - cross report agency name
   */
  public String getName() {
    return name;
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
