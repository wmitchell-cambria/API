package gov.ca.cwds.rest.services.investigation;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.google.inject.Inject;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Screening;
import gov.ca.cwds.fixture.investigation.ScreeningSummaryEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Screening Summary
 * 
 * @author CWDS API Team
 */
public class ScreeningSummaryService
    implements TypedCrudsService<String, ScreeningSummary, Response> {

  private ScreeningDao screeningDao;

  /**
   * 
   * @param screeningDao - screening dao
   */
  @Inject
  public ScreeningSummaryService(ScreeningDao screeningDao) {
    super();
    this.screeningDao = screeningDao;
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */

  @Override
  public Response find(String primaryKey) {
    if (StringUtils.equals(primaryKey, "STUB")) {
      return new ScreeningSummaryEntityBuilder().build();

    }

    Transaction txn = null;
    final Session session = this.screeningDao.getSessionFactory().getCurrentSession();
    try {
      txn = session.beginTransaction();
    } catch (Exception e) {
      txn = session.getTransaction();
    }
    try {
      Screening screening = this.screeningDao.find(primaryKey);

      session.clear();

    } catch (HibernateException he) {
      txn.rollback();
      throw new DaoException(he);
    }

    ScreeningSummary serialized = new ScreeningSummary();
    return serialized;
  }



  @Override
  public Response create(ScreeningSummary request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, ScreeningSummary request) {
    throw new NotImplementedException("update not implemented");
  }

}
