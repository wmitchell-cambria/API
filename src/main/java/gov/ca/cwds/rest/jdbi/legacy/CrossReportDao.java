package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class CrossReportDao extends CrudsDaoImpl<CrossReport> {

	public CrossReportDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}

