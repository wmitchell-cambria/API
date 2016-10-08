package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class StaffPersonDao extends CrudsDaoImpl<StaffPerson> {

	public StaffPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
