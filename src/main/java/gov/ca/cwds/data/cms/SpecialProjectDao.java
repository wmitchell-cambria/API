package gov.ca.cwds.data.cms;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.SpecialProject;
import gov.ca.cwds.inject.CmsSessionFactory;

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
  public List<SpecialProject> findSpecialProjectsByGovernmentEntityAndName(Short governmentEntityType,
    String name) {
    @SuppressWarnings("unchecked")
    Query<SpecialProject> query = this.getSessionFactory().getCurrentSession()
      .getNamedQuery(SpecialProject.FIND_BY_PROJECT_NAME)
      .setParameter("governmentEntityType", governmentEntityType)
      .setParameter("name", name);
    return query.list();      
    }
  
}
