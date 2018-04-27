package gov.ca.cwds.data.persistence.xa;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.google.common.collect.ImmutableMap;

public class XAUnitOfWorkAspect {

  private XAUnitOfWork xaUnitOfWork;

  private ImmutableMap<String, SessionFactory> sessionFactories;

  private List<Session> sessions = new ArrayList<>();

  private final UserTransaction txn = new UserTransactionImp();

  public XAUnitOfWorkAspect(ImmutableMap<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

  public Session grabSession(SessionFactory sessionFactory) {
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

  protected void openSessions() {
    sessionFactories.values().stream().forEach(this::grabSession);
  }

  protected void closeSessions() {
    sessions.stream().forEach(this::closeSession);
  }

  protected void closeSession(Session session) {
    if (session != null) {
      session.close();
    }
  }

  protected void configureSession(Session session) {
    session.setDefaultReadOnly(xaUnitOfWork.readOnly());
    session.setCacheMode(xaUnitOfWork.cacheMode());
    session.setHibernateFlushMode(xaUnitOfWork.flushMode());
  }

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

  public void onFinish() throws Exception {
    closeSessions();
  }

  protected void beginTransaction() throws SystemException, NotSupportedException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    // Start XA transaction.
    txn.setTransactionTimeout(80);
    txn.begin();
  }

  protected void rollbackTransaction()
      throws SystemException, NotSupportedException, HeuristicRollbackException,
      HeuristicMixedException, SecurityException, IllegalStateException, RollbackException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    txn.rollback();
  }

  protected void commitTransaction()
      throws SystemException, NotSupportedException, HeuristicRollbackException,
      HeuristicMixedException, SecurityException, IllegalStateException, RollbackException {
    if (!xaUnitOfWork.transactional()) {
      return;
    }

    // Commit XA transaction.
    txn.commit();
  }

  public ImmutableMap<String, SessionFactory> getSessionFactories() {
    return sessionFactories;
  }

  public void setSessionFactories(ImmutableMap<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

}
