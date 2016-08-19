package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class ReporterDao extends CrudsDaoImpl<StaffPerson> {

	public ReporterDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}