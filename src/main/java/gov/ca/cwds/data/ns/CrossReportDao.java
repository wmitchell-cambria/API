package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * CWDS API Team
 */
public class CrossReportDao extends BaseDaoImpl<CrossReportEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public CrossReportDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<CrossReportEntity> findByScreeningId(String screeningId) {
    final Query<CrossReportEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findByScreeningId"))
        .setParameter("screeningId", screeningId);
    return query.getResultList();
  }

}