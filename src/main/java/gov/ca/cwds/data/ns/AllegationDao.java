package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Allegation;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Allegation DAO, you know, for referral/screening allegations.
 * 
 * @author CWDS API Team
 */
public class AllegationDao extends CrudsDaoImpl<Allegation> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public AllegationDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unchecked")
  public List<Allegation> findByVictimId(String id) {
    final Query<Allegation> query =
        grabSession().getNamedQuery(Allegation.FIND_BY_VICTIM_ID).setParameter("victimId", id);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  public List<Allegation> findByPerpetratorId(String id) {
    final Query<Allegation> query = grabSession().getNamedQuery(Allegation.FIND_BY_PERPETRATOR_ID)
        .setParameter("perpetratorId", id);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  public List<Allegation> findByVictimOrPerpetratorId(String id) {
    final Query<Allegation> query = grabSession()
        .getNamedQuery(Allegation.FIND_BY_VICTIM_OR_PERPETRATOR_ID).setParameter("id", id);
    return query.list();
  }

  public void deleteByIdList(List<String> idList) {
    if (idList != null) {
      for (String id : idList) {
        delete(id);
      }
    }
  }

}
