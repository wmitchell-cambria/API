package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsAssignmentUnitDaoImpl extends AssignmentUnitDao {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsAssignmentUnitDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
