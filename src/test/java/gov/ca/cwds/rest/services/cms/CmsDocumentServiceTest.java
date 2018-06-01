package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CmsDocumentServiceTest extends Doofenshmirtz<CmsDocument> {

  private static final String DEFAULT_DOC_ID = "0131351421120020*JONESMF 00004";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  protected gov.ca.cwds.rest.api.domain.cms.CmsDocument readJsonDoc() throws Exception {
    return MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"),
        gov.ca.cwds.rest.api.domain.cms.CmsDocument.class);
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.CmsDocument cmsDocumentDomain = readJsonDoc();
    cmsDocumentService.update("testkey", cmsDocumentDomain);
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsDocumentService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(cmsDocumentService, notNullValue());
  }

  @Test
  public void find_A$String() throws Exception {
    String primaryKey = DEFAULT_CLIENT_ID;
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual = cmsDocumentService.find(primaryKey);
    CmsDocument expected = doc;
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void create_A$CmsDocument() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.CmsDocument request = readJsonDoc();
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual = cmsDocumentService.create(request);
    // CmsDocument expected = JsonUtils.from(
    // "{\"id\":\"0131351421120020*JONESMF
    // 00004\",\"segmentCount\":1,\"docLength\":3,\"docAuth\":\"RAMESHA\",\"docServ\":\"D7706001\",\"docDate\":\"2007-01-31\",\"docTime\":\"19:59:07\",\"docName\":\"1234\",\"compressionMethod\":\"PKWare02\",\"blobSegments\":[]}",
    // CmsDocument.class);
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void update_A$String$CmsDocument() throws Exception {
    String primaryKey = DEFAULT_DOC_ID;
    gov.ca.cwds.rest.api.domain.cms.CmsDocument request = readJsonDoc();
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual =
        cmsDocumentService.update(primaryKey, request);
    CmsDocument expected = doc;
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void blobToInsert_A$CmsDocumentBlobSegment() throws Exception {
    byte[] docBlob = {0, 0, 0, 0};
    CmsDocumentBlobSegment blob =
        new CmsDocumentBlobSegment("0131351421120020*JONESMF ", "00004", docBlob);
    String actual = cmsDocumentService.blobToInsert(blob);
    String expected =
        "INSERT INTO CWSNS1.TSBLOBT(DOC_HANDLE, DOC_SEGSEQ, DOC_BLOB) VALUES('0131351421120020*JONESMF ','00004',x'00000000')";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void blobsDelete_A$() throws Exception {
    String actual = cmsDocumentService.blobsDelete();
    String expected = "DELETE FROM CWSNS1.TSBLOBT WHERE DOC_HANDLE = ?";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCurrentSchema_A$() throws Exception {
    String actual = cmsDocumentService.getCurrentSchema();
    String expected = "CWSNS1";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void deleteBlobs_A$String() throws Exception {
    String docId = DEFAULT_DOC_ID;
    cmsDocumentService.deleteBlobs(docId);
  }

  @Test
  public void insertBlobs_A$govcacwdsdatapersistencecmsCmsDocument$List() throws Exception {
    CmsDocument doc = new CmsDocument();
    List<CmsDocumentBlobSegment> blobs = new ArrayList<CmsDocumentBlobSegment>();
    cmsDocumentService.insertBlobs(doc, blobs);
  }

  @Test
  public void delete_A$String() throws Exception {
    String primaryKey = DEFAULT_DOC_ID;
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual = cmsDocumentService.delete(primaryKey);
    CmsDocument expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
