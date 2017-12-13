package gov.ca.cwds.fixture;

import java.util.Date;
import java.util.Set;

import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.data.persistence.ns.Participant;
import gov.ca.cwds.data.persistence.ns.Screening;

@SuppressWarnings("javadoc")
public class ScreeningEntityBuilder {

  private String reference = "screening reference";
  private Date endedAt;
  private String incidentCounty = "020";
  private Date incidentDate;
  private String locationType = "1111";
  private String communicationMethod = "2222";
  private String name = "screening name";
  private String responseTime = "2 day";
  private String screeningDecision = "screening decision";
  private Date startedAt;
  private String narrative = "screening narrative";
  private Address contactAddress;
  private Set<Participant> participants;

  public Screening build() {
    return new Screening(reference, endedAt, incidentCounty, incidentDate, locationType,
        communicationMethod, name, responseTime, screeningDecision, startedAt, narrative,
        contactAddress, participants);
  }

  public ScreeningEntityBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public ScreeningEntityBuilder setEndedAt(Date endedAt) {
    this.endedAt = endedAt;
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

  public ScreeningEntityBuilder setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public ScreeningEntityBuilder setNarrative(String narrative) {
    this.narrative = narrative;
    return this;
  }

}
