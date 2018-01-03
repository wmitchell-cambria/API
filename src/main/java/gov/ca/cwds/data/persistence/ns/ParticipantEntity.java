package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;

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
public class ParticipantEntity implements PersistentObject {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_id")
  @SequenceGenerator(name = "participant_id", sequenceName = "participants_id_seq")
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
  private String races;

  @Column(name = "ethnicity")
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

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public ParticipantEntity() {
    super();
  }

  @Override
  public String getPrimaryKey() {
    return id;
  }

  public String getId() {
    return id;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
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
