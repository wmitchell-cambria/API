package gov.ca.cwds.rest.util.jni;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * {@code java -Djava.library.path=.:/usr/local/lib/ -cp .:/var/lib/jenkins/workspace/API/build/classes/main:/var/lib/jenkins/workspace/API/build/classes/test:/var/lib/jenkins/workspace/API/build/resources/test:/var/lib/jenkins/test_lib/junit-4.12.jar:/var/lib/jenkins/test_lib/hamcrest-core-1.3.jar:/var/lib/jenkins/test_lib/* org.junit.runner.JUnitCore gov.ca.cwds.rest.util.jni.LZWCompressionTest}
 * </pre>
 * 
 * </blockquote>
 * </p>
 * 
 * <p>
 * <strong>Windows</strong>
 * </p>
 * <p>
 * On Windows systems, use this sample library path: <blockquote>
 * 
 * {@code -Djava.library.path=.;/Users/joeschmoe/workspace/API/lib;/Windows/System32}
 * </pre>
 * 
 * </blockquote>
 * </p>
 * 
 * <p>
 * On Windows, check the execution permissions of LZW.dll. Verify that "deny" is disabled and that
 * "read an execute" is enabled. Check file properties under security/advanced.
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
public class CmsLZWCompressor {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsLZWCompressor.class);

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
    LOGGER.info("LZWEncoder: user.dir=" + System.getProperty("user.dir"));
    LOGGER.info("LZWEncoder: java.library.path=" + System.getProperty("java.library.path"));

    final boolean forceLoad = "Y".equalsIgnoreCase(System.getProperty("cwds.jni.force", "N"));
    LOGGER.info("LZWEncoder: cwds.jni.force=" + forceLoad);

    boolean retval = false;

    try {
      System.loadLibrary("LZW");
      retval = true;
    } catch (UnsatisfiedLinkError e) {
      retval = false;
      e.printStackTrace();
      LOGGER.error("Failed to load LZW library", e);
    }

    if (!retval && forceLoad) {
      retval = true;
    }

    return retval;
  }

  /**
   * Deflates a file compressed with LZW variable 15 bit.
   * 
   * @param src source file name to compress
   * @param tgt target file name of compressed result
   */
  public native void fileCopyCompress(String src, String tgt);

  /**
   * Inflates a file compressed with LZW variable 15 bit.
   * 
   * @param src source file name to decompress
   * @param tgt target file name of decompressed result
   */
  public native void fileCopyUncompress(String src, String tgt);

  /**
   * Some JUnit tests may not run in all environments, if native libraries did not load correctly.
   * 
   * @return whether dependent native libraries loaded correctly
   */
  public static boolean isClassloaded() {
    return classLoaded;
  }

}


