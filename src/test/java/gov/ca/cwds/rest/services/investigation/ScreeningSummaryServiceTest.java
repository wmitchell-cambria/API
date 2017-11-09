package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.fixture.investigation.ScreeningSummaryEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ScreeningSummaryServiceTest {
  private static final String DEFAULT_STUB_KEY = "999999";

  private ScreeningSummaryService screeningSummaryService;
  private ScreeningDao screeningDao;
  private ScreeningSummary screeningSummaryStub;


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    this.screeningDao = mock(ScreeningDao.class);
    screeningSummaryService = new ScreeningSummaryService(screeningDao);
    screeningSummaryStub = new ScreeningSummaryEntityBuilder().build();
  }

  // find test
  @Test
  public void findReturnsExpectedContact() throws Exception {
    // ScreeningSummary serialized = new ScreeningSummary();
    // serialized =
    // MAPPER.readValue(fixture("fixtures/domain/investigation/screening/valid/valid.json"),
    // ScreeningSummary.class);
    Response returned = screeningSummaryService.find(DEFAULT_STUB_KEY);
    assertThat(returned, is(screeningSummaryStub));
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSummaryService.delete(DEFAULT_STUB_KEY);
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSummaryService.update(DEFAULT_STUB_KEY, screeningSummaryStub);
  }


  // create test
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSummaryService.create(screeningSummaryStub);
  }

}
