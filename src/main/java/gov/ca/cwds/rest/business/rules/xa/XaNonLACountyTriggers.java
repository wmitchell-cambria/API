package gov.ca.cwds.rest.business.rules.xa;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.xa.XaCmsCountyOwnershipDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsReferralClientDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsReferralDaoImpl;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;

public class XaNonLACountyTriggers extends NonLACountyTriggers {

  /**
   * @param countyOwnershipDao - countyOwnershipDao
   * @param referralDao - referralDao
   * @param referralClientDao - referralClientDao
   */
  @Inject
  public XaNonLACountyTriggers(XaCmsCountyOwnershipDaoImpl countyOwnershipDao,
      XaCmsReferralDaoImpl referralDao, XaCmsReferralClientDaoImpl referralClientDao) {
    super(countyOwnershipDao, referralDao, referralClientDao);
  }

}
