package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PaperTrail;
import gov.ca.cwds.inject.NsSessionFactory;
import org.hibernate.SessionFactory;

import java.io.Serializable;

/**
 * Address DAO
 * 
 * @author Intake Team 4
 */
public class PaperTrailDao extends CrudsDaoImpl<PaperTrail> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public PaperTrailDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public PaperTrail delete(Serializable id) {
    //Rerstrict deleting paper trail.
    return this.get(id);
  }
}
