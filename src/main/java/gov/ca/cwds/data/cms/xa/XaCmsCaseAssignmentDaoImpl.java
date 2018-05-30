package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CaseAssignmentDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCaseAssignmentDaoImpl extends CaseAssignmentDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsCaseAssignmentDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
