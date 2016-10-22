package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.CrossReport;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class CrossReportDao extends CmsCrudsDaoImpl<CrossReport> {

  public CrossReportDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

