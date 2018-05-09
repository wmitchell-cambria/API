package gov.ca.cwds.rest.core;

import gov.ca.cwds.rest.api.domain.DomainChef;

public class FerbConstants {

  public static final boolean SENSITIVITY_OVERRIDE =
      DomainChef.uncookBooleanString(System.getProperty("OVERRIDE_CLIENT_SENSITIVITY"));

  private FerbConstants() {
    // no-op
  }

}
