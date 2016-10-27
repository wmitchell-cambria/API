package gov.ca.cwds.rest.jdbi.cms;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class CmsDocReferralClientDao extends CmsCrudsDaoImpl<CmsDocReferralClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public CmsDocReferralClientDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<CmsDocReferralClient> listDocReferralClient(String docHandle) {
    Query query = this.getSessionFactory().getCurrentSession().getNamedQuery("DocReferalClient")
        .setString("docHandle", docHandle);
    return query.list();
  }

}
