package gov.ca.cwds.rest.services.cms.xa;

import javax.validation.Validator;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.xa.XaCmsAddressDao;
import gov.ca.cwds.data.cms.xa.XaCmsSsaName3Dao;
import gov.ca.cwds.rest.business.rules.xa.XaUpperCaseTables;
import gov.ca.cwds.rest.services.cms.AddressService;

public class XaCmsAddressService extends AddressService {

  @Inject
  public XaCmsAddressService(XaCmsAddressDao addressDao, XaCmsSsaName3Dao ssaname3Dao,
      XaUpperCaseTables upperCaseTables, Validator validator) {
    super(addressDao, ssaname3Dao, upperCaseTables, validator);
  }

}
