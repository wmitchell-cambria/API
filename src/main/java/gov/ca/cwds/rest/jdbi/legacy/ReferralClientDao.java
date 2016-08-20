package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class ReferralClientDao extends CrudsDaoImpl<ReferralClient> {

	public ReferralClientDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}