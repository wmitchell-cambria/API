package gov.ca.cwds.rest.services.submit;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;

/**
 * Business layer object to transform an NS {@link AddressIntakeApi } to an {@link Address}
 * 
 * @author CWDS API Team
 */
public class TransformAddress {

  public Address transform(AddressIntakeApi addressIntake,
      Map<String, IntakeLov> nsCodeToNsLovMap) {
    Integer state = StringUtils.isNotBlank(addressIntake.getState())
        ? nsCodeToNsLovMap.get(addressIntake.getState()).getLegacySystemCodeId().intValue()
        : null;
    Integer type =
        StringUtils.isNotBlank(addressIntake.getType()) ? Integer.parseInt(addressIntake.getType())
            : null;
    return new Address(addressIntake.getLegacySourceTable(), addressIntake.getLegacyId(),
        addressIntake.getStreetAddress(), addressIntake.getCity(), state, addressIntake.getZip(),
        type, addressIntake.getLegacyDescriptor());

  }

}
