package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate;
import gov.ca.cwds.inject.CmsSessionFactory;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * DAO for {@link DrmsDocumentTemplateDao}.
 *
 * @author Intake Team 4
 */
public class DrmsDocumentTemplateDao  extends AbstractDAO<DrmsDocumentTemplateDao> {

  private SessionFactory sessionFactory;

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public DrmsDocumentTemplateDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  /**
   * finding reporters based on application contex id
   *
   * @param applicationContextType - application context type id
   * @param govermentEntityType - govermant entity type id.
   * @return - list of DrmsDocumentTemplates
   */
  @SuppressWarnings("unchecked")
  public DrmsDocumentTemplate[] findByApplicationContextAndGovermentEntity(
      Short applicationContextType, Short govermentEntityType) {

    final Query<DrmsDocumentTemplate> query = this.getSessionFactory().getCurrentSession()
            .getNamedQuery(DrmsDocumentTemplate.NQ_TEMPLATES_BY_APPLICATION_CONTEXT_AND_GOVERMANT_ENTITY);

    query.setParameter("applicationContextType", applicationContextType)
         .setParameter("govermentEntityType", govermentEntityType);
    return query.list().toArray(new DrmsDocumentTemplate[0]);
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
