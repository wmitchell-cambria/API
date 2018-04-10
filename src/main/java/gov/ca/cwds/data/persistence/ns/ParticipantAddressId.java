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
public class ParticipantAddressId implements Serializable {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @Column(name = "participant_id")
  private String participantId;

  @JsonIgnore
  @Column(name = "address_id")
  private String addressId;

  /**
   * Default constructor
   */
  public ParticipantAddressId() {
  }

  public ParticipantAddressId(String participantId, String addressId) {
    this.participantId = participantId;
    this.addressId = addressId;
  }

  @JsonIgnore
  public String getParticipantId() {
    return participantId;
  }

  public void setParticipantId(String person) {
    this.participantId = person;
  }

  @JsonIgnore
  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String address) {
    this.addressId = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParticipantAddressId that = (ParticipantAddressId) o;
    return Objects.equals(participantId, that.participantId) &&
        Objects.equals(addressId, that.addressId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(participantId, addressId);
  }
}
