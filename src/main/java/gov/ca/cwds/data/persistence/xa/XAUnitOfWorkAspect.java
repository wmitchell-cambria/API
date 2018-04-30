package gov.ca.cwds.data.persistence.xa;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.UserTransaction;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.google.common.collect.ImmutableMap;

import gov.ca.cwds.data.DaoException;

/**
 * AOP aspect supports {@link XAUnitOfWork}.
 * 
 * @author CWDS API Team
 */
public class XAUnitOfWorkAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(XAUnitOfWorkAspect.class);

  private XAUnitOfWork xaUnitOfWork;

  private UserTransaction txn = new UserTransactionImp();

  private ImmutableMap<String, SessionFactory> sessionFactories;

  private List<Session> sessions = new ArrayList<>();

  /**
   * 
   * @param sessionFactories - all datasources to participate in the XA transaction
   */
  public XAUnitOfWorkAspect(ImmutableMap<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

  /**
   * Aspect entry point.
   * 
   * @param xaUnitOfWork - take settings from annotation
   */
  public void beforeStart(XAUnitOfWork xaUnitOfWork) {
    if (xaUnitOfWork == null) {
      return;
    }
    this.xaUnitOfWork = xaUnitOfWork;

    openSessions();
    beginTransaction();
  }

  /**
   * NOTE: method onFinish() closes the session.
   * 
   * @throws Exception on database error
   */
  public void afterEnd() throws Exception {
    if (sessions.isEmpty()) {
      return;
    }

    try {
      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw e;
    }
  }

  /**
   * 
   * @throws Exception on database error
   */
  public void onError() throws Exception {
    if (sessions.isEmpty()) {
      return;
    }

    try {
      rollbackTransaction();
    } finally {
      onFinish();
    }
  }

  /**
   * Close open sessions.
   * 
   * @throws Exception on database error
   */
  public void onFinish() throws Exception {
    txn = null;
    closeSessions();
  }

  /**
   * Get the current Hibernate session, if open, or open a new session.
   * 
   * @param sessionFactory - open a session for this datasource
   * @return session
   */
  protected Session grabSession(SessionFactory sessionFactory) {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      LOGGER.warn("No current session. Open a new one.", e.getCause());
      session = sessionFactory.openSession();
    }

    sessions.add(session);
    configureSession(session);
    return session;
  }

  /**
   * Open sessions for selected datasources.
   */
  protected void openSessions() {
    final String[] sources = xaUnitOfWork.value();
    if (sources != null && sources.length > 0) {
      sessionFactories.values().stream().filter(e -> ArrayUtils.contains(sources, e))
          .forEach(this::grabSession);
    } else {
      sessionFactories.values().stream().forEach(this::grabSession);
    }
  }

  /**
   * Close all sessions.
   */
  protected void closeSessions() {
    sessions.stream().forEach(this::closeSession);
  }

  protected void closeSession(Session session) {
    if (session != null) {
      session.close();
    }
  }

  /**
   * Set cache mode, flush mode, and read-only properties on a Hibernate session.
   * 
   * @param session - target Hibernate session
   */
  protected void configureSession(Session session) {
    session.setDefaultReadOnly(xaUnitOfWork.readOnly());
    session.setCacheMode(xaUnitOfWork.cacheMode());
    session.setHibernateFlushMode(xaUnitOfWork.flushMode());
  }

  /**
   * Start XA transaction. Set timeout to 80 seconds.
   */
  protected void beginTransaction() {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    try {
      txn.setTransactionTimeout(80);
      txn.begin();
    } catch (Exception e) {
      LOGGER.error("XA BEGIN FAILED! {}", e.getMessage(), e);
      throw new DaoException("XA BEGIN FAILED!", e);
    }
  }

  /**
   * Roll back XA transaction.
   */
  protected void rollbackTransaction() {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    try {
      txn.rollback();
    } catch (Exception e) {
      LOGGER.error("XA ROLLBACK FAILED! {}", e.getMessage(), e);
      throw new DaoException("XA ROLLBACK FAILED!", e);
    }
  }

  /**
   * Commit XA transaction.
   */
  protected void commitTransaction() {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    try {
      txn.commit();
    } catch (Exception e) {
      LOGGER.error("XA COMMIT FAILED! {}", e.getMessage(), e);
      throw new DaoException("XA COMMIT FAILED!", e);
    }
  }

  public ImmutableMap<String, SessionFactory> getSessionFactories() {
    return sessionFactories;
  }

  public void setSessionFactories(ImmutableMap<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

  public XAUnitOfWork getXaUnitOfWork() {
    return xaUnitOfWork;
  }

  public void setXaUnitOfWork(XAUnitOfWork xaUnitOfWork) {
    this.xaUnitOfWork = xaUnitOfWork;
  }

}
