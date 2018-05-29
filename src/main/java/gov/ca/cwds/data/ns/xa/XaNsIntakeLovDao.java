package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsIntakeLovDao extends IntakeLovDao {

  /**
   * Constructor.
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsIntakeLovDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
