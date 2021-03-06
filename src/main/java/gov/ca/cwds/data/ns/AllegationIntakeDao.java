package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * CWDS API Team
 */
public class AllegationIntakeDao extends BaseDaoImpl<AllegationEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public AllegationIntakeDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<AllegationEntity> findByScreeningId(String screeningId) {
    final Query<AllegationEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findByScreeningId"))
        .setParameter("screeningId", screeningId);
    return query.getResultList();
  }

}