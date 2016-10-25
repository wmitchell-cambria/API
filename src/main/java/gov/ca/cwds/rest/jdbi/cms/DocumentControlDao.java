package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.DocumentControl;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class DocumentControlDao extends CmsCrudsDaoImpl<DocumentControl> {

  public DocumentControlDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
