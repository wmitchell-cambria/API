package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidLogicalId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Cross Report Agency
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"legacy_descriptor", "read_only", "reported_on", "communication_method",
    "county"})
public class CrossReport extends ReportingDomain implements Response {
  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_descriptor")
  @ApiModelProperty(required = true, readOnly = false, value = "CMS record descriptor")
  private CmsRecordDescriptor legacyDescriptor;

  @JsonProperty("read_only")
  @ApiModelProperty(required = true, readOnly = false, value = "Cross Report is read only")
  private Boolean readOnly;

  @JsonProperty("reported_on")
  @gov.ca.cwds.rest.validation.Date
  @JsonFormat(shape = JsonFormat.Shape.STRING,
      pattern = gov.ca.cwds.rest.api.domain.DomainChef.DATE_FORMAT)
  @ApiModelProperty(required = true, readOnly = false, value = "Reported on date",
      example = "2017-10-31")
  private Date reportedOn;

  @JsonProperty("communication_method")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.COMMUNICATION_METHOD)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Cross Report communication method type system code ID e.g) 408 -> In-Person",
      example = "408")
  private String communicationMethod;

  @JsonProperty("county")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false,
      value = "County where Cross Report were sent", example = "34")
  @ValidLogicalId(required = true, category = SystemCodeCategoryId.COUNTY_CODE)
  private String county;

  @JsonProperty("agencies")
  @ApiModelProperty(required = false, readOnly = false)
  private Set<CrossReportAgency> crossReportAgencies;

  /**
   * empty constructor
   */
  public CrossReport() {
    super();
  }

  /**
   * @param legacyDescriptor - CMS record descriptor
   * @param readOnly - Cross Report is read only
   * @param reportedOn - Date of cross report
   * @param communicationMethod - cross report communication method
   * @param county - county code of cross report agencies
   * @param crossReportAgencies - array of cross report agencies
   */
  public CrossReport(CmsRecordDescriptor legacyDescriptor, Boolean readOnly,
      @gov.ca.cwds.rest.validation.Date Date reportedOn,
      @ValidSystemCodeId(required = false, category = "CMM_MTHC") String communicationMethod,
      @ValidLogicalId(required = true, category = "GVR_ENTC") String county,
      Set<CrossReportAgency> crossReportAgencies) {
    super();
    this.legacyDescriptor = legacyDescriptor;
    this.readOnly = readOnly;
    this.reportedOn = freshDate(reportedOn);
    this.communicationMethod = communicationMethod;
    this.county = county;
    this.crossReportAgencies = crossReportAgencies;
  }

  /**
   * @return - CMS record descriptor
   */
  public CmsRecordDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @return - read only
   */
  public Boolean getReadOnly() {
    return readOnly;
  }

  /**
   * @return - cross report date
   */
  public Date getReportedOn() {
    return freshDate(reportedOn);
  }

  /**
   * @return - communication method
   */
  public String getCommunicationMethod() {
    return communicationMethod;
  }

  /**
   * @return - county code from which agencies were selected
   */
  public String getCounty() {
    return county;
  }

  /**
   * @return - array of cross report agencies
   */
  public Set<CrossReportAgency> getCrossReportAgencies() {
    return crossReportAgencies;
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
