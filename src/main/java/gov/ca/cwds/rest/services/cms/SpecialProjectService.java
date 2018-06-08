package gov.ca.cwds.rest.services.cms;

import java.util.List;
import gov.ca.cwds.data.cms.SpecialProjectDao;
import gov.ca.cwds.data.persistence.cms.SpecialProject;

/**
 * Business layer object to work on {@link SpecialProject}
 * 
 * @author CWDS API Team
 */
public class SpecialProjectService {
  
  private SpecialProjectDao specialProjectDao;
  
  SpecialProjectService() {}
  

  /**
   * Retrieves the list of Special Services that match the government entity type and special project name.
   * 
   * @param governmentEntityType - government entity type
   * @param name - special project name
   * 
   * @return - special projects - list of special projects
   * 
   */
  public List<SpecialProject> getByGovernmentEntityTypeAndName(Short governmentEntityType, String name) {    
    List<SpecialProject> specialProjects = specialProjectDao.findSpecialProjectsByGovernmentEntityAndName(name, governmentEntityType);
    return specialProjects;
  }
}
