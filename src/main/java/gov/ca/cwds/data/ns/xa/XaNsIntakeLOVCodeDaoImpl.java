package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLOVCodeDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsIntakeLOVCodeDaoImpl extends IntakeLOVCodeDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsIntakeLOVCodeDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
