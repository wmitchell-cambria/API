package gov.ca.cwds.rest.services.hoi;

import static gov.ca.cwds.fixture.ParticipantEntityBuilder.DEFAULT_PERSON_ID;
import static gov.ca.cwds.fixture.ParticipantEntityBuilder.DEFAULT_REPORTER_ID;
import static gov.ca.cwds.fixture.ScreeningEntityBuilder.DEFAULT_ASSIGNEE_STAFF_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.ParticipantEntityBuilder;
import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonResourceBuilder;
import gov.ca.cwds.fixture.hoi.HOIPersonResourceBuilder;
import gov.ca.cwds.fixture.hoi.HOIReporterResourceBuilder;
import gov.ca.cwds.fixture.hoi.HOIScreeningBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.StaffPerson;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.resources.StaffPersonResource;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import gov.ca.cwds.rest.util.CmsRecordUtils;

/**
 * @author CWDS API Team
 */
public class HOIScreeningServiceTest {

  private ScreeningDao screeningDao;
  private HOIScreeningService hoiScreeningService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    new TestingRequestExecutionContext(DEFAULT_ASSIGNEE_STAFF_ID);

    screeningDao = mock(ScreeningDao.class);
    ParticipantDao participantDao = mock(ParticipantDao.class);
    StaffPersonResource staffPersonResource = mock(StaffPersonResource.class);

    when(participantDao.findParticipantLegacyDescriptor(DEFAULT_PERSON_ID))
        .thenReturn(mockLegacyDescriptorEntity(DEFAULT_PERSON_ID));
    when(participantDao.findParticipantLegacyDescriptor(DEFAULT_REPORTER_ID))
        .thenReturn(mockLegacyDescriptorEntity(DEFAULT_REPORTER_ID));

    StaffPerson mockStaffPerson = new StaffPersonResourceBuilder().build();
    when(staffPersonResource.get(DEFAULT_ASSIGNEE_STAFF_ID))
        .thenReturn(Response.ok(mockStaffPerson).build());
    HOIPersonFactory hoiPersonFactory = new HOIPersonFactory();
    hoiPersonFactory.participantDao = participantDao;
    hoiPersonFactory.staffPersonResource = staffPersonResource;

    IntakeLOVCodeEntity mockIntakeLOVCodeEntity = new IntakeLOVCodeEntity();
    mockIntakeLOVCodeEntity.setLgSysId(1101L);
    mockIntakeLOVCodeEntity.setIntakeDisplay("Sacramento");
    when(screeningDao.findIntakeLOVCodeByIntakeCode(any(String.class)))
        .thenReturn(mockIntakeLOVCode());

    HOIScreeningFactory hoiScreeningFactory = new HOIScreeningFactory();
    hoiScreeningFactory.screeningDao = screeningDao;
    hoiScreeningFactory.hoiPersonFactory = hoiPersonFactory;

    AuthorizationService authorizationService = mock(AuthorizationService.class);

    hoiScreeningService = new HOIScreeningService();
    hoiScreeningService.screeningDao = screeningDao;
    hoiScreeningService.hoiScreeningFactory = hoiScreeningFactory;
    hoiScreeningService.authorizationService = authorizationService;
  }

  @Test
  public void findReturnsExpectedAndSortedHOIScreenings() {
    HOIScreeningResponse expectedResponse = createExpectedResponse();

    Set<String> clientIds = new HashSet<>();
    clientIds.add("1");
    when(screeningDao.findScreeningsByClientIds(clientIds))
        .thenReturn(mockScreeningEntityList(null));

    HOIRequest hoiScreeningRequest = new HOIRequest();
    hoiScreeningRequest.setClientIds(Stream.of("1").collect(Collectors.toSet()));
    HOIScreeningResponse actualResponse = hoiScreeningService.handleFind(hoiScreeningRequest);
    assertThat(actualResponse, is(expectedResponse));

    Iterator<HOIScreening> actualScreenings = actualResponse.getScreenings().iterator();
    Iterator<HOIScreening> expectedScreenings = expectedResponse.getScreenings().iterator();
    while (actualScreenings.hasNext()) {
      HOIScreening actualScreening = actualScreenings.next();
      HOIScreening expectedScreening = expectedScreenings.next();
      assertThat(actualScreening.getAllPeople(), is(expectedScreening.getAllPeople()));
      assertThat(actualScreening.getReporter(), is(expectedScreening.getReporter()));
      assertThat(actualScreening.getAssignedSocialWorker(),
          is(expectedScreening.getAssignedSocialWorker()));
    }
  }

  private HOIScreeningResponse createExpectedResponse() {
    HOIPerson person1 = new HOIPersonResourceBuilder(null).setFirstName("John").setLastName("Smith")
        .createHOIPerson();

    HOIReporter reporter = new HOIReporterResourceBuilder(null).setRole(Role.MANDATED_REPORTER)
        .setFirstName("Alec").setLastName("Nite").setNameSuffix("Jr.").createHOIReporter();

    HOIPerson personReporter = new HOIPersonResourceBuilder(null).setId(DEFAULT_REPORTER_ID)
        .setFirstName("Alec").setLastName("Nite").setNameSuffix("Jr.")
        .setLegacyDescriptor(reporter.getLegacyDescriptor()).createHOIPerson();

    HOISocialWorker socialWorker = new HOISocialWorker(DEFAULT_ASSIGNEE_STAFF_ID, "b", "d", "g",
        expectedSocialWorkerLegacyDescriptor(DEFAULT_ASSIGNEE_STAFF_ID));

    Set<HOIScreening> screenings =
        new TreeSet<>((s1, s2) -> s2.getStartDate().compareTo(s1.getStartDate()));
    screenings.add(new HOIScreeningBuilder().addHOIPerson(personReporter).setReporter(reporter)
        .setSocialWorker(socialWorker).createHOIScreening());
    screenings.add(
        new HOIScreeningBuilder().setId("223").setStartDate("2017-09-25").setEndDate("2017-10-01")
            .addHOIPerson(person1).setSocialWorker(socialWorker).createHOIScreening());

    return new HOIScreeningResponse(screenings);
  }

  private Set<ScreeningEntity> mockScreeningEntityList(String accessRestriction) {
    ParticipantEntity participant1 =
        new ParticipantEntityBuilder().setId(DEFAULT_PERSON_ID).build();

    ScreeningEntity screening1 = new ScreeningEntityBuilder().setId("223")
        .setStartedAt("2017-09-25").setEndedAt("2017-10-01").setIncidentCounty("sacramento")
        .setName(null).setScreeningDecision("promote to referral")
        .setScreeningDecisionDetail("drug counseling").addParticipant(participant1).build();
    screening1.setAccessRestrictions(accessRestriction);

    ParticipantEntity reporter = new ParticipantEntityBuilder().setId(DEFAULT_REPORTER_ID)
        .setFirstName("Alec").setLastName("Nite").setRoles(new String[]{"Mandated Reporter"}).setNameSuffix("Jr.").build();

    ScreeningEntity screening2 = new ScreeningEntityBuilder().setId("224")
        .setStartedAt("2017-11-30").setEndedAt("2017-12-10").setIncidentCounty("sacramento")
        .setName(null).setScreeningDecision("promote to referral")
        .setScreeningDecisionDetail("drug counseling").addParticipant(reporter).build();
    screening2.setAccessRestrictions(accessRestriction);

    Set<ScreeningEntity> result = new HashSet<>();
    result.add(screening1);
    result.add(screening2);
    return result;
  }

  private IntakeLOVCodeEntity mockIntakeLOVCode() {
    IntakeLOVCodeEntity mockIntakeLOVCodeEntity = new IntakeLOVCodeEntity();
    mockIntakeLOVCodeEntity.setLgSysId(1101L);
    mockIntakeLOVCodeEntity.setIntakeDisplay("Sacramento");
    return mockIntakeLOVCodeEntity;
  }

  private LegacyDescriptorEntity mockLegacyDescriptorEntity(String participantId) {
    switch (participantId) {
      case DEFAULT_PERSON_ID:
        return new LegacyDescriptorEntity(DEFAULT_PERSON_ID, "jhdgfkhaj-hohj-jkj",
            LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription(), null, LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT, 1L);
      case DEFAULT_REPORTER_ID:
        return new LegacyDescriptorEntity(DEFAULT_REPORTER_ID, "reporterabc-hohj-jkj",
            LegacyTable.REPORTER.getName(), LegacyTable.REPORTER.getDescription(),null,  LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT, 2L);
      default:
        return null;
    }
  }

  private LegacyDescriptor expectedSocialWorkerLegacyDescriptor(String staffId) {
    CmsRecordDescriptor cmsRecordDescriptor =
        CmsRecordUtils.createLegacyDescriptor(staffId, LegacyTable.STAFF_PERSON);

    return new LegacyDescriptor(cmsRecordDescriptor.getId(), cmsRecordDescriptor.getUiId(), null,
        cmsRecordDescriptor.getTableName(), cmsRecordDescriptor.getTableDescription());
  }
}
