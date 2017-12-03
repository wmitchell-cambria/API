package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.CmsCrossReportResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ReporterResourceBuilder;

@SuppressWarnings("javadoc")
public class PostedCmsReferralTest {

  private PostedReferral referral;
  private PostedClient client;
  private Set<PostedClient> clients = new HashSet<>();
  private PostedAllegation allegation;
  private Set<PostedAllegation> allegations = new HashSet<>();
  private CrossReport crossReport;
  private Set<CrossReport> crossReports = new HashSet<>();
  private ReferralClient referralClient;
  private Set<ReferralClient> referralClients = new HashSet<>();
  private PostedReporter reporter;

  private gov.ca.cwds.data.persistence.cms.Referral persistentReferral;
  private gov.ca.cwds.data.persistence.cms.Client persistentClient;
  private gov.ca.cwds.data.persistence.cms.Allegation persistentAllegation;
  private Reporter domainReporter;
  private gov.ca.cwds.data.persistence.cms.Reporter persistentReporter;
  private String lastUpdatedBy;
  private Date lastUpdatedAt;


  @Before
  public void setup() {
    lastUpdatedBy = "0X5";
    lastUpdatedAt = new Date();

    persistentReferral = new ReferralEntityBuilder().setId("1234567ABC").build();
    referral = new PostedReferral(persistentReferral);

    persistentClient = new ClientEntityBuilder().setId("1234567ABC").build();
    client = new PostedClient(persistentClient, Boolean.TRUE);
    clients.add(client);

    persistentAllegation = new AllegationEntityBuilder().build();
    allegation = new PostedAllegation(persistentAllegation);
    allegations.add(allegation);

    crossReport = new CmsCrossReportResourceBuilder().build();
    crossReports.add(crossReport);

    referralClient = new ReferralClientResourceBuilder().buildReferralClient();
    referralClients.add(referralClient);

    domainReporter = new ReporterResourceBuilder().setReferralId("1234567ABC").build();
    persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(domainReporter, lastUpdatedBy, lastUpdatedAt);
    reporter = new PostedReporter(persistentReporter);
  }

  @Test
  public void testPostedRefferalConstructor() throws Exception {
    PostedCmsReferral postedCmsReferral = new PostedCmsReferral(referral, clients, allegations,
        crossReports, referralClients, reporter);
    assertThat(postedCmsReferral.getReferral(), is(equalTo(referral)));
    assertThat(postedCmsReferral.getClient(), is(equalTo(clients)));
    assertThat(postedCmsReferral.getAllegation(), is(equalTo(allegations)));
    assertThat(postedCmsReferral.getCrossReport(), is(equalTo(crossReports)));
    assertThat(postedCmsReferral.getReporter(), is(equalTo(reporter)));
  }

  @Test
  public void testPostedReferralConstructorWithMessages() throws Exception {
    PostedCmsReferral postedCmsReferral = new PostedCmsReferral(referral, clients, allegations,
        crossReports, referralClients, reporter);
    assertThat(postedCmsReferral, is(not(equalTo(null))));
  }

  @Test
  public void equalsHashCodeWork() {
    // EqualsVerifier.forClass(PostedCmsReferral.class).suppress(Warning.NONFINAL_FIELDS).verify();
    PostedCmsReferral postedCmsReferral = new PostedCmsReferral(referral, clients, allegations,
        crossReports, referralClients, reporter);
    assertThat(postedCmsReferral.hashCode(), is(not(0)));
  }

}
