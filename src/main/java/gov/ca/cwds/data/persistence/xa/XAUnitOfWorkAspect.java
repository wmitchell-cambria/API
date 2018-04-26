package gov.ca.cwds.data.persistence.xa;

import javax.transaction.UserTransaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class XAUnitOfWorkAspect {

  public XAUnitOfWorkAspect() {}

  // Context variables
  private XAUnitOfWork xaUnitOfWork;
  private Session session;
  private SessionFactory sessionFactory;

  private UserTransaction txn;

  public Session grabSession(SessionFactory sessionFactory) {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    return session;
  }

  public Transaction joinTransaction(Session session) {
    Transaction txn = session.getTransaction();
    txn = txn != null ? txn : session.beginTransaction();

    if (TransactionStatus.NOT_ACTIVE == txn.getStatus() || !txn.isActive()) {
      txn.begin();
    }

    return txn;
  }

  public void beforeStart(XAUnitOfWork xaUnitOfWork) {
    if (xaUnitOfWork == null) {
      return;
    }
    this.xaUnitOfWork = xaUnitOfWork;


    session = sessionFactory.openSession();
    try {
      configureSession();
      ManagedSessionContext.bind(session);
      beginTransaction();
    } catch (Throwable th) {
      session.close();
      session = null;
      ManagedSessionContext.unbind(sessionFactory);
      throw th;
    }

  }

  public void afterEnd() {
    if (session == null) {
      return;
    }

    try {
      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw e;
    }
    // We should not close the session to let the lazy loading work during serializing a response to
    // the client.
    // If the response successfully serialized, then the session will be closed by the `onFinish`
    // method
  }

  public void onError() {
    if (session == null) {
      return;
    }

    try {
      rollbackTransaction();
    } finally {
      onFinish();
    }
  }

  public void onFinish() {
    try {
      if (session != null) {
        session.close();
      }
    } finally {
      session = null;
      ManagedSessionContext.unbind(sessionFactory);
    }
  }

  protected void configureSession() {
    session.setDefaultReadOnly(xaUnitOfWork.readOnly());
    session.setCacheMode(xaUnitOfWork.cacheMode());
    session.setHibernateFlushMode(xaUnitOfWork.flushMode());
  }

  protected void beginTransaction() {
    if (!xaUnitOfWork.transactional()) {
      return;
    }
    session.beginTransaction();
  }

  protected void rollbackTransaction() {
    if (!xaUnitOfWork.transactional()) {
      return;
    }
    final Transaction txn = session.getTransaction();
    if (txn != null && txn.getStatus().canRollback()) {
      txn.rollback();
    }
  }

  protected void commitTransaction() {
    if (!xaUnitOfWork.transactional()) {
      return;
    }
    final Transaction txn = session.getTransaction();
    if (txn != null && txn.getStatus().canRollback()) {
      txn.commit();
    }
  }

}
