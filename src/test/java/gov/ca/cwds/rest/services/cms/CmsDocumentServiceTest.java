package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;

import java.sql.Connection;
import java.sql.SQLException;

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

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    // thrown.expect(NotImplementedException.class);
    CmsDocument cmsDocumentDomain = MAPPER
        .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
    cmsDocumentService.update("testkey", cmsDocumentDomain);
  }
}
