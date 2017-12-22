package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * {@link PersistentObject} representing Legacy Descriptor in NS
 *
 * @author CWDS API Team
 */
@Entity
@Table(name = "legacy_descriptors")
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.LegacyDescriptor.findParticipantLegacyDescriptor",
    query = "SELECT l FROM LegacyDescriptor l WHERE l.describableId = :participantId AND l.describableType = 'Participant'")
public class LegacyDescriptor implements PersistentObject {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "legacy_descriptors_id")
  @SequenceGenerator(name = "legacy_descriptors_id", sequenceName = "legacy_descriptors_id_seq")
  private Long id;

  @Column(name = "legacy_id")
  private String legacyId;

  @Column(name = "legacy_ui_id")
  private String legacyUiId;

  @Column(name = "legacy_table_name")
  private String legacyTableName;

  @Column(name = "legacy_table_description")
  private String legacyTableDescription;

  @Column(name = "legacy_last_updated")
  private String legacyLastUpdated;

  @Column(name = "describable_type")
  private String describableType;

  @Column(name = "describable_id")
  private Long describableId;

  /**
   * Default constructor
   *
   * Required for Hibernate
   */
  public LegacyDescriptor() {
    super();
  }

  public Long getId() {
    return id;
  }

  public String getLegacyId() {
    return legacyId;
  }

  public String getLegacyUiId() {
    return legacyUiId;
  }

  public String getLegacyTableName() {
    return legacyTableName;
  }

  public String getLegacyTableDescription() {
    return legacyTableDescription;
  }

  public String getLegacyLastUpdated() {
    return legacyLastUpdated;
  }

  public String getDescribableType() {
    return describableType;
  }

  public Long getDescribableId() {
    return describableId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
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
