package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class StaffPersonDao extends CrudsDaoImpl<StaffPerson> {

	public StaffPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
