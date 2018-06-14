package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.DrmsDocumentTemplateDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsDrmsDocumentTemplateDaoImpl extends DrmsDocumentTemplateDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaCmsDrmsDocumentTemplateDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
