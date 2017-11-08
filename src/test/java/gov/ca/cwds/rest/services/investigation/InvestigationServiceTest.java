package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;

@SuppressWarnings("javadoc")
public class InvestigationServiceTest {
  private InvestigationDao investigationDao;
  private StaffPersonDao staffPersonDao;
  private AddressDao addressDao;
  private LongTextService longTextService;
  private PeopleService peopleService;
  private AllegationService allegationService;
  private RelationshipListService relationshipListService;
  private ContactService contactService;
  private HistoryOfInvolvementService hoiService;
  private ScreeningSummaryService screeningSummaryService;

  private InvestigationService investigationService;

  private static final String DEFAULT_KEY = "Anb27uN00I";
  private static final String STUB_DATA_KEY = "999999";

  private Investigation investigationStub;


  @Before
  public void setup() throws Exception {
    this.investigationDao = mock(InvestigationDao.class);
    this.staffPersonDao = mock(StaffPersonDao.class);
    this.addressDao = mock(AddressDao.class);
    this.longTextService = mock(LongTextService.class);
    this.peopleService = mock(PeopleService.class);
    this.allegationService = mock(AllegationService.class);
    this.relationshipListService = mock(RelationshipListService.class);
    this.contactService = mock(ContactService.class);
    this.hoiService = mock(HistoryOfInvolvementService.class);
    this.screeningSummaryService = mock(ScreeningSummaryService.class);

    investigationService = new InvestigationService(investigationDao, staffPersonDao, addressDao,
        longTextService, peopleService, allegationService, relationshipListService, contactService,
        hoiService, screeningSummaryService);

    investigationStub = new InvestigationEntityBuilder().build();

  }

  @Test
  public void type() throws Exception {
    assertThat(InvestigationDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(investigationService, notNullValue());
  }

  @Test
  public void findReturnsInvestigationStub() {
    Response response = investigationService.find(STUB_DATA_KEY);
    assertThat(response, is(equalTo(investigationStub)));

  }

  @Test
  public void deleteReturnsInvestigationStub() {
    Response response = investigationService.delete(DEFAULT_KEY);
    assertThat(response, is(equalTo(investigationStub)));
  }

  @Test
  public void createReturnsInvestigationStub() {
    Response response = investigationService.create(investigationStub);
    assertThat(response, is(equalTo(investigationStub)));

  }

  @Test
  public void updateReturnsInvestigationStub() {
    Response response = investigationService.update(DEFAULT_KEY, investigationStub);
    assertThat(response, is(equalTo(investigationStub)));

  }
}
