package gov.ca.cwds.data.ns;

import gov.ca.cwds.data.BaseDaoImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Participant DAO in PostgreSQL.
 *
 * @author CWDS API Team
 */
public class ParticipantDao extends BaseDaoImpl<ParticipantEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ParticipantDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find Legacy Id-s by screeningId
   *
   * @param screeningId screeningId
   * @return Set of Legacy Id-s
   */
  public Set<String> findLegacyIdListByScreeningId(String screeningId) {
    @SuppressWarnings("unchecked")
    final Query<String> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(ParticipantEntity.FIND_LEGACY_ID_LIST_BY_SCREENING_ID)
        .setParameter("screeningId", screeningId);
    return new HashSet<>(query.list());
  }

  public List<ParticipantEntity> getByScreeningId(String screeningId) {
    @SuppressWarnings("unchecked")
    final Query<ParticipantEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findByScreeningId"))
        .setParameter("screeningId", screeningId);
    return query.list();
  }
}
