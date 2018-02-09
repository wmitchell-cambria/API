package gov.ca.cwds.rest.services.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;


import gov.ca.cwds.IntakeBaseTest;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOICaseServiceTest extends IntakeBaseTest {

  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    WebTarget target = clientTestRule.withSecurityToken("security/social-worker-only-principal.json")
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of("-1").collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON);
    Response response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

    String value = IOUtils.toString((InputStream) response.getEntity(), "UTF-8");

    assertEqualJsonArrays("[ ]", value);
  }

  /**
   * Test setup
   * 
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
/*
    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
    caseDao = mock(CaseDao.class);
    clientDao = mock(ClientDao.class);
    clientRelationshipDao = mock(ClientRelationshipDao.class);
    target = new HOICaseService(caseDao, clientDao, clientRelationshipDao);
    request = new HOIRequest();
    request.setClientIds(Stream.of("123").collect(Collectors.toSet()));
*/
  }

/*
  @Test
  public void testHandleFind() throws Exception {
    CmsCase cmscase1 = new CmsCaseEntityBuilder().setId("TAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
    CmsCase cmscase2 = new CmsCaseEntityBuilder().setId("RAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
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
*/

/*
  @Test
  public void testNotPermittedHandleFind() throws Exception {
    HOICaseService spy = spy(target);
    doThrow(new AuthorizationException()).when(spy).authorizeClient(anyString());
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(new CmsCase[] {});

    HOICaseResponse response = spy.handleFind(request);
    assertEquals(response.getHoiCases().size(), 0);
  }
*/

/*
  @Test
  public void testHandleFindWhenNoClientIdsProvided() throws Exception {
    HOIRequest emptyRequest = new HOIRequest();
    emptyRequest.setClientIds(new HashSet<String>());
    HOICaseResponse response = target.handleFind(emptyRequest);
    assertThat(response, notNullValue());
  }
*/

/*
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
    List<Integer> parentToChildTypes = new ArrayList<Integer>();
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
    List<Integer> childToParentTypes = new ArrayList<Integer>();
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
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
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
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
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
*/

/*
  @Test(expected = NotImplementedException.class)
  public void testHandleRequest() throws Exception {
    target.handleRequest(new HOICase());
  }
*/
}
