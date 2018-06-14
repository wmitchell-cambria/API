package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.CsecEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * DAO for Postgres CSEC, acronym for Commercial Sexual Exploitation of Children. Disgusting.
 * 
 * @author CWDS API Team
 */
public class CsecDao extends BaseDaoImpl<CsecEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public CsecDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
