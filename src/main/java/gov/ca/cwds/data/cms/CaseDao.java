package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CmsCase}.
 * 
 * @author CWDS API Team
 */
public class CaseDao extends CrudsDaoImpl<CmsCase> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find by client id
   * 
   * @param clientId - clientId
   * @return the case
   */
  @SuppressWarnings("unchecked")
  public CmsCase[] findByClientId(String clientId) {
    final Query<CmsCase> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.CmsCase.findByClient");
    query.setParameter("clientId", clientId, StringType.INSTANCE);
    return query.list().toArray(new CmsCase[0]);
  }

}
