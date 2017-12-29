package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.NotNull;

/**
 * CWDS API Team
 */
public enum AgeUnit {
  D(1), W(7), M(30.5f), Y(365);

  private float days;

  AgeUnit(float days) {
    this.days = days;
  }

  private float getDays() {
    return days;
  }

  public static int convertTo(int ageNumber,
      @NotNull(message = "null Age Unit is not supported")
      AgeUnit from,
      @NotNull(message = "null Age Unit is not supported")
      AgeUnit to) {
    return Math.round(ageNumber * from.getDays() / to.getDays());
  }
}
