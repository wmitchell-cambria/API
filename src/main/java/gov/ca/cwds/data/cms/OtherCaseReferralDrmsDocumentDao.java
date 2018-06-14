package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link OtherCaseReferralDrmsDocumentDao}.
 *
 * @author CWDS API Team
 */
public class OtherCaseReferralDrmsDocumentDao extends CrudsDaoImpl<OtherCaseReferralDrmsDocument> {

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
