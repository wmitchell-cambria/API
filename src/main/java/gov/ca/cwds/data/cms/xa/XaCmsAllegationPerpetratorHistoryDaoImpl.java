package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsAllegationPerpetratorHistoryDaoImpl extends AllegationPerpetratorHistoryDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsAllegationPerpetratorHistoryDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
