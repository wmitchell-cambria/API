package gov.ca.cwds.data.cms.xa;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.persistence.cms.SystemMeta;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsSystemMetaDaoImpl extends SystemMetaDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaCmsSystemMetaDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Play nice with XA transactions. Standard playground rules.
   * 
   * @return all meta data records
   */
  @Override
  @SuppressWarnings("unchecked")
  public SystemMeta[] findAll() {
    final String namedQueryName = SystemMeta.class.getName() + ".findAll";
    final Session session = grabSession();

    try {
      final Query query = session.getNamedQuery(namedQueryName);
      final SystemMeta[] systemMetas = (SystemMeta[]) query.list().toArray(new SystemMeta[0]);
      return systemMetas;
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

}
