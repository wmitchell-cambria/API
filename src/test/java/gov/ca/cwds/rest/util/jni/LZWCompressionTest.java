package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class LZWCompressionTest {

  protected static final String TEST_BASE = "/jni/lzw/";
  protected static final String GOOD_LZW = TEST_BASE + "good.lzw";
  protected static final String GOOD_DOC = TEST_BASE + "good.doc";

  protected LZWEncoder inst;

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
