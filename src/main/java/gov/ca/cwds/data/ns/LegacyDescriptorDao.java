package gov.ca.cwds.data.ns;

import static gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.DESCRIBABLE_TYPE_ADDRESS;
import static gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * Address DAO
 *
 * @author Intake Team 4
 */
public class LegacyDescriptorDao extends CrudsDaoImpl<LegacyDescriptorEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public LegacyDescriptorDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find LegacyDescriptorEntity by participantId
   *
   * @param participantId participantId
   * @return LegacyDescriptorEntity
   */
  public LegacyDescriptorEntity findParticipantLegacyDescriptor(String participantId) {
    return findLegacyDescriptor(participantId, DESCRIBABLE_TYPE_PARTICIPANT);
  }

  /**
   * Find LegacyDescriptorEntity by participantId
   *
   * @param addressId address id
   * @return LegacyDescriptorEntity
   */
  public LegacyDescriptorEntity findAddressLegacyDescriptor(String addressId) {
    return findLegacyDescriptor(addressId, DESCRIBABLE_TYPE_ADDRESS);
  }

  @SuppressWarnings("unchecked")
  private LegacyDescriptorEntity findLegacyDescriptor(String describableId, String describableType) {
    if (describableId == null) {
      return null;
    }
    final Query<LegacyDescriptorEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(LegacyDescriptorEntity.FIND_BY_DESCRIBABLE_ID_AND_TYPE)
        .setParameter("describableId", Long.valueOf(describableId))
        .setParameter("describableType", describableType);
    List<LegacyDescriptorEntity> entityList = query.getResultList();
    return entityList.isEmpty() ? null : entityList.get(0);
  }
}
