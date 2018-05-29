package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ParticipantPhoneNumbersDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsParticipantPhoneNumbersDao extends ParticipantPhoneNumbersDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsParticipantPhoneNumbersDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
