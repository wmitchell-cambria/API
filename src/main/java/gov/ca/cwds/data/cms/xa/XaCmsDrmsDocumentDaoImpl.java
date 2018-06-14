package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsDrmsDocumentDaoImpl extends DrmsDocumentDao {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public XaCmsDrmsDocumentDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
