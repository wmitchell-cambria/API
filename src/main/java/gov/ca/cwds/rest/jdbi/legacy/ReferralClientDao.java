package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class ReferralClientDao extends CrudsDaoImpl<StaffPerson> {

	public ReferralClientDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}