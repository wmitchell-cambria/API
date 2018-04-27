package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

/**
 * Logical representation of a CmsNSReferral.
 * 
 * @author CWDS API Team
 */
public class CmsNSReferral extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Referral referral;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Person person;

  /**
   * Construct from JSON.
   * 
   * @param referral - Referral object
   * @param person - Person object
   */
  @JsonCreator
  public CmsNSReferral(@JsonProperty("referral") Referral referral,
      @JsonProperty("person") Person person) {
    super();
    this.referral = referral;
    this.person = person;
  }

  /**
   * @return the referral
   */
  public Referral getReferral() {
    return referral;
  }

  /**
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
