package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.util.FerbDateUtils;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * Referral for HOI.
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"id", "startDate", "endDate", "county", "responseTime", "reporter",
    "assignedSocialWorker", "accessLimitation", "allegations", "legacyDescriptor"})
public class HOIReferral extends ApiObjectIdentity implements ApiTypedIdentifier<String>, Request {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("start_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-22")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("end_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-23")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("response_time")
  private SystemCodeDescriptor responseTime;

  @JsonProperty("reporter")
  private HOIReporter reporter;

  @JsonProperty("assigned_social_worker")
  private HOISocialWorker assignedSocialWorker;

  @JsonProperty("access_limitation")
  private AccessLimitation accessLimitation;

  @JsonProperty("allegations")
  private List<HOIAllegation> allegations = new ArrayList<>();

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public HOIReferral() {
    // No-argument constructor
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return FerbDateUtils.freshDate(startDate);
  }

  /**
   * @param startDate - startDate
   */
  public void setStartDate(Date startDate) {
    this.startDate = FerbDateUtils.freshDate(startDate);
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return FerbDateUtils.freshDate(endDate);
  }

  /**
   * @param endDate - endDate
   */
  public void setEndDate(Date endDate) {
    this.endDate = FerbDateUtils.freshDate(endDate);
  }

  /**
   * @return the county
   */
  public SystemCodeDescriptor getCounty() {
    return county;
  }

  /**
   * @param county - county
   */
  public void setCounty(SystemCodeDescriptor county) {
    this.county = county;
  }

  /**
   * @return the responseTime
   */
  public SystemCodeDescriptor getResponseTime() {
    return responseTime;
  }

  /**
   * @param responseTime - responseTime
   */
  public void setResponseTime(SystemCodeDescriptor responseTime) {
    this.responseTime = responseTime;
  }

  /**
   * @return the reporter
   */
  public HOIReporter getReporter() {
    return reporter;
  }

  /**
   * @param reporter - reporter
   */
  public void setReporter(HOIReporter reporter) {
    this.reporter = reporter;
  }

  /**
   * @return the assignedSocialWorker
   */
  public HOISocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  /**
   * @param assignedSocialWorker - assignedSocialWorker
   */
  public void setAssignedSocialWorker(HOISocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  /**
   * @return the allegations
   */
  public List<HOIAllegation> getAllegations() {
    return allegations;
  }

  /**
   * @param allegations - allegations
   */
  public void setAllegations(List<HOIAllegation> allegations) {
    this.allegations = allegations;
  }

  /**
   * @return the accessLimitation
   */
  public AccessLimitation getAccessLimitation() {
    return accessLimitation;
  }

  /**
   * @param accessLimitation - accessLimitation
   */
  public void setAccessLimitation(AccessLimitation accessLimitation) {
    this.accessLimitation = accessLimitation;
  }

  /**
   * @return the legacyDescriptor
   */
  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @param legacyDescriptor - legacyDescriptor
   */
  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
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
