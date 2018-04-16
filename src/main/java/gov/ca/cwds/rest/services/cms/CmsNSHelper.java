package gov.ca.cwds.rest.services.cms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * <strong>NOTE:</strong> XA transactions make this helper class obsolete.
 * 
 * @author CWDS API Team
 */
public class CmsNSHelper {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsNSHelper.class);

  private SessionFactory cmsSessionFactory;

  private SessionFactory nsSessionFactory;

  public CmsNSHelper(SessionFactory sessionFactory, SessionFactory nsSessionFactory) {
    this.cmsSessionFactory = sessionFactory;
    this.nsSessionFactory = nsSessionFactory;
  }

  public Map<String, Map<CrudsService, Response>> handleResponse(
      Map<CrudsService, Request> cmsRequests, Map<CrudsService, Request> nsRequests) {

    Map<CrudsService, Response> cmsResponse = new HashMap<>();
    Map<CrudsService, Response> nsResponse = new HashMap<>();
    Map<String, Map<CrudsService, Response>> response = new HashMap<>();
    Response referral = null;
    Response person;

    try (Session sessionCMS = cmsSessionFactory.openSession();
        Session sessionNS = nsSessionFactory.openSession();) {
      ManagedSessionContext.bind(sessionCMS); // NOSONAR
      Transaction transactionCMS = sessionCMS.beginTransaction();
      for (Entry<CrudsService, Request> cmsRequestsService : cmsRequests.entrySet()) {
        try {
          CrudsService service = cmsRequestsService.getKey();
          referral = service.create(cmsRequests.get(service));
          cmsResponse.put(service, referral);
          sessionCMS.flush();
        } catch (Exception e) {
          transactionCMS.rollback();
          throw e;
        }
      }

      ManagedSessionContext.bind(sessionNS); // NOSONAR
      Transaction transactionNS = sessionNS.beginTransaction();
      for (Entry<CrudsService, Request> nsRequestsService : nsRequests.entrySet()) {
        try {
          CrudsService service = nsRequestsService.getKey();
          person = service.create(nsRequests.get(service));
          nsResponse.put(service, person);
          sessionNS.flush();
        } catch (Exception e) {
          transactionNS.rollback();
          transactionCMS.rollback();
          throw e;
        }
        try {
          transactionCMS.commit();
          transactionNS.commit();
        } catch (Exception e) {
          throw e;
        }
      }
    } finally {
      ManagedSessionContext.unbind(cmsSessionFactory); // NOSONAR
      ManagedSessionContext.unbind(nsSessionFactory); // NOSONAR

    }
    response.put("cms", cmsResponse);
    response.put("ns", nsResponse);

    return response;

  }

}
