package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.util.jni.KeyJNI.KeyDetail;

public class JNIKeyGenTest {

  private static final int GOOD_KEY_LEN = 10;

  private KeyJNI inst;

  @Before
  public void setUpBeforeTest() throws Exception {
    this.inst = new KeyJNI();
  }

  // ===================
  // GENERATE KEY:
  // ===================

  @Test
  public void testGenKeyGood() {
    final String key = inst.generateKey("0X5");
    assertTrue("key not generated", key != null && key.length() == GOOD_KEY_LEN);
  }

  @Test
  public void testGenKeyGoodStaff2() {
    // Good staff id.
    final String key = inst.generateKey("0yz");
    assertTrue("key not generated", key != null && key.length() == GOOD_KEY_LEN);
  }

  @Test
  public void testGenKeyBadStaffEmpty() {
    // Empty staff id.
    final String key = inst.generateKey("");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffNull() {
    // Null staff id.
    final String key = inst.generateKey(null);
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffWrongLength() {
    // Wrong staff id length.
    final String key = inst.generateKey("abcdefg");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffTooShort() {
    // Wrong staff id length.
    final String key = inst.generateKey("a");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffTooLong() {
    // Wrong staff id length.
    final String key =
        inst.generateKey("ab7777d7d7d7s8283jh4jskksjajfkdjbjdjjjasdfkljcxmzxcvjdhshfjjdkksahf");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test
  public void testGenKeyBadStaffBadChars() {
    // Invalid chars in staff id.
    final String key = inst.generateKey("ab&");
    assertTrue("key generated", key == null || key.length() == 0);
  }

  // ===================
  // DECOMPOSE KEY:
  // ===================

  @Test
  public void testDecomposeKeyLong() {
    // Wrong staff id size: too long.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("wro000000000000ng", kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  public void testDecomposeKeyShort() {
    // Wrong staff id size: too short.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("w", kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  public void testDecomposeKeyEmpty() {
    // Empty staff id.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("", kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  public void testDecomposeKeyNull() {
    // Null staff id.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey(null, kd);
    assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }


}
