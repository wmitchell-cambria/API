package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;


/**
 * 
 * @author CWDS API Team
 */
public class PKCompressionTest {

  private static final String GOOD_PK = "/jni/pk/good.pk";
  private static final String GOOD_DOC = "/jni/pk/good.doc";

  private CmsPKCompressor inst;

  public static String checksum(InputStream iss) throws IOException, NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA1");
    try {
      byte[] dataBytes = new byte[1024];
      int nread = 0;

      while ((nread = iss.read(dataBytes)) != -1) {
        md.update(dataBytes, 0, nread);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    } finally {
      iss.close();
    }

    return DatatypeConverter.printHexBinary(md.digest());
  }

  public static String checksum(final byte[] bytes) throws IOException, NoSuchAlgorithmException {
    return checksum(new ByteArrayInputStream(bytes));
  }

  public static String checksum(File file) throws IOException, NoSuchAlgorithmException {
    return checksum(new FileInputStream(file));
  }

  @Before
  public void setUpBeforeTest() throws Exception {
    this.inst = new CmsPKCompressor();
  }

  // ===================
  // DECOMPRESS:
  // ===================

  @Test
  public void testDecompressGoodFile() {
    try {
      final String src = PKCompressionTest.class.getResource(GOOD_PK).getPath();
      final String good = PKCompressionTest.class.getResource(GOOD_DOC).getPath();

      File tgt = File.createTempFile("tgt", ".pk");
      tgt.deleteOnExit();

      inst.decompressPKToFile(src, tgt.getAbsolutePath());

      final String chkTgt = checksum(tgt);
      final String chkGood = checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testDecompressGoodBase64() {
    try {
      final String src = PKCompressionTest.class.getResource(GOOD_PK).getPath();
      final String good = PKCompressionTest.class.getResource(GOOD_DOC).getPath();

      final String base64Doc = DatatypeConverter
          .printBase64Binary(IOUtils.toByteArray(new FileInputStream(new File(src))));

      final byte[] bytes = inst.decompressPKToBytes(base64Doc);

      final String chkTgt = checksum(bytes);
      final String chkGood = checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testDecompressGoodInputStream() {
    try {
      final String src = PKCompressionTest.class.getResource(GOOD_PK).getPath();
      final String good = PKCompressionTest.class.getResource(GOOD_DOC).getPath();

      final byte[] bytes = inst.decompressPKToBytes(
          new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(new File(src)))));

      final String chkTgt = checksum(bytes);
      final String chkGood = checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  // ===================
  // COMPRESS:
  // ===================

  @Test
  public void testCompressGoodFile() {
    try {
      final String src = PKCompressionTest.class.getResource(GOOD_DOC).getPath();
      final String good = PKCompressionTest.class.getResource(GOOD_PK).getPath();

      File tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();

      inst.compressToPKFile(src, tgt.getAbsolutePath());

      final String chkTgt = checksum(tgt);
      final String chkGood = checksum(new File(good));

      assertTrue("PK compression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testCompressGoodBytes() {
    try {
      final String src = PKCompressionTest.class.getResource(GOOD_DOC).getPath();
      final String good = PKCompressionTest.class.getResource(GOOD_PK).getPath();

      final byte[] bytes =
          inst.compressToPKBytes(IOUtils.toByteArray(new FileInputStream(new File(src))));

      final String chkTgt = checksum(bytes);
      final String chkGood = checksum(new File(good));

      assertTrue("PK compression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

}
