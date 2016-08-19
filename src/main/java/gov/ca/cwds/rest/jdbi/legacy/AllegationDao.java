package gov.ca.cwds.rest.jdbi.legacy;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Allegation;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class AllegationDao extends CrudsDaoImpl<Allegation> {

	public AllegationDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
