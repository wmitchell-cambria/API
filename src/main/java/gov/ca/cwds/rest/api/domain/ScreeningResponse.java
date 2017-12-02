package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link DomainObject} representing a screening response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class ScreeningResponse implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("address")
  private Address address;

  @JsonProperty("participants")
  private Set<Participant> participants;

  /**
   * Default ctor.
   */
  public ScreeningResponse() {
    super();
  }

  // /**
  // * Constructor
  // *
  // * @param reference The reference
  // * @param endedAt The ended at
  // * @param incidentCounty The incident county
  // * @param incidentDate The incident date
  // * @param locationType The location type
  // * @param communicationMethod The communication method
  // * @param name The name
  // * @param responseTime The response time
  // * @param screeningDecision The screening decision
  // * @param startedAt The started at
  // * @param narrative The narrative
  // * @param address The {@link Address}
  // * @param participants The {@link Set}
  // */
  // public ScreeningResponse(String reference, String endedAt, String incidentCounty,
  // String incidentDate, String locationType, String communicationMethod, String name,
  // String responseTime, String screeningDecision, String startedAt, String narrative,
  // Address address, Set<Participant> participants) {
  // super(reference, endedAt, incidentCounty, incidentDate, locationType, communicationMethod,
  // name, responseTime, screeningDecision, startedAt, narrative);
  // this.address = address;
  // this.participants = participants;
  // }

  // /**
  // * Constructor
  // *
  // * @param screening The persistent version of a screening
  // * @param participants The list of participants for this screening
  // */
  // public ScreeningResponse(gov.ca.cwds.data.persistence.ns.Screening screening,
  // Set<gov.ca.cwds.data.persistence.ns.Participant> participants) {
  // super(screening);
  // if (screening.getContactAddress() != null) {
  // this.address = new Address(screening.getContactAddress());
  // }
  // ImmutableSet.Builder<Participant> participantSetBuilder = ImmutableSet.builder();
  // if (participants != null) {
  // for (gov.ca.cwds.data.persistence.ns.Participant participant : participants) {
  // participantSetBuilder.add(new Participant(participant));
  // }
  // }
  // this.participants = participantSetBuilder.build();
  // }


  public void addParticipant(Participant participant) {
    participants.add(participant);
  }

}
