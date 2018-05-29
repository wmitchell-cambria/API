package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ParticipantAddressesDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsParticipantAddressesDao extends ParticipantAddressesDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsParticipantAddressesDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
