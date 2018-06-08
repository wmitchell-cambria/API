package gov.ca.cwds.data.persistence.xa;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomikos.icatch.jta.UserTransactionImp;

/**
 * AOP aspect supports annotation {@link XAUnitOfWork}.
 * 
 * <p>
 * In AOP terms, this wrapper method follows the <strong>"around"</strong> protocol. Start with
 * {@link XAUnitOfWorkAspect#beforeStart(XAUnitOfWork)}, call the annotated method, and finish with
 * {@link XAUnitOfWorkAspect#afterEnd()}.
 * </p>
 * 
 * <p>
 * {@link XAUnitOfWork} annotations may be nested. This aspect automatically adds nested
 * {@link XAUnitOfWork} to the XA transaction and opens sessions for datasources not already
 * included.
 * </p>
 *
 * @author CWDS API Team
 */
public class XAUnitOfWorkAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(XAUnitOfWorkAspect.class);

  private final UserTransaction txn = new UserTransactionImp();

  private final Map<String, SessionFactory> sessionFactories = new ConcurrentHashMap<>();

  private final Map<String, Session> sessions = new ConcurrentHashMap<>();

  private final Map<String, XAUnitOfWork> units = new ConcurrentHashMap<>();

  private XAUnitOfWork xaUnitOfWork;

  /**
   * Preferred constructor.
   * 
   * @param sessionFactories - all datasources to participate in the XA transaction
   */
  public XAUnitOfWorkAspect(Map<String, SessionFactory> sessionFactories) {
    this.sessionFactories.putAll(sessionFactories);
  }

  /**
   * Aspect entry point.
   * 
   * @param xaUnitOfWork take settings from annotation
   * @throws CaresXAException on database error
   */
  public void beforeStart(Method method, XAUnitOfWork xaUnitOfWork) throws CaresXAException {
    if (xaUnitOfWork == null) {
      LOGGER.error("XA beforeStart: no annotation");
      return;
    }
    this.xaUnitOfWork = xaUnitOfWork;

    openSessions();
    beginTransaction();
  }

  /**
   * Commit or rollback.
   * <p>
   * NOTE: method {@link #onFinish()} closes the session.
   * </p>
   * 
   * @throws CaresXAException on database error
   */
  public void afterEnd() throws CaresXAException {
    if (sessions.isEmpty()) {
      LOGGER.warn("XA afterEnd: no sessions");
      return;
    }

    try {
      LOGGER.debug("XA afterEnd: commit");
      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw e;
    }
  }

  /**
   * Add a nested {@link XAUnitOfWork} to the transaction and open sessions for datasources not
   * already included.
   * 
   * @param xaUnitOfWork annotation
   * @throws CaresXAException on database error
   */
  public void joinTransaction(XAUnitOfWork xaUnitOfWork) throws CaresXAException {
    // TODO1: Open sessions for any datasources not already opened.
  }

  /**
   * Call on error to rollback transactions and close sessions.
   * 
   * @throws CaresXAException on database error
   */
  public void onError() throws CaresXAException {
    if (sessions.isEmpty()) {
      LOGGER.warn("XA onError: no sessions");
      return;
    }

    LOGGER.warn("XA onError: rollback");
    try {
      rollbackTransaction();
    } finally {
      // nix
    }
  }

  /**
   * Close open sessions, set transaction to null.
   */
  public void onFinish() {
    closeSessions();
  }

  /**
   * Get the current Hibernate session, if open, or open a new session.
   * 
   * <p>
   * For DB2 sessions, this method calls {@link WorkDB2UserInfo} to populate user information fields
   * on the JDBC connection.
   * </p>
   * 
   * @param key datasource name
   * @param sessionFactory - open a session for this datasource
   * @return session current session for this datasource
   */
  protected Session grabSession(String key, SessionFactory sessionFactory) {
    Session session;
    if (sessions.containsKey(key)) {
      session = sessions.get(key);
    } else {
      LOGGER.trace("XA grabSession()!");
      try {
        session = sessionFactory.getCurrentSession();
      } catch (HibernateException e) {
        LOGGER.trace("No current session. Open a new one. {}", e.getMessage(), e);
        session = sessionFactory.openSession();
      }

      configureSession(session);
      sessions.put(key, session);

      // Add user info to DB2 connections. Harmless for other connections.
      session.doWork(new WorkDB2UserInfo());
    }

    return session;
  }

  protected boolean hasTransactionalAnnotation() {
    return this.xaUnitOfWork.transactional();
  }

  /**
   * Open sessions for selected datasources.
   */
  protected void openSessions() {
    LOGGER.debug("XA OPEN SESSIONS!");
    sessionFactories.entrySet().stream().forEach(e -> grabSession(e.getKey(), e.getValue()));
  }

  /**
   * Close all sessions.
   */
  protected void closeSessions() {
    LOGGER.debug("XA CLOSE SESSIONS!");
    sessions.values().stream().forEach(this::closeSession);
  }

  protected void closeSession(Session session) {
    if (session != null && (session.isOpen() || session.isDirty())) {
      LOGGER.debug("XA CLOSE SESSION");
      try {
        final int status = txn.getStatus();
        if (status != Status.STATUS_NO_TRANSACTION && status != Status.STATUS_UNKNOWN) {
          session.flush();
        }
      } catch (Exception e) {
        LOGGER.warn("Flush session failed! {}", e.getMessage(), e);
      }

      session.close();
    }
  }

  /**
   * Set cache mode, flush mode, and read-only properties on a Hibernate session.
   * 
   * <p>
   * Read-only operations run faster when flush mode is set to manual.
   * </p>
   * 
   * @param session - target Hibernate session
   */
  protected void configureSession(Session session) {
    session.setDefaultReadOnly(xaUnitOfWork.readOnly());
    session.setCacheMode(xaUnitOfWork.cacheMode());
    session.setHibernateFlushMode(
        xaUnitOfWork.readOnly() ? FlushMode.MANUAL : xaUnitOfWork.flushMode());
  }

  /**
   * Start XA transaction. Set timeout to 80 seconds.
   * 
   * @throws CaresXAException on database error
   */
  protected void beginTransaction() throws CaresXAException {
    if (!hasTransactionalAnnotation()) {
      LOGGER.trace("XA BEGIN TRANSACTION: unit of work is not transactional");
      return;
    }

    try {
      LOGGER.debug("XA BEGIN TRANSACTION!");
      txn.setTransactionTimeout(80); // NEXT: soft-code timeout
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
    if (!hasTransactionalAnnotation()) {
      LOGGER.trace("XA ROLLBACK TRANSACTION: unit of work not transactional");
      return;
    }

    try {
      LOGGER.debug("XA ROLLBACK TRANSACTION!");
      txn.rollback();
    } catch (Exception e) {
      LOGGER.error("XA ROLLBACK FAILED! {}", e.getMessage(), e);
      throw new CaresXAException("XA ROLLBACK FAILED!", e);
    }
  }

  /**
   * Commit XA transaction.
   * 
   * @throws CaresXAException on database error
   */
  protected void commitTransaction() throws CaresXAException {
    if (!xaUnitOfWork.transactional()) {
      LOGGER.debug("XA COMMIT TRANSACTION: unit of work not transactional");
      return;
    }

    try {
      LOGGER.debug("XA COMMIT TRANSACTION!");
      txn.commit();
    } catch (Exception e) {
      LOGGER.error("XA COMMIT FAILED! {}", e.getMessage(), e);
      throw new CaresXAException("XA COMMIT FAILED!", e);
    }
  }

  public Map<String, SessionFactory> getSessionFactories() {
    return sessionFactories;
  }

  public XAUnitOfWork getXaUnitOfWork() {
    return xaUnitOfWork;
  }

  public void setXaUnitOfWork(XAUnitOfWork xaUnitOfWork) {
    this.xaUnitOfWork = xaUnitOfWork;
  }

}
