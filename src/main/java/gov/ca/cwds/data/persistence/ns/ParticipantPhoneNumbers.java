package gov.ca.cwds.data.persistence.ns;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "participant_phone_numbers")
public class ParticipantPhoneNumbers implements HasPaperTrail {

  /**
   * Base serialization value. Increment by version
   */
//  private static final long serialVersionUID = 1L;
//
//  @Id
//  @Column(name = "id")
//  @GenericGenerator(
//      name = "participant_phone_numbers_id",
//      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
//      parameters = {
//          @org.hibernate.annotations.Parameter(
//              name = "sequence_name", value = "participant_phone_numbers_id_seq")
//      }
//  )
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_phone_numbers_id")
////  @SequenceGenerator(name = "participant_phone_numbers_id", sequenceName = "participant_phone_numbers_id_seq")
//  private String id;

  @EmbeddedId
  private ParticipantPhoneNumberId participantPhoneNumberId;


  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("participantId")
  private ParticipantEntity  participant;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("phoneNumberId")
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
      PhoneNumbers  phoneNumber) {
    this.participant = participant;
    this.phoneNumber = phoneNumber;
    this.participantPhoneNumberId = new ParticipantPhoneNumberId(participant.getId(), phoneNumber.getId());
  }

//  public String getId() {
//    return id;
//  }

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
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
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
}
