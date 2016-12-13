package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.jdbi.cms.CmsDocumentDao;


/**
 * JUnit tests for {@link CmsDocumentDao}.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentDaoTest {
  // extends CmsBaseDaoTest<CmsDocumentDao> {

  // private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentDaoTest.class);

  // ===================
  // CONSTANTS:
  // ===================

  private static final String TEST_BASE = "/jni/";
  private static final String SMALL_LZW_B64 = TEST_BASE + "lzw/small_lzw.b64";
  private static final String ZIP_PK_1 = TEST_BASE + "first.pk";
  private static final String ZIP_DOC_1 = TEST_BASE + "first.doc";

  private static final String ZIP_B64_3 = TEST_BASE + "third.b64";
  private static final String ZIP_HEX_3 = TEST_BASE + "third.hex";
  private static final String ZIP_DOC_3 = TEST_BASE + "third.doc";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  // ===================
  // MEMBERS:
  // ===================

  private CmsDocumentDao uut; // "unit under test"

  @Before
  public void setUpBeforeTest() throws Exception {
    // this.uut = new CmsDocumentDao(CmsBaseDaoTest.getSessionFactory());
    this.uut = new CmsDocumentDao(mock(SessionFactory.class));
  }

  // ===================
  // DECOMPRESS:
  // ===================

  @SuppressWarnings("static-access")
  @Test
  public void testDecompressLZW() {
    try {
      final String good = CmsDocumentDaoTest.class.getResource(SMALL_LZW_B64).getPath();

      final String docHandle = "3555570915020020*DUCKP   00001";
      CmsDocument doc = new CmsDocument(docHandle // id/doc_handle
          , (short) 1 // segmentCount
          , 25L // docLength
          , "DUCKP" // docAuth
          , "B7704002" // docServ
          , DomainObject.uncookDateString("2000-02-15") // docDate
          , DomainObject.uncookTimeString("09:57:55") // docTime
          , "kk.dot" // docName
          , "CWSCMP01" // compressionMethod
      );

      doc.addBlobSegment(new CmsDocumentBlobSegment(docHandle, "0001",
          "6833c22e050ac434e10042e190d870007c0001801fcff048000d0f87006191c8d04216028ec322aff7fc680126954ae592d974be6131994ce69359b4de71399d4ee793d9f4fe8141a150e84fd95495ff48024300b0c8cc2c0f24a254ea955ab55eb159ad56eb95daf50ca40037d880074000800045001baca0039000f30caf858014d97c7a160991435810f234365e005cba862406cae4031385b022f299515ec96f32000896431800ea0036800cb6bb6c2e4d78520213e8286d1a4d28d5430340001006f78e96c3747a5a45e2f1abd580b1b0d8e10ec99b380009e0031000d5aaa46d504fb954a04852d2ca1ff13027474a12d76ca61ba87033bd1a12c32d9e18a4ba9180950e2470b08226163057b48fdb9dd3f0fca0d35ca650d0c405ed000802290b340977d0b08187e222097fd0b5e0b83b91c3306e7e84000da1431b16280052d0b5350b53c005450b0210c6f40a4300b431e042c0d4300e4300f4312042c11431da42c1343014430154317442c174320d000196b10c06d0c0710c0750c0790c07d0c5a10b0850c0890c08d0c09152498269715f9826198a639926557cdc2959d08d8c875e63c17b3c57b6f1e66fa749d90d0b2250006c4327942e7c9de770527a8f281a190f9ea2842c0aa25ba2a97b5fd0b0a27a9b687a5a743b27a6c697a710c2627a1510c3ba84a769648913471f6a96abab2adabaafac2b1aca745fe3a8380013453080531846e1cebb194721a4660022c0011c14c791b4621be7c0325342ec91cc741959b8f90d150691b465afc4e19477080521bc6daf4008bab3ba2e9baaebbb2ed6ea6f42e7142cfbbbaf5bdaf7be2f9beafbbf2fdbac06175131840987401bc000bc8003eafec330dc3b0fc4311c4b0f874143c2c76fb07c2522c697b86eb77a9a97703887e308ca254704b00232435ad000403c4ff489ad0184e1bc72b927c86800c0e959d257104403c8ff2840095c1611065198611d46c1d020140611c86119f531c068080461bc6e599e6cfa04a6f136a8ff102859dd0341507426764451345517885286e1288d1224711500e8a4ac005e90e5f50e11a4b42c4d1a4631c86f1cc6f19b4f15f37190200d82e0c0201106f18c75b6f5c0028aae053e34721939565e340039f1902ee8b981975c0b836000f40e4b21c687dcf21999334000"));

      // Don't call statically cuz you want to construct the DAO and test.
      final String base64Doc = uut.decompressDoc(doc);

      final String chkTgt = CWDSCompressionUtils.checksum(base64Doc);
      final String chkFirst = CWDSCompressionUtils.checksum(new File(good));
      assertTrue("LZW decompression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  // ===================
  // COMPRESS:
  // ===================


}
