package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.Identifiable;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

/**
 * {@link PersistentObject} representing Participant.
 *
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId",
    query = "SELECT legacyId FROM ParticipantEntity WHERE screeningEntity.id = :screeningId)")
@Entity
@Table(name = "participants")
public class ParticipantEntity implements PersistentObject, HasPaperTrail, Identifiable<String> {

  @Id
  @Column(name = "id")
  @GenericGenerator(
      name = "participant_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name", value = "participants_id_seq")
      }
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_id")
//  @SequenceGenerator(name = "participant_id", sequenceName = "participants_id_seq")
  private String id;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "ssn")
  private String ssn;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "screening_id", nullable = false, insertable = false, updatable = false)
  private ScreeningEntity screeningEntity;

  @Column(name = "legacy_id")
  private String legacyId;

  @Column(name = "roles")
  private String roles;

  @Column(name = "languages")
  private String languages;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "name_suffix")
  private String nameSuffix;

  @Column(name = "races")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringJsonUserType")
  private String races;

  @Column(name = "ethnicity")
  @Type(type = "gov.ca.cwds.data.persistence.hibernate.StringJsonUserType")
  private String ethnicity;

  @Column(name = "legacy_source_table")
  private String legacySourceTable;

  @Column(name = "sensitive")
  private String sensitive;

  @Column(name = "sealed")
  private String sealed;

  @Column(name = "approximate_age")
  private String approximateAge;

  @Column(name = "approximate_age_units")
  private String approximateAgeUnits;

  @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ParticipantAddresses> participantAddresses = new ArrayList<>();

  @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ParticipantPhoneNumbers> participantPhoneNumbers = new ArrayList<>();


  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public ParticipantEntity() {
  }

  public ParticipantEntity(ParticipantIntakeApi participantIntakeApi, ScreeningEntity screeningEntity, Set<Addresses> addresses, Set<PhoneNumbers> phoneNumbers) {
    id = participantIntakeApi.getId();
    try {
      dateOfBirth = new SimpleDateFormat(DomainObject.DATE_FORMAT).parse(participantIntakeApi.getDateOfBirth());
    } catch (ParseException e){
      //swallow it
    }
    firstName = participantIntakeApi.getFirstName();
    gender = participantIntakeApi.getGender();
    lastName = participantIntakeApi.getLastName();
    ssn = participantIntakeApi.getSsn();
    this.screeningEntity = screeningEntity;
    legacyId = participantIntakeApi.getLegacyId();
    roles = String.join(",", participantIntakeApi.getRoles());
    languages = String.join(",", participantIntakeApi.getLanguages());
    middleName = participantIntakeApi.getMiddleName();
    nameSuffix = participantIntakeApi.getNameSuffix();
    races = participantIntakeApi.getRaces();
    ethnicity = participantIntakeApi.getEthnicity();
    legacySourceTable = participantIntakeApi.getLegacySourceTable();
    sensitive = String.valueOf(participantIntakeApi.isSensitive());
    sealed = String.valueOf(participantIntakeApi.isSealed());
    approximateAge = participantIntakeApi.getApproximateAge();
    approximateAgeUnits = participantIntakeApi.getApproximateAgeUnits();

    addresses.forEach(this::addAddress);
    phoneNumbers.forEach(this::addPhoneNumber);

 }



  public ParticipantEntity(String id, Date dateOfBirth, String firstName,
		  String gender, String lastName, String ssn, 
		  ScreeningEntity screeningEntity, String legacyId, String roles,
		  String languages, String middleName, String nameSuffix,
		  String races, String ethnicity, String legacySourceTable,
		  String sensitive, String sealed, String approximateAge,
		  String approximateAgeUnits,
      List<ParticipantAddresses> participantAddresses,
      List<ParticipantPhoneNumbers> participantPhoneNumbers) {
    this( id,  dateOfBirth,  firstName,
         gender,  lastName,  ssn,
         screeningEntity,  legacyId,  roles,
         languages,  middleName,  nameSuffix,
         races,  ethnicity,  legacySourceTable,
         sensitive,  sealed,  approximateAge,
         approximateAgeUnits);
    this.participantAddresses = participantAddresses;
    this.participantPhoneNumbers = participantPhoneNumbers;
  }

  public ParticipantEntity(String id, Date dateOfBirth, String firstName,
		  String gender, String lastName, String ssn,
		  ScreeningEntity screeningEntity, String legacyId, String roles,
		  String languages, String middleName, String nameSuffix,
		  String races, String ethnicity, String legacySourceTable,
		  String sensitive, String sealed, String approximateAge,
		  String approximateAgeUnits) {
	  this.id = id;
	  this.dateOfBirth = dateOfBirth;
	  this.firstName = firstName;
	  this.gender = gender;
	  this.lastName = lastName;
	  this.ssn = ssn;
	  this.languages = languages;
	  this.middleName = middleName;
	  this.nameSuffix = nameSuffix;
	  this.screeningEntity = screeningEntity;
	  this.legacyId = legacyId;
	  this.roles = roles;
	  this.races = races;
	  this.ethnicity = ethnicity;
	  this.legacySourceTable = legacySourceTable;
	  this.sensitive = sensitive;
	  this.sealed = sealed;
	  this.approximateAge = approximateAge;
	  this.approximateAgeUnits = approximateAgeUnits;	  
  }
  
		  
  @Override
  public String getPrimaryKey() {
    return id;
  }

  public String getId() {
    return id;
  }

  public Date getDateOfBirth() {
    return freshDate(dateOfBirth);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getGender() {
    return gender;
  }

  public String getLastName() {
    return lastName;
  }

  public String getSsn() {
    return ssn;
  }

  public ScreeningEntity getScreening() {
    return screeningEntity;
  }

  public String getLegacyId() {
    return legacyId;
  }

  public String getRoles() {
    return roles;
  }

  public String getLanguages() {
    return languages;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public String getRaces() {
    return races;
  }

  public String getEthnicity() {
    return ethnicity;
  }

  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  public String getSensitive() {
    return sensitive;
  }

  public String getSealed() {
    return sealed;
  }

  public String getApproximateAge() {
    return approximateAge;
  }

  public String getApproximateAgeUnits() {
    return approximateAgeUnits;
  }

  public List<ParticipantAddresses> getParticipantAddresses() {
    return participantAddresses;
  }


  public void addAddress(Addresses address){
    participantAddresses.add(new ParticipantAddresses(this, address));
  }

  public void removeAddress(Addresses address){
    for (Iterator<ParticipantAddresses> iterator = participantAddresses.iterator(); iterator.hasNext();){
      ParticipantAddresses participantAddress = iterator.next();
      if (participantAddress.getParticipant().equals(this) && participantAddress.getAddress().equals(address)){
        iterator.remove();
        participantAddress.setAddress(null);
        participantAddress.setParticipant(null);
      }
    }
  }

  public void addPhoneNumber(PhoneNumbers phoneNumber){
    participantPhoneNumbers.add(new ParticipantPhoneNumbers(this, phoneNumber));
  }

  public void removePhone(PhoneNumbers phoneNumber){
    for (Iterator<ParticipantPhoneNumbers> iterator = participantPhoneNumbers.iterator(); iterator.hasNext();){
      ParticipantPhoneNumbers participantPhoneNumber = iterator.next();
      if (participantPhoneNumber.getParticipant().equals(this) && participantPhoneNumber.getPhoneNumber().equals(phoneNumber)){
        iterator.remove();
        participantPhoneNumber.setPhoneNumber(null);
        participantPhoneNumber.setParticipant(null);
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
