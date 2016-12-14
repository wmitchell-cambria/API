package gov.ca.cwds.rest.util.jni;

/**
 * Command line tool to inflate/deflate LZW-compressed documents.
 * 
 * @author CWDS API Team
 */
public class LZWCmdLine {

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
    LZWEncoder inst = new LZWEncoder();

    if (args[0].startsWith("-d")) { // De-compress target file.
      final long startingMemory = calcMemory();
      inst.fileCopyUncompress(args[1], args[2]);
      System.out.println("used memory = " + (calcMemory() - startingMemory));
    } else { // Compress target file.
      final long startingMemory = calcMemory();
      inst.fileCopyCompress(args[1], args[2]);
      System.out.println("used memory = " + (calcMemory() - startingMemory));
    }
  }

}
