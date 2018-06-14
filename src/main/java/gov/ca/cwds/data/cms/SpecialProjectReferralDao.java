package gov.ca.cwds.data.cms;

import java.util.List;
import org.hibernate.SessionFactory;
import com.google.common.collect.ImmutableList;
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
  
  public List<SpecialProjectReferral> findSpecialProjectReferralsByReferralIdAndSpecialProjectId(String referralId,
      String specialProjectId) {
    
    final List<SpecialProjectReferral> specialProjects = currentSession()
    .createNamedQuery(SpecialProjectReferral.FIND_BY_REFERRAL_ID_AND_SPECIAL_PROJECT_ID, SpecialProjectReferral.class)
    .setParameter(SpecialProjectReferral.PARAM_REFERRAL_ID, referralId)
    .setParameter(SpecialProjectReferral.PARAM_SPECIAL_PROJECT_ID, specialProjectId)
    .list();
    
    return ImmutableList.copyOf(specialProjects);
  }
  
}
