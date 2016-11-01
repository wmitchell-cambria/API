package gov.ca.cwds.rest.util.jni;

//
// http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html
//

// COMPILE JAVA:
// javac gov/ca/cwds/rest/util/jni/LZWEncoder.java

// GENERATE C HEADERS:
// javah -jni gov.ca.cwds.rest.util.jni.LZWEncoder

// JAVA EXECUTE: OS X:
// java -Djava.library.path=.:/usr/local/lib/ gov.ca.cwds.rest.util.jni.LZWEncoder

/**
 * Calls native CWDS key generation library via JNI.
 * 
 * <h2>Steps to build and run</h2>
 * 
 * @author CWDS API Team
 */
public class LZWEncoder {

  private static final boolean classLoaded = loadLibs();

  /**
   * Load native library at runtime, when the classloader loads this class. Native libraries follow
   * the naming convention of the host operating system:
   * 
   * <p>
   * <ul>
   * <li>Windows: LZW.dll</li>
   * <li>OS X: libLZW.dylib</li>
   * <li>LinuxlibLZW.so</li>
   * </ul>
   * </p>
   * 
   * @return true = native libraries load correctly
   */
  private static final boolean loadLibs() {
    System.out.println("LZWEncoder: user.dir=" + System.getProperty("user.dir"));
    System.out.println("LZWEncoder: java.library.path=" + System.getProperty("java.library.path"));

    final boolean forceLoad = "Y".equalsIgnoreCase(System.getProperty("cwds.jni.force", "N"));
    System.out.println("LZWEncoder: cwds.jni.force=" + forceLoad);

    boolean retval = false;

    try {
      System.loadLibrary("LZW");
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
   * Compresses a file.
   * 
   * @param src source file name to compress
   * @param tgt target file name of compressed result
   */
  public native void fileCopyCompress(String src, String tgt);

  /**
   * Decompresses a file.
   * 
   * @param src source file name to decompress
   * @param tgt target file name of decompressed result
   */
  public native void fileCopyUncompress(String src, String tgt);

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

  /**
   * Some JUnit tests may not run in all environments, if native libraries did not load correctly.
   * 
   * @return whether dependent native libraries loaded correctly
   */
  public static boolean isClassloaded() {
    return classLoaded;
  }
}


