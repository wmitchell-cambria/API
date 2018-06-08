package gov.ca.cwds.rest.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cfg.Settings;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.loader.BatchFetchStyle;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDaoImpl;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.cms.xa.XaCmsAddressDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsAllegationDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsAllegationPerpetratorHistoryDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsAssignmentDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsAssignmentUnitDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsCaseDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsCaseLoadDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsChildClientDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsClientAddressDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsClientDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsClientRelationshipDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsCrossReportDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsCwsOfficeDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsDocumentDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsDrmsDocumentDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsLongTextDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsReferralClientDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsReferralDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsReporterDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsSsaName3DaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsStaffPersonDaoImpl;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.IntakeLOVCodeDao;
import gov.ca.cwds.data.ns.xa.XaNsAddressDaoImpl;
import gov.ca.cwds.data.ns.xa.XaNsAddressesDaoImpl;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryEntityBuilder;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.business.rules.xa.XaNonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.xa.XaUpperCaseTables;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContextImplTest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.AbstractShiroTest;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientScpEthnicityService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsAddressService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsReferralService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferralClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;

/**
 * <a href="http://phineasandferb.wikia.com/wiki/Heinz_Doofenshmirtz">Heinz Doofenshmirtz</a> is the
 * main villain from Disney's Phineas and Ferb. This wanna-be megalomaniac is melodramatic,
 * clueless, and repeatedly outwitted by Perry. He names his gadgets with the suffix, "-inator".
 * 
 * <p>
 * This test support class offers common mocks and scaffolding for common dependencies, like
 * SessionFactory and Hibernate queries.
 * </p>
 * 
 * @param <T> persistence class type
 * 
 * @author CWDS API Team
 */
public class Doofenshmirtz<T extends PersistentObject> extends AbstractShiroTest {

  public static final ObjectMapper MAPPER = ElasticSearchPerson.MAPPER;

  public static final String DEFAULT_CLIENT_ID = "Jtq8ab8H3N";
  public static final String DEFAULT_PARTICIPANT_ID = "10";

  public static SystemCodeCache systemCodeCache;

  public SessionFactoryImplementor sessionFactoryImplementor;
  public org.hibernate.SessionFactory sessionFactory;
  public Session session;
  public EntityManager em;
  public SessionFactoryOptions sfo;
  public Transaction transaction;
  public StandardServiceRegistry reg;
  public ConnectionProvider cp;
  public Connection con;
  public Statement stmt;
  public ResultSet rs;
  public NativeQuery<T> nq;
  public ProcedureCall proc;
  public Settings settings;
  PreparedStatement prepStmt;

  public Validator validator;
  public ScreeningToReferralResourceBuilder defaultReferralBuilder;

  public SystemCodeDao systemCodeDao;
  public SystemMetaDao systemMetaDao;

  public XaCmsAllegationDaoImpl allegationDao;
  public XaCmsClientDaoImpl clientDao;
  public XaCmsReferralDaoImpl referralDao;
  public XaCmsStaffPersonDaoImpl staffPersonDao;
  public XaNonLACountyTriggers nonLACountyTriggers;
  public XaCmsAddressDaoImpl addressDao;
  public XaCmsSsaName3DaoImpl ssaName3Dao;
  public XaCmsClientRelationshipDaoImpl clientRelationshipDao;
  public XaCmsCaseLoadDaoImpl caseLoadDao;
  public XaCmsReporterDaoImpl reporterDao;
  public XaCmsReferralClientDaoImpl referralClientDao;
  public XaCmsClientAddressDaoImpl clientAddressDao;
  public XaCmsDocumentDaoImpl cmsDocumentDao;
  public XaUpperCaseTables upperCaseTables;
  public XaCmsStaffPersonDaoImpl staffpersonDao;

  public AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;
  public AssignmentDao assignmentDao;
  public AssignmentUnitDao assignmentUnitDao;
  public CaseDao caseDao;
  public ChildClientDao childClientDao;
  public CrossReportDao crossReportDao;
  public CwsOfficeDao cwsOfficeDao;
  public DrmsDocumentDao drmsDocumentDao;
  public LongTextDaoImpl longTextDao;
  public TriggerTablesDao triggerTablesDao;
  public LACountyTrigger laCountyTrigger;
  public IntakeLOVCodeDao intakeLOVCodeDao;

  public RIChildClient riChildClient;
  public RIAllegationPerpetratorHistory riAllegationPerpetratorHistory;
  public RIClientAddress riClientAddress;
  public RIAllegation riAllegation;
  public RICrossReport riCrossReport;
  public RIReporter riReporter;
  public RIReferral riReferral;
  public RIReferralClient riReferralClient;

  public AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  public AllegationService allegationService;
  public AssignmentService assignmentService;
  public ChildClientService childClientService;
  public ClientAddressService clientAddressService;
  public ClientScpEthnicityService clientScpEthnicityService;
  public ClientService clientService;
  public CrossReportService crossReportService;
  public LongTextService longTextService;
  public ParticipantService participantService;
  public ReferralClientService referralClientService;
  public ReporterService reporterService;

  public Reminders reminders;
  public ExternalInterfaceTables externalInterfaceTables;
  public GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService;

  public XaCmsReferralService referralService;
  public XaCmsAddressService addressService;
  public CmsDocumentService cmsDocumentService;

  public Query<CmsDocument> docQuery;
  public CmsDocument doc;

  public DrmsDocumentService drmsDocumentService;
  public DrmsDocumentTemplateService drmsDocumentTemplateService;

  public ScreeningToReferralService screeningToReferralService;

  public XaNsAddressDaoImpl xaNsAddressDao;
  public XaNsAddressesDaoImpl xaNsAddressesDao;

  public MessageBuilder messageBuilder;
  public StaffPerson staffPerson;

  public ElasticsearchConfiguration esConfig = mock(ElasticsearchConfiguration.class);
  public ElasticsearchDao esDao = mock(ElasticsearchDao.class);

  public Subject mockSubject;
  public PrincipalCollection principalCollection;
  public RequestExecutionContext ctx;

  @BeforeClass
  public static void setupClass() {
    systemCodeCache = new TestSystemCodeCache();
  }

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);

    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    // Authentication, authorization:
    messageBuilder = mock(MessageBuilder.class);
    mockSubject = mock(Subject.class);
    principalCollection = mock(PrincipalCollection.class);

    final List list = new ArrayList();
    list.add("msg");
    when(principalCollection.asList()).thenReturn(list);
    when(mockSubject.getPrincipals()).thenReturn(principalCollection);
    setSubject(mockSubject);

    // Request context:
    new RequestExecutionContextImplTest().setup();
    RequestExecutionContextImplTest.startRequest();
    ctx = RequestExecutionContext.instance();

    // Hibernate, JDBC:
    sessionFactoryImplementor = mock(SessionFactoryImplementor.class);
    sessionFactory = mock(org.hibernate.SessionFactory.class);
    session = mock(Session.class);
    transaction = mock(Transaction.class);
    sfo = mock(SessionFactoryOptions.class);
    reg = mock(StandardServiceRegistry.class);
    cp = mock(ConnectionProvider.class);
    con = mock(Connection.class);
    rs = mock(ResultSet.class);
    stmt = mock(Statement.class);
    em = mock(EntityManager.class);
    proc = mock(ProcedureCall.class);

    when(sfo.getBatchFetchStyle()).thenReturn(BatchFetchStyle.DYNAMIC);
    settings = new Settings(sfo, "CWSNS1", "CWSNS1");

    final Map<String, Object> sessionProperties = new HashMap<>();
    sessionProperties.put("hibernate.default_schema", "CWSNS1");

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(sessionFactory.createEntityManager()).thenReturn(em);
    when(sessionFactory.getSessionFactoryOptions()).thenReturn(sfo);
    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(sessionFactory.getProperties()).thenReturn(sessionProperties);

    when(sessionFactoryImplementor.getCurrentSession()).thenReturn(session);
    when(sessionFactoryImplementor.createEntityManager()).thenReturn(em);
    when(sessionFactoryImplementor.getSessionFactoryOptions()).thenReturn(sfo);
    when(sessionFactoryImplementor.getCurrentSession()).thenReturn(session);
    when(sessionFactoryImplementor.getProperties()).thenReturn(sessionProperties);
    when(sessionFactoryImplementor.getSettings()).thenReturn(settings);

    when(session.getSessionFactory()).thenReturn(sessionFactory);
    when(session.getProperties()).thenReturn(sessionProperties);
    when(session.beginTransaction()).thenReturn(transaction);
    when(session.getTransaction()).thenReturn(transaction);
    when(session.createStoredProcedureCall(any(String.class))).thenReturn(proc);

    when(sfo.getServiceRegistry()).thenReturn(reg);
    when(reg.getService(ConnectionProvider.class)).thenReturn(cp);
    when(cp.getConnection()).thenReturn(con);
    when(con.createStatement()).thenReturn(stmt);
    when(stmt.executeQuery(any())).thenReturn(rs);

    // Result set:
    when(rs.next()).thenReturn(true).thenReturn(false);
    when(rs.getString(any())).thenReturn(DEFAULT_CLIENT_ID);
    when(rs.getString(contains("IBMSNAP_OPERATION"))).thenReturn("I");
    when(rs.getString("LIMITED_ACCESS_CODE")).thenReturn("N");
    when(rs.getInt(any())).thenReturn(0);

    final java.util.Date date = new java.util.Date();
    final Timestamp ts = new Timestamp(date.getTime());
    when(rs.getDate(any())).thenReturn(new Date(date.getTime()));
    when(rs.getTimestamp("LIMITED_ACCESS_CODE")).thenReturn(ts);
    when(rs.getTimestamp(any())).thenReturn(ts);

    // Native Query:
    nq = mock(NativeQuery.class);
    when(session.getNamedNativeQuery(any(String.class))).thenReturn(nq);
    when(nq.setString(any(String.class), any(String.class))).thenReturn(nq);
    when(nq.setParameter(any(String.class), any(String.class), any(StringType.class)))
        .thenReturn(nq);
    when(nq.setFlushMode(any(FlushMode.class))).thenReturn(nq);
    when(nq.setHibernateFlushMode(any(FlushMode.class))).thenReturn(nq);
    when(nq.setReadOnly(any(Boolean.class))).thenReturn(nq);
    when(nq.setCacheMode(any(CacheMode.class))).thenReturn(nq);
    when(nq.setFetchSize(any(Integer.class))).thenReturn(nq);
    when(nq.setCacheable(any(Boolean.class))).thenReturn(nq);
    when(nq.addEntity(any(Class.class))).thenReturn(nq);

    final ScrollableResults scrollableResults = mock(ScrollableResults.class);
    when(nq.scroll(any(ScrollMode.class))).thenReturn(scrollableResults);

    prepStmt = mock(PreparedStatement.class);
    when(con.prepareStatement(any(String.class))).thenReturn(prepStmt);
    when(prepStmt.executeUpdate()).thenReturn(10);

    // =================
    // DAO's:
    // =================

    systemCodeDao = mock(SystemCodeDao.class);
    systemMetaDao = mock(SystemMetaDao.class);

    addressDao = mock(XaCmsAddressDaoImpl.class);
    allegationDao = mock(XaCmsAllegationDaoImpl.class);
    allegationPerpetratorHistoryDao = mock(XaCmsAllegationPerpetratorHistoryDaoImpl.class);
    assignmentDao = mock(XaCmsAssignmentDaoImpl.class);
    assignmentUnitDao = mock(XaCmsAssignmentUnitDaoImpl.class);
    caseDao = mock(XaCmsCaseDaoImpl.class);
    caseLoadDao = mock(XaCmsCaseLoadDaoImpl.class);
    childClientDao = mock(XaCmsChildClientDaoImpl.class);
    clientAddressDao = mock(XaCmsClientAddressDaoImpl.class);
    clientDao = mock(XaCmsClientDaoImpl.class);
    clientRelationshipDao = mock(XaCmsClientRelationshipDaoImpl.class);
    cmsDocumentDao = mock(XaCmsDocumentDaoImpl.class);
    crossReportDao = mock(XaCmsCrossReportDaoImpl.class);
    cwsOfficeDao = mock(XaCmsCwsOfficeDaoImpl.class);
    drmsDocumentDao = mock(XaCmsDrmsDocumentDaoImpl.class);
    intakeLOVCodeDao = mock(IntakeLOVCodeDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    longTextDao = mock(XaCmsLongTextDaoImpl.class);
    nonLACountyTriggers = mock(XaNonLACountyTriggers.class);
    referralClientDao = mock(XaCmsReferralClientDaoImpl.class);
    referralDao = mock(XaCmsReferralDaoImpl.class);
    reporterDao = mock(XaCmsReporterDaoImpl.class);
    ssaName3Dao = mock(XaCmsSsaName3DaoImpl.class);
    staffpersonDao = mock(XaCmsStaffPersonDaoImpl.class);
    staffPersonDao = mock(XaCmsStaffPersonDaoImpl.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    upperCaseTables = mock(XaUpperCaseTables.class);
    xaNsAddressDao = mock(XaNsAddressDaoImpl.class);
    xaNsAddressesDao = mock(XaNsAddressesDaoImpl.class);

    when(addressDao.grabSession()).thenReturn(session);
    when(allegationDao.grabSession()).thenReturn(session);
    when(allegationPerpetratorHistoryDao.grabSession()).thenReturn(session);
    when(assignmentDao.grabSession()).thenReturn(session);
    when(assignmentUnitDao.grabSession()).thenReturn(session);
    when(caseDao.grabSession()).thenReturn(session);
    when(caseLoadDao.grabSession()).thenReturn(session);
    when(childClientDao.grabSession()).thenReturn(session);
    when(clientAddressDao.grabSession()).thenReturn(session);
    when(clientDao.grabSession()).thenReturn(session);
    when(clientRelationshipDao.grabSession()).thenReturn(session);
    when(cmsDocumentDao.grabSession()).thenReturn(session);
    when(crossReportDao.grabSession()).thenReturn(session);
    when(cwsOfficeDao.grabSession()).thenReturn(session);
    when(drmsDocumentDao.grabSession()).thenReturn(session);
    when(intakeLOVCodeDao.grabSession()).thenReturn(session);
    when(longTextDao.grabSession()).thenReturn(session);
    when(referralClientDao.grabSession()).thenReturn(session);
    when(referralDao.grabSession()).thenReturn(session);
    when(reporterDao.grabSession()).thenReturn(session);
    when(staffpersonDao.grabSession()).thenReturn(session);
    when(staffPersonDao.grabSession()).thenReturn(session);
    when(xaNsAddressDao.grabSession()).thenReturn(session);
    when(xaNsAddressesDao.grabSession()).thenReturn(session);

    Addresses adr1 = new Addresses(DEFAULT_PARTICIPANT_ID, "123 main street", "Elk Grove", "1838",
        "95757", "32", DEFAULT_CLIENT_ID, "ADDRS_T");
    when(xaNsAddressesDao.find(any())).thenReturn(adr1);
    when(xaNsAddressesDao.create(any())).thenReturn(adr1);
    when(xaNsAddressesDao.update(any())).thenReturn(adr1);

    gov.ca.cwds.data.persistence.ns.Address adr2 = new gov.ca.cwds.data.persistence.ns.Address(10L,
        "123 main street", "Elk Grove", "1838", "95757", "32");
    when(xaNsAddressDao.find(any())).thenReturn(adr2);
    when(xaNsAddressDao.create(any())).thenReturn(adr2);

    gov.ca.cwds.rest.api.domain.cms.Address addressDomain =
        new CmsAddressResourceBuilder().buildCmsAddress();

    gov.ca.cwds.data.persistence.cms.Address adrCms = new gov.ca.cwds.data.persistence.cms.Address(
        DEFAULT_CLIENT_ID, addressDomain, "ABC", new java.util.Date());

    when(addressDao.find(any())).thenReturn(adrCms);
    when(addressDao.update(any())).thenReturn(adrCms);
    when(addressDao.create(any())).thenReturn(adrCms);

    // staffPerson = new StaffPersonEntityBuilder().setId("ZZp").build();
    staffPerson = new StaffPersonEntityBuilder().setId("0X5").setCountyCode("34").build();
    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("52");

    final Reporter reporter = new Reporter("AbiQCgu0AA", "  ", "City", (short) 591, (short) 0, "N",
        null, " ", null, "N", "Fred", "Reporter", "N", 0, 0L, " ", " ", 0L, 0, (short) 1828,
        "Street", "12345", " ", new Integer(95845), null, (short) 0, "51");
    when(reporterDao.create(any(Reporter.class))).thenReturn(reporter);

    riAllegationPerpetratorHistory = mock(RIAllegationPerpetratorHistory.class);
    riAllegation = mock(RIAllegation.class);
    riCrossReport = mock(RICrossReport.class);
    riReporter = mock(RIReporter.class);
    riClientAddress = mock(RIClientAddress.class);
    riChildClient = mock(RIChildClient.class);
    riReferral = mock(RIReferral.class);

    reminders = mock(Reminders.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);

    // =================
    // SERVICES:
    // =================

    defaultReferralBuilder = new ScreeningToReferralResourceBuilder();

    cmsDocumentService = new CmsDocumentService(cmsDocumentDao);
    T t = null;
    docQuery = queryInator(this, Arrays.asList(t).toArray());

    doc = readPersistedDocumentPkCompression();
    when(cmsDocumentDao.grabSession()).thenReturn(session);
    when(cmsDocumentDao.find(any(CmsDocument.class))).thenReturn(doc);
    when(cmsDocumentDao.find(any(String.class))).thenReturn(doc);
    when(cmsDocumentDao.create(any(CmsDocument.class))).thenReturn(doc);
    when(cmsDocumentDao.getSessionFactory()).thenReturn(sessionFactoryImplementor);

    drmsDocumentDao = mock(DrmsDocumentDao.class);
    drmsDocumentTemplateService = mock(DrmsDocumentTemplateService.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao);

    when(caseDao.findAllRelatedByVictimClientId(any(String.class)))
        .thenReturn(createCases(LimitedAccessType.SENSITIVE, LimitedAccessType.NONE));

    final Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createVictimParticipant();
    final Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createPerpParticipant();

    final Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0, true);
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5",
            new java.util.Date());
    savedClient.setLastUpdatedTime(new java.util.Date());
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(clientDao.update(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(savedClient);
    when(referralClientDao.findByClientIds(any(Collection.class)))
        .thenReturn(createReferralClients(LimitedAccessType.NONE, LimitedAccessType.NONE));

    clientScpEthnicityService = mock(ClientScpEthnicityService.class);
    addressService = new XaCmsAddressService(addressDao, ssaName3Dao, upperCaseTables, validator);
    governmentOrganizationCrossReportService = mock(GovernmentOrganizationCrossReportService.class);

    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffPersonDao,
        triggerTablesDao, validator, externalInterfaceTables, caseLoadDao, referralDao,
        assignmentUnitDao, cwsOfficeDao, messageBuilder);

    clientService = new ClientService(clientDao, staffPersonDao, triggerTablesDao,
        nonLACountyTriggers, ssaName3Dao, upperCaseTables, externalInterfaceTables);

    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffPersonDao, riReferralClient);

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new AllegationPerpetratorHistoryEntityBuilder().build();
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(allegationPerpHistoryToCreate);

    allegationService = new AllegationService(allegationDao, riAllegation);
    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, riAllegationPerpetratorHistory);

    crossReportService = new CrossReportService(crossReportDao, riCrossReport);
    reporterService = new ReporterService(reporterDao, riReporter);

    clientAddressService =
        new ClientAddressService(clientAddressDao, staffPersonDao, triggerTablesDao,
            laCountyTrigger, nonLACountyTriggers, riClientAddress, validator, addressService);

    longTextService = new LongTextService(longTextDao);
    childClientService = new ChildClientService(childClientDao, riChildClient);
    participantService = new ParticipantService(clientService, referralClientService,
        reporterService, childClientService, clientAddressService, validator,
        clientScpEthnicityService, caseDao, referralClientDao);

    referralService = new XaCmsReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffPersonDao, assignmentService, validator, cmsDocumentService,
        drmsDocumentService, drmsDocumentTemplateService, addressService, longTextService,
        riReferral);

    screeningToReferralService =
        new ScreeningToReferralService(referralService, allegationService, crossReportService,
            participantService, Validation.buildDefaultValidatorFactory().getValidator(),
            referralDao, new MessageBuilder(), allegationPerpetratorHistoryService, reminders,
            governmentOrganizationCrossReportService, clientRelationshipDao);

    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
  }

  public CmsDocument readPersistedDocumentPkCompression() throws IOException {
    return MAPPER.readValue(
        "{\"id\":\"0131351421120020*JONESMF     00004\",\"segmentCount\":1,\"docLength\":3,\"docAuth\":\"RAMESHA\",\"docServ\":\"D7706001\",\"docDate\":\"2007-01-31\",\"docTime\":\"19:59:07\",\"docName\":\"1234\",\"compressionMethod\":\"PKWare02\",\"blobSegments\":[]}",
        CmsDocument.class);
  }

  public CmsDocument readPersistedDocumentLzwCompression() throws IOException {
    return MAPPER.readValue(
        "{\"id\":\"0131351421120020*JONESMF     00004\",\"segmentCount\":1,\"docLength\":3,\"docAuth\":\"RAMESHA\",\"docServ\":\"D7706001\",\"docDate\":\"2007-01-31\",\"docTime\":\"19:59:07\",\"docName\":\"1234\",\"compressionMethod\":\"COMPRESSION_TYPE_LZW_FULL\",\"blobSegments\":[]}",
        CmsDocument.class);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> Query queryInator(Doofenshmirtz<?> heinz, T... values) {
    final Query<T> q = Mockito.mock(Query.class);
    if (values != null && values.length != 0) {
      final T t = ArrayUtils.toArray(values)[0];
      when(heinz.session.get(any(Class.class), any(Serializable.class))).thenReturn(t);
      when(heinz.session.get(any(String.class), any(Serializable.class))).thenReturn(t);
      when(heinz.session.get(any(String.class), any(Serializable.class), any(LockMode.class)))
          .thenReturn(t);
      when(heinz.session.get(any(String.class), any(Serializable.class), any(LockOptions.class)))
          .thenReturn(t);
      when(heinz.session.get(any(Class.class), any(Serializable.class), any(LockMode.class)))
          .thenReturn(t);
      when(heinz.session.get(any(Class.class), any(Serializable.class), any(LockOptions.class)))
          .thenReturn(t);
    }

    final List<T> list = new ArrayList<>();
    when(heinz.sessionFactory.getCurrentSession()).thenReturn(heinz.session);
    when(heinz.session.getNamedQuery(any())).thenReturn(q);
    when(q.list()).thenReturn(list);

    when(q.setHibernateFlushMode(any(FlushMode.class))).thenReturn(q);
    when(q.setReadOnly(any(Boolean.class))).thenReturn(q);
    when(q.setCacheMode(any(CacheMode.class))).thenReturn(q);
    when(q.setFlushMode(any(FlushMode.class))).thenReturn(q);
    when(q.setFetchSize(any(Integer.class))).thenReturn(q);
    when(q.setCacheable(any(Boolean.class))).thenReturn(q);

    when(q.setParameter(any(String.class), any(String.class))).thenReturn(q);
    when(q.setParameter(any(String.class), any(Long.class))).thenReturn(q);
    when(q.setParameter(any(String.class), any(Set.class))).thenReturn(q);
    when(q.setParameter(any(String.class), any(String.class), any(StringType.class))).thenReturn(q);
    when(q.setString(any(String.class), any(String.class))).thenReturn(q);

    final ScrollableResults results = mock(ScrollableResults.class);
    when(q.scroll(any(ScrollMode.class))).thenReturn(results);
    when(results.next()).thenReturn(true).thenReturn(false);
    when(results.get()).thenReturn(new Object[0]);

    return q;
  }

  /**
   * Pass variable arguments of type T.
   * 
   * @param values any number of T values
   * @return mock Hibernate Query of type T
   */
  @SuppressWarnings("unchecked")
  public Query<T> queryInator(T... values) {
    return Doofenshmirtz.<T>queryInator(this, values);
  }

  private CmsCase[] createCases(LimitedAccessType firstCaseLimitedAccessCode,
      LimitedAccessType secondCaseLimitedAccessCode) {
    CmsCase[] cmsCases = {new CmsCase(), new CmsCase()};
    cmsCases[0].setLimitedAccessCode(firstCaseLimitedAccessCode.getValue());
    cmsCases[1].setLimitedAccessCode(secondCaseLimitedAccessCode.getValue());
    return cmsCases;
  }

  private ReferralClient[] createReferralClients(LimitedAccessType firstReferralLimitedAccessCode,
      LimitedAccessType secondReferralLimitedAccessCode) {
    ReferralClient[] referralClients = {new ReferralClient(), new ReferralClient()};
    referralClients[0].setReferral(new Referral());
    referralClients[0].getReferral()
        .setLimitedAccessCode(firstReferralLimitedAccessCode.getValue());
    referralClients[1].setReferral(new Referral());
    referralClients[1].getReferral()
        .setLimitedAccessCode(secondReferralLimitedAccessCode.getValue());
    return referralClients;
  }

  public Screening makeScreening() {
    return new Screening("abc", "screening", "reference", "screeningDecision",
        "screeningDecisionDetail", "assignee", LocalDateTime.now(), null, "0X5", "");
  }

}
