package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.rest.util.jni.KeyJNI.KeyDetail;

/**
 * This JNI native library runs correctly on Linux Jenkins when libLZW.so and libstdc++.so.6 are
 * installed into /usr/local/lib/.
 * 
 * <p>
 * The library does build and run on OS X and Linux environments with current compilers installed.
 * </p>
 * 
 * <p>
 * The following JUnit test runs manually on the clone Jenkins server but not through Gradle on
 * Linux. However, Gradle runs successfully on OS X and Windows. Switch to the Jenkins user with:
 * </p>
 * 
 * <p>
 * <blockquote>
 * 
 * <pre>
 * {@code sudo -u jenkins bash}.
 * </pre>
 * 
 * </blockquote>
 * </p>
 * 
 * <p>
 * Run the JUnit manually with the sample command below. Note that jars are copied manually with the
 * sample script, cp_api_libs.sh.
 * </p>
 * 
 * <p>
 * <blockquote>
 * 
 * <pre>
 * {@code java -Djava.library.path=.:/usr/local/lib/ -cp .:/var/lib/jenkins/workspace/API/build/classes/main:/var/lib/jenkins/workspace/API/build/classes/test:/var/lib/jenkins/workspace/API/build/resources/test:/var/lib/jenkins/test_lib/junit-4.12.jar:/var/lib/jenkins/test_lib/hamcrest-core-1.3.jar:/var/lib/jenkins/test_lib/* org.junit.runner.JUnitCore gov.ca.cwds.rest.util.jni.KeyGenTest}
 * </pre>
 * 
 * </blockquote>
 * </p>
 * 
 * <p>
 * Force JUnit tests to run against native libraries, loaded or not, with JVM argument
 * </p>
 * 
 * <p>
 * <blockquote>
 * 
 * <pre>
 * {@code -Dcwds.jni.force=Y}
 * </pre>
 * 
 * </blockquote>
 * </p>
 * 
 * @author CWDS API Team
 */
public class KeyGenTest {

  private static final int GOOD_KEY_LEN = CmsPersistentObject.CMS_ID_LEN;

  private KeyJNI inst;

  @Before
  public void setUpBeforeTest() throws Exception {
    this.inst = new KeyJNI();
  }

  /**
   * Do not proceed with tests of native libraries, if the build platform does not yet support it.
   * 
   * <p>
   * Some JUnit tests can run manually but fail when run via Gradle in a specific environment. Work
   * in progress.
   * </p>
   * 
   * <p>
   * Force JUnit tests to run against native libraries, loaded or not, with JVM argument
   * </p>
   * 
   * <p>
   * <blockquote>
   * 
   * <pre>
   * {@code -Dcwds.jni.force=Y}
   * </pre>
   * 
   * </blockquote>
   * </p>
   * 
   * @return
   */
  protected boolean doesPlatformSupport() {
    return (this.inst == null || !KeyJNI.isClassloaded());
  }

  // ===================
  // GENERATE KEY:
  // ===================

  @Test
  public void testGenKeyGood() {
    if (doesPlatformSupport()) {
      return;
    }

    final String key = inst.generateKey("0X5");
    assertTrue("key not generated", key != null && key.length() == GOOD_KEY_LEN);
  }

  @Test
  public void testGenKeyGoodStaff2() {
    if (doesPlatformSupport()) {
      return;
    }

    // Good staff id.
    final String key = inst.generateKey("0yz");
    assertTrue("key not generated", key != null && key.length() == GOOD_KEY_LEN);
  }

  @Test
  public void testGenKeyBadStaffEmpty() {
    if (doesPlatformSupport()) {
      return;
    }

    // Empty staff id.
    final String key = inst.generateKey("");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffNull() {
    if (doesPlatformSupport()) {
      return;
    }

    // Null staff id.
    final String key = inst.generateKey(null);
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffWrongLength() {
    if (doesPlatformSupport()) {
      return;
    }

    // Wrong staff id length.
    final String key = inst.generateKey("abcdefg");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffTooShort() {
    if (doesPlatformSupport()) {
      return;
    }

    // Wrong staff id length.
    final String key = inst.generateKey("a");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffTooLong() {
    if (doesPlatformSupport()) {
      return;
    }

    // Wrong staff id length.
    final String key =
        inst.generateKey("ab7777d7d7d7s8283jh4jskksjajfkdjbjdjjjasdfkljcxmzxcvjdhshfjjdkksahf");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffBadChars() {
    if (doesPlatformSupport()) {
      return;
    }

    // Invalid chars in staff id.
    final String key = inst.generateKey("ab&");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  // ===================
  // DECOMPOSE KEY:
  // ===================

  @Test
  public void testDecomposeGoodKey() {
    if (doesPlatformSupport()) {
      return;
    }

    // Good key, decomposes correctly.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("1qxx0OC0X5", kd);
    assertTrue("Staff ID empty", kd.staffId != null && "0X5".equals(kd.staffId));
  }

  @Test
  public void testDecomposeKeyLong() {
    if (doesPlatformSupport()) {
      return;
    }

    // Wrong staff id size: too long.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("wro000000000000ng", kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  public void testDecomposeKeyShort() {
    if (doesPlatformSupport()) {
      return;
    }

    // Wrong staff id size: too short.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("w", kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  public void testDecomposeKeyEmpty() {
    if (doesPlatformSupport()) {
      return;
    }

    // Empty staff id.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("", kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  public void testDecomposeKeyNull() {
    if (doesPlatformSupport()) {
      return;
    }

    // Null staff id.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey(null, kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

}
