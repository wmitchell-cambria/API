package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class CmsDocumentDao extends CmsCrudsDaoImpl<CmsDocument> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public CmsDocumentDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
