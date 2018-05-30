package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsClientDao extends ClientDao {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsClientDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
