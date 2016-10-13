package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Referral;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class ReferralDao extends CrudsDaoImpl<Referral> {

  public ReferralDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
