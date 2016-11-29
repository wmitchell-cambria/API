package gov.ca.cwds.rest.jdbi;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.ns.Person;

/**
 * Base class for DAO with some common methods
 * 
 * @author CWDS API Team
 */
public abstract class BaseDaoImpl<T extends PersistentObject> extends CrudsDaoImpl<T>
    implements BaseDao<T> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  public BaseDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.jdbi.BaseDao#findAll()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAll() {
    String namedQueryName = constructNamedQueryName("findAll");
    Session session = getSessionFactory().getCurrentSession();


    Transaction txn = null;
    try {
      txn = session.beginTransaction();
      Query query = session.getNamedQuery(namedQueryName);
      ImmutableList.Builder<Person> persons = new ImmutableList.Builder<Person>();
      persons.addAll(query.list());
      txn.commit();
      return (List<T>) persons.build();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    } finally {
      session.close();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.jdbi.BaseDao#findAllUpdatedAfter(java.util.Date)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAllUpdatedAfter(Date datetime) {
    String namedQueryName = constructNamedQueryName("findAllUpdatedAfter");
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = null;
    try {
      txn = session.beginTransaction();
      Query query = session.getNamedQuery(namedQueryName).setDate("after", datetime);
      ImmutableList.Builder<Person> persons = new ImmutableList.Builder<Person>();
      persons.addAll(query.list());
      txn.commit();
      return (List<T>) persons.build();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    } finally {
      session.close();
    }
  }

  private String constructNamedQueryName(String suffix) {
    return getEntityClass().getName() + "." + suffix;
  }
}
