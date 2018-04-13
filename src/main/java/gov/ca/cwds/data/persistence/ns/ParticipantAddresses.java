package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Intake Team 4
 */
@Entity
@Table(name = "participant_addresses")
public class ParticipantAddresses implements PersistentObject, Serializable {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
//  @Column(name = "id", insertable = false, updatable = false)
  @GenericGenerator(
      name = "participant_addresses_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name", value = "participant_addresses_id_seq")
      }
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_addresses_id")
  private String id;

  @EmbeddedId
  private ParticipantAddressId participantAddressId;


  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("participantId")
  private ParticipantEntity participant;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("addressId")
  private Addresses address;

  /**
   * Default constructor
   */
  public ParticipantAddresses() {

  }

  public ParticipantAddresses(ParticipantEntity participant, Addresses address) {
    this.participant = participant;
    this.address = address;
    this.participantAddressId = new ParticipantAddressId(participant.getId(), address.getId());
  }

  public String getId() {
    return id;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
  }

  public ParticipantAddressId getParticipantAddressId() {
    return participantAddressId;
  }

  public ParticipantEntity getParticipant() {
    return participant;
  }

  public void setParticipant(ParticipantEntity participant) {
    this.participant = participant;
  }

  public Addresses getAddress() {
    return address;
  }

  public void setAddress(Addresses address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParticipantAddresses that = (ParticipantAddresses) o;
    return Objects.equals(participant, that.participant) &&
        Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {

    return Objects.hash(participant, address);
  }

  @Embeddable
  public static class ParticipantAddressId implements Serializable {

    /**
     * Base serialization value. Increment by version
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "participant_id")
    private String participantId;

    @Column(name = "address_id")
    private String addressId;

    /**
     * Default constructor
     */
    public ParticipantAddressId() {
    }

    ParticipantAddressId(String participantId, String addressId) {
      this.participantId = participantId;
      this.addressId = addressId;
    }

    public String getParticipantId() {
      return participantId;
    }

    public void setParticipantId(String person) {
      this.participantId = person;
    }

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
}
