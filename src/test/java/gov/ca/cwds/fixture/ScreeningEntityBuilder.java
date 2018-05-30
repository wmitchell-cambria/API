package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.DomainChef;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.data.persistence.ns.Address;

@SuppressWarnings("javadoc")
public class ScreeningEntityBuilder {
  public static String DEFAULT_ASSIGNEE_STAFF_ID = "0X5";

  private String id = null;
  private String reference = "screening reference";
  private String incidentCounty = "020";
  private Date incidentDate;
  private String locationType = "1111";
  private String communicationMethod = "2222";
  private String name = "screening name";
  private String responseTime = "2 day";
  private String screeningDecision = "screening decision";
  private String screeningDecisionDetail = null;
  private Date startedAt;
  private Date endedAt;
  private String narrative = "screening narrative";
  private Address contactAddress;
  private String assigneeStaffId = DEFAULT_ASSIGNEE_STAFF_ID;
  private Set<ParticipantEntity> participants = new HashSet<>();
  private String reportType = "ssb";

  public ScreeningEntity build() {
    return new ScreeningEntity(id, reference, startedAt, endedAt, incidentCounty, incidentDate,
        locationType, communicationMethod, name, responseTime, screeningDecision, screeningDecisionDetail,
        narrative, contactAddress, assigneeStaffId, participants, reportType);
  }

  public ScreeningEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ScreeningEntityBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public ScreeningEntityBuilder setEndedAt(Date endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  public ScreeningEntityBuilder setEndedAt(String endedAt) {
    this.endedAt = DomainChef.uncookDateString(endedAt);
    return this;
  }

  public ScreeningEntityBuilder setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
    return this;
  }

  public ScreeningEntityBuilder setIncidentDate(Date incidentDate) {
    this.incidentDate = incidentDate;
    return this;
  }

  public ScreeningEntityBuilder setCommunicationMethod(String communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }

  public ScreeningEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }


  public ScreeningEntityBuilder setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
    return this;
  }

  public ScreeningEntityBuilder setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
    return this;
  }

  public ScreeningEntityBuilder setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public ScreeningEntityBuilder setStartedAt(String startedAt) {
    this.startedAt = DomainChef.uncookDateString(startedAt);
    return this;
  }

  public ScreeningEntityBuilder setNarrative(String narrative) {
    this.narrative = narrative;
    return this;
  }

  public ScreeningEntityBuilder addParticipant(ParticipantEntity participant) {
    this.participants.add(participant);
    return this;
  }
}
