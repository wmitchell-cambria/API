package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ScreeningAddressDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsScreeningAddressDao extends ScreeningAddressDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsScreeningAddressDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
