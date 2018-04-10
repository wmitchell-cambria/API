package gov.ca.cwds.data.persistence.ns;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Intake Team 4
 * 
 */
@Embeddable
public class ParticipantPhoneNumberId implements Serializable {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @Column(name = "participant_id")
  private String participantId;

  @JsonIgnore
  @Column(name = "phone_number_id")
  private String phoneNumberId;

  /**
   * Default constructor
   */
  public ParticipantPhoneNumberId() {
  }

  public ParticipantPhoneNumberId(String participantId, String phoneNumberId) {
    this.participantId = participantId;
    this.phoneNumberId = phoneNumberId;
  }

  @JsonIgnore
  public String getParticipantId() {
    return participantId;
  }

  public void setParticipantId(String person) {
    this.participantId = person;
  }

  @JsonIgnore
  public String getPhoneNumberId() {
    return phoneNumberId;
  }

  public void setPhoneNumberId(String address) {
    this.phoneNumberId = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParticipantPhoneNumberId that = (ParticipantPhoneNumberId) o;
    return Objects.equals(participantId, that.participantId) &&
        Objects.equals(phoneNumberId, that.phoneNumberId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(participantId, phoneNumberId);
  }
}
