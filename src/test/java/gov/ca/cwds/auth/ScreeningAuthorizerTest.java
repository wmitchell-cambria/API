package gov.ca.cwds.auth;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PerrySubject.class)
public class ScreeningAuthorizerTest {

  private ScreeningAuthorizer screeningAuthorizer;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    screeningAuthorizer = new ScreeningAuthorizer();
  }

  @Test
  public void checkInstanceWithNullScreeing() {
    boolean authorized = screeningAuthorizer.checkInstance(null);
    Assert.assertTrue(authorized);
  }

  @Test
  public void checkInstanceWithNoScreeingAccessRestriction() {
    ScreeningEntity screening = new ScreeningEntity();
    screening.setAccessRestrictions(null);
    boolean authorized = screeningAuthorizer.checkInstance(screening);
    Assert.assertTrue(authorized);
  }

  @Test
  public void checkInstanceWithNoStaffPersonPrivilegs() {
    ScreeningEntity screening = new ScreeningEntity();
    screening.setAccessRestrictions("sealed");

    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(new PerryAccount());

    boolean authorized = screeningAuthorizer.checkInstance(screening);
    Assert.assertFalse(authorized);
  }

  @Test
  public void checkInstanceWithSensitiveScreening() {
    ScreeningEntity screening = new ScreeningEntity();
    screening.setAccessRestrictions("sensitive");

    PerryAccount perryAccount = initPerryAccountWithPrivileges("Sensitive Persons");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    boolean authorized = screeningAuthorizer.checkInstance(screening);
    Assert.assertTrue(authorized);
  }

  @Test
  public void checkInstanceWithSensitiveScreeningAndSealedStaff() {
    ScreeningEntity screening = new ScreeningEntity();
    screening.setAccessRestrictions("sensitive");

    PerryAccount perryAccount = initPerryAccountWithPrivileges("Sealed");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    boolean authorized = screeningAuthorizer.checkInstance(screening);
    Assert.assertFalse(authorized);
  }

  @Test
  public void checkInstanceWithSealedScreening() {
    ScreeningEntity screening = new ScreeningEntity();
    screening.setAccessRestrictions("sealed");

    PerryAccount perryAccount = initPerryAccountWithPrivileges("Sealed");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    boolean authorized = screeningAuthorizer.checkInstance(screening);
    Assert.assertTrue(authorized);
  }

  @Test
  public void checkInstanceWithSealedScreeningAndSensitiveStaff() {
    ScreeningEntity screening = new ScreeningEntity();
    screening.setAccessRestrictions("sealed");

    PerryAccount perryAccount = initPerryAccountWithPrivileges("Sensitive Persons");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    boolean authorized = screeningAuthorizer.checkInstance(screening);
    Assert.assertFalse(authorized);
  }

  @Test
  public void checkInstanceWithSealedScreeningAndUnknownStaff() {
    ScreeningEntity screening = new ScreeningEntity();
    screening.setAccessRestrictions("sealed");

    PerryAccount perryAccount = initPerryAccountWithPrivileges("blablabla");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    boolean authorized = screeningAuthorizer.checkInstance(screening);
    Assert.assertFalse(authorized);
  }

  private static PerryAccount initPerryAccountWithPrivileges(String... privileges) {
    final PerryAccount perryAccount = new PerryAccount();
    final HashSet<String> privilegeSet = new HashSet<>();
    privilegeSet.addAll(Arrays.asList(privileges));
    perryAccount.setPrivileges(privilegeSet);
    return perryAccount;
  }

}
