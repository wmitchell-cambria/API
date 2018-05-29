package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.AllegationIntakeDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsAllegationIntakeDao extends AllegationIntakeDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsAllegationIntakeDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
