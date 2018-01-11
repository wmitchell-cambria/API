package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * DAO for {@link OtherCaseReferralDrmsDocumentDao}.
 *
 * @author Intake Team 4
 */
public class OtherCaseReferralDrmsDocumentDao extends CrudsDaoImpl<OtherCaseReferralDrmsDocument>{


  /**
   * Constructor
   *
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public OtherCaseReferralDrmsDocumentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
