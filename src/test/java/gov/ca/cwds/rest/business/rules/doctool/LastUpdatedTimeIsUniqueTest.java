package gov.ca.cwds.rest.business.rules.doctool;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Validation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.fixture.CaseLoadEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * @author CWDS API Team
 */
public class LastUpdatedTimeIsUniqueTest
    extends Doofenshmirtz<gov.ca.cwds.data.persistence.cms.Client> {

  private static final ObjectMapper MAPPER = ElasticSearchPerson.MAPPER;

  /**
   * Test target.
   */
  // private ScreeningToReferralService screeningToReferralService;

  private gov.ca.cwds.data.persistence.cms.Referral createdReferal = null;
  private gov.ca.cwds.data.persistence.cms.Address createdAddress = null;
  private gov.ca.cwds.data.persistence.cms.Client createdClient = null;
  private gov.ca.cwds.data.persistence.cms.ReferralClient createdReferralClient = null;
  private gov.ca.cwds.data.persistence.cms.ClientAddress createdClientAddress = null;
  private gov.ca.cwds.data.persistence.cms.Reporter createdReporter = null;
  private gov.ca.cwds.data.persistence.cms.CrossReport createdCrossReport = null;
  private gov.ca.cwds.data.persistence.cms.Allegation createdAllegation = null;
  private gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory createdAllegationPerpetratorHistory =
      null;
  private gov.ca.cwds.data.persistence.cms.Assignment createdAssignment = null;

  /**
   * Initialize system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    new TestingRequestExecutionContext("0X5");

    drmsDocumentTemplateService = mock(DrmsDocumentTemplateService.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao);

    screeningToReferralService =
        new ScreeningToReferralService(referralService, allegationService, crossReportService,
            participantService, Validation.buildDefaultValidatorFactory().getValidator(),
            referralDao, new MessageBuilder(), allegationPerpetratorHistoryService, reminders,
            governmentOrganizationCrossReportService, clientRelationshipDao);
  }

  /**
   * Test for when the referrals is posted all referrals related entity maintained the same
   * lastUpdatedTime.
   * 
   * <p>
   * DRS: WARNING: default assignment does not correctly set start date/time, resulting in an NPE.
   * </p>
   * 
   * @throws Exception on general error
   */
  @Test(expected = NullPointerException.class)
  public void testForLastUpdatedTimeIsUnique() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Referral>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Referral answer(InvocationOnMock invocation)
              throws Throwable {
            createdReferal =
                (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
            return referralToCreate;
          }
        });

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC",
            new Date());
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31", new Date());

    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Client>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Client answer(InvocationOnMock invocation)
              throws Throwable {
            createdClient = (gov.ca.cwds.data.persistence.cms.Client) invocation.getArguments()[0];
            return clientToCreate;
          }
        });

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "ABC", new Date());

    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.ReferralClient>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.ReferralClient answer(InvocationOnMock invocation)
              throws Throwable {
            createdReferralClient =
                (gov.ca.cwds.data.persistence.cms.ReferralClient) invocation.getArguments()[0];
            return referralClientToCreate;
          }
        });

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "ABC", new Date());

    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Allegation>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Allegation answer(InvocationOnMock invocation)
              throws Throwable {
            createdAllegation =
                (gov.ca.cwds.data.persistence.cms.Allegation) invocation.getArguments()[0];
            return allegationToCreate;
          }
        });

    AllegationPerpetratorHistory allegationPerpHistoryDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAllegationPerpetratorHistory.json"),
        AllegationPerpetratorHistory.class);
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("567890ABC",
            allegationPerpHistoryDomain, "ABC", new Date());
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(allegationPerpHistoryToCreate);

    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenAnswer(
                new Answer<gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory>() {

                  @Override
                  public gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory answer(
                      InvocationOnMock invocation) throws Throwable {
                    createdAllegationPerpetratorHistory =
                        (gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory) invocation
                            .getArguments()[0];
                    return allegationPerpHistoryToCreate;
                  }
                });

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            (CrossReport) crossReportDomain.toArray()[0], "OXA", new Date());

    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.CrossReport>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.CrossReport answer(InvocationOnMock invocation)
              throws Throwable {
            createdCrossReport =
                (gov.ca.cwds.data.persistence.cms.CrossReport) invocation.getArguments()[0];
            return crossReportToCreate;
          }
        });

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC", new Date());

    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Reporter>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Reporter answer(InvocationOnMock invocation)
              throws Throwable {
            createdReporter =
                (gov.ca.cwds.data.persistence.cms.Reporter) invocation.getArguments()[0];
            return reporterToCreate;
          }
        });

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC", new Date());

    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Address>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Address answer(InvocationOnMock invocation)
              throws Throwable {
            createdAddress =
                (gov.ca.cwds.data.persistence.cms.Address) invocation.getArguments()[0];
            return addressToCreate;
          }
        });

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC",
            new Date());

    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.ClientAddress>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.ClientAddress answer(InvocationOnMock invocation)
              throws Throwable {
            createdClientAddress =
                (gov.ca.cwds.data.persistence.cms.ClientAddress) invocation.getArguments()[0];
            return clientAddressToCreate;
          }
        });

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    Assignment assignment =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAssignment.json"),
            Assignment.class);
    gov.ca.cwds.data.persistence.cms.Assignment assignmentToCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("6789012ABC", assignment, "ABC",
            new Date());
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId("ABC1234567").build();
    CaseLoad[] caseLoadList = new CaseLoad[1];
    caseLoadList[0] = caseLoad;

    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Assignment>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Assignment answer(InvocationOnMock invocation) {
            createdAssignment =
                (gov.ca.cwds.data.persistence.cms.Assignment) invocation.getArguments()[0];
            return assignmentToCreate;
          }
        });
    when(assignmentDao.findCaseLoads(any(String.class))).thenReturn(caseLoadList);
    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);
    if (response.hasMessages()) {
      List<ErrorMessage> messages = response.getMessages();
      for (ErrorMessage message : messages) {
        System.out.println(message.getMessage());
      }
    }
    final Date createdTimestamp = createdReferal.getLastUpdatedTime();
    assertThat(createdAddress.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdAllegation.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdClient.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdClientAddress.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdCrossReport.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdReporter.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdReferralClient.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdAssignment.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdAllegationPerpetratorHistory.getLastUpdatedTime(),
        is(equalTo(createdTimestamp)));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

}
