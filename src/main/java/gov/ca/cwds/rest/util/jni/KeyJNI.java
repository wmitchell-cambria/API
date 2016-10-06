package gov.ca.cwds.rest.util.jni;


//
// http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html
//

// GENERATE C HEADER:
// javah -jni KeyJNI

// COMPILE:
// javac KeyJNI.java

// JAVA EXECUTE:
// java -Djava.library.path=.:/usr/lib/ gov.ca.cwds.rest.util.jni.KeyJNI


/**
 * Demonstrates calling native CWDS key generation library via JNI.
 * 
 * @author David R. Smith, Taborda Solutions, Inc., 2016
 */
public class KeyJNI {

  static {
    System.out.println("user.dir=" + System.getProperty("user.dir"));
    System.out.println("java.library.path=" + System.getProperty("java.library.path"));

    // keyJNI.dll (Windows), libKeyJNI.dylib (Mac), libKeyJNI.so (Unix)
    // Load native library at runtime.
     System.loadLibrary("KeyJNI");
    // System.loadLibrary("libKeyJNI.dylib");

    // System.load("libKeyJNI.dylib");
    // System.load("/Users/CWS-NS3/Documents/workspace_neon/CWDS_API/libKeyJNI.dylib");
  }

  /**
   * Track memory to hunt memory leaks and overall memory consumption.
   * 
   * @return free memory in MB
   */
  private static long calcMemory() {
    Runtime runtime = Runtime.getRuntime();
    long maxMemory = runtime.maxMemory();
    long allocatedMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();
    return (freeMemory + (maxMemory - allocatedMemory)) / 1024L;
  }

  public static final class KeyDetail {
    public String key;
    public String staffId;
    public String UITimestamp;
    public String PTimestamp;
  }

  private native String generateKey(String staffId);

  private native void decomposeKey(String key, KeyDetail kd);

  // Test Driver
  public static void main(String[] args) {
    KeyJNI inst = new KeyJNI();

    { // Generate a key from a staff id.
      System.out.println("Java: Call JNI generateKey ... ");
      final String key = inst.generateKey("0X5");
      System.out.println("Java: key=" + key);
    }

    // ===================
    // DECOMPOSE KEY:
    // ===================

    final long startingMemory = calcMemory();

    for (int i = 0; i < 10; i++) {
      System.out.println("current memory = " + calcMemory());
      System.out.println("Java: Call JNI decomposeKey ... " + i);

      KeyDetail kd = new KeyDetail();
      inst.decomposeKey("S8eScDM0X5", kd);
      System.out.println("Java: key=" + kd.key + ", staffId=" + kd.staffId + ", UITimestamp="
          + kd.UITimestamp + ", PTimestamp=" + kd.PTimestamp);
    }

    System.out.println("used memory = " + (calcMemory() - startingMemory));

    // Wrong key size.
    KeyDetail kd = new KeyDetail();
    inst.decomposeKey("wrong", kd);

    // Wrong key size: too long.
    kd = new KeyDetail();
    inst.decomposeKey("wro000000000000ng", kd);

    // Wrong key size: too short.
    kd = new KeyDetail();
    inst.decomposeKey("w", kd);

    // Empty key.
    kd = new KeyDetail();
    inst.decomposeKey("", kd);

    // Null key.
    kd = new KeyDetail();
    inst.decomposeKey(null, kd);

    // ===================
    // GENERATE KEY:
    // ===================

    // Empty staff id.
    String key = inst.generateKey("");
    System.out.println("Java: key=" + key);

    // Null staff id.
    key = inst.generateKey(null);
    System.out.println("Java: key=" + key);

    // Wrong staff id length.
    key = inst.generateKey("abcdefg");
    System.out.println("Java: key=" + key);

    // Wrong staff id length.
    key = inst.generateKey("a");
    System.out.println("Java: key=" + key);

    // Wrong staff id length.
    key = inst.generateKey("ab7777d7d7d7s8283jh4jskksjajfkdjbjdjjjasdfkljcxmzxcvjdhshfjjdkksahf");
    System.out.println("Java: key=" + key);

    // Invalid chars in staff id.
    key = inst.generateKey("ab&");
    System.out.println("Java: key=" + key);

    // Try decomposing a bad key.
    kd = new KeyDetail();
    inst.decomposeKey(key, kd);

    // Good staff id.
    key = inst.generateKey("0yz");
    System.out.println("Java: key=" + key);

  }
}


