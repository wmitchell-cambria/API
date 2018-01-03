package gov.ca.cwds.rest.services.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

/***
 *
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOIScreeningServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private HOIScreeningService hoiScreeningService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd z");
    MAPPER.getSerializationConfig().with(dateFormat).with(TimeZone.getTimeZone("PST"));

    new TestingRequestExecutionContext("0X5");

    ParticipantDao participantDao = mock(ParticipantDao.class);
    HOIPersonFactory hoiPersonFactory = new HOIPersonFactory();
    hoiPersonFactory.participantDao = participantDao;

    ScreeningDao screeningDao = mock(ScreeningDao.class);
    when(screeningDao.findHoiScreeningsByScreeningId(any(String.class)))
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
  public void findReturnsExpectedHistoryOfInvolvement() throws Exception {
    HOIScreening expectedScreening = MAPPER
        .readValue(fixture("gov/ca/cwds/rest/services/hoi/hoi_screenings/valid_HOIScreening.json"),
            HOIScreening.class);
    Set<HOIScreening> screenings = new HashSet<>();
    screenings.add(expectedScreening);
    HOIScreeningResponse expectedResponse = new HOIScreeningResponse(screenings);
    HOIScreeningResponse actualResponse = hoiScreeningService.handleFind("1");
    assertThat(actualResponse, is(expectedResponse));
  }

  private Set<ScreeningEntity> mockScreeningEntityList() {
    ScreeningEntity entity = new ScreeningEntity("224", null,
        DomainChef.uncookStrictTimestampString("2017-11-29T16:00:00.000-0800"),
        DomainChef.uncookStrictTimestampString("2017-12-09T16:00:00.000-0800"), "sacramento", null,
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
