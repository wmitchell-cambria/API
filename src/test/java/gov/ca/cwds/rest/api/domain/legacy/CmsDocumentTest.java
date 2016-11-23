package gov.ca.cwds.rest.api.domain.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.api.persistence.cms.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.resources.cms.CmsDocumentResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class CmsDocumentTest {
  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CMS_DOCUMENT + "/";

  private static final CmsDocumentResource mockedCmsDocumentResource =
      mock(CmsDocumentResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedCmsDocumentResource).build();
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CmsDocument validCmsDocument = validCmsDocument();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat tf = new SimpleDateFormat("HH:mm:ss");
  private String id = "0131351421120020*JONESMF 00004";
  private Short segmentCount = 1234;
  private Long docLength = (long) 1000;
  private String docAuth = "RAMESHA";
  private String docServ = "D7706001";
  private String docDate = "2007-01-31";
  private String docTime = "19:59:07";
  private String docName = "1234";
  private String compressionMethod = "CWSCMP01";
  private String base64Blob = "string";


  public CmsDocumentTest() throws ParseException {}

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    DataAccessEnvironment.register(gov.ca.cwds.rest.api.persistence.cms.CmsDocument.class,
        crudsDao);

    when(crudsDao.find(any())).thenReturn(mock(Referral.class));

    when(mockedCmsDocumentResource.create(eq(validCmsDocument)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class));

    assertThat(MAPPER.writeValueAsString(validCmsDocument()), is(equalTo(expected)));
  }

  // constructor test
  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"),
        CmsDocument.class), is(equalTo(validCmsDocument())));
  }

  // constructor test
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    CmsDocument domain = new CmsDocument(id, segmentCount, docLength, docAuth, docServ, docDate,
        docTime, docName, compressionMethod, base64Blob);

    gov.ca.cwds.rest.api.persistence.cms.CmsDocument persistent =
        new gov.ca.cwds.rest.api.persistence.cms.CmsDocument(domain);

    CmsDocument totest = new CmsDocument(persistent);
    assertThat(totest.getId(), is(equalTo(persistent.getId())));
    assertThat(totest.getSegmentCount(), is(equalTo(persistent.getSegmentCount())));
    assertThat(totest.getDocLength(), is(equalTo(persistent.getDocLength())));
    assertThat(totest.getDocAuth(), is(equalTo(persistent.getDocAuth())));
    assertThat(totest.getDocServ(), is(equalTo(persistent.getDocServ())));
    assertThat(totest.getDocDate(), is(equalTo(df.format(persistent.getDocDate()))));
    assertThat(totest.getDocTime(), is(equalTo(tf.format(persistent.getDocTime()))));
    assertThat(totest.getDocName(), is(equalTo(persistent.getDocName())));
    assertThat(totest.getCompressionMethod(), is(equalTo(persistent.getCompressionMethod())));
    // assertThat(totest.getBase64Blob(), is(equalTo(persistent.getBlobSegments())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    CmsDocument domain = new CmsDocument(id, segmentCount, docLength, docAuth, docServ, docDate,
        docTime, docName, compressionMethod, base64Blob);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getSegmentCount(), is(equalTo(segmentCount)));
    assertThat(domain.getDocLength(), is(equalTo(docLength)));
    assertThat(domain.getDocAuth(), is(equalTo(docAuth)));
    assertThat(domain.getDocServ(), is(equalTo(docServ)));
    assertThat(domain.getDocDate(), is(equalTo(docDate)));
    assertThat(domain.getDocTime(), is(equalTo(docTime)));
    assertThat(domain.getDocName(), is(equalTo(docName)));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    CmsDocument toCreate = MAPPER
        .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    //
    assertThat(response.getStatus(), is(equalTo(204)));
  }


  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    CmsDocument toCreate =
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocument/valid/optionalsNotIncluded.json"),
            CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  // document date test

  @Test
  public void failsWhenIdMissing() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/idMissing.json"), CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));
  }

  @Test
  public void failsWhenIdInvalid() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/idInvalid.json"), CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));
  }

  @Test
  public void failsWhenDocDateInvalid() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/invalidDate.json"), CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));
  }

  @Test
  public void failsWhenDocTimeInvalid() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/invalidTime.json"), CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));
  }

  @Test
  public void failsWhenDocLengthMissing() throws Exception {
    CmsDocument toCreate =
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocument/invalid/docLengthMissing.json"),
            CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));

  }

  @Test
  public void failsWhenSegmentCountMissing() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/segmentCountMissing.json"),
        CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));

  }


  @Test
  public void failsWhenDocServMissing() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/docServMissing.json"), CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));

  }

  @Test
  public void failsWhenCompressionMethodMissing() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/compressionMethodMissing.json"),
        CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));

  }

  @Test
  public void failsWhenDocNameMissing() throws Exception {
    CmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsDocument/invalid/docNameMissing.json"), CmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(422)));

  }

  private CmsDocument validCmsDocument() {

    return new CmsDocument("0131351421120020*JONESMF 00004", (short) 1234, (long) 1000, "RAMESHA",
        "D7706001", "2007-01-31", "19:59:07", "1234", "CWSCMP01", "string");
  }

}
