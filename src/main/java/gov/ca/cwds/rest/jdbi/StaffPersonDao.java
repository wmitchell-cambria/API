package gov.ca.cwds.rest.jdbi;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.StaffPerson;

public class StaffPersonDao extends CrudsDaoImpl<StaffPerson> {

	public StaffPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
