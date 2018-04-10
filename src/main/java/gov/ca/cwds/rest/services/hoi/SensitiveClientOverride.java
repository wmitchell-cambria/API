package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.DomainChef;

public interface SensitiveClientOverride extends ApiMarker {

  static boolean sensitivityOverride =
      DomainChef.uncookBooleanString(System.getProperty("OVERRIDE_CLIENT_SENSITIVITY"));

  static boolean getClientSensitivityOverride() {
    return sensitivityOverride;
  }

  default boolean developmentOnlyClientSensitivityOverride() {
    return sensitivityOverride;
  }

}
