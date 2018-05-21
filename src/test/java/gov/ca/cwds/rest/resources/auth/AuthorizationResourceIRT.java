package gov.ca.cwds.rest.resources.auth;

import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_DIFFERENT_COUNTY_SEALED_ID;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_NO_CONDITIONS_ID;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_NO_COUNTY_SEALED_ID;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_NO_COUNTY_SENSITIVE_ID;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_SAME_COUNTY_SEALED_ID_1;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_SAME_COUNTY_SEALED_ID_2;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_SAME_COUNTY_SEALED_ID_3;
import static gov.ca.cwds.IntakeBaseTestConstants.CLIENT_SAME_COUNTY_SENSITIVE_ID_1;
import static gov.ca.cwds.IntakeBaseTestConstants.USER_COUNTY_SEALED;
import static gov.ca.cwds.IntakeBaseTestConstants.USER_COUNTY_SENSITIVE;
import static gov.ca.cwds.IntakeBaseTestConstants.USER_SOCIAL_WORKER_ONLY;
import static gov.ca.cwds.IntakeBaseTestConstants.USER_STATE_SEALED;
import static gov.ca.cwds.IntakeBaseTestConstants.USER_STATE_SENSITIVE;
import static gov.ca.cwds.rest.core.Api.RESOURCE_AUTHORIZE;
import static org.junit.Assert.assertEquals;

import gov.ca.cwds.IntakeBaseTest;
import java.io.IOException;
import org.junit.Test;

public class AuthorizationResourceIRT extends IntakeBaseTest {

  @Test
  public void testAuthorizeForClientNoConditions() throws IOException {
    assertStatus(USER_SOCIAL_WORKER_ONLY, CLIENT_NO_CONDITIONS_ID, 200);
    assertStatus(USER_COUNTY_SENSITIVE, CLIENT_NO_CONDITIONS_ID, 200);
    assertStatus(USER_COUNTY_SEALED, CLIENT_NO_CONDITIONS_ID, 200);
    assertStatus(USER_STATE_SENSITIVE, CLIENT_NO_CONDITIONS_ID, 200);
    assertStatus(USER_STATE_SEALED, CLIENT_NO_CONDITIONS_ID, 200);
  }

  @Test
  public void testAuthorizeForClientSameCountySensitive() throws IOException {
    assertStatus(USER_SOCIAL_WORKER_ONLY, CLIENT_SAME_COUNTY_SENSITIVE_ID_1, 403);
    assertStatus(USER_COUNTY_SENSITIVE, CLIENT_SAME_COUNTY_SENSITIVE_ID_1, 200);
    assertStatus(USER_COUNTY_SEALED, CLIENT_SAME_COUNTY_SENSITIVE_ID_1, 403);
    assertStatus(USER_STATE_SENSITIVE, CLIENT_SAME_COUNTY_SENSITIVE_ID_1, 200);
    assertStatus(USER_STATE_SEALED, CLIENT_SAME_COUNTY_SENSITIVE_ID_1, 403);
  }

  @Test
  public void testAuthorizeForClientSameCountySealed() throws IOException {
    assertStatus(USER_SOCIAL_WORKER_ONLY, CLIENT_SAME_COUNTY_SEALED_ID_1, 403);
    assertStatus(USER_COUNTY_SENSITIVE, CLIENT_SAME_COUNTY_SEALED_ID_1, 403);
    assertStatus(USER_COUNTY_SEALED, CLIENT_SAME_COUNTY_SEALED_ID_2, 200);
    assertStatus(USER_STATE_SENSITIVE, CLIENT_SAME_COUNTY_SEALED_ID_1, 403);
    assertStatus(USER_STATE_SEALED, CLIENT_SAME_COUNTY_SEALED_ID_3, 200);
  }

  @Test
  public void testAuthorizeForClientDifferentCountySensitive() throws IOException {
    assertStatus(USER_SOCIAL_WORKER_ONLY, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID, 403);
    assertStatus(USER_COUNTY_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID, 403);
    assertStatus(USER_COUNTY_SEALED, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID, 403);
    assertStatus(USER_STATE_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID, 200);
    assertStatus(USER_STATE_SEALED, CLIENT_DIFFERENT_COUNTY_SENSITIVE_ID, 403);
  }

  @Test
  public void testAuthorizeForClientDifferentCountySealed() throws IOException {
    assertStatus(USER_SOCIAL_WORKER_ONLY, CLIENT_DIFFERENT_COUNTY_SEALED_ID, 403);
    assertStatus(USER_COUNTY_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SEALED_ID, 403);
    assertStatus(USER_COUNTY_SEALED, CLIENT_DIFFERENT_COUNTY_SEALED_ID, 403);
    assertStatus(USER_STATE_SENSITIVE, CLIENT_DIFFERENT_COUNTY_SEALED_ID, 403);
    assertStatus(USER_STATE_SEALED, CLIENT_DIFFERENT_COUNTY_SEALED_ID, 403);
  }

  @Test
  public void testAuthorizeForClientNoCountySensitive() throws IOException {
    assertStatus(USER_SOCIAL_WORKER_ONLY, CLIENT_NO_COUNTY_SENSITIVE_ID, 403);
    assertStatus(USER_COUNTY_SENSITIVE, CLIENT_NO_COUNTY_SENSITIVE_ID, 200);
    assertStatus(USER_COUNTY_SEALED, CLIENT_NO_COUNTY_SENSITIVE_ID, 403);
    assertStatus(USER_STATE_SENSITIVE, CLIENT_NO_COUNTY_SENSITIVE_ID, 403);
    assertStatus(USER_STATE_SEALED, CLIENT_NO_COUNTY_SENSITIVE_ID, 403);
  }

  @Test
  public void testAuthorizeForClientNoCountySealed() throws IOException {
    assertStatus(USER_SOCIAL_WORKER_ONLY, CLIENT_NO_COUNTY_SEALED_ID, 403);
    assertStatus(USER_COUNTY_SENSITIVE, CLIENT_NO_COUNTY_SEALED_ID, 403);
    assertStatus(USER_COUNTY_SEALED, CLIENT_NO_COUNTY_SEALED_ID, 200);
    assertStatus(USER_STATE_SENSITIVE, CLIENT_NO_COUNTY_SEALED_ID, 403);
    assertStatus(USER_STATE_SEALED, CLIENT_NO_COUNTY_SEALED_ID, 200);
  }

  private void assertStatus(String userFilePath, String clientId, int expectedStatus) throws IOException {
    int status = doAuthorizedGetCallStatus(userFilePath, RESOURCE_AUTHORIZE + "/client/" + clientId);
    assertEquals(expectedStatus, status);
  }
}
