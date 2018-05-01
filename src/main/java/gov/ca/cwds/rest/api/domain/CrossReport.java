package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;


import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an cross_report
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("NsCrossReport")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CrossReport extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

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
  @ApiModelProperty(required = true,
      value = "Communication method system code ID e.g) 2097 -> Telephone Report", example = "2097")
  @NotNull
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.CROSS_REPORT_METHOD)
  private Integer method;

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
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.COUNTY_CODE)
  private String countyId;

  @JsonProperty("agencies")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<GovernmentAgency> agencies = new HashSet<>();

  /**
   * default constructor
   */
  public CrossReport() {
    // default
  }

  /**
   * Construct from all fields.
   * 
   * @param id primary key identifier
   * @param legacySourceTable - legacy source table name
   * @param legacyId - legacy Id
   * @param filedOutOfState - filedOutOfState
   * @param method - reporting method
   * @param informDate - reported date
   * @param countyId - countyId
   * @param agencies - agencies
   */
  @SuppressWarnings("squid:S00107")
  public CrossReport(String id, String legacySourceTable, String legacyId, boolean filedOutOfState,
      Integer method, String informDate, String countyId, Set<GovernmentAgency> agencies) {
    super();
    this.id = id;
    this.legacySourceTable = legacySourceTable;
    this.legacyId = legacyId;
    this.method = method;
    this.filedOutOfState = filedOutOfState;
    this.informDate = informDate;
    this.countyId = countyId;
    if (agencies == null) {
      this.agencies = Sets.newHashSet();
    } else {
      this.agencies = agencies;
    }
  }

  public void setMethod(Integer method) {
    this.method = method;
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
  public Integer getMethod() {
    return method;
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
