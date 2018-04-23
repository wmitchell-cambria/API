package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.DomainChef;

public interface SensitiveClientOverride extends ApiMarker {

  static boolean SENSITIVITY_OVERRIDE =
      DomainChef.uncookBooleanString(System.getProperty("OVERRIDE_CLIENT_SENSITIVITY"));

  static boolean getClientSensitivityOverride() {
    return SENSITIVITY_OVERRIDE;
  }

  default boolean developmentOnlyClientSensitivityOverride() {
    return SENSITIVITY_OVERRIDE;
  }

}
