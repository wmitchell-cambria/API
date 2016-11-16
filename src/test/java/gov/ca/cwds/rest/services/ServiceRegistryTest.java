package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;

public class ServiceRegistryTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    ServiceRegistry.clear();
  }

  @Test
  public void instantiation() throws Exception {
    ServiceRegistry target = new ServiceRegistry();
    assertThat(target, notNullValue());
  }

  @Test
  public void registerServiceItsAllGood() throws Exception {
    final Service expected = mock(CmsDocumentService.class);

    // Set:
    final Class<CmsDocument> clazz = CmsDocument.class;
    ServiceRegistry.register(clazz, expected);

    // Get:
    final Service returned = ServiceRegistry.get(clazz);
    assertThat(returned, is(expected));
  }

  @Test
  public void registerServiceUnknownService() throws Exception {
    // Set:
    final Class<CmsDocument> clazz = CmsDocument.class;

    // Get:
    final Service returned = ServiceRegistry.get(clazz);
    assertThat(returned, is(nullValue()));
  }

  @Test
  public void testRegisterNullValue() throws Exception {
    thrown.expect(NullPointerException.class);
    final Class<CmsDocument> clazz = CmsDocument.class;
    ServiceRegistry.register(clazz, null);
  }

  @Test
  public void testGetNullKey() throws Exception {
    thrown.expect(NullPointerException.class);
    final Service expected = mock(CmsDocumentService.class);
    ServiceRegistry.register(null, expected);
  }

  @Test
  public void testRegisterNullKeyValue() throws Exception {
    thrown.expect(NullPointerException.class);
    ServiceRegistry.register(null, null);
  }

  @Test
  public void testRegisterDuplicateService() throws Exception {
    final Service expected = mock(CmsDocumentService.class);
    final Class<CmsDocument> clazz1 = CmsDocument.class;
    final Class<CmsDocReferralClient> clazz2 = CmsDocReferralClient.class;

    ServiceRegistry.register(clazz1, expected);
    ServiceRegistry.register(clazz2, expected);
  }

}
