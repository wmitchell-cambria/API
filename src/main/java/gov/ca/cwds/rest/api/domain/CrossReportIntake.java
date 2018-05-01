package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

/**
 * CWDS API Team
 */
@SuppressWarnings("serial")
@JsonSnakeCase
@ApiModel("NsCrossReportIntake")
public class CrossReportIntake extends ReportingDomain implements Request, Response {

  @JsonProperty("id")
  @ApiModelProperty(required = false, value = "Id", example = "1234")
  private String id;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = false, value = "Legacy Source Table", example = "CRSS_RPT")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = false, value = "legacy Id", example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("method")
  @NotNull
  @ApiModelProperty(required = true, value = "Communication method", example = "Child Abuse Form")
  @OneOf(value = {"Child Abuse Form", "Electronic Report", "Suspected Child Abuse Report",
      "Telephone Report"})
  private String method;

  @JsonProperty("filed_out_of_state")
  @ApiModelProperty(required = false, value = "Cross Report was filed out of state", example = "N")
  private boolean filedOutOfState;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @Date
  @JsonProperty("inform_date")
  @ApiModelProperty(required = true, value = "yyyy-MM-dd", example = "2001-09-13")
  private String informDate;

  @JsonProperty("county_id")
  @ApiModelProperty(required = true, readOnly = false, value = "County of the crossReport agency",
      example = "34")
  private String countyId;

  @JsonProperty("agencies")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<GovernmentAgency> agencies = new HashSet<>();

  /**
   * default constructor
   */
  public CrossReportIntake() {
    // default
  }


  public void setFiledOutOfState(boolean filedOutOfState) {
    this.filedOutOfState = filedOutOfState;
  }

  public void setInformDate(String informDate) {
    this.informDate = informDate;
  }

  public void setCountyId(String countyId) {
    this.countyId = countyId;
  }

  public void setAgencies(Set<GovernmentAgency> agencies) {
    this.agencies = agencies;
  }

  /**
   * @return legacy source table name
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return legacy Id
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param legacyId - the legacy Id
   */
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id - the id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return method
   */
  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  /**
   * @return filedOutOfState
   */
  public boolean isFiledOutOfState() {
    return filedOutOfState;
  }

  /**
   * @return the countyId
   */
  public String getCountyId() {
    return countyId;
  }

  /**
   * Get agencies
   *
   * @return Agencies
   */
  public Set<GovernmentAgency> getAgencies() {
    return agencies;
  }

  /**
   * @return informDate
   */
  public String getInformDate() {
    return informDate;
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
