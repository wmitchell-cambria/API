package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Client Uppercase
 * 
 * @author CWDS API Team
 */
@ApiModel
public class SafelySurrenderedBabies extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @NotEmpty
  @NotBlank
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "identifies a child client",
      example = "ABC1234567")
  private String childClientId;

  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "surrendered date",
      example = "2000-01-01")
  private String surrenderedDate;

  @NotEmpty
  @ApiModelProperty(required = false, readOnly = false, value = "surrendered time",
      example = "16:41:49")
  private String surrenderedTime;

  @NotEmpty
  @Size(min = 1, max = 40)
  @ApiModelProperty(required = true, readOnly = false, value = "surrendered by", example = "Thomas")
  private String surrenderedByName;

  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "MACKAY")
  private String relationToClient;

  @NotNull
  @Size(max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "HOWARD")
  private String commonMiddleName;


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
