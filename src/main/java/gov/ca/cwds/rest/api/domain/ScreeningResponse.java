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

  public void addParticipant(Participant participant) {
    participants.add(participant);
  }

}
