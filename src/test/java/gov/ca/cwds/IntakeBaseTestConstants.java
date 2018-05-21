package gov.ca.cwds;

public final class IntakeBaseTestConstants {

  private IntakeBaseTestConstants() {
    // no-op
  }

  public static final String USER_SOCIAL_WORKER_ONLY =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-social-worker-only.json";
  public static final String USER_COUNTY_SENSITIVE =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-county-sensitive.json";
  public static final String USER_COUNTY_SEALED =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-county-sealed.json";
  public static final String USER_STATE_SENSITIVE =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-state-sensitive.json";
  public static final String USER_STATE_SEALED =
      "fixtures/gov/ca/cwds/rest/resources/hoi/user-state-sealed.json";

  public static final String CLIENT_SAME_COUNTY_SENSITIVE_ID_1 = "1S3k0iv00T";
  public static final String CLIENT_SAME_COUNTY_SENSITIVE_ID_2 = "PQ3s1OD01t";
  public static final String CLIENT_SAME_COUNTY_SENSITIVE_ID_3 = "AbA4BJy0Aq";

  public static final String CLIENT_SAME_COUNTY_SEALED_ID_1 = "4kgIiDy00T";
  public static final String CLIENT_SAME_COUNTY_SEALED_ID_2 = "Ba29OOP75A";
  public static final String CLIENT_SAME_COUNTY_SEALED_ID_3 = "Abxl9D005Y";

  public static final String CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID = "SZdBGYk75C";

  public static final String CLIENT_DIFFERENT_COUNTY_SEALED_ID = "4jCKVgx0GE";

  public static final String CLIENT_NO_COUNTY_SENSITIVE_ID = "F187hFj00E";

  public static final String CLIENT_NO_COUNTY_SEALED_ID = "SIfUah90GD";

  public static final String CLIENT_NO_CONDITIONS_ID = "AhGPhcm0T1";
}
