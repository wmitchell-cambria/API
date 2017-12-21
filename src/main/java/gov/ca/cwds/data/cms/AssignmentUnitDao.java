package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.AssignmentUnit;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * CWDS API Team
 */
public class AssignmentUnitDao extends CrudsDaoImpl<AssignmentUnit> {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public AssignmentUnitDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
