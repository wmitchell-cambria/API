package gov.ca.cwds.data.cms;
import java.util.List;
import org.hibernate.SessionFactory;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.SpecialProject;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;

/**
 * DAO for {@link SpecialProject}.
 * 
 * @author CWDS API Team
 */

public class SpecialProjectDao extends CrudsDaoImpl<SpecialProject>{
  
  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public SpecialProjectDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * find SpecialProject by government entity and special project name
   * 
   * @param governmentEntityType - government entity type
   * @param name - special project name
   * @return - list of special projects matching parameters
   */
  public List<SpecialProject> findSpecialProjectsByGovernmentEntityAndName(String name, 
      Short governmentEntityType) {

    final List<SpecialProject> specialProjects = currentSession()
      .createNamedQuery(SpecialProject.FIND_BY_PROJECT_NAME, SpecialProject.class)
      .setParameter(SpecialProject.PARAM_GOVERNMENT_ENTITY, governmentEntityType)
      .setParameter(SpecialProject.PARAM_NAME, name)
      .list();
    return ImmutableList.copyOf(specialProjects);
    }

  /**
   * Find active SSB special projects for given government entity.
   * 
   * @param governmentEntity Government entity for which to find SSB special project
   * @return The active SSB special projects for given government entity, returns null if not found.
   */
  public List<SpecialProject> findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(
      Short governmentEntity) {
    Require.requireNotNullAndNotEmpty(governmentEntity);

    final List<SpecialProject> specialProjects = currentSession()
        .createNamedQuery(SpecialProject.FIND_ACTIVE_SSB_BY_GOVERNMENT_ENTITY, SpecialProject.class)
        .setParameter(SpecialProject.PARAM_GOVERNMENT_ENTITY, governmentEntity).list();

    return ImmutableList.copyOf(specialProjects);
    
  }

}
