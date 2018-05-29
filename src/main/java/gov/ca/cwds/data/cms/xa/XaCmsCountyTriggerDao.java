package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyTriggerDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCountyTriggerDao extends CountyTriggerDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaCmsCountyTriggerDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
