package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;


/**
 * 
 * @author CWDS API Team
 */
public class PKCompressionTest {

  private static final String ZIP_PK_1 = "/jni/pk/first.pk";
  private static final String ZIP_DOC_1 = "/jni/pk/first.doc";

  private static final String ZIP_PK_3 = "/jni/pk/third.pk";
  private static final String ZIP_B64_3 = "/jni/pk/third.b64";
  private static final String ZIP_DOC_3 = "/jni/pk/third.doc";

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
  public void testDecompressFile2File1() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();

      File tgt = File.createTempFile("tgt", ".pk");
      tgt.deleteOnExit();

      inst.decompressFile(src, tgt.getAbsolutePath());

      final String chkTgt = checksum(tgt);
      final String chkFirst = checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testDecompressInputStream1() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();

      final byte[] bytes = inst.decompressStream(
          new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(new File(src)))));

      final String chkTgt = checksum(bytes);
      final String chkFirst = checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testDecompressBase64Encoded3() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_B64_3).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_DOC_3).getPath();

      final String b64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
      System.out.println("b64 len=" + b64.length());

      final byte[] bytes = inst.decompressBase64(b64);
      System.out.println("bytes len=" + bytes.length);

      final String chkTgt = checksum(bytes);
      final String chkFirst = checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  // ===================
  // COMPRESS:
  // ===================

  @Test
  public void testCompressFirstFile() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();

      File tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();

      inst.compress(src, tgt.getAbsolutePath());

      final String chkTgt = checksum(tgt);
      final String chkFirst = checksum(new File(good));

      assertTrue("PK compression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testCompressFirstBytes() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();

      final byte[] bytes = inst.compress(IOUtils.toByteArray(new FileInputStream(new File(src))));

      final String chkTgt = checksum(bytes);
      final String chkFirst = checksum(new File(good));

      assertTrue("PK compression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

}
