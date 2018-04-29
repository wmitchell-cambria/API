package gov.ca.cwds.data.persistence.xa;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.google.common.collect.ImmutableMap;

/**
 * AOP aspect supports {@link XAUnitOfWork}.
 * 
 * @author CWDS API Team
 */
public class XAUnitOfWorkAspect {

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
   * @throws Exception on database error
   */
  public void beforeStart(XAUnitOfWork xaUnitOfWork) throws Exception {
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
   * @throws SystemException internal error
   * @throws NotSupportedException internal error
   */
  protected void beginTransaction() throws SystemException, NotSupportedException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    txn.setTransactionTimeout(80);
    txn.begin();
  }

  protected void rollbackTransaction() throws SystemException, HeuristicRollbackException,
      HeuristicMixedException, RollbackException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    txn.rollback();
  }

  /**
   * Commit XA transaction.
   * 
   * @throws SystemException internal error
   * @throws HeuristicRollbackException internal error
   * @throws HeuristicMixedException internal error
   * @throws SecurityException internal error
   * @throws IllegalStateException internal error
   * @throws RollbackException internal error
   */
  protected void commitTransaction() throws SystemException, HeuristicRollbackException,
      HeuristicMixedException, RollbackException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    txn.commit();
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
