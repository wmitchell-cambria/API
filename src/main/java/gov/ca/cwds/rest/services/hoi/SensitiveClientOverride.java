package gov.ca.cwds.rest.services.hoi;

import static gov.ca.cwds.rest.core.FerbConstants.SENSITIVITY_OVERRIDE;

import gov.ca.cwds.data.std.ApiMarker;

public interface SensitiveClientOverride extends ApiMarker {

  static boolean getClientSensitivityOverride() {
    return SENSITIVITY_OVERRIDE;
  }

  default boolean developmentOnlyClientSensitivityOverride() {
    return SENSITIVITY_OVERRIDE;
  }

}
