package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.ParticipantAddresses;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * Participant Addresses DAO
 *
 * @author Intake Team 4
 */
public class ParticipantAddressesDao extends CrudsDaoImpl<ParticipantAddresses> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ParticipantAddressesDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Set<ParticipantAddresses> findByParticipantId(String participantId){
    final Query<ParticipantAddresses> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(ParticipantAddresses.PARTICIPANT_ADDRESSES_BY_PARTICIPANT_ID)
        .setParameter(ParticipantAddresses.PARAM_PARTICIPANT_ID, participantId);
    return new HashSet<>(query.getResultList());
  }

}