package gov.ca.cwds.data.ns;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PaperTrail;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Paper Trail DAO (i.e., Hibernate synthetic trigger).
 * 
 * @author CWDS API Team
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
    // Restrict deleting paper trail.
    return this.get(id);
  }

}
