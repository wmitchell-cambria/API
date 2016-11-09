package gov.ca.cwds.rest.util.jni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.compress.utils.IOUtils;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * <strong>Java PKWare SDK details:</strong>
 * <ul>
 * <li>Name: com/pkware/archive</li>
 * <li>Implementation-Vendor: PKWARE, Inc.</li>
 * <li>Specification-Title: SecureZIP Toolkit for Java</li>
 * <li>Implementation-Title: com.pkware.archive</li>
 * <li>Implementation-Version: 3.20</li>
 * <li>Specification-Version: 3.20</li>
 * <li>Specification-Vendor: PKWARE, Inc.</li>
 * </ul>
 * 
 * @author CWDS API Team
 * @see LZWEncoder
 */
public class CmsPKCompressor implements LicenseCWDS {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsPKCompressor.class);

  /**
   * Constructor
   */
  public CmsPKCompressor() {}

  /**
   * Decompress (inflate) a CMS PKWare archive.
   * 
   * @param inputFileName file name of resulting decompressed output
   * @param outputFileName file name to decompress
   * @throws IOException If an I/O error occurs
   */
  public void decompressFile(String inputFileName, String outputFileName) throws IOException {
    FileInputStream fis = new FileInputStream(new File(inputFileName));

    InputStream iis = new InflateInputStream(fis, true);
    FileOutputStream fos = new FileOutputStream(new File(outputFileName));
    IOUtils.copy(iis, fos);

    fis.close();
    fos.close();
  }

  /**
   * Decompress (inflate) raw bytes of a PK-compressed document.
   * 
   * @param bytes raw bytes of PK-compressed document.
   * @return byte array of decompressed document
   * @throws IOException If an I/O error occurs
   */
  public byte[] decompressBytes(byte[] bytes) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    InputStream iis = new InflateInputStream(bis, true);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(0x10000);
    IOUtils.copy(iis, bos);

    iis.close();
    bis.close();
    bos.flush();
    bos.close();

    final byte[] retval = bos.toByteArray();
    LOGGER.debug("CmsPKCompressor.decompress(byte[]): retval len=" + retval.length);

    return retval;
  }

  /**
   * Decompress (inflate) an InputStream of a PK-compressed document.
   * 
   * @param input InputStream of PK-compressed document.
   * @return byte array of decompressed document
   * @throws IOException If an I/O error occurs
   */
  public byte[] decompressStream(InputStream input) throws IOException {
    InputStream iis = new InflateInputStream(input, true);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(0x10000);
    IOUtils.copy(iis, bos);

    iis.close();
    bos.flush();
    bos.close();

    final byte[] bytes = bos.toByteArray();
    LOGGER.debug("CmsPKCompressor.decompressStream(InputStream): bytes len=" + bytes.length);

    return bytes;
  }

  /**
   * Convenience method. Decompress (inflate) a base64-encoded string of a PK-compressed archive.
   * 
   * @param base64Doc base64-encoded, PK-compressed archive
   * @return raw byte array of decompressed document
   * @throws IOException If an I/O error occurs
   */
  public byte[] decompressBase64(String base64Doc) throws IOException {
    final byte[] bytes = decompressBytes(DatatypeConverter.parseBase64Binary(base64Doc.trim()));
    LOGGER.debug("CmsPKCompressor.decompressBase64(String): bytes len=" + bytes.length);
    return bytes;
  }

  /**
   * Convenience method. Decompress (inflate) a hexadecimal string of a PK-compressed archive.
   * 
   * @param hex base64-encoded, PK-compressed archive
   * @return raw byte array of decompressed document
   * @throws IOException If an I/O error occurs
   */
  public byte[] decompressHex(String hex) throws IOException {
    final byte[] bytes = decompressBytes(DatatypeConverter.parseHexBinary(hex.trim()));
    LOGGER.debug("CmsPKCompressor.decompressHex(String): bytes len=" + bytes.length);
    return bytes;
  }

  /**
   * Compress (deflate) a CMS PKWare archive and writes resulting decompressed document to given
   * output file.
   * 
   * @param inputFileName file name to decompress
   * @param outputFileName file name of resulting decompressed output
   * @throws IOException If an I/O error occurs
   */
  public void compressFile(String inputFileName, String outputFileName) throws IOException {
    FileInputStream fis = new FileInputStream(new File(inputFileName));
    OutputStream fos =
        new DeflateOutputStream(new FileOutputStream(new File(outputFileName)), 6, true);
    IOUtils.copy(fis, fos);

    fis.close();
    fos.close();
  }

  /**
   * Convenience method. Compress (deflate) a document InputStream.
   * 
   * @param bytes raw bytes of the document to compress
   * @return raw byte array of compressed document
   * @throws IOException If an I/O error occurs
   */
  public byte[] compressBytes(byte[] bytes) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(0x10000);

    OutputStream dos = new DeflateOutputStream(bos, 6, true);
    IOUtils.copy(bis, dos);

    bis.close();
    dos.close();

    return bos.toByteArray();
  }

}
