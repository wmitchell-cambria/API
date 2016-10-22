package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class ReferralClientDao extends CmsCrudsDaoImpl<ReferralClient> {

  public ReferralClientDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
