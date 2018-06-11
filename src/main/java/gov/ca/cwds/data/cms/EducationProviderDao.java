package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.EducationProvider;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link EducationProvider}.
 * 
 * @author CWDS API Team
 */
public class EducationProviderDao extends BaseDaoImpl<EducationProvider> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public EducationProviderDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
