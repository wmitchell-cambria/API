package gov.ca.cwds.data.persistence.ns;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Intake Team 4
 * 
 */
@Entity
@Table(name = "participant_addresses")
public class ParticipantAddresses implements HasPaperTrail {

  /**
   * Base serialization value. Increment by version
   */
//  private static final long serialVersionUID = 1L;

//  @Id
//  @Column(name = "id")
//  @GenericGenerator(
//      name = "participant_addresses_id",
//      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
//      parameters = {
//          @org.hibernate.annotations.Parameter(
//              name = "sequence_name", value = "participant_addresses_id_seq")
//      }
//  )
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_addresses_id")
////  @SequenceGenerator(name = "participant_addresses_id", sequenceName = "participant_addresses_id_seq")
//  private String id;

  @EmbeddedId
  private ParticipantAddressId participantAddressId;


  @ManyToOne(fetch = FetchType.LAZY)
//  @MapsId("participantId")
  @JoinColumn(name = "participant_id", insertable=false, updatable = false)
  private ParticipantEntity  participant;

  @ManyToOne(fetch = FetchType.LAZY)
//  @MapsId("addressId")
  @JoinColumn(name = "address_id", insertable=false, updatable = false)
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

//  public String getId() {
//    return id;
//  }

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
}
