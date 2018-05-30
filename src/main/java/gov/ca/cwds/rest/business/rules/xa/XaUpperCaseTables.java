package gov.ca.cwds.rest.business.rules.xa;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AddressUcDao;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;

public class XaUpperCaseTables extends UpperCaseTables {

  /**
   * @param clientUcDao - client upper case
   * @param addressUcDao - address upper case
   */
  @Inject
  public XaUpperCaseTables(ClientUcDao clientUcDao, AddressUcDao addressUcDao) {
    super(clientUcDao, addressUcDao);
  }

}
