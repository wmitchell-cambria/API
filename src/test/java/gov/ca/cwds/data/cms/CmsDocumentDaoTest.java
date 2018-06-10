package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;

import org.flywaydb.core.internal.util.FileCopyUtils;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.util.jni.LZWCompressionTest;
import gov.ca.cwds.rest.util.jni.PKCompressionTest;

public class CmsDocumentDaoTest extends LZWCompressionTest {

  CmsDocumentDao target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setUpBeforeTest();
    target = new CmsDocumentDao(sessionFactory);
    doc = new CmsDocument();

    new TestingRequestExecutionContext("0X5");
    RequestExecutionContext.instance();
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsDocumentDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void compressPK_Args__CmsDocument__String__zip() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.ZIP_B64_3).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
    final List<CmsDocumentBlobSegment> actual = target.compressPK(doc, base64);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void compressPK_Args__CmsDocument__String__plain() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.ZIP_B64_3).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
    final List<CmsDocumentBlobSegment> actual = target.compressPK(doc, base64);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void decompressDoc_Args__CmsDocument__zip_base64() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.ZIP_B64_3).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
    final List<CmsDocumentBlobSegment> blobs = target.compressPK(doc, base64);
    doc.setBlobSegments(new HashSet<>(blobs));

    String actual = target.decompressDoc(doc);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void decompressDoc_Args__CmsDocument__plain_base64() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.PLAIN_B64_1).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();

    doc.setCompressionMethod(CmsDocumentDao.COMPRESSION_TYPE_PLAIN);
    final List<CmsDocumentBlobSegment> blobs = target.compressDoc(doc, base64);
    doc.setBlobSegments(new HashSet<>(blobs));

    String actual = target.decompressDoc(doc);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void decompressDoc_A$CmsDocument() throws Exception {
    doc = readPersistedDocumentLzwCompression();
    final String actual = target.decompressDoc(doc);
    final String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void compressDoc_A$CmsDocument$String() throws Exception {
    doc = readPersistedDocumentLzwCompression();
    final String base64 = null;

    final List<CmsDocumentBlobSegment> actual = target.compressDoc(doc, base64);
    final List<CmsDocumentBlobSegment> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
