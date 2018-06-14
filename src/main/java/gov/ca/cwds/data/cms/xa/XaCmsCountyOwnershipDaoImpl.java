package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCountyOwnershipDaoImpl extends CountyOwnershipDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaCmsCountyOwnershipDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
