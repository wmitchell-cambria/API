package gov.ca.cwds.rest.util.jni;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;

import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class LZWCompressionTest extends Doofenshmirtz<CmsDocument> {

  public static final String TEST_BASE = "/jni/lzw/";
  public static final String GOOD_LZW = TEST_BASE + "good.lzw";
  public static final String GOOD_DOC = TEST_BASE + "good.doc";

  protected LZWEncoder inst;

  @Before
  public void setUpBeforeTest() throws Exception {
    super.setup();
    this.inst = mock(LZWEncoder.class);

    when(inst.didLibraryLoad()).thenReturn(true);
  }

}
