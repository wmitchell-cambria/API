package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ParticipantPhoneNumbersDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsParticipantPhoneNumbersDaoImpl extends ParticipantPhoneNumbersDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsParticipantPhoneNumbersDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
