package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.Date;
import org.hibernate.SessionFactory;

/**
 * Participant Phone Numbers DAO
 *
 * @author Intake Team 4
 */
public class ParticipantPhoneNumbersDao extends CrudsDaoImpl<ParticipantPhoneNumbers> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ParticipantPhoneNumbersDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public ParticipantPhoneNumbers create(ParticipantPhoneNumbers object) {

    object.setCreatedAt(new Date());
    object.setUpdatedAt(object.getCreatedAt());
    return super.create(object);
  }

  @Override
  public ParticipantPhoneNumbers update(ParticipantPhoneNumbers object) {
    object.setUpdatedAt(new Date());
    return super.update(object);
  }

}