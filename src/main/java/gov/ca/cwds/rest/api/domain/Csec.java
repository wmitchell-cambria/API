package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * CWDS API Team
 */
@SuppressWarnings({"squid:S3437"})
public class Csec extends ReportingDomain {
  @JsonProperty("id")
  private String id;

  @JsonProperty("participant_id")
  private String participantId;

  @JsonProperty("csec_code_id")
  private String csecCodeId;

  @JsonProperty("start_date")
  private LocalDate startDate;

  @JsonProperty("end_date")
  private LocalDate endDate;

  public Csec() {
    // required by third party library
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParticipantId() {
    return participantId;
  }

  public void setParticipantId(String participantId) {
    this.participantId = participantId;
  }

  public String getCsecCodeId() {
    return csecCodeId;
  }

  public void setCsecCodeId(String csecCodeId) {
    this.csecCodeId = csecCodeId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
