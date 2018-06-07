package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * DAO for Intake LOV codes.
 * 
 * @author CWDS API Team
 */
public class IntakeLovDao extends BaseDaoImpl<IntakeLov> {

  private static final Logger LOGGER = LoggerFactory.getLogger(IntakeLovDao.class);

  /**
   * Constructor.
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public IntakeLovDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param legacyCategoryId - legacyCategoryId
   * @return the intake code based on the category id
   */
  @SuppressWarnings("rawtypes")
  public List<IntakeLov> findByLegacyMetaId(String legacyCategoryId) {
    final String namedQueryName = IntakeLov.class.getName() + ".findByLegacyCategoryId";

    final Session session = grabSession();
    Transaction txn = session.getTransaction();
    boolean transactionExists = txn != null && txn.isActive();
    txn = transactionExists ? txn : session.beginTransaction();

    try {
      final Query query =
          session.getNamedQuery(namedQueryName).setParameter("legacyCategoryId", legacyCategoryId);
      final List<IntakeLov> intakeCodes = query.list();

      if (!transactionExists)
        txn.commit();
      return intakeCodes;
    } catch (HibernateException h) {
      txn.rollback();
      throw new DaoException(h);
    }
  }

}
