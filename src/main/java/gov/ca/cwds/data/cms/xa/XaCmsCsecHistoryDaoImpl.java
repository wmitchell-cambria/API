package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCsecHistoryDaoImpl extends CsecHistoryDao {

  @Inject
  public XaCmsCsecHistoryDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
