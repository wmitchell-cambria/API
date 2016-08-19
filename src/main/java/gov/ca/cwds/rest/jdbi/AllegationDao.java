package gov.ca.cwds.rest.jdbi;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Allegation;

public class AllegationDao extends CrudsDaoImpl<Allegation> {

	public AllegationDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
