package gov.ca.cwds.rest.core;

import gov.ca.cwds.rest.api.domain.DomainChef;

public class FerbConstants {

  public static final boolean SENSITIVITY_OVERRIDE =
      DomainChef.uncookBooleanString(System.getProperty("OVERRIDE_CLIENT_SENSITIVITY"));

  public class ReportType {
    private ReportType() {
      // no-op
    }

    public static final String SSB = "ssb";
    public static final String CSEC = "csec";
  }

  private FerbConstants() {
    // no-op
  }

}
