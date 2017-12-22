package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;

public class CmsDocumentServiceTest {

  private static final ObjectMapper MAPPER = ElasticSearchPerson.MAPPER;
  private CmsDocumentService cmsDocumentService;
  private CmsDocumentDao cmsDocumentDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    cmsDocumentDao = mock(CmsDocumentDao.class);
    cmsDocumentService = new CmsDocumentService(cmsDocumentDao);
  }

  @Ignore
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    CmsDocument cmsDocumentDomain = MAPPER
        .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
    cmsDocumentService.create(cmsDocumentDomain);
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    // thrown.expect(NotImplementedException.class);
    CmsDocument cmsDocumentDomain = MAPPER
        .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
    cmsDocumentService.update("testkey", cmsDocumentDomain);
  }

  @Ignore
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    CmsDocument cmsDocumentDomain = MAPPER
            .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
    cmsDocumentService.delete("testkey");
  }

  // DRS: CmsDocument constructor doesn't populate blob segments directly from a base64-encoded,
  // decompressed document because that translation requires compression, which doesn't belong
  // there.;)

  // TODO: found.base64Blob is not set in persistent CmsDocument constructor
  // @Test
  // public void findReturnsCorrectCmsDocumentWhenFound() throws Exception {
  // CmsDocument expected = MAPPER
  // .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
  //
  // gov.ca.cwds.rest.api.persistence.cms.CmsDocument cmsDocument =
  // new gov.ca.cwds.rest.api.persistence.cms.CmsDocument(expected);
  //
  // when(cmsDocumentDao.find("0131351421120020*JONESMF 00004")).thenReturn(cmsDocument);
  // CmsDocument found = cmsDocumentService.find("0131351421120020*JONESMF 00004");
  //
  // assertThat(found, is(expected));
  // }

}
