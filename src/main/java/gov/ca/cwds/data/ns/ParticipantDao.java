package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * Address DAO
 *
 * @author CWDS API Team
 */
public class ParticipantDao extends CrudsDaoImpl<ParticipantEntity> {

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
   * Find LegacyDescriptorEntity by participantId
   *
   * @param participantId participantId
   * @return LegacyDescriptorEntity
   */
  public LegacyDescriptorEntity findParticipantLegacyDescriptor(String participantId) {
    if (participantId == null) {
      return null;
    }
    final Query<LegacyDescriptorEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(LegacyDescriptorEntity.FIND_BY_DESCRIBABLE_ID_AND_TYPE)
        .setParameter(LegacyDescriptorEntity.PARAM_DESCRIBABLE_ID, Long.valueOf(participantId))
        .setParameter(LegacyDescriptorEntity.PARAM_DESCRIBABLE_TYPE, LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT);
    List<LegacyDescriptorEntity> entityList = query.getResultList();
    return entityList.isEmpty() ? null : entityList.get(0);
  }

  /**
   * Find Legacy Id-s by screeningId
   *
   * @param screeningId screeningId
   * @return Set of Legacy Id-s
   */
  public Set<String> findLegacyIdListByScreeningId(String screeningId) {
    final Query<String> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(ParticipantEntity.FIND_LEGACY_ID_LIST_BY_SCREENING_ID)
        .setParameter("screeningId", screeningId);
    return new HashSet<>(query.list());
  }
}
