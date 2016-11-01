package gov.ca.cwds.rest.util.jni;

import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;

//
// http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html
//

// COMPILE JAVA:
// javac gov/ca/cwds/rest/util/jni/KeyJNI.java

// GENERATE C HEADERS:
// javah -jni gov.ca.cwds.rest.util.jni.KeyJNI

// JAVA EXECUTE: OS X:
// java -Djava.library.path=.:/usr/local/lib/ gov.ca.cwds.rest.util.jni.KeyJNI

/**
 * Calls native CWDS key generation library via JNI.
 * 
 * <h2>Steps to build and run</h2>
 * 
 * @author CWDS API Team
 */
public class KeyJNI {

  private static final boolean classLoaded = loadLibs();

  /**
   * Load native library at runtime, when the classloader loads this class. Native libraries follow
   * the naming convention of the host operating system:
   * 
   * <p>
   * <ul>
   * <li>Windows: KeyJNI.dll</li>
   * <li>OS X: libKeyJNI.dylib</li>
   * <li>LinuxlibKeyJNI.so</li>
   * </ul>
   * </p>
   * 
   * @return true = native libraries load correctly
   */
  private static final boolean loadLibs() {
    System.out.println("KeyJNI: user.dir=" + System.getProperty("user.dir"));
    System.out.println("KeyJNI: java.library.path=" + System.getProperty("java.library.path"));

    final boolean forceLoad = "Y".equalsIgnoreCase(System.getProperty("cwds.jni.force", "N"));
    System.out.println("KeyJNI: cwds.jni.force=" + forceLoad);

    boolean retval = false;

    try {
      System.loadLibrary("KeyJNI");
      retval = true;
    } catch (UnsatisfiedLinkError e) {
      retval = false;
      e.printStackTrace();
    }

    if (!retval && forceLoad) {
      retval = true;
    }

    return retval;
  }

  /**
   * Track memory to hunt memory leaks and overall memory consumption.
   * 
   * @return free memory in MB
   */
  public static long calcMemory() {
    Runtime runtime = Runtime.getRuntime();
    long maxMemory = runtime.maxMemory();
    long allocatedMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();
    return (freeMemory + (maxMemory - allocatedMemory)) / 1024L;
  }

  /**
   * Utility struct class stores details of CWDS key decomposition.
   */
  public static final class KeyDetail {
    public String key;
    public String staffId;
    public String UITimestamp;
    public String PTimestamp;
  }

  /**
   * Generates a unique key for use within CWDS, derived from a staff person id.
   * 
   * @param staffId the {@link StaffPerson}
   * @return The generated key
   */
  public native String generateKey(String staffId);

  /**
   * Decomposes a generated key.
   * 
   * @param key the key
   * @param kd the key detail
   */
  public native void decomposeKey(String key, KeyDetail kd);

  // Test Driver
  public static void main(String[] args) {
    KeyJNI inst = new KeyJNI();

    if (args[0].startsWith("-d")) {
      // ===================
      // DECOMPOSE KEY:
      // ===================

      final long startingMemory = calcMemory();
      KeyDetail kd = new KeyDetail();
      inst.decomposeKey(args[1], kd);
      System.out.println("Java: key=" + kd.key + ", staffId=" + kd.staffId + ", UITimestamp="
          + kd.UITimestamp + ", PTimestamp=" + kd.PTimestamp);
      System.out.println("used memory = " + (calcMemory() - startingMemory));
    } else {
      // ===================
      // GENERATE KEY:
      // ===================

      final long startingMemory = calcMemory();
      System.out.println("Java: Call JNI generateKey ... ");
      final String key = inst.generateKey(args[1]);
      System.out.println("Java: key=" + key);
      System.out.println("used memory = " + (calcMemory() - startingMemory));
    }
  }

  public static boolean isClassloaded() {
    return classLoaded;
  }
}
