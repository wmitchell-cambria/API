package gov.ca.cwds.data.ns;

import gov.ca.cwds.data.persistence.ns.IntakeLOVCode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  /**
   * Find HOI Screening objects by screening id.
   *
   * @param screeningId - screening Id
   * @return list of Screening objects
   */
  @SuppressWarnings("unchecked")
  public Set<Screening> findHoiScreeningsByScreeningId(String screeningId) {
    final Query<Screening> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.ns.Screening.findHoiScreeningsByScreeningId")
        .setParameter("screeningId", screeningId);
    return new HashSet(query.list());
  }

  /**
   * Find IntakeLOVCode object by intakeCode
   *
   * @param intakeCode intakeCode
   * @return IntakeLOVCode
   */
  public IntakeLOVCode findIntakeLOVCodeByIntakeCode(String intakeCode) {
    final Query<IntakeLOVCode> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(
            "gov.ca.cwds.data.persistence.ns.IntakeLOVCode.findIntakeLOVCodeByIntakeCode")
        .setParameter("intakeCode", intakeCode);
    List<IntakeLOVCode> codes = query.list();
    return codes.isEmpty() ? null : codes.get(0);
  }
}
