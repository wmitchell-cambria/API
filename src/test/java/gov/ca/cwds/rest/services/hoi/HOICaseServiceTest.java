package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.shiro.authz.AuthorizationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientRelationshipEntityBuilder;
import gov.ca.cwds.fixture.CmsCaseEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOICaseServiceTest {

  private CaseDao caseDao;
  private ClientDao clientDao;
  private ClientRelationshipDao clientRelationshipDao;
  private AuthorizationService authorizationService;
  private HOICaseService target;
  private HOIRequest request;

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test setup
   *
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
    caseDao = mock(CaseDao.class);
    clientDao = mock(ClientDao.class);
    clientRelationshipDao = mock(ClientRelationshipDao.class);
    authorizationService = new AuthorizationService();
    target = new HOICaseService(caseDao, clientDao, clientRelationshipDao, authorizationService);
    request = new HOIRequest();
    request.setClientIds(Stream.of("123").collect(Collectors.toSet()));
  }

  @Test
  public void testHandleFind() throws Exception {
    CmsCase cmscase1 = new CmsCaseEntityBuilder().setId("TAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("FOCUS-CHOLD").build();
    CmsCase cmscase2 = new CmsCaseEntityBuilder().setId("RAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("FC2").build();
    CmsCase[] cases = {cmscase1, cmscase2};
    ClientRelationship relationship = new ClientRelationshipEntityBuilder().build();
    ClientRelationship[] relationships = {relationship};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class))).thenReturn(relationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(relationships);
    Client client = new ClientEntityBuilder().build();
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
    when(clientDao.find(any(String.class))).thenReturn(client);
    HOICaseResponse response = target.handleFind(request);
    assertThat(response, notNullValue());
  }

  @Test
  public void testHandleFindWhenNoClientIdsProvided() throws Exception {
    HOIRequest emptyRequest = new HOIRequest();
    emptyRequest.setClientIds(new HashSet<String>());
    HOICaseResponse response = target.handleFind(emptyRequest);
    assertThat(response, notNullValue());
  }

  @Test
  public void testFindParentsBySecondaryRelationship254MotherSonStepSuccess() throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship((short) 254);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsBySecondaryRelationshipType214FatherSonStepSuccess() throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship((short) 214);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsByPrimaryRelationshipTypeSonDeFactoParent282Success()
      throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsPrimaryClientInTheRelationship((short) 282);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsBySecondaryRelationshipType6361MotherSonPresumedSuccess()
      throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship((short) 6361);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsByPrimaryRelationshipType294SonNonCustodialParentSuccess()
      throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsPrimaryClientInTheRelationship((short) 294);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsBySecondaryRelationshipType273NonCustodialParentSonSuccess()
      throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship((short) 273);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsByPrimaryRelationshipType254MotherSonStepFails() throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsPrimaryClientInTheRelationship((short) 254);
    assertEquals(0, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsByPrimaryRelationshipType296UncleNephewMaternalFails()
      throws Exception {
    HOICaseResponse response =
        getCaseResponseWhereChildIsPrimaryClientInTheRelationship((short) 296);
    assertEquals(0, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsWhereChildIsSecondaryClientInTheRelationshipForAllValidTypesSuccess()
      throws Exception {
    List<Integer> parentToChildTypes = new ArrayList<>();
    parentToChildTypes.addAll(Arrays.asList(272, 273, 5620, 6361));
    for (Integer i = 201; i <= 214; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 245; i <= 254; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer parentToChildType : parentToChildTypes) {
      HOICaseResponse response = getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship(
          parentToChildType.shortValue());
      assertEquals(1, response.getHoiCases().get(0).getParents().size());
    }
  }

  @Test
  public void testFindParentsWhereChildIsPrimaryClientInTheRelationshipForAllValidTypesSuccess()
      throws Exception {
    List<Integer> childToParentTypes = new ArrayList<>();
    childToParentTypes.addAll(Arrays.asList(6360));
    for (Integer i = 187; i <= 200; i++) {
      childToParentTypes.add(i);
    }
    for (Integer i = 282; i <= 294; i++) {
      childToParentTypes.add(i);
    }
    for (Integer childToParentType : childToParentTypes) {
      HOICaseResponse response =
          getCaseResponseWhereChildIsPrimaryClientInTheRelationship(childToParentType.shortValue());
      assertEquals(1, response.getHoiCases().get(0).getParents().size());
    }
  }


  @Test(expected = NotImplementedException.class)
  public void testHandleRequest() throws Exception {
    target.handleRequest(new HOICase());
  }

  public HOICaseResponse getCaseResponseWhereChildIsPrimaryClientInTheRelationship(short type)
      throws Exception {
    CmsCase cmscase = new CmsCaseEntityBuilder().setId("TAZGOO205C")
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("FOCUS-CHILD").build();
    CmsCase[] cases = {cmscase};
    ClientRelationship relationship =
        new ClientRelationshipEntityBuilder().setClientRelationshipType(type).build();
    ClientRelationship[] relationships = {relationship};
    ClientRelationship[] emptyRelationships = {};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class))).thenReturn(relationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(emptyRelationships);
    Client client = new ClientEntityBuilder().build();
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
    when(clientDao.find(any(String.class))).thenReturn(client);
    HOICaseResponse response = target.handleFind(request);
    return response;
  }

  public HOICaseResponse getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship(short type)
      throws Exception {
    CmsCase cmscase = new CmsCaseEntityBuilder().setId("TAZGOO205C")
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("CHILDCLIENT").build();
    CmsCase[] cases = {cmscase};
    ClientRelationship relationship =
        new ClientRelationshipEntityBuilder().setClientRelationshipType(type).build();
    ClientRelationship[] relationships = {relationship};
    ClientRelationship[] emptyRelationships = {};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class)))
        .thenReturn(emptyRelationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(relationships);
    Client client = new ClientEntityBuilder().build();
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
    when(clientDao.find(any(String.class))).thenReturn(client);
    HOICaseResponse response = target.handleFind(request);
    return response;
  }

  @Test(expected = AuthorizationException.class)
  public void testUnAuthorizedClient() {
    HOICaseService spyTarget = spy(target);
    doThrow(AuthorizationException.class).when(spyTarget).authorizeClient("123");
    spyTarget.handleFind(request);
  }

}
