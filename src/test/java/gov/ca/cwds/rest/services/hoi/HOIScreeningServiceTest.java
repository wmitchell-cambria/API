package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.hoi.HOIScreeningBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

/***
 *
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
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
    when(screeningDao.findScreeningsByClientIds(clientIds))
        .thenReturn(mockScreeningEntityList());
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
  public void findReturnsExpectedHistoryOfInvolvement() {
    HOIScreeningResponse expectedResponse = createExpectedResponse();

    Set<String> clientIds = new HashSet<>();
    clientIds.add("1");
    HOIScreeningRequest hoiScreeningRequest = new HOIScreeningRequest();
    hoiScreeningRequest.setClientIds(clientIds);
    HOIScreeningResponse actualResponse = hoiScreeningService.handleFind(hoiScreeningRequest);
    
    assertThat(actualResponse, is(expectedResponse));
  }

  private HOIScreeningResponse createExpectedResponse() {
    HOIScreeningBuilder hoiScreeningBuilder = new HOIScreeningBuilder();
    hoiScreeningBuilder.setId("224");
    hoiScreeningBuilder.setStartDate("2017-11-30").setEndDate("2017-12-10");
    hoiScreeningBuilder.setCountyId(1101).setCountyDescription("Sacramento");
    hoiScreeningBuilder.setDecision("promote to referral").setDecisionDetail("drug counseling");

    Set<HOIScreening> screenings = new HashSet<>();
    screenings.add(hoiScreeningBuilder.createHOIScreening());
    return new HOIScreeningResponse(screenings);
  }

  private Set<ScreeningEntity> mockScreeningEntityList() {
    ScreeningEntity entity = new ScreeningEntity("224", null,
        DomainChef.uncookDateString("2017-11-30"),
        DomainChef.uncookDateString("2017-12-10"), "sacramento", null,
        null, null, null, null, "promote to referral",
        "drug counseling", null, null, null);

    Set<ScreeningEntity> result = new HashSet<>();
    result.add(entity);
    return result;
  }

  private IntakeLOVCodeEntity mockIntakeLOVCode() {
    IntakeLOVCodeEntity mockIntakeLOVCodeEntity = new IntakeLOVCodeEntity();
    mockIntakeLOVCodeEntity.setLgSysId(1101L);
    mockIntakeLOVCodeEntity.setIntakeDisplay("Sacramento");
    return mockIntakeLOVCodeEntity;
  }
}
