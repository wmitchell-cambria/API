package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaLegacyDescriptorDao extends LegacyDescriptorDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaLegacyDescriptorDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
