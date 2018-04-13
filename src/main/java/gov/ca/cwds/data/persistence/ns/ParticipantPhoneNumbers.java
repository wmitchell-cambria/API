package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Intake Team 4
 */
@Entity
@Table(name = "participant_phone_numbers")
public class ParticipantPhoneNumbers implements PersistentObject, Serializable {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GenericGenerator(
      name = "participant_phone_numbers_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name", value = "participant_phone_numbers_id_seq")
      }
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_phone_numbers_id")
  private String id;

  @EmbeddedId
  private ParticipantPhoneNumberId participantPhoneNumberId;


  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("participantId")
  @JoinColumn(name = "participant_id", updatable = false, insertable = false)
  private ParticipantEntity participant;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("phNumberId")
  @JoinColumn(name = "phone_number_id", updatable = false, insertable = false)
  private PhoneNumbers phoneNumber;

  @Column(name = "created_at")
  private Date createdAt = new Date();

  @Column(name = "updated_at")
  private Date updatedAt = new Date();

  /**
   * Default constructor
   */
  public ParticipantPhoneNumbers() {
  }

  public ParticipantPhoneNumbers(ParticipantEntity participant,
      PhoneNumbers phoneNumber) {
    this.participant = participant;
    this.phoneNumber = phoneNumber;
    this.participantPhoneNumberId = new ParticipantPhoneNumberId(participant.getId(),
        phoneNumber.getId());
  }

  public String getId() {
    return id;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
  }

  public ParticipantPhoneNumberId getParticipantPhoneNumberId() {
    return participantPhoneNumberId;
  }

  public ParticipantEntity getParticipant() {
    return participant;
  }

  public void setParticipant(ParticipantEntity participant) {
    this.participant = participant;
  }

  public PhoneNumbers getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(PhoneNumbers phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Date getCreatedAt() {
    return (Date) createdAt.clone();
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = (Date) createdAt.clone();
  }

  public Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = (Date) updatedAt.clone();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParticipantPhoneNumbers that = (ParticipantPhoneNumbers) o;
    return Objects.equals(participant, that.participant) &&
        Objects.equals(phoneNumber, that.phoneNumber);
  }

  @Override
  public int hashCode() {

    return Objects.hash(participant, phoneNumber);
  }

  @Embeddable
  public static class ParticipantPhoneNumberId implements Serializable {

    /**
     * Base serialization value. Increment by version
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "participant_id")
    private String participantId;

    @Column(name = "phone_number_id")
    private String phNumberId;

    /**
     * Default constructor
     */
    public ParticipantPhoneNumberId() {
    }

    ParticipantPhoneNumberId(String participantId, String phoneNumberId) {
      this.participantId = participantId;
      this.phNumberId = phoneNumberId;
    }

    public String getParticipantId() {
      return participantId;
    }

    public void setParticipantId(String person) {
      this.participantId = person;
    }

    public String getPhNumberId() {
      return phNumberId;
    }

    public void setPhNumberId(String address) {
      this.phNumberId = address;
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
          Objects.equals(phNumberId, that.phNumberId);
    }

    @Override
    public int hashCode() {

      return Objects.hash(participantId, phNumberId);
    }
  }
}
