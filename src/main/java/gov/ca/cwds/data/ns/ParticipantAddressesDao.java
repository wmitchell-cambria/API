package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.ParticipantAddresses;
import gov.ca.cwds.inject.NsSessionFactory;
import org.hibernate.SessionFactory;

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
}