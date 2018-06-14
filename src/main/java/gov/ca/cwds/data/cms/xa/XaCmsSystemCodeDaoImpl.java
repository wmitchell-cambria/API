package gov.ca.cwds.data.cms.xa;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.persistence.cms.SystemCode;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsSystemCodeDaoImpl extends SystemCodeDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaCmsSystemCodeDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param foreignKeyMetaTable meta group
   * @return all keys by meta table
   */
  @Override
  @SuppressWarnings("unchecked")
  public SystemCode[] findByForeignKeyMetaTable(String foreignKeyMetaTable) {
    final String namedQueryName = SystemCode.class.getName() + ".findByForeignKeyMetaTable";

    // DRS: Don't interfere with transaction management, like XA. Play nice on the playground.
    final Session session = grabSession();

    try {
      final Query query = session.getNamedQuery(namedQueryName).setString("foreignKeyMetaTable",
          foreignKeyMetaTable);
      final SystemCode[] systemCodes = (SystemCode[]) query.list().toArray(new SystemCode[0]);

      return systemCodes;
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public SystemCode findBySystemCodeId(Number systemCodeId) {
    final String namedQueryName = SystemCode.class.getName() + ".findBySystemCodeId";

    // DRS: Don't interfere with transaction management, like XA. Play nice on the playground.
    final Session session = grabSession();

    try {
      final Query query =
          session.getNamedQuery(namedQueryName).setShort("systemId", systemCodeId.shortValue());
      final SystemCode systemCode = (SystemCode) query.getSingleResult();

      return systemCode;
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

}
