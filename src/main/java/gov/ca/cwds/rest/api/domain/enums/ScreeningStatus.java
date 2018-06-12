package gov.ca.cwds.rest.api.domain.enums;

public enum ScreeningStatus {

  OPEN("Open"),
  SUBMITTED("Submitted");

  private String status;

  ScreeningStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

}
