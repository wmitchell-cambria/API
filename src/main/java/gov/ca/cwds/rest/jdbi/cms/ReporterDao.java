package gov.ca.cwds.rest.jdbi.cms;

import gov.ca.cwds.rest.api.persistence.cms.Reporter;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

import org.hibernate.SessionFactory;

public class ReporterDao extends BaseDaoImpl<Reporter> {

  public ReporterDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
