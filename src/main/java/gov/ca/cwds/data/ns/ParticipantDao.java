package gov.ca.cwds.data.ns;

import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.inject.NsSessionFactory;
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
    final Query<LegacyDescriptorEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(
            "gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.findParticipantLegacyDescriptor")
        .setParameter("participantId", Long.valueOf(participantId));
    return query.getSingleResult();
  }
}
