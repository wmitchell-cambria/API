package gov.ca.cwds.rest.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import gov.ca.cwds.data.cms.xa.XaCmsAddressDao;
import gov.ca.cwds.data.cms.xa.XaCmsAllegationDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsCaseLoadDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsClientAddressDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsClientDao;
import gov.ca.cwds.data.cms.xa.XaCmsClientRelationshipDao;
import gov.ca.cwds.data.cms.xa.XaCmsReferralClientDao;
import gov.ca.cwds.data.cms.xa.XaCmsReferralDao;
import gov.ca.cwds.data.cms.xa.XaCmsReporterDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsSsaName3Dao;
import gov.ca.cwds.data.cms.xa.XaCmsStaffPersonDao;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
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
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsAddressService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsReferralService;

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

  protected static final ObjectMapper MAPPER = ElasticSearchPerson.MAPPER;

  public static final String DEFAULT_CLIENT_ID = "Jtq8ab8H3N";
  public static final String DEFAULT_PARTICIPANT_ID = "10";

  protected static Validator validator;
  protected static SystemCodeCache systemCodeCache;

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

  public SystemCodeDao systemCodeDao;
  public SystemMetaDao systemMetaDao;

  protected XaCmsAllegationDaoImpl allegationDao;
  protected XaCmsClientDao clientDao;
  protected XaCmsReferralDao referralDao;
  protected XaCmsStaffPersonDao staffpersonDao;
  protected XaNonLACountyTriggers nonLACountyTriggers;
  protected XaCmsAddressDao addressDao;
  protected XaCmsSsaName3Dao ssaName3Dao;
  protected XaCmsClientRelationshipDao clientRelationshipDao;
  protected XaCmsCaseLoadDaoImpl caseLoadDao;
  protected XaCmsReporterDaoImpl reporterDao;
  protected XaCmsReferralClientDao referralClientDao;
  protected XaCmsClientAddressDaoImpl clientAddressDao;

  protected XaUpperCaseTables upperCaseTables;

  protected AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;
  protected AssignmentDao assignmentDao;
  protected AssignmentUnitDao assignmentUnitDao;
  protected CaseDao caseDao;
  protected ChildClientDao childClientDao;
  protected CrossReportDao crossReportDao;
  protected CwsOfficeDao cwsOfficeDao;
  protected DrmsDocumentDao drmsDocumentDao;
  protected LongTextDaoImpl longTextDao;
  protected TriggerTablesDao triggerTablesDao;

  protected LACountyTrigger laCountyTrigger;

  protected AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  protected AllegationService allegationService;
  protected AssignmentService assignmentService;
  protected ChildClientService childClientService;
  protected ClientAddressService clientAddressService;
  protected ClientService clientService;
  protected CrossReportService crossReportService;
  protected LongTextService longTextService;
  protected ParticipantService participantService;
  protected ReferralClientService referralClientService;
  protected ReporterService reporterService;

  protected Reminders reminders;
  protected GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService;

  protected XaCmsReferralService referralService;
  protected XaCmsAddressService addressService;

  protected ScreeningToReferralService screeningToReferralService;

  protected MessageBuilder messageBuilder;

  Subject mockSubject;
  PrincipalCollection principalCollection;
  RequestExecutionContext ctx;

  @BeforeClass
  public static void setupClass() {
    systemCodeCache = new TestSystemCodeCache();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);

    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();

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

    systemCodeDao = mock(SystemCodeDao.class);
    systemMetaDao = mock(SystemMetaDao.class);

    referralDao = mock(XaCmsReferralDao.class);
    staffpersonDao = mock(XaCmsStaffPersonDao.class);
    ssaName3Dao = mock(XaCmsSsaName3Dao.class);
    upperCaseTables = mock(XaUpperCaseTables.class);
    addressDao = mock(XaCmsAddressDao.class);
    clientRelationshipDao = mock(XaCmsClientRelationshipDao.class);
    clientDao = mock(XaCmsClientDao.class);
    allegationDao = mock(XaCmsAllegationDaoImpl.class);
    caseLoadDao = mock(XaCmsCaseLoadDaoImpl.class);
    reporterDao = mock(XaCmsReporterDaoImpl.class);
    referralDao = mock(XaCmsReferralDao.class);
    nonLACountyTriggers = mock(XaNonLACountyTriggers.class);
    referralClientDao = mock(XaCmsReferralClientDao.class);
    clientAddressDao = mock(XaCmsClientAddressDaoImpl.class);

    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    assignmentUnitDao = mock(AssignmentUnitDao.class);
    caseDao = mock(CaseDao.class);
    childClientDao = mock(ChildClientDao.class);
    crossReportDao = mock(CrossReportDao.class);
    cwsOfficeDao = mock(CwsOfficeDao.class);
    drmsDocumentDao = mock(DrmsDocumentDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    longTextDao = mock(LongTextDaoImpl.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);

    reminders = mock(Reminders.class);
    addressService = new XaCmsAddressService(addressDao, ssaName3Dao, upperCaseTables, validator);
    governmentOrganizationCrossReportService = mock(GovernmentOrganizationCrossReportService.class);

    screeningToReferralService =
        new ScreeningToReferralService(referralService, allegationService, crossReportService,
            participantService, Validation.buildDefaultValidatorFactory().getValidator(),
            referralDao, new MessageBuilder(), allegationPerpetratorHistoryService, reminders,
            governmentOrganizationCrossReportService, clientRelationshipDao);

    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
  }

  /**
   * Pass variable arguments of type T.
   * 
   * @param values any number of T values
   * @return mock Hibernate Query of type T
   */
  @SuppressWarnings("unchecked")
  protected Query<T> queryInator(T... values) {
    final Query<T> q = Mockito.mock(Query.class);
    if (values != null && values.length != 0) {
      final T t = ArrayUtils.toArray(values)[0];
      when(session.get(any(Class.class), any(Serializable.class))).thenReturn(t);
      when(session.get(any(String.class), any(Serializable.class))).thenReturn(t);
      when(session.get(any(String.class), any(Serializable.class), any(LockMode.class)))
          .thenReturn(t);
      when(session.get(any(String.class), any(Serializable.class), any(LockOptions.class)))
          .thenReturn(t);
      when(session.get(any(Class.class), any(Serializable.class), any(LockMode.class)))
          .thenReturn(t);
      when(session.get(any(Class.class), any(Serializable.class), any(LockOptions.class)))
          .thenReturn(t);
    }

    final List<T> list = new ArrayList<>();
    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getNamedQuery(any())).thenReturn(q);
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

}
