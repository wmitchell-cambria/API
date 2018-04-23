package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.ParticipantAddresses.PARTICIPANT_ADDRESSES_BY_PARTICIPANT_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantAddresses.PARTICIPANT_ADDRESSES_BY_PARTICIPANT_ID_QUERY;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Intake Team 4
 */

@NamedQuery(
    name = PARTICIPANT_ADDRESSES_BY_PARTICIPANT_ID,
    query = PARTICIPANT_ADDRESSES_BY_PARTICIPANT_ID_QUERY
)

@Entity
@Table(name = "participant_addresses")
public class ParticipantAddresses implements PersistentObject, HasPaperTrail, Serializable {

  public static final String PARAM_PARTICIPANT_ID = "participantId";
  public static final String PARTICIPANT_ADDRESSES_BY_PARTICIPANT_ID =
      "gov.ca.cwds.data.persistence.ns.ParticipantAddresses.findByParticipantId";
  static final String PARTICIPANT_ADDRESSES_BY_PARTICIPANT_ID_QUERY =
      " FROM ParticipantAddresses pa"
          + " WHERE pa.participant.id = :"
          + PARAM_PARTICIPANT_ID;

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
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

  @Column(name = "participant_id")
  private String participantId;

  @Column(name = "address_id")
  private String addressId;


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
    this.participantId = participant.getId();
    this.addressId = address.getId();
    this.participant = participant;
    this.address = address;
  }

  public String getId() {
    return id;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
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
