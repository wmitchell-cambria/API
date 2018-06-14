package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsIntakeLovDaoImpl extends IntakeLovDao {

  /**
   * Constructor.
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsIntakeLovDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
