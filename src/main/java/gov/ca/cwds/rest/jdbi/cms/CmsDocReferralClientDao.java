package gov.ca.cwds.rest.jdbi.cms;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class CmsDocReferralClientDao extends CrudsDaoImpl<CmsDocReferralClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public CmsDocReferralClientDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Result set sort order is determined by {@link CmsDocReferralClient}.
   * 
   * @param docHandle "document handle", the primary key of CMS legacy document
   * @return ordered list of referral/client document records
   */
  @SuppressWarnings("unchecked")
  public List<CmsDocReferralClient> listDocReferralClient(String docHandle) {
    Query query = this.getSessionFactory().getCurrentSession().getNamedQuery("DocReferalClient")
        .setString("docHandle", docHandle);
    return query.list();
  }

}
