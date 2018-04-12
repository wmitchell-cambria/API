package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a phone number.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class PhoneNumber extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(example = "478")
  Long id;

  @JsonProperty("number")
  @ApiModelProperty(example = "4083455678")
  @Size(max = 50)
  String number;

  @JsonProperty("type")
  @ApiModelProperty(example = "Cell")
  @Size(max = 50)
  String type;

  /**
   * Construct from persistence class
   * 
   * @param phoneNumber persistence level address object
   */
  public PhoneNumber(gov.ca.cwds.data.persistence.ns.PhoneNumber phoneNumber) {
    this.id = phoneNumber.getId();
    this.number = phoneNumber.getNumber();
    this.type = phoneNumber.getType();
  }

  /**
   * Construct from persistence class
   *
   * @param phoneNumber persistence level address object
   */
  public PhoneNumber(gov.ca.cwds.data.persistence.ns.PhoneNumbers phoneNumber) {
    this.id = Long.valueOf(phoneNumber.getId());
    this.number = phoneNumber.getNumber();
    this.type = phoneNumber.getType();
  }

  /**
   * @param phoneNumber - phone number
   * @param phoneType - phone number type
   */
  public PhoneNumber(@JsonProperty("id") Long id, @JsonProperty("number") String phoneNumber,
      @JsonProperty("phone_number_type_id") String phoneType) {
    super();
    this.id = id;
    this.number = phoneNumber;
    this.type = phoneType;
  }

  public PhoneNumber(String number, String type) {
    this.number = number;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return - phone number
   */
  public String getPhoneNumber() {
    return number;
  }

  /**
   * @return - phone type
   */
  public String getPhoneType() {
    return type;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
