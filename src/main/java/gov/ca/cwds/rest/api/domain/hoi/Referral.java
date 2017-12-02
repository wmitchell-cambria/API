package gov.ca.cwds.rest.api.domain.hoi;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * Referral.
 * 
 * @author CWDS API Team
 */
public class Referral extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  private Date startDate;

  @JsonProperty("end_date")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("response_time")
  private SystemCodeDescriptor responseTime;

  @JsonProperty("reporter")
  private Person reporter;

  @JsonProperty("assigned_social_worker")
  private Person assignedSocialWorker;

  @JsonProperty("allegations")
  private List<Allegation> allegations = new ArrayList<>();

  @JsonProperty("access_limitation")
  private AccessLimitation accessLimitation;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public Referral() {
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

  public Date getStartDate() {
    return freshDate(startDate);
  }

  public void setStartDate(Date startDate) {
    this.startDate = freshDate(startDate);
  }

  public Date getEndDate() {
    return freshDate(endDate);
  }

  public void setEndDate(Date endDate) {
    this.endDate = freshDate(endDate);
  }

  public SystemCodeDescriptor getCounty() {
    return county;
  }

  public void setCounty(SystemCodeDescriptor county) {
    this.county = county;
  }

  public SystemCodeDescriptor getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(SystemCodeDescriptor responseTime) {
    this.responseTime = responseTime;
  }

  public Person getReporter() {
    return reporter;
  }

  public void setReporter(Person reporter) {
    this.reporter = reporter;
  }

  public Person getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(Person assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  public List<Allegation> getAllegations() {
    return allegations;
  }

  public void setAllegations(List<Allegation> allegations) {
    this.allegations = allegations;
  }

  public AccessLimitation getAccessLimitation() {
    return accessLimitation;
  }

  public void setAccessLimitation(AccessLimitation accessLimitation) {
    this.accessLimitation = accessLimitation;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }
}
