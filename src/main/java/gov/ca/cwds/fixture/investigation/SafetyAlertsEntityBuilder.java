package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;

@SuppressWarnings("javadoc")
public class SafetyAlertsEntityBuilder {

  private Set<String> alerts = new HashSet<>();
  private String alertInformation = "information about the safety alert on this referral";

  public SafetyAlerts build() {
    alerts.add("6401");
    alerts.add("6402");

    return new SafetyAlerts(alerts, alertInformation);
  }

  public SafetyAlertsEntityBuilder setAlerts(Set<String> alerts) {
    this.alerts = alerts;
    return this;
  }

  public Set<String> getAlerts() {
    return alerts;
  }

  public SafetyAlertsEntityBuilder setAlertInformation(String alertInformation) {
    this.alertInformation = alertInformation;
    return this;
  }

  public String getAlertInformation() {
    return alertInformation;
  }
}
