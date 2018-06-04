package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaCmsRelationshipDaoImpl extends RelationshipDao {

  @Inject
  public XaCmsRelationshipDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
