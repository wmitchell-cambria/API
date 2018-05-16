package gov.ca.cwds.rest.services.submit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

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

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.rest.api.Response;
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

  @Before
  public void setup() throws Exception {
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    MockitoAnnotations.initMocks(this);
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
  public void testFindReturnsNullWhenNotFound() throws Exception {
    when(screenigService.find("000")).thenReturn(null);
    Response found = screeningSubmitService.find("000");

    assertThat(found, is(nullValue()));

  }

}
