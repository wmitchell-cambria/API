package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;


/**
 * This JNI native library runs correctly on Linux Jenkins when libLZW.so and libstdc++.so.6 are
 * installed in /usr/local/lib/.
 * 
 * <p>
 * The library does build and run on OS X and Linux environments with current compilers installed.
 * </p>
 * 
 * @author CWDS API Team
 */
public class LZWCompressionTest {

  private LZWEncoder inst;

  public static String checksum(File file) throws IOException, NoSuchAlgorithmException {

    MessageDigest md = MessageDigest.getInstance("SHA1");
    try (FileInputStream fis = new FileInputStream(file)) {
      byte[] dataBytes = new byte[1024];
      int nread = 0;

      while ((nread = fis.read(dataBytes)) != -1) {
        md.update(dataBytes, 0, nread);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }

    return DatatypeConverter.printHexBinary(md.digest());
  }

  @Before
  public void setUpBeforeTest() throws Exception {
    this.inst = new LZWEncoder();
  }

  // ===================
  // DECOMPRESS:
  // ===================

  @Test
  public void testDecompressGood() {
    try {
      final String src = LZWCompressionTest.class.getResource("/jni/lzw/good.lzw").getPath();
      final String good = LZWCompressionTest.class.getResource("/jni/lzw/good.doc").getPath();

      File tgt = File.createTempFile("tgt", ".lzw");
      tgt.deleteOnExit();

      inst.fileCopyUncompress(src, tgt.getAbsolutePath());

      final String chkTgt = checksum(tgt);
      final String chkGood = checksum(new File(good));

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
    try {
      final String src = LZWCompressionTest.class.getResource("/jni/lzw/good.doc").getPath();
      final String good = LZWCompressionTest.class.getResource("/jni/lzw/good.lzw").getPath();

      File tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();

      inst.fileCopyCompress(src, tgt.getAbsolutePath());

      final String chkTgt = checksum(tgt);
      final String chkGood = checksum(new File(good));

      assertTrue("LZW compression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

}
