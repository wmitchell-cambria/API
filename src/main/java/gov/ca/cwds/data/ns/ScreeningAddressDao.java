package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * CWDS API Team
 */
public class ScreeningAddressDao extends BaseDaoImpl<ScreeningAddressEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ScreeningAddressDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<ScreeningAddressEntity> findByScreeningId(String screeningId) {
    final Query<ScreeningAddressEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findByScreeningId"))
        .setParameter("screeningId", screeningId);
    return query.getResultList();
  }

}
