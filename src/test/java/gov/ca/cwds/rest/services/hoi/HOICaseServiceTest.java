package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
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

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOICaseServiceTest {

  private CaseDao caseDao;
  private ClientDao clientDao;
  private ClientRelationshipDao clientRelationshipDao;
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
    target = new HOICaseService(caseDao, clientDao, clientRelationshipDao);
    request = new HOIRequest();
    request.setClientIds(Stream.of("123").collect(Collectors.toSet()));
  }

  @Test
  public void testHandleFind() throws Exception {
    CmsCase cmscase = new CmsCaseEntityBuilder().setId("TAZGOO205C")
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
    CmsCase[] cases = {cmscase};
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
  public void testFindParentsByPrimaryRelationship() throws Exception {
    CmsCase cmscase = new CmsCaseEntityBuilder().setId("TAZGOO205C")
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
    CmsCase[] cases = {cmscase};
    ClientRelationship relationship =
        new ClientRelationshipEntityBuilder().setClientRelationshipType((short) 245).build();
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

  @Test(expected = NotImplementedException.class)
  public void testHandleRequest() throws Exception {
    target.handleRequest(new HOICase());
  }
}
