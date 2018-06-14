package gov.ca.cwds.fixture;

import java.time.LocalDate;
import java.util.Date;
import gov.ca.cwds.data.persistence.cms.SpecialProject;

public class SpecialProjectEntityBuilder {

  private String archiveAssociationIndicator = "N";
  private String projectDescription = "special project description";
  private LocalDate endDate = null;
  private Short governmentEntityType = 1084;
  private String id = "1234567ABC";
  private String name = "special project name";
  private LocalDate startDate = LocalDate.now();
  
  public SpecialProject build() {
    return new SpecialProject(archiveAssociationIndicator, projectDescription,
        endDate, governmentEntityType, id, name, startDate);
  }
  
  public SpecialProjectEntityBuilder setArchiveAssociationIndicator(String archiveAssociationIndicator) {
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    return this;
  }
  
  public SpecialProjectEntityBuilder setProjectDescription(String projectDescription) {
    this.projectDescription = projectDescription;
    return this;
  }
  
  public SpecialProjectEntityBuilder setEndDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }
  
  public SpecialProjectEntityBuilder setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
    return this;
  }
  
  public SpecialProjectEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }
  
  public SpecialProjectEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }
  
  public SpecialProjectEntityBuilder setStartDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }
  
}
