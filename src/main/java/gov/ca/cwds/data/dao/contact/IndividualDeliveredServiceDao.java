package gov.ca.cwds.data.dao.contact;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link IndividualDeliveredServiceEntity}.
 * 
 * @author CWDS API Team
 */
public class IndividualDeliveredServiceDao extends CrudsDaoImpl<IndividualDeliveredServiceEntity> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public IndividualDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}