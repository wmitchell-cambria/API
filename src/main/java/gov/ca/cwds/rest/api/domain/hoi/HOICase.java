package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.util.FerbDateUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * Case for HOI.
 * 
 * @author CWDS API Team
 */
public class HOICase extends ApiObjectIdentity implements ApiTypedIdentifier<String>, Request {


  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date startDate;

  @JsonProperty("end_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-09-30")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("service_component")
  private SystemCodeDescriptor serviceComponent;

  @JsonProperty("focus_child")
  private HOIVictim focusChild;

  @JsonProperty("assigned_social_worker")
  private HOISocialWorker assignedSocialWorker;

  @JsonProperty("access_limitation")
  private AccessLimitation accessLimitation;

  @JsonProperty("parents")
  private List<HOIRelatedPerson> parents = new ArrayList<>();

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public HOICase() {
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
   * @param startDate the startDate
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
   * @return the serviceComponent
   */
  public SystemCodeDescriptor getServiceComponent() {
    return serviceComponent;
  }

  /**
   * @param serviceComponent - serviceComponent
   */
  public void setServiceComponent(SystemCodeDescriptor serviceComponent) {
    this.serviceComponent = serviceComponent;
  }

  /**
   * @return the focusChild
   */
  public HOIVictim getFocusChild() {
    return focusChild;
  }

  /**
   * @param focusChild - focusChild
   */
  public void setFocusChild(HOIVictim focusChild) {
    this.focusChild = focusChild;
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
   * @return the parents
   */
  public List<HOIRelatedPerson> getParents() {
    return parents;
  }

  /**
   * @param parents - parents
   */
  public void setParents(List<HOIRelatedPerson> parents) {
    this.parents = parents;
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
