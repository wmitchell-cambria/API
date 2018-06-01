package gov.ca.cwds.data.persistence.ns;

import static gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.FIND_BY_DESCRIBABLE_IDS_AND_TYPE;
import static gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.FIND_BY_DESCRIBABLE_ID_AND_TYPE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;
import org.joda.time.format.DateTimeFormat;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * {@link PersistentObject} representing Legacy Descriptor in NS.
 *
 * @author CWDS API Team
 */
@NamedQuery(name = FIND_BY_DESCRIBABLE_ID_AND_TYPE, query = "FROM LegacyDescriptorEntity l"
    + " WHERE l.describableId = :describableId AND l.describableType = :describableType")
@NamedQuery(name = FIND_BY_DESCRIBABLE_IDS_AND_TYPE, query = "FROM LegacyDescriptorEntity l"
    + " WHERE l.describableId IN :describableIds AND l.describableType = :describableType")
@Entity
@Table(name = "legacy_descriptors")
public class LegacyDescriptorEntity implements PersistentObject {

  private static final long serialVersionUID = 1L;

  public static final String FIND_BY_DESCRIBABLE_ID_AND_TYPE =
      "gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.findByDescribableIdAndType";
  public static final String FIND_BY_DESCRIBABLE_IDS_AND_TYPE =
      "gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.findByDescribableIdsAndType";
  public static final String DESCRIBABLE_TYPE_ADDRESS = "Address";
  public static final String DESCRIBABLE_TYPE_PARTICIPANT = "Participant";

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
  public LegacyDescriptorEntity() {
    super();
  }

  public LegacyDescriptorEntity(LegacyDescriptor legacyDescriptor, String describableType,
      Long describableId) {
    this.legacyId = legacyDescriptor.getId();
    this.legacyUiId = legacyDescriptor.getUiId();
    this.legacyTableName = legacyDescriptor.getTableName();
    this.legacyTableDescription = legacyDescriptor.getTableDescription();
    this.legacyLastUpdated = DateTimeFormat.forPattern(LegacyDescriptor.DATETIME_FORMAT)
        .print(legacyDescriptor.getLastUpdated());
    this.describableType = describableType;
    this.describableId = describableId;
  }

  public LegacyDescriptorEntity(String legacyId, String legacyUiId, String legacyTableName,
      String legacyTableDescription, String legacyLastUpdated, String describableType,
      Long describableId) {
    this.legacyId = legacyId;
    this.legacyUiId = legacyUiId;
    this.legacyTableName = legacyTableName;
    this.legacyTableDescription = legacyTableDescription;
    this.legacyLastUpdated = legacyLastUpdated;
    this.describableType = describableType;
    this.describableId = describableId;
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
