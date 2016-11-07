package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.Allegation;
import gov.ca.cwds.rest.jdbi.cms.AllegationDao;
import io.dropwizard.jackson.Jackson;

public class AllegationServiceTest {
  private AllegationService allegationService;
  private AllegationDao allegationDao;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao);
  }

  @Test
  public void findReturnsCorrectAllegationWhenFound() throws Exception {
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation allegation =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation(expected.getId(), expected, "0XA");

    when(allegationDao.find("Aaeae9r0F4")).thenReturn(allegation);

    Allegation found = allegationService.find("Aaeae9r0F4");

    assertThat(found, is(expected));
  }


}
