package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.xa.XaNsScreeningDaoImpl;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.fixture.ScreeningWrapperEntityBuilder;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.api.domain.ScreeningDashboardList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ScreeningServiceTest extends Doofenshmirtz<ScreeningEntity> {

  private ScreeningService target;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ElasticsearchDao esDao;

  @Mock
  private XaNsScreeningDaoImpl screeningDao;

  @Mock
  private Client esClient;

  @Mock
  private IndexRequestBuilder indexRequestBuilder;

  @Mock
  private IndexResponse indexResponse;

  @Mock
  private ElasticsearchConfiguration esConfig;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    MockitoAnnotations.initMocks(this);
    when(esDao.getConfig()).thenReturn(esConfig);
    when(esDao.getClient()).thenReturn(esClient);
    when(esConfig.getElasticsearchAlias()).thenReturn("screenings");
    when(esConfig.getElasticsearchDocType()).thenReturn("screening");
    when(esClient.prepareIndex(any(), any(), any())).thenReturn(indexRequestBuilder);
    when(indexRequestBuilder.get()).thenReturn(indexResponse);
    target = new ScreeningService();
    target.setEsDao(esDao);
    target.setScreeningDao(screeningDao);
    new TestingRequestExecutionContext("0X5");
  }

  @Test
  public void testFind() {
    try {
      target.find("abc");
      fail("Expected exception");
    } catch (Exception e) {
    }
  }

  @Test
  public void testDelete() {
    try {
      target.delete("abc");
      fail("Expected exception");
    } catch (Exception e) {
    }
  }

  @Test
  public void testCreate() {
    when(indexResponse.status()).thenReturn(RestStatus.CREATED);
    Screening screening = new Screening("abc", null, null, null, null, null, null, null, "0X5", "");
    Screening actual = target.create(screening);
    assertThat(actual, is(screening));
  }

  @Test
  public void testUpdate() {
    when(indexResponse.status()).thenReturn(RestStatus.OK);
    Screening screening =
        new Screening("abc", null, null, null, null, null, null, null, "0X5", "ssb");
    Screening actual = target.update("abc", screening);
    assertThat(actual, is(screening));
  }

  @Test
  public void testUpdatePrimaryKeyValueMismatch() {
    Screening screening = new Screening("abc", null, null, null, null, null, null, null, "0X5", "");
    try {
      target.update("abcd", screening);
      fail("Expected exception");
    } catch (Exception e) {
      assertThat(e.getMessage(), is("Primary key mismatch, [abcd != abc]"));
    }
  }

  @Test
  public void testUpdatePrimaryKeyObjectTypMismatchn() {
    try {
      target.update(new Integer(1), null);
      fail("Expected exception");
    } catch (java.lang.AssertionError e) {
    }
  }

  @Test
  public void testUpdateRequestObjectTypMismatchn() {
    Request request = mock(Request.class);
    try {
      target.update("abc", request);
      fail("Expected exception");
    } catch (java.lang.AssertionError e) {
    }
  }

  @Test
  public void testCreateRequestObjectTypMismatchn() {
    Request request = mock(Request.class);
    try {
      target.create(request);
      fail("Expected exception");
    } catch (java.lang.AssertionError e) {
    }
  }

  @Test
  public void testFindScreeningDashboard() throws Exception {
    ScreeningWrapper sw1 = new ScreeningWrapperEntityBuilder().build();
    ScreeningWrapper sw2 = new ScreeningWrapperEntityBuilder().build();
    List<ScreeningWrapper> screenings = new ArrayList<>();
    screenings.add(sw1);
    screenings.add(sw2);
    when(screeningDao.findScreeningsByUserId(any())).thenReturn(screenings);
    ScreeningDashboardList sdl = (ScreeningDashboardList) target.findScreeningDashboard();
    List<ScreeningDashboard> screeningDashboard = sdl.getScreeningDashboard();
    assertThat(screeningDashboard.size(), is(2));
  }

  @Test
  public void testFindScreeningDashboardWhenEmptyShouldBeZero() throws Exception {
    List<ScreeningWrapper> screenings = new ArrayList<>();
    when(screeningDao.findScreeningsByUserId(any())).thenReturn(screenings);
    ScreeningDashboardList sdl = (ScreeningDashboardList) target.findScreeningDashboard();
    List<ScreeningDashboard> screeningDashboard = sdl.getScreeningDashboard();
    assertThat(screeningDashboard.size(), is(0));
  }

  @Test
  public void type() throws Exception {
    assertThat(ScreeningService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test(expected = NotImplementedException.class)
  public void find_A$Serializable() throws Exception {
    Serializable primaryKey = DEFAULT_CLIENT_ID;
    Response actual = target.find(primaryKey);
    Response expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findScreeningDashboard_A$() throws Exception {
    Response actual = target.findScreeningDashboard();
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = NotImplementedException.class)
  public void delete_A$Serializable() throws Exception {
    Serializable primaryKey = DEFAULT_CLIENT_ID;
    Response actual = target.delete(primaryKey);
    Response expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = Exception.class)
  public void create_A$Request() throws Exception {
    Screening screening = mock(Screening.class);
    Screening actual = target.create(screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void update_A$Serializable$Request() throws Exception {
    Serializable primaryKey = DEFAULT_CLIENT_ID;
    Screening screening = mock(Screening.class);
    Screening actual = target.update(primaryKey, screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getScreening_A$String() throws Exception {
    String id = DEFAULT_CLIENT_ID;
    Screening actual = target.getScreening(id);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void createScreening_A$Screening() throws Exception {
    Screening screening = mock(Screening.class);
    Screening actual = target.createScreening(screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void updateScreening_A$String$Screening() throws Exception {
    String id = null;
    Screening screening = mock(Screening.class);
    Screening actual = target.updateScreening(id, screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void validateParticipants_A$Screening() throws Exception {
    Screening screening = mock(Screening.class);
    target.validateParticipants(screening);
  }

  @Test
  public void createOrUpdateAllegations_A$Screening() throws Exception {
    Screening screening = mock(Screening.class);
    target.createOrUpdateAllegations(screening);
  }

  @Test
  public void createOrUpdateCrossReports_A$Screening() throws Exception {
    Screening screening = mock(Screening.class);
    target.createOrUpdateCrossReports(screening);
  }

  @Test
  public void createOrUpdateAgencies_A$CrossReportIntake() throws Exception {
    CrossReportIntake crossReport = mock(CrossReportIntake.class);
    target.createOrUpdateAgencies(crossReport);
  }

  @Test
  public void createOrUpdateAddresses_A$Screening() throws Exception {
    Screening screening = mock(Screening.class);
    target.createOrUpdateAddresses(screening);
  }

  @Test
  public void createOrUpdateParticipants_A$Screening() throws Exception {
    Screening screening = mock(Screening.class);
    target.createOrUpdateParticipants(screening);
  }

  @Test
  public void setEsDao_A$ElasticsearchDao() throws Exception {
    target.setEsDao(esDao);
  }

  @Test
  public void setScreeningDao_A$ScreeningDao() throws Exception {
    target.setScreeningDao(screeningDao);
  }

}
