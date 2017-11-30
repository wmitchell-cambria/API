package gov.ca.cwds.rest.api.domain.hoi;

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
 * Case.
 * 
 * @author CWDS API Team
 */
public class Case extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  private Date startDate;

  @JsonProperty("end_date")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("service_component")
  private SystemCodeDescriptor serviceComponent;

  @JsonProperty("focus_child")
  private Person focusChild;

  @JsonProperty("assigned_social_worker")
  private Person assignedSocialWorker;

  @JsonProperty("parents")
  private List<Person> parents = new ArrayList<>();

  @JsonProperty("access_limitation")
  private AccessLimitation accessLimitation;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public Case() {
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
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public SystemCodeDescriptor getCounty() {
    return county;
  }

  public void setCounty(SystemCodeDescriptor county) {
    this.county = county;
  }

  public SystemCodeDescriptor getServiceComponent() {
    return serviceComponent;
  }

  public void setServiceComponent(SystemCodeDescriptor serviceComponent) {
    this.serviceComponent = serviceComponent;
  }

  public Person getFocusChild() {
    return focusChild;
  }

  public void setFocusChild(Person focusChild) {
    this.focusChild = focusChild;
  }

  public Person getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(Person assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  public List<Person> getParents() {
    return parents;
  }

  public void setParents(List<Person> parents) {
    this.parents = parents;
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

