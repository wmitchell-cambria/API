package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class ReferralClientDao extends CrudsDaoImpl<ReferralClient> {

	public ReferralClientDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}