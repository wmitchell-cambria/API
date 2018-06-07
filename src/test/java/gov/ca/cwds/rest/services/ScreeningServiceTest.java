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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.inject.Inject;
import com.google.inject.Injector;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.xa.XaNsScreeningDaoImpl;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.fixture.ScreeningWrapperEntityBuilder;
import gov.ca.cwds.inject.DoofenshmirtzModule;
import gov.ca.cwds.inject.GuiceJUnitRunner.GuiceModules;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.api.domain.ScreeningDashboardList;
import gov.ca.cwds.rest.util.Doofenshmirtz;

@RunWith(gov.ca.cwds.inject.GuiceJUnitRunner.class)
@GuiceModules({DoofenshmirtzModule.class})
public class ScreeningServiceTest extends Doofenshmirtz<ScreeningEntity> {

  private ScreeningService target;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  // @Mock
  @Inject
  private ElasticsearchDao esDao;

  // @Mock
  @Inject
  private XaNsScreeningDaoImpl screeningDao;

  // @Mock
  // private Client esClient;

  @Mock
  private IndexRequestBuilder indexRequestBuilder;

  @Mock
  private IndexResponse indexResponse;

  // @Mock
  // private ElasticsearchConfiguration esConfig;

  @Inject
  private Injector injector;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    MockitoAnnotations.initMocks(this);

    // when(esDao.getConfig()).thenReturn(esConfig);
    // when(esDao.getClient()).thenReturn(esClient);
    // when(esConfig.getElasticsearchAlias()).thenReturn("screenings");
    // when(esConfig.getElasticsearchDocType()).thenReturn("screening");
    // when(esClient.prepareIndex(any(), any(), any())).thenReturn(indexRequestBuilder);
    // when(indexRequestBuilder.get()).thenReturn(indexResponse);
    //
    // final ScreeningEntity screeningEntity = new ScreeningEntity();
    // when(screeningDao.find(any(String.class))).thenReturn(screeningEntity);

    // final ScreeningMapper screeningMapper = mock(ScreeningMapper.class);

    // target = new ScreeningService();
    // target.setEsDao(esDao);
    // target.setScreeningDao(screeningDao);
    // target.setScreeningMapper(screeningMapper);

    // new TestingRequestExecutionContext("0X5");

    // esDao = injector.getInstance(ElasticsearchDao.class);
  }

  /**
   * @param service the service to set
   */
  @Inject
  public void setTarget(ScreeningService service) {
    this.target = service;
  }

  @Test(expected = Exception.class)
  public void testFind() {
    target.find("abc");
  }

  @Test(expected = Exception.class)
  public void testDelete() {
    target.delete("abc");
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

  @Test(expected = Exception.class)
  public void testUpdatePrimaryKeyValueMismatch() {
    Screening screening = new Screening("abc", null, null, null, null, null, null, null, "0X5", "");
    target.update("abcd", screening);
    fail("Expected exception");
  }

  @Test(expected = Exception.class)
  public void testUpdatePrimaryKeyObjectTypMismatchn() {
    target.update(new Integer(1), null);
  }

  @Test(expected = Exception.class)
  public void testUpdateRequestObjectTypMismatchn() {
    Screening request = new Screening("abc", "screening", "reference", "screeningDecision",
        "screeningDecisionDetail", "assignee", LocalDateTime.now(), null, "0X5", "");
    target.update("abc", request);
  }

  @Test(expected = Exception.class)
  public void testCreateRequestObjectTypMismatchn() {
    Request request = mock(Request.class);
    target.create(request);
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
    final Screening screening = make();
    Screening actual = target.create(screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void update_A$Serializable$Request() throws Exception {
    Serializable primaryKey = DEFAULT_CLIENT_ID;
    Screening screening = make();
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
    final Screening screening = make();
    Screening actual = target.createScreening(screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void updateScreening_A$String$Screening() throws Exception {
    String id = DEFAULT_PARTICIPANT_ID;
    Screening screening = new Screening("abc", "screening", "reference", "screeningDecision",
        "screeningDecisionDetail", "assignee", LocalDateTime.now(), null, "0X5", "");
    Screening actual = target.updateScreening(id, screening);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void validateParticipants_A$Screening() throws Exception {
    final Screening screening = make();
    target.validateParticipants(screening);
  }

  @Test
  public void createOrUpdateAllegations_A$Screening() throws Exception {
    final Screening screening = make();
    target.createOrUpdateAllegations(screening);
  }

  @Test
  public void createOrUpdateCrossReports_A$Screening() throws Exception {
    final Screening screening = make();
    target.createOrUpdateCrossReports(screening);
  }

  @Test
  public void createOrUpdateAgencies_A$CrossReportIntake() throws Exception {
    CrossReportIntake crossReport = mock(CrossReportIntake.class);
    target.createOrUpdateAgencies(crossReport);
  }

  @Test
  public void createOrUpdateAddresses_A$Screening() throws Exception {
    final Screening screening = make();
    target.createOrUpdateAddresses(screening);
  }

  @Test
  public void createOrUpdateParticipants_A$Screening() throws Exception {
    final Screening screening = make();
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

  protected Screening make() {
    return new Screening("abc", "screening", "reference", "screeningDecision",
        "screeningDecisionDetail", "assignee", LocalDateTime.now(), null, "0X5", "");

  }

}
