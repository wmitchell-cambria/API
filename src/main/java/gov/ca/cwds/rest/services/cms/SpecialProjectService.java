package gov.ca.cwds.rest.services.cms;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gov.ca.cwds.data.cms.SpecialProjectDao;
import gov.ca.cwds.data.persistence.cms.SpecialProject;

/**
 * Business layer object to work on {@link SpecialProject}
 * 
 * @author CWDS API Team
 */
public class SpecialProjectService {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SpecialProject.class);
  
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
    List<SpecialProject> specialProjects = specialProjectDao.findSpecialProjectsByGovernmentEntityAndName(governmentEntityType, name);
    return specialProjects;
  }
}
