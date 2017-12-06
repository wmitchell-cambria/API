package gov.ca.cwds.rest.business.rules;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyTriggerDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.CountyTrigger;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Business layer object to work on LA County Trigger
 * 
 * <p>
 * If the staffPerson is from the LA County, it will trigger the countyTrigger table with the
 * associated foreign key and the table will be cleared after the batch jobs run.
 * <p>
 * 
 * @author CWDS API Team
 */
public class LACountyTrigger {

  private static final String LA_COUNTY_SPECIFIC_CODE = "19";

  private static final String CLIENT_COUNTYOWNERSHIP = "C";

  private static final String ADDRESS_COUNTYOWNERSHIP = "A";

  private static final Logger LOGGER = LoggerFactory.getLogger(LACountyTrigger.class);

  private CountyTriggerDao countyTriggerDao;

  /**
   * @param countyTriggerDao - countyTriggerDao
   */
  @Inject
  public LACountyTrigger(CountyTriggerDao countyTriggerDao) {
    this.countyTriggerDao = countyTriggerDao;
  }

  /**
   * @param object The object creates the county Trigger for the Referral and referralClient
   * @return the countyTrigger created
   */
  public boolean createCountyTrigger(Object object) {
    Referral referral;
    ReferralClient referralClient;
    if (object instanceof Referral) {
      referral = (Referral) object;

      if (StringUtils.isNotBlank(referral.getAllegesAbuseOccurredAtAddressId())) {
        CountyTrigger countyTrigger = new CountyTrigger(referral.getCountySpecificCode(),
            ADDRESS_COUNTYOWNERSHIP, referral.getAllegesAbuseOccurredAtAddressId(),
            LegacyTable.REFERRAL.getName(), new Date());

        countyTriggerDao.create(countyTrigger);
        LOGGER.info("LA county referral address triggered");
      }
    }

    if (object instanceof ReferralClient) {
      referralClient = (ReferralClient) object;

      if (StringUtils.isNotBlank(referralClient.getClientId())) {
        CountyTrigger countyTrigger =
            new CountyTrigger(referralClient.getCountySpecificCode(), CLIENT_COUNTYOWNERSHIP,
                referralClient.getClientId(), LegacyTable.REFERRAL_CLIENT.getName(), new Date());

        countyTriggerDao.create(countyTrigger);
        LOGGER.info("LA county referralClient triggered");
      }
    }

    return true;
  }

  /**
   * @param object The object creates the county Trigger for the clientAddress
   * @return the CountyTrigger created
   */
  public boolean createClientAddressCountyTrigger(Object object) {
    ClientAddress clientAddress;

    if (object instanceof ClientAddress) {
      clientAddress = (ClientAddress) object;

      if (StringUtils.isNotBlank(clientAddress.getFkClient())
          && StringUtils.isNotBlank(clientAddress.getFkAddress())) {
        CountyTrigger countyTrigger1 =
            new CountyTrigger(LA_COUNTY_SPECIFIC_CODE, CLIENT_COUNTYOWNERSHIP,
                clientAddress.getFkClient(), LegacyTable.CLIENT_ADDRESS.getName(), new Date());

        CountyTrigger countyTrigger2 =
            new CountyTrigger(LA_COUNTY_SPECIFIC_CODE, ADDRESS_COUNTYOWNERSHIP,
                clientAddress.getFkAddress(), LegacyTable.CLIENT_ADDRESS.getName(), new Date());

        countyTriggerDao.create(countyTrigger1);
        countyTriggerDao.create(countyTrigger2);
        LOGGER.info("LA county clientAddress triggered");
      }
    }
    return true;
  }

}
