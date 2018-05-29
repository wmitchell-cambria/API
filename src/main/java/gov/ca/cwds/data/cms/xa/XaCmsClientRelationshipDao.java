package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsClientRelationshipDao extends ClientRelationshipDao {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsClientRelationshipDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
