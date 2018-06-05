package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.data.persistence.cms.SpecialProject.FIND_BY_PROJECT_NAME;
import static gov.ca.cwds.data.persistence.cms.SpecialProject.FIND_BY_PROJECT_NAME_QUERY;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;

/**
 * {@link CmsPersistentObject} Class representing a Special Project.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = FIND_BY_PROJECT_NAME,
    query = FIND_BY_PROJECT_NAME_QUERY)

@SuppressWarnings("serial")
@Entity
@Table(name = "SPC_PRJT")
public class SpecialProject extends CmsPersistentObject {

  public static final String FIND_BY_PROJECT_NAME = 
      "gov.ca.cwds.data.persistence.cms.SpecialProject.findByProjectName";
  static final String FIND_BY_PROJECT_NAME_QUERY = 
      "FROM ScreeningWrapper WHERE GOV_ENTC = :governementEntityType AND PROJECT_NM = :name";
  
  @Column(name = "ARCASS_IND")
  private String archiveAssociationIndicator;

  @Column(name = "PRJCT_DSC")
  private String projectDescription;
  
  @Column(name = "END_DT")
  private Date endDate;
  
  @Column(name = "GOV_ENTC")
  private Short governmentEntityType;
  
  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;
  
  @Column(name = "PROJECT_NM") 
  private String name;
  
  @Column(name = "START_DT")
  private Date startDate;
  
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
      Date endDate, Short governmentEntityType, String id, String name, Date startDate) {
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
  public String getPrimaryKey() {
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
  public Date getEndDate() {
    return freshDate(endDate);
  }

  /**
   * 
   * @param endDate - end date
   */
  public void setEndDate(Date endDate) {
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
  public Date getStartDate() {
    return freshDate(startDate);
  }

  /**
   * 
   * @param startDate - start date
   */
  public void setStartDate(Date startDate) {
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
