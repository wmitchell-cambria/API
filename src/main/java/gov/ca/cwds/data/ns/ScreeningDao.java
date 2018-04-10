package gov.ca.cwds.data.ns;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Screening DAO
 *
 * @author CWDS API Team
 */
public class ScreeningDao extends CrudsDaoImpl<ScreeningEntity> {

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
   * Find ScreeningEntity objects by referral id.
   *
   * @param referralId - referral Id
   * @return list of ScreeningEntity object
   */
  @SuppressWarnings("unchecked")
  public ScreeningEntity[] findScreeningsByReferralId(String referralId) {
    final Query<ScreeningEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.ns.ScreeningEntity.findScreeningsByReferralId")
        .setParameter("referralId", referralId);
    return query.list().toArray(new ScreeningEntity[0]);
  }

  /**
   * Find HOI ScreeningEntity objects by screening id.
   *
   * @param clientIds - Legacy Client Id-s
   * @return list of ScreeningEntity objects
   */
  @SuppressWarnings("unchecked")
  public Set<ScreeningEntity> findScreeningsByClientIds(Set<String> clientIds) {
    final Query<ScreeningEntity> query = this.getSessionFactory()
    	  .getCurrentSession()
      .getNamedQuery("gov.ca.cwds.data.persistence.ns.ScreeningEntity.findScreeningsByClientIds")
    	  .setParameter("clientIds", clientIds);
    return new HashSet<ScreeningEntity>(query.list());
  }

  /**
   * Find IntakeLOVCodeEntity object by intakeCode
   *
   * @param intakeCode intakeCode
   * @return IntakeLOVCodeEntity
   */
  public IntakeLOVCodeEntity findIntakeLOVCodeByIntakeCode(String intakeCode) {
    @SuppressWarnings("unchecked")
	final Query<IntakeLOVCodeEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(
            "gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity.findIntakeLOVCodeByIntakeCode")
        .setParameter("intakeCode", intakeCode);
    List<IntakeLOVCodeEntity> codes = query.list();
    return codes.isEmpty() ? null : codes.get(0);
  }
  
  /**
   * Find screenings of current user (staff ID)
   * 
   * @param staffId - staff Id
   * @return - array of screeningDashboard object
   */
  public List<ScreeningWrapper> findScreeningsByUserId(String staffId) {
	@SuppressWarnings("unchecked")
	final Query<ScreeningWrapper> query = this.getSessionFactory()
		.getCurrentSession()
		.getNamedQuery("gov.ca.cwds.data.persistence.ns.ScreeningWrapper.findScreeningsOfUser")
		.setParameter("staffId", staffId, StringType.INSTANCE);
	return query.list();
  }
  
}
