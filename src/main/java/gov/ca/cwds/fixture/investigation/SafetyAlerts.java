package gov.ca.cwds.fixture.investigation;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author CWDS API Team
 */
@JsonSnakeCase
public class SafetyAlerts extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("alerts")
  @ApiModelProperty(required = false, readOnly = false, value = "Safety Alert Type")
  private Set<String> safetyAlerts;

  @JsonProperty("alert_information")
  @ApiModelProperty(required = false, readOnly = false, value = "Alert Information")
  private String alertInformation;

  /**
   * 
   */
  public SafetyAlerts() {
    // default
  }

  /**
   * @param safetyAlerts - set of safety alert types
   * @param alertInformation - alert information
   */
  public SafetyAlerts(Set<String> safetyAlerts, String alertInformation) {
    this.safetyAlerts = safetyAlerts;
    this.alertInformation = alertInformation;
  }

  /**
   * @return - set of safety alert types
   */
  public Set<String> getSafetyAlerts() {
    return safetyAlerts;
  }

  /**
   * @param safetyAlerts - set of safety alert types
   */
  public void setSafetyAlerts(Set<String> safetyAlerts) {
    this.safetyAlerts = safetyAlerts;
  }

  /**
   * @return - alert information
   */
  public String getAlertInformation() {
    return alertInformation;
  }

  /**
   * @param alertInformation - alert information
   */
  public void setAlertInformation(String alertInformation) {
    this.alertInformation = alertInformation;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((alertInformation == null) ? 0 : alertInformation.hashCode());
    result = prime * result + ((safetyAlerts == null) ? 0 : safetyAlerts.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SafetyAlerts other = (SafetyAlerts) obj;
    if (alertInformation == null) {
      if (other.alertInformation != null)
        return false;
    } else if (!alertInformation.equals(other.alertInformation))
      return false;
    if (safetyAlerts == null) {
      if (other.safetyAlerts != null)
        return false;
    } else if (!safetyAlerts.equals(other.safetyAlerts))
      return false;
    return true;
  }


}

