package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * Address DAO
 *
 * @author Intake Team 4
 */
public class AddressesDao extends CrudsDaoImpl<Addresses> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public AddressesDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Addresses> findByParticipant(String participantId) {
    @SuppressWarnings("unchecked")
    final Query<Addresses> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(Addresses.FIND_BY_PARTICIPANT_ID)
        .setParameter(Addresses.PARAM_PARTICIPANT_ID, participantId);
    return query.getResultList();
  }
}
