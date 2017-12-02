package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Access Limitation (sealed, sensitive, restricted).
 * 
 * @author CWDS API Team
 */
public class AccessLimitation extends ApiObjectIdentity {

  private static final long serialVersionUID = 1L;

  private static final LimitedAccessType DEFAULT_LIMITED_ACCESS_CODE = LimitedAccessType.NONE;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessCode = DEFAULT_LIMITED_ACCESS_CODE;

  @JsonProperty("limited_access_date")
  private String limitedAccessDate;

  @JsonProperty("limited_access_description")
  private String limitedAccessDescription;

  @JsonProperty("limited_access_government_entity_id")
  private String limitedAccessGovernmentEntityId;

  @JsonProperty("limited_access_government_entity_name")
  private String limitedAccessGovernmentEntityName;

  /**
   * No-argument constructor
   */
  public AccessLimitation() {
    // No-argument constructor
  }

  public LimitedAccessType getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public void setLimitedAccessCode(LimitedAccessType limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
  }

  public String getLimitedAccessDate() {
    return limitedAccessDate;
  }

  public void setLimitedAccessDate(String limitedAccessDate) {
    this.limitedAccessDate = limitedAccessDate;
  }

  public String getLimitedAccessDescription() {
    return limitedAccessDescription;
  }

  public void setLimitedAccessDescription(String limitedAccessDescription) {
    this.limitedAccessDescription = limitedAccessDescription;
  }

  public String getLimitedAccessGovernmentEntityId() {
    return limitedAccessGovernmentEntityId;
  }

  public void setLimitedAccessGovernmentEntityId(String limitedAccessGovernmentEntityId) {
    this.limitedAccessGovernmentEntityId = limitedAccessGovernmentEntityId;
  }

  public String getLimitedAccessGovernmentEntityName() {
    return limitedAccessGovernmentEntityName;
  }

  public void setLimitedAccessGovernmentEntityName(String limitedAccessGovernmentEntityName) {
    this.limitedAccessGovernmentEntityName = limitedAccessGovernmentEntityName;
  }

}

