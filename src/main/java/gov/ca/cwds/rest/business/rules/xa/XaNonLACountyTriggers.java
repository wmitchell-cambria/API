package gov.ca.cwds.rest.business.rules.xa;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.xa.XaCmsCountyOwnershipDao;
import gov.ca.cwds.data.cms.xa.XaCmsReferralClientDao;
import gov.ca.cwds.data.cms.xa.XaCmsReferralDao;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;

public class XaNonLACountyTriggers extends NonLACountyTriggers {

  /**
   * @param countyOwnershipDao - countyOwnershipDao
   * @param referralDao - referralDao
   * @param referralClientDao - referralClientDao
   */
  @Inject
  public XaNonLACountyTriggers(XaCmsCountyOwnershipDao countyOwnershipDao,
      XaCmsReferralDao referralDao, XaCmsReferralClientDao referralClientDao) {
    super(countyOwnershipDao, referralDao, referralClientDao);
  }

}
