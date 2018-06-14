package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsDocumentDaoImpl extends CmsDocumentDao {

  /**
   * Constructor.
   * 
   * @param sessionFactory Hibernate session factory
   */
  @Inject
  public XaCmsDocumentDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
