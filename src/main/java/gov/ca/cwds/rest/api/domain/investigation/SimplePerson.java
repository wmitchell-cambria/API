package gov.ca.cwds.rest.api.domain.investigation;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Simple Person.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"last_name", "first_name", "sealed", "sensitive"})
public class SimplePerson extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @NotEmpty
  @JsonProperty("last_name")
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Smith")
  private String lastName;

  @JsonProperty("first_name")
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "John")
  private String firstName;

  @JsonIgnore
  @Size(max = 1)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "R")
  private String sensitivityIndicator;

  /**
   * @param lastName last name
   * @param firstName first name
   * @param sensitivityIndicator The Sensitivity Indicator
   */
  public SimplePerson(@JsonProperty("last_name") String lastName,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("sensitivity_indicator") String sensitivityIndicator) {
    super();
    this.lastName = lastName;
    this.firstName = firstName;
    this.sensitivityIndicator = sensitivityIndicator;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the sensitivityIndicator
   */
  @JsonIgnore
  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  @SuppressWarnings("javadoc")
  @JsonProperty("sealed")
  public Boolean getSealed() {
    return StringUtils.isNotBlank(sensitivityIndicator) && "R".equals(sensitivityIndicator);
  }

  @SuppressWarnings("javadoc")
  @JsonProperty("sensitive")
  public Boolean getSensitive() {
    return StringUtils.isNotBlank(sensitivityIndicator) && "S".equals(sensitivityIndicator);
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
