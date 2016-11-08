package gov.ca.cwds.rest.util.jni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.compress.utils.IOUtils;

import com.pkware.deflate.DeflateOutputStream;
import com.pkware.deflate.InflateInputStream;

/**
 * Compresses (deflates) and decompresses (inflates) PK archives created by the Windows PKWare
 * library.
 * 
 * <p>
 * <strong>NOTE: </strong>This class only works with PK-compressed docs, not the LZW variable 15-bit
 * algorithm. For the latter, see {@link LZWEncoder}.
 * </p>
 *
 * <p>
 * For some unknown reason, this particular compression algorithm (PK_COMPRESS_METHOD_DEFLATE, level
 * 6) does not require a current license key. For example, this initialization is not needed:
 * </p>
 * 
 * <pre>
 * <code>PKSession session = new PKSession(PKWARE_LICENSE_KEY);</code>
 * </pre>
 * 
 * <p>
 * Java PKWare SDK:
 * <ul>
 * <li>Name: com/pkware/archive</li>
 * <li>Implementation-Vendor: PKWARE, Inc.</li>
 * <li>Specification-Title: SecureZIP Toolkit for Java</li>
 * <li>Implementation-Title: com.pkware.archive</li>
 * <li>Implementation-Version: 3.20</li>
 * <li>Specification-Version: 3.20</li>
 * <li>Specification-Vendor: PKWARE, Inc.</li>
 * </ul>
 * </p>
 * 
 * @author CWDS API Team
 * @see LZWEncoder
 */
public class CmsPKCompressor implements LicenseCWDS {

  /**
   * Constructor
   */
  public CmsPKCompressor() {}

  /**
   * Extract a CMS PKWare archive.
   * 
   * @throws IOException If an I/O error occurs
   */
  public void decompressPKToFile(String inputFileName, String outputFileName) throws IOException {
    FileInputStream fis = new FileInputStream(new File(inputFileName));

    InputStream iis = new InflateInputStream(fis, true);
    FileOutputStream fos = new FileOutputStream(new File(outputFileName));
    IOUtils.copy(iis, fos);

    fis.close();
    fos.close();
  }

  /**
   * Extract a CMS PKWare archive.
   * 
   * @throws IOException If an I/O error occurs
   */
  public void compressToPKFile(String inputFileName, String outputFileName) throws IOException {
    FileInputStream fis = new FileInputStream(new File(inputFileName));
    OutputStream fos =
        new DeflateOutputStream(new FileOutputStream(new File(outputFileName)), 6, true);
    IOUtils.copy(fis, fos);

    fis.close();
    fos.close();
  }

  /**
   * Inflates an InputStream of a PK-compressed document.
   * 
   * @param input InputStream of PK-compressed document.
   * @return byte array of decompressed document
   * @throws IOException
   */
  public byte[] decompressPKToBytes(InputStream input) throws IOException {
    return IOUtils.toByteArray(new InflateInputStream(input, true));
  }

  /**
   * Deflates a document InputStream.
   * 
   * @param bytes raw bytes of the document to compress
   * @return byte array of compressed document
   * @throws IOException
   */
  public byte[] compressToPKBytes(byte[] bytes) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(0x10000);

    OutputStream fos = new DeflateOutputStream(bos, 6, true);
    IOUtils.copy(bis, fos);

    bis.close();
    bos.close();

    return bos.toByteArray();
  }

  /**
   * Convenience method. Deflates a base64-encoded, PK-compressed archive.
   * 
   * @param base64Doc base64-encoded, PK-compressed archive
   * @return byte array of decompressed document
   * @throws IOException
   */
  public byte[] decompressPKToBytes(String base64Doc) throws IOException {
    return decompressPKToBytes(
        new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64Doc)));
  }

  /**
   * Compress or decompress a CMS PKWare archive.
   * 
   * <p>
   * EXAMPLE USAGE:
   * </p>
   * 
   * <pre>
   * {@code -d 6916351513091620_CWDST___00007.pk from_java_pk.doc}
   * </pre>
   *
   * <pre>
   * {@code -c from_java_pk.doc something.pk}
   * </pre>
   *
   * <pre>
   * {@code -d something.pk again.doc}
   * </pre>
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    try {
      CmsPKCompressor app = new CmsPKCompressor();

      final boolean is_deflate = ("-d".equals(args[0]));

      if (is_deflate) {
        app.decompressPKToFile(args[1], args[2]);
      } else {
        app.compressToPKFile(args[1], args[2]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
