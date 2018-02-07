package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.hoi.HOIScreeningBuilder;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

/***
 *
 * @author CWDS API Team
 *
 */
public class HOIScreeningServiceTest {

  private HOIScreeningService hoiScreeningService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    new TestingRequestExecutionContext("0X5");

    ParticipantDao participantDao = mock(ParticipantDao.class);
    HOIPersonFactory hoiPersonFactory = new HOIPersonFactory();
    hoiPersonFactory.participantDao = participantDao;

    ScreeningDao screeningDao = mock(ScreeningDao.class);
    Set<String> clientIds = new HashSet<>();
    clientIds.add("1");
    when(screeningDao.findScreeningsByClientIds(clientIds)).thenReturn(mockScreeningEntityList());
    IntakeLOVCodeEntity mockIntakeLOVCodeEntity = new IntakeLOVCodeEntity();
    mockIntakeLOVCodeEntity.setLgSysId(1101L);
    mockIntakeLOVCodeEntity.setIntakeDisplay("Sacramento");
    when(screeningDao.findIntakeLOVCodeByIntakeCode(any(String.class)))
        .thenReturn(mockIntakeLOVCode());

    HOIScreeningFactory hoiScreeningFactory = new HOIScreeningFactory();
    hoiScreeningFactory.screeningDao = screeningDao;
    hoiScreeningFactory.hoiPersonFactory = hoiPersonFactory;

    hoiScreeningService = new HOIScreeningService();
    hoiScreeningService.screeningDao = screeningDao;
    hoiScreeningService.hoiScreeningFactory = hoiScreeningFactory;
  }

  @Test
  public void findReturnsExpectedAndSortedHOIScreenings() {
    HOIScreeningResponse expectedResponse = createExpectedResponse();

    HOIRequest hoiScreeningRequest = new HOIRequest();
    hoiScreeningRequest.setClientIds(Stream.of("1").collect(Collectors.toSet()));
    HOIScreeningResponse actualResponse = hoiScreeningService.handleFind(hoiScreeningRequest);
    assertThat(actualResponse, is(expectedResponse));
  }

  private HOIScreeningResponse createExpectedResponse() {
    Set<HOIScreening> screenings = new TreeSet<>(
        (s1, s2) -> s2.getStartDate().compareTo(s1.getStartDate()));
    screenings.add(new HOIScreeningBuilder().createHOIScreening());
    screenings.add(new HOIScreeningBuilder().setId("223").setStartDate("2017-09-25")
        .setEndDate("2017-10-01").createHOIScreening());
    return new HOIScreeningResponse(screenings);
  }

  private Set<ScreeningEntity> mockScreeningEntityList() {
    ScreeningEntity entity1 = new ScreeningEntityBuilder().setId("223").setStartedAt("2017-09-25")
        .setEndedAt("2017-10-01").setIncidentCounty("sacramento").setName(null)
        .setScreeningDecision("promote to referral").setScreeningDecisionDetail("drug counseling")
        .build();

    ScreeningEntity entity2 = new ScreeningEntityBuilder().setId("224").setStartedAt("2017-11-30")
        .setEndedAt("2017-12-10").setIncidentCounty("sacramento").setName(null)
        .setScreeningDecision("promote to referral").setScreeningDecisionDetail("drug counseling")
        .build();

    Set<ScreeningEntity> result = new HashSet<>();
    result.add(entity1);
    result.add(entity2);
    return result;
  }

  private IntakeLOVCodeEntity mockIntakeLOVCode() {
    IntakeLOVCodeEntity mockIntakeLOVCodeEntity = new IntakeLOVCodeEntity();
    mockIntakeLOVCodeEntity.setLgSysId(1101L);
    mockIntakeLOVCodeEntity.setIntakeDisplay("Sacramento");
    return mockIntakeLOVCodeEntity;
  }
}
