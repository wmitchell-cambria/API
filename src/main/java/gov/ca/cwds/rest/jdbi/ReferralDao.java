package gov.ca.cwds.rest.jdbi;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;

public class ReferralDao extends CrudsDaoImpl<Referral> {

	public ReferralDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
