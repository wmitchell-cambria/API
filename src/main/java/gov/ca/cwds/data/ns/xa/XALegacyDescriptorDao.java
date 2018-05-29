package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XALegacyDescriptorDao extends LegacyDescriptorDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XALegacyDescriptorDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
