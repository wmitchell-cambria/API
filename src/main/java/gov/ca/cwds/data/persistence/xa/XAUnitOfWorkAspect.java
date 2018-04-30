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

import gov.ca.cwds.rest.services.ServiceException;

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
   * @throws CaresXAException on database error
   */
  public void beforeStart(XAUnitOfWork xaUnitOfWork) throws CaresXAException {
    if (xaUnitOfWork == null) {
      return;
    }
    this.xaUnitOfWork = xaUnitOfWork;

    openSessions();
    beginTransaction();
  }

  /**
   * Commit or rollback.
   * <p>
   * NOTE: method onFinish() closes the session.
   * </p>
   * 
   * @throws CaresXAException on database error
   */
  public void afterEnd() throws CaresXAException {
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
   * Call on error to rollback transactions and close sessions.
   * 
   * @throws CaresXAException on database error
   */
  public void onError() throws CaresXAException {
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
   * Close open sessions, set transaction to null.
   */
  public void onFinish() {
    txn = null;
    closeSessions();
  }

  /**
   * Get the current Hibernate session, if open, or open a new session.
   * 
   * @param sessionFactory - open a session for this datasource
   * @return session current session for this datasource
   */
  protected Session grabSession(SessionFactory sessionFactory) {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      LOGGER.warn("No current session. Open a new one. {}", e.getCause(), e);
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
   * 
   * @throws CaresXAException on database error
   */
  protected void beginTransaction() throws CaresXAException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    try {
      txn.setTransactionTimeout(80);
      txn.begin();
    } catch (Exception e) {
      LOGGER.error("XA BEGIN FAILED! {}", e.getMessage(), e);
      throw new CaresXAException("XA BEGIN FAILED!", e);
    }
  }

  /**
   * Roll back XA transaction.
   * 
   * @throws CaresXAException on database error
   */
  protected void rollbackTransaction() throws CaresXAException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    try {
      txn.rollback();
    } catch (Exception e) {
      LOGGER.error("XA ROLLBACK FAILED! {}", e.getMessage(), e);
      throw new CaresXAException("XA ROLLBACK FAILED!", e);
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
      throw new ServiceException("XA COMMIT FAILED!", e);
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
