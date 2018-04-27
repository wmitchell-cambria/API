package gov.ca.cwds.data.persistence.xa;

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

public class XAUnitOfWorkAspect {

  // Context variables
  private XAUnitOfWork xaUnitOfWork;
  private Session session;
  private SessionFactory sessionFactory;

  private SessionFactory[] sessionFactories;
  private Session[] sessions;

  private final UserTransaction txn = new UserTransactionImp();

  public XAUnitOfWorkAspect() {
    // Default, no-op
  }

  public XAUnitOfWorkAspect(SessionFactory... sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

  public Session grabSession(SessionFactory sessionFactory) {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    return session;
  }

  protected void openSession() {

  }

  protected void closeSession() {

  }

  protected void configureSession() {
    session.setDefaultReadOnly(xaUnitOfWork.readOnly());
    session.setCacheMode(xaUnitOfWork.cacheMode());
    session.setHibernateFlushMode(xaUnitOfWork.flushMode());
  }

  public void beforeStart(XAUnitOfWork xaUnitOfWork) throws Exception {
    if (xaUnitOfWork == null) {
      return;
    }
    this.xaUnitOfWork = xaUnitOfWork;

    beginTransaction();

    // session = sessionFactory.openSession();
    // try {
    // configureSession();
    // ManagedSessionContext.bind(session);
    // beginTransaction();
    // } catch (Throwable th) {
    // session.close();
    // session = null;
    // ManagedSessionContext.unbind(sessionFactory);
    // throw th;
    // }
  }

  public void afterEnd() throws Exception {
    // if (session == null) {
    // return;
    // }

    try {
      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw e;
    }

    // onFinish() closes the session.
  }

  public void onError() throws Exception {
    // if (session == null) {
    // return;
    // }

    try {
      rollbackTransaction();
    } finally {
      onFinish();
    }
  }

  public void onFinish() throws Exception {
    // try {
    // if (session != null) {
    // session.close();
    // }
    // } finally {
    // session = null;
    // ManagedSessionContext.unbind(sessionFactory);
    // }
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

    // final Transaction txn = session.getTransaction();
    // if (txn != null && txn.getStatus().canRollback()) {
    // txn.rollback();
    // }

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

  public SessionFactory[] getSessionFactories() {
    return sessionFactories;
  }

  public void setSessionFactories(SessionFactory[] sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

}
