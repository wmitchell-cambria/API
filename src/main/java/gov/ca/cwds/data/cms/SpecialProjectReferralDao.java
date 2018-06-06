package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;
import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.SpecialProjectReferral;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link SpecialProjectReferral}.
 * 
 * @author CWDS API Team
 */
public class SpecialProjectReferralDao extends CrudsDaoImpl<SpecialProjectReferral>{

  /**
   * Constructor
   * 
   * @param sessionFactory - the session factory
   * 
   */
  @Inject
  public SpecialProjectReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
  
}
