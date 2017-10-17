package gov.ca.cwds.data.dao.contact;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ContactPartyDeliveredServiceEntity}.
 *
 * @author CWDS API Team
 */
public class ContactPartyDeliveredServiceDao
    extends CrudsDaoImpl<ContactPartyDeliveredServiceEntity> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public ContactPartyDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);

  }

  /**
   * Find the unique Contact Party Delivered Service Record associated with a Delivered Service
   * 
   * @param deliveredServiceId the Delivered Service identifier
   * @return the Contact Party Delivered Service Record
   */
  @SuppressWarnings("unchecked")
  public ContactPartyDeliveredServiceEntity findByDeliveredServiceId(String deliveredServiceId) {
    Query query = this.getSessionFactory().getCurrentSession().getNamedQuery(
        "gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity.findByDeliveredServiceId")
        .setString("deliveredServiceId", deliveredServiceId);
    return (ContactPartyDeliveredServiceEntity) query.getSingleResult();
  }

}
