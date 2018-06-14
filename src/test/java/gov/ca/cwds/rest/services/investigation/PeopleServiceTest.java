package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.fixture.ReferralClientEntityBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.investigation.PeopleEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class PeopleServiceTest extends Doofenshmirtz<Referral> {

  private static final String DEFAULT_KEY = "1234567ABC";
  private People stubPeople;

  private PeopleService target;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    new TestingRequestExecutionContext("0X5");

    target = new PeopleService(peopleDao, reporterDao, clientScpEthnicityDao);
    stubPeople = new PeopleEntityBuilder().build();
  }

  @Test
  public void testGetInvestigationPeople() {
    final String id = "1234567ABC";

    new ReferralClientResourceBuilder().buildReferralClient();
    final gov.ca.cwds.rest.api.domain.cms.Referral domainReferral =
        new ReferralResourceBuilder().build();
    final Referral persistent = new Referral(id, domainReferral, "0X5");
    persistent.addReferralClient(new ReferralClientEntityBuilder().build());

    final Set<Person> persons = target.getInvestigationPeople(persistent);
    assertNotNull(persons);
  }

  @Test
  public void testFindReturnsStubPeople() {
    Response response = target.find(DEFAULT_KEY);
    assertThat(response, is(equalTo(stubPeople)));
  }

  @Test
  public void testCreateReturnsStubPeople() {
    Response response = target.create(stubPeople);
    assertThat(response, is(equalTo(stubPeople)));
  }

  @Test
  public void testDeleteReturnsStubPeople() {
    Response response = target.delete(DEFAULT_KEY);
    assertThat(response, is(equalTo(stubPeople)));

  }

  @Test
  public void testUpdateReturnsStubPeople() {
    Response response = target.update(DEFAULT_KEY, stubPeople);
    assertThat(response, is(equalTo(stubPeople)));
  }

}
