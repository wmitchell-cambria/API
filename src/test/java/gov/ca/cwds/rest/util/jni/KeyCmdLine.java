package gov.ca.cwds.rest.util.jni;

import gov.ca.cwds.rest.util.jni.KeyJNI.KeyDetail;

/**
 * Command line tool to call native CWDS key generation library.
 * 
 * @author CWDS API Team
 */
public class KeyCmdLine {

  /**
   * Track memory to hunt memory leaks and record overall memory consumption.
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

}
