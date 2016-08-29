package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class ReferralDao extends CrudsDaoImpl<Referral> {

	public ReferralDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
