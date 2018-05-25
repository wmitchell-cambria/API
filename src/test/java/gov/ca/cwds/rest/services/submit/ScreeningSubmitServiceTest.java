package gov.ca.cwds.rest.services.submit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.fixture.ScreeningResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.StaffPersonService;
import io.dropwizard.jackson.Jackson;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ScreeningSubmitServiceTest {

  static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Screening screening;
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ScreeningService screenigService;
  @Mock
  private IntakeLovDao intakeLovDao;
  @Mock
  private ScreeningToReferralService screeningToReferralService;
  @Mock
  private StaffPersonService staffPersonService;

  @InjectMocks
  private ScreeningSubmitService screeningSubmitService = new ScreeningSubmitService();

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();


  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    new TestingRequestExecutionContext("0X5");
    SystemCodeCache.global().getAllSystemCodes();

    PostedStaffPerson staffPerson = mock(PostedStaffPerson.class);
    when(staffPerson.getCountyCode()).thenReturn("99");
    when(staffPersonService.find(any())).thenReturn(staffPerson);
    IntakeLov intakeLovInPerson = mock(IntakeLov.class);
    when(intakeLovInPerson.getIntakeCode()).thenReturn("in_person");
    when(intakeLovInPerson.getLegacySystemCodeId()).thenReturn(new Long(408));
    when(intakeLovInPerson.getLegacyLogicalCode()).thenReturn("34");

    IntakeLov intakeLovResponseTime = mock(IntakeLov.class);
    when(intakeLovResponseTime.getIntakeCode()).thenReturn("evaluate_out");
    when(intakeLovResponseTime.getLegacySystemCodeId()).thenReturn(new Long(1519));
    when(intakeLovInPerson.getLegacyLogicalCode()).thenReturn("34");

    IntakeLov intakeLovCountyCode = mock(IntakeLov.class);
    when(intakeLovCountyCode.getIntakeCode()).thenReturn("1101");
    when(intakeLovCountyCode.getLegacySystemCodeId()).thenReturn(new Long(1101));
    when(intakeLovCountyCode.getLegacyLogicalCode()).thenReturn("34");

    IntakeLov intakeLovStateCa = mock(IntakeLov.class);
    when(intakeLovStateCa.getIntakeCode()).thenReturn("CA");
    when(intakeLovStateCa.getLegacySystemCodeId()).thenReturn(new Long(1828));

    List<IntakeLov> intakeLovs = new ArrayList<>();
    intakeLovs.add(intakeLovStateCa);
    intakeLovs.add(intakeLovInPerson);
    intakeLovs.add(intakeLovCountyCode);
    intakeLovs.add(intakeLovResponseTime);

    when(intakeLovDao.findAll()).thenReturn(intakeLovs);

    ScreeningToReferral screeningToReferral = mock(ScreeningToReferral.class);
    when(screeningToReferral.getReferralId()).thenReturn("ABC1234567");
    when(screeningToReferralService.create(any())).thenReturn(screeningToReferral);

    screening = new ScreeningResourceBuilder().build();
  }

  // create test
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSubmitService.create(null);
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSubmitService.delete("string");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSubmitService.update("string", null);
  }

  @Test
  public void testFindThrowsExceptionWhenNotFound() throws Exception {
    when(screenigService.find("000")).thenReturn(null);
    try {
      screeningSubmitService.find("000");
      fail("Expected exception");
    } catch (Exception e) {
    }
  }

  @Test
  public void testFindReturnsUpdatedScreening() throws Exception {

    when(screenigService.getScreening("1")).thenReturn(screening);
    when(screenigService.update(any(), any())).thenReturn(screening);

    Response found = screeningSubmitService.find("1");
    assertThat(found, is(screening));

  }

}
