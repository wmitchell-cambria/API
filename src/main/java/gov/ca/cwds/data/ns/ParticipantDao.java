package gov.ca.cwds.data.ns;

import gov.ca.cwds.data.persistence.ns.LegacyDescriptor;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Participant;
import gov.ca.cwds.inject.NsSessionFactory;
import org.hibernate.query.Query;

/**
 * Address DAO
 *
 * @author CWDS API Team
 */
public class ParticipantDao extends CrudsDaoImpl<Participant> {

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
   * Find LegacyDescriptor by participantId
   *
   * @param participantId participantId
   * @return LegacyDescriptor
   */
  public LegacyDescriptor findParticipantLegacyDescriptor(String participantId) {
    final Query<LegacyDescriptor> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(
            "gov.ca.cwds.data.persistence.ns.LegacyDescriptor.findParticipantLegacyDescriptor")
        .setParameter("participantId", Long.valueOf(participantId));
    return query.getSingleResult();
  }
}
