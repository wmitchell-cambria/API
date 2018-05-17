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
import gov.ca.cwds.data.dao.investigation.InjuryBodyDetailDao;
import gov.ca.cwds.data.dao.investigation.InjuryHarmDetailDao;
import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

@SuppressWarnings("javadoc")
public class AllegationServiceTest {

  private static final String DEFAULT_KEY = "1234567ABC";

  Allegation stubAllegation;

  private AllegationService allegationService;
  private AllegationsDao allegationDao;

  private InjuryBodyDetailDao injuryBodyDetailDao;
  private InjuryHarmDetailDao injuryHarmDetailDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    this.allegationDao = mock(AllegationsDao.class);
    this.injuryBodyDetailDao = mock(InjuryBodyDetailDao.class);
    this.injuryHarmDetailDao = mock(InjuryHarmDetailDao.class);

    allegationService = new AllegationService(injuryBodyDetailDao, injuryHarmDetailDao);

    stubAllegation = new AllegationEntityBuilder().build();

  }

  @Test
  public void findThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    allegationService.find(DEFAULT_KEY);
  }

  @Test
  public void createReturnsStubAllegation() {
    Response response = allegationService.create(stubAllegation);
    assertThat(response, is(equalTo(stubAllegation)));
  }

  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    allegationService.delete(DEFAULT_KEY);

  }

  @Test
  public void updateReturnsStubAllegation() {
    Response response = allegationService.update(DEFAULT_KEY, stubAllegation);
    assertThat(response, is(equalTo(stubAllegation)));

  }
}
