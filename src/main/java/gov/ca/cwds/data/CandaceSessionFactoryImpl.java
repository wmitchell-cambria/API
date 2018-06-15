package gov.ca.cwds.data;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.inject.CwsRsSessionFactory;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.inject.XaCmsSessionFactory;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * Ferb session factory facade. Candace is the emotionally volatile sister of Phineas and Ferb.
 * 
 * @author CWDS API Team
 */
public class CandaceSessionFactoryImpl {

  private final SessionFactory cmsSessionFactory;
  private final SessionFactory nsSessionFactory;
  private final SessionFactory rsSessionFactory;

  private final SessionFactory xaCmsSessionFactory;
  private final SessionFactory xaNsSessionFactory;
  private SessionFactory xaRsSessionFactory;

  @Inject
  public CandaceSessionFactoryImpl(@CmsSessionFactory SessionFactory cmsSessionFactory,
      @NsSessionFactory SessionFactory nsSessionFactory,
      @CwsRsSessionFactory SessionFactory rsSessionFactory,
      @XaCmsSessionFactory SessionFactory xaCmsSessionFactory,
      @XaNsSessionFactory SessionFactory xaNsSessionFactory
  // @xaRsSessionFactory SessionFactory xaRsSessionFactory
  ) {
    super();
    this.cmsSessionFactory = cmsSessionFactory;
    this.nsSessionFactory = nsSessionFactory;
    this.rsSessionFactory = rsSessionFactory;
    this.xaCmsSessionFactory = xaCmsSessionFactory;
    this.xaNsSessionFactory = xaNsSessionFactory;
    // this.xaRsSessionFactory = xaRsSessionFactory;
  }

}
