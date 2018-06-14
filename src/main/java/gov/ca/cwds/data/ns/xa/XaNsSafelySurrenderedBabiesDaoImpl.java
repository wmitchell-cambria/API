package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.SafelySurrenderedBabiesDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsSafelySurrenderedBabiesDaoImpl extends SafelySurrenderedBabiesDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsSafelySurrenderedBabiesDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
