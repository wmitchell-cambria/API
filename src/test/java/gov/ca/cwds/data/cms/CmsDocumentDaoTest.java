package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;

import org.flywaydb.core.internal.util.FileCopyUtils;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.util.jni.LZWCompressionTest;
import gov.ca.cwds.rest.util.jni.PKCompressionTest;

public class CmsDocumentDaoTest extends LZWCompressionTest {

  CmsDocument doc;
  CmsDocumentDao target;

  @Before
  public void setup() throws Exception {
    super.setUpBeforeTest();
    SessionFactory sessionFactory = mock(SessionFactory.class);
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
  public void compressPK_Args__CmsDocument__String() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.ZIP_B64_3).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
    List<CmsDocumentBlobSegment> actual = target.compressPK(doc, base64);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void decompressDoc_Args__CmsDocument() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.ZIP_B64_3).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
    final List<CmsDocumentBlobSegment> blobs = target.compressPK(doc, base64);
    doc.setBlobSegments(new HashSet<>(blobs));

    String actual = target.decompressDoc(doc);
    assertThat(actual, is(notNullValue()));
  }

  // @Test
  // public void decompressPK_Args__CmsDocument() throws Exception {
  // String actual = target.decompressPK(doc);
  // String expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  // @Test
  // public void decompressLZW_Args__CmsDocument() throws Exception {
  // if (this.inst == null || !LZWEncoder.isClassloaded()) {
  // // Build platform does not yet support this test.
  // return;
  // }
  //
  // final String good = LZWCompressionTest.class.getResource(GOOD_DOC).getPath();
  //
  // String actual = target.decompressLZW(doc);
  // String expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

}
