package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers.PARTICIPANT_PHONE_NUMBERS_BY_PARTICIPANT_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers.PARTICIPANT_PHONE_NUMBERS_BY_PARTICIPANT_ID_QUERY;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
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
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;

/**
 * @author Intake Team 4
 */
@NamedQuery(name = PARTICIPANT_PHONE_NUMBERS_BY_PARTICIPANT_ID,
    query = PARTICIPANT_PHONE_NUMBERS_BY_PARTICIPANT_ID_QUERY)
@Entity
@Table(name = "participant_phone_numbers")
public class ParticipantPhoneNumbers implements PersistentObject, HasPaperTrail, Serializable {

  public static final String PARAM_PARTICIPANT_ID = "participantId";
  public static final String PARTICIPANT_PHONE_NUMBERS_BY_PARTICIPANT_ID =
      "gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers.findByParticipantId";
  static final String PARTICIPANT_PHONE_NUMBERS_BY_PARTICIPANT_ID_QUERY =
      " FROM ParticipantPhoneNumbers pa" + " WHERE pa.participant.id = :" + PARAM_PARTICIPANT_ID;

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GenericGenerator(name = "participant_phone_numbers_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "participant_phone_numbers_id_seq")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_phone_numbers_id")
  private String id;

  @Column(name = "participant_id")
  private String participantId;

  @Column(name = "phone_number_id")
  private String phNumberId;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("participantId")
  @JoinColumn(name = "participant_id", updatable = false, insertable = false)
  private ParticipantEntity participant;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("phNumberId")
  @JoinColumn(name = "phone_number_id", updatable = false, insertable = false)
  private PhoneNumbers phoneNumber;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  /**
   * Default constructor
   */
  public ParticipantPhoneNumbers() {}

  public ParticipantPhoneNumbers(ParticipantPhoneNumbers src) {
    super();
    this.id = src.id;
    this.participantId = src.participantId;
    this.phNumberId = src.phNumberId;
    this.participant = src.participant;
    this.phoneNumber = src.phoneNumber;
    this.createdAt = src.createdAt;
    this.updatedAt = src.updatedAt;
  }

  public ParticipantPhoneNumbers(ParticipantEntity participant, PhoneNumbers phoneNumber) {
    this.participantId = participant.getId();
    this.phNumberId = phoneNumber.getId();
    this.participant = participant;
    this.phoneNumber = phoneNumber;
  }

  @Override
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

  public void setId(String id) {
    this.id = id;
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
    return Objects.equals(participant, that.participant)
        && Objects.equals(phoneNumber, that.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(participant, phoneNumber);
  }

}
