package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CwsOffice;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * CWDS API Team
 */
public class CwsOfficeDao extends CrudsDaoImpl<CwsOffice> {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CwsOfficeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}


