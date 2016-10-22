package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Reporter;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class ReporterDao extends CmsCrudsDaoImpl<Reporter> {

  public ReporterDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
