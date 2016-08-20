package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Reporter;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class ReporterDao extends CrudsDaoImpl<Reporter> {

	public ReporterDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}