package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;


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
public class LZWCompressionTest {

  private static final String TEST_BASE = "/jni/lzw/";
  private static final String GOOD_LZW = TEST_BASE + "good.lzw";
  private static final String GOOD_DOC = TEST_BASE + "good.doc";

  private LZWEncoder inst;

  @Before
  public void setUpBeforeTest() throws Exception {
    this.inst = new LZWEncoder();
  }

  // ===================
  // DECOMPRESS:
  // ===================

  @Test
  public void testDecompressGood() {
    if (this.inst == null || !LZWEncoder.isClassloaded()) {
      // Build platform does not yet support this test.
      return;
    }

    // TODO: verify that temp files are deleted!
    try {
      final String src = LZWCompressionTest.class.getResource(GOOD_LZW).getPath();
      final String good = LZWCompressionTest.class.getResource(GOOD_DOC).getPath();

      File tgt = File.createTempFile("tgt", ".lzw");
      tgt.deleteOnExit();

      inst.fileCopyUncompress(src, tgt.getAbsolutePath());

      final String chkTgt = CWDSCompressionUtils.checksum(tgt);
      final String chkGood = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("LZW decompression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  // ===================
  // COMPRESS:
  // ===================

  @Test
  public void testCompressGood() {
    if (this.inst == null || !LZWEncoder.isClassloaded()) {
      // Build platform does not yet support this test.
      return;
    }

    try {
      final String src = LZWCompressionTest.class.getResource(GOOD_DOC).getPath();
      final String good = LZWCompressionTest.class.getResource(GOOD_LZW).getPath();

      File tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();

      inst.fileCopyCompress(src, tgt.getAbsolutePath());

      final String chkTgt = CWDSCompressionUtils.checksum(tgt);
      final String chkGood = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("LZW compression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

}
