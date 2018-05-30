package gov.ca.cwds.data.persistence.xa;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.UserTransaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.google.common.collect.ImmutableMap;

/**
 * AOP aspect supports annotation {@link XAUnitOfWork}.
 * 
 * <p>
 * In AOP terms, this wrapper method follows the <strong>"around"</strong> protocol. Start with
 * {@link XAUnitOfWorkAspect#beforeStart(XAUnitOfWork)}, call the annotated method, and finish with
 * {@link XAUnitOfWorkAspect#afterEnd()}.
 * </p>
 *
 * @author CWDS API Team
 */
public class XAUnitOfWorkAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(XAUnitOfWorkAspect.class);

  private final UserTransaction txn = new UserTransactionImp();

  private final ImmutableMap<String, SessionFactory> sessionFactories;

  private final List<Session> sessions = new ArrayList<>();

  private XAUnitOfWork xaUnitOfWork;

  /**
   * Preferred constructor.
   * 
   * @param sessionFactories - all datasources to participate in the XA transaction
   */
  public XAUnitOfWorkAspect(ImmutableMap<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

  /**
   * Aspect entry point.
   * 
   * @param xaUnitOfWork take settings from annotation
   * @throws CaresXAException on database error
   */
  public void beforeStart(XAUnitOfWork xaUnitOfWork) throws CaresXAException {
    if (xaUnitOfWork == null) {
      LOGGER.error("XA beforeStart(): no annotation");
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
      LOGGER.warn("XA afterEnd(): no sessions");
      return;
    }

    try {
      LOGGER.debug("XA afterEnd(): commit");
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
      LOGGER.warn("XA onError(): no sessions");
      return;
    }

    LOGGER.warn("XA onError(): rollback");
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
   * @param sessionFactory - open a session for this datasource
   * @return session current session for this datasource
   */
  protected Session grabSession(SessionFactory sessionFactory) {
    LOGGER.trace("XA grabSession()!");
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      LOGGER.trace("No current session. Open a new one. {}", e.getMessage(), e);
      session = sessionFactory.openSession();
    }

    configureSession(session);
    sessions.add(session);

    // Add user info to DB2 connections.
    session.doWork(new WorkDB2UserInfo());
    return session;
  }

  /**
   * Open sessions for selected datasources.
   */
  protected void openSessions() {
    LOGGER.debug("XA OPEN SESSIONS!");
    sessionFactories.values().stream().forEach(this::grabSession);
  }

  /**
   * Close all sessions.
   */
  protected void closeSessions() {
    LOGGER.debug("XA CLOSE SESSIONS!");
    sessions.stream().forEach(this::closeSession);
  }

  protected void closeSession(Session session) {
    if (session != null) {
      LOGGER.debug("XA CLOSE SESSION");
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
      LOGGER.debug("XA BEGIN TRANSACTION: unit of work is not transactional");
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
    if (!xaUnitOfWork.transactional()) {
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

  public ImmutableMap<String, SessionFactory> getSessionFactories() {
    return sessionFactories;
  }

  public XAUnitOfWork getXaUnitOfWork() {
    return xaUnitOfWork;
  }

  public void setXaUnitOfWork(XAUnitOfWork xaUnitOfWork) {
    this.xaUnitOfWork = xaUnitOfWork;
  }

}
