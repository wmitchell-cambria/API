package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

/**
 * CWDS API Team
 */
@JsonSnakeCase
@ApiModel("nsAllegationIntake")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AllegationIntake extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(example = "1")
  private String id;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(value = "Legacy Source Table", example = "ALLGTN_T")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, value = "Legacy Id", example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("victim_person_id")
  @ApiModelProperty(value = "id of victim", example = "12345")
  private long victimPersonId;

  @JsonProperty("perpetrator_person_id")
  @ApiModelProperty(value = "id of perpatrator", example = "12345")
  private long perpetratorPersonId;

  @JsonProperty("non_protecting_parent")
  @ApiModelProperty(value = "Non protecting parent Code", example = "U",
      allowableValues = "U, P, Y, N")
  @OneOf(value = {"U", "P", "Y", "N"})
  @Size(max = 1)
  private String nonProtectingParent;

  @JsonProperty("types")
  @ApiModelProperty(required = true, value = "types of allegation description",
      dataType = "java.util.List", example = "['General neglect','Physical Abuse']")
  @NotNull
  private Set<String> types = new HashSet<>();

  @JsonProperty("county")
  @ApiModelProperty(example = "34")
  private String county;

  /**
   * default constructor
   */
  public AllegationIntake() {
    // default
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setVictimPersonId(long victimPersonId) {
    this.victimPersonId = victimPersonId;
  }

  public void setPerpetratorPersonId(long perpetratorPersonId) {
    this.perpetratorPersonId = perpetratorPersonId;
  }

  public String getNonProtectingParent() {
    return nonProtectingParent;
  }


  public void setCounty(String county) {
    this.county = county;
  }

  public void setNonProtectingParent(String nonProtectingParent) {
    this.nonProtectingParent = nonProtectingParent;
  }

  /**
   * @return legacy source table
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
   * @return legacy id - the CWS/CMS database table name
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param legacyId - the identifier from CWS/CMS database
   */
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  /**
   * @return victimPersonId
   */
  public long getVictimPersonId() {
    return victimPersonId;
  }

  /**
   * @return perpetratorPersonId
   */
  public long getPerpetratorPersonId() {
    return perpetratorPersonId;
  }

  /**
   * @return types
   */
  public Set<String> getTypes() {
    return types;
  }

  public void setTypes(Set<String> types) {
    this.types = types;
  }

  /**
   * @return county
   */
  public String getCounty() {
    return county;
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
