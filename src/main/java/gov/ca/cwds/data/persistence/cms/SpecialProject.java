package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.data.persistence.cms.SpecialProject.FIND_BY_PROJECT_NAME;
import static gov.ca.cwds.data.persistence.cms.SpecialProject.FIND_BY_PROJECT_NAME_QUERY;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

/**
 * {@link CmsPersistentObject} Class representing a Special Project.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "SPC_PRJT")

@NamedQuery(name = FIND_BY_PROJECT_NAME,
query = FIND_BY_PROJECT_NAME_QUERY)

@NamedQuery(name = SpecialProject.FIND_ACTIVE_SSB_BY_GOVERNMENT_ENTITY,
query = "FROM SpecialProject WHERE PROJECT_NM = '" + SpecialProject.SSB_SPECIAL_PROJECT_NAME
    + "' AND END_DT IS NULL AND GVR_ENTC = :governmentEntity")

public class SpecialProject extends CmsPersistentObject {

  private static final long serialVersionUID = 241170224860954003L;

  public static final String FIND_ACTIVE_SSB_BY_GOVERNMENT_ENTITY =
      "SpecialProject.findActiveSSBByGovernmentEntity";

  public static final String SSB_SPECIAL_PROJECT_NAME = "S-Safely Surrendered Baby";
  public static final String PARAM_GOVERNMENT_ENTITY = "governmentEntity";
  public static final String PARAM_NAME = "name";

  public static final String FIND_BY_PROJECT_NAME = 
      "gov.ca.cwds.data.persistence.cms.SpecialProject.findByProjectName";
  static final String FIND_BY_PROJECT_NAME_QUERY = 
      "FROM SpecialProject WHERE GOV_ENTC = :governementEntityType AND END_DT IS NULL AND PROJECT_NM = :name";
  
  @Column(name = "ARCASS_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private String archiveAssociationIndicator;

  @Column(name = "PRJCT_DSC", nullable = false, length = 254)
  private String projectDescription;
  
  @Column(name = "END_DT", nullable = true, length = 10)
  private transient LocalDate endDate;
  
  @Column(name = "GVR_ENTC", nullable = false, length = 5)
  private Short governmentEntityType;
  
  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  private String id;
  
  @Column(name = "PROJECT_NM", nullable = false, length = 30) 
  @ColumnTransformer(read = "trim(PROJECT_NM)")
  private String name;
  
  @Column(name = "START_DT", nullable = false, length = 10)
  private transient LocalDate startDate;
  
  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public SpecialProject() {
    super();
  }
  
  /**
   * Constructor
   * 
   * @param archiveAssociationIndicator - archive association indicator
   * @param projectDescription - project description
   * @param endDate - end date
   * @param governmentEntityType - government entity type
   * @param id - primary key
   * @param name - special project name
   * @param startDate - start date
   * 
   */
  
  public SpecialProject(String archiveAssociationIndicator, String projectDescription,
      LocalDate endDate, Short governmentEntityType, String id, String name, LocalDate startDate) {
    super();
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.projectDescription = projectDescription;
    this.endDate = endDate;
    this.governmentEntityType = governmentEntityType;
    this.id = id;
    this.name = name;
    this.startDate = startDate;
  }
  
  /**
   *  @return the id
   */
  public Serializable getPrimaryKey() {
    return this.getId();
  }

  /**
   * 
   * @return the archiveAssoicationIndicator
   */
  public String getArchiveAssociationIndicator() {
    return archiveAssociationIndicator;
  }

  /**
   * 
   * @param archiveAssociationIndicator - archive association indicator
   */
  public void setArchiveAssociationIndicator(String archiveAssociationIndicator) {
    this.archiveAssociationIndicator = archiveAssociationIndicator;
  }

  /**
   * 
   * @return the description
   */
  public String getProjectDescription() {
    return projectDescription;
  }

/**
 * 
 * @param projectDescription - project description
 */
  public void setProjectDescription(String projectDescription) {
    this.projectDescription = projectDescription;
  }

  /**
   * 
   * @return the endDate
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * 
   * @param endDate - end date
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * 
   * @return the governmentEntityType
   */
  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  /**
   * 
   * @param governmentEntityType - government entity type
   */
  public void setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
  }

  /**
   * 
   * @return the name
   */
  public String getId() {
    return id;
  }

  /**
   * 
   * @param id - identifier
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 
   * @return - special project name
   */
  public String getName() {
    return name;
  }

  /**
   * 
   * @param name - special project name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 
   * @return the startDate
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * 
   * @param startDate - start date
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
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
