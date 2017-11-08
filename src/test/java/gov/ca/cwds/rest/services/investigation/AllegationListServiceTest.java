package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.dao.investigation.AllegationsDao;
import gov.ca.cwds.fixture.investigation.AllegationListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.AllegationList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

@SuppressWarnings("javadoc")
public class AllegationListServiceTest {
  private static final String DEFAULT_KEY = "1234567ABC";

  private AllegationList stubAllegationList;

  private AllegationListService allegationListService;
  private AllegationsDao allegationsDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");

    this.allegationsDao = mock(AllegationsDao.class);
    allegationListService = new AllegationListService(allegationsDao);

    stubAllegationList = new AllegationListEntityBuilder().build();

  }

  @Test
  public void testFindReturnsStubAllegationList() {
    Response response = allegationListService.find(DEFAULT_KEY);
    assertThat(response, is(equalTo(stubAllegationList)));
  }

  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    allegationListService.create(stubAllegationList);
  }

  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    allegationListService.delete(DEFAULT_KEY);
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    allegationListService.update(DEFAULT_KEY, stubAllegationList);
  }
}
