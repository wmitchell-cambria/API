package gov.ca.cwds.rest.services.submit;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;

/**
 * Business layer object to transform an NS {@link AddressIntakeApi } to an {@link Address}
 * 
 * @author CWDS API Team
 */
public class AddressTransformer {

  /**
   * @param addressIntake - addressIntake
   * @return the {@link Address}
   */
  public Address transform(AddressIntakeApi addressIntake) {

    Integer state = StringUtils.isNotBlank(addressIntake.getState()) ? IntakeCodeCache.global()
        .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.STATE_CODE, addressIntake.getState())
        .intValue() : null;
    Integer addressType =
        StringUtils.isNotBlank(addressIntake.getType()) ? Integer.valueOf(addressIntake.getType())
            : null;
    return new Address(addressIntake.getLegacySourceTable(), addressIntake.getLegacyId(),
        addressIntake.getStreetAddress(), addressIntake.getCity(), state, addressIntake.getZip(),
        addressType, addressIntake.getLegacyDescriptor());
  }

}
