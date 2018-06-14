package gov.ca.cwds.rest.business.rules.xa;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.xa.XaCmsCountyTriggerDaoImpl;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;

public class XaLACountyTrigger extends LACountyTrigger {

  /**
   * @param countyTriggerDao - countyTriggerDao
   */
  @Inject
  public XaLACountyTrigger(XaCmsCountyTriggerDaoImpl countyTriggerDao) {
    super(countyTriggerDao);
  }

}
