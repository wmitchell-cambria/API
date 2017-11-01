package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Screening;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Screening DAO
 * 
 * @author CWDS API Team
 */
public class ScreeningDao extends CrudsDaoImpl<Screening> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ScreeningDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find Screening objects by referral id.
   * 
   * @param referralId - referral Id
   * @return list of Screening object
   */
  @SuppressWarnings("unchecked")
  public Screening[] findScreeningsByReferralId(String referralId) {
    final Query<Screening> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.ns.Screening.findScreeningsByReferralId")
        .setParameter("referralId", referralId);
    return query.list().toArray(new Screening[0]);
  }

}
