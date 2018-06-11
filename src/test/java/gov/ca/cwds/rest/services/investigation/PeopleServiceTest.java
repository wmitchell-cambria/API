package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientScpEthnicityDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.dao.investigation.PeopleDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.investigation.PeopleEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

public class PeopleServiceTest {

  private static final String DEFAULT_KEY = "1234567ABC";
  private People stubPeople;

  private PeopleDao peopleDao;
  private ReporterDao reporterDao;
  private ClientScpEthnicityDao clientScpEthnicityDao;

  private PeopleService peopleService;


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");

    this.peopleDao = mock(PeopleDao.class);
    this.reporterDao = mock(ReporterDao.class);
    this.clientScpEthnicityDao = mock(ClientScpEthnicityDao.class);

    peopleService = new PeopleService(peopleDao, reporterDao, clientScpEthnicityDao);
    stubPeople = new PeopleEntityBuilder().build();
  }

  @Test
  public void testGetInvestigationPeople() {
    String id = "1234567ABC";
    gov.ca.cwds.rest.api.domain.cms.Referral domainReferral = new ReferralResourceBuilder().build();
    Referral persistent = new Referral(id, domainReferral, "0X5");
    Set<Person> persons = peopleService.getInvestigationPeople(persistent);
    assertNotNull(persons);
  }

  @Test
  public void testFindReturnsStubPeople() {
    Response response = peopleService.find(DEFAULT_KEY);
    assertThat(response, is(equalTo(stubPeople)));
  }

  @Test
  public void testCreateReturnsStubPeople() {
    Response response = peopleService.create(stubPeople);
    assertThat(response, is(equalTo(stubPeople)));
  }

  @Test
  public void testDeleteReturnsStubPeople() {
    Response response = peopleService.delete(DEFAULT_KEY);
    assertThat(response, is(equalTo(stubPeople)));

  }

  @Test
  public void testUpdateReturnsStubPeople() {
    Response response = peopleService.update(DEFAULT_KEY, stubPeople);
    assertThat(response, is(equalTo(stubPeople)));
  }

}
