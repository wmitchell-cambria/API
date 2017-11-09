package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.investigation.AllegationList;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

/****
 * NOTE:The CWDS API Team has taken the pattern of delegating Resource functions to
 * 
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 *
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AllegationListResourceTest {

  private AllegationListResource resource;
  private TypedResourceDelegate<String, AllegationList> service;
  private String cmsId = "1234567ABC";


  @SuppressWarnings("unchecked")
  @Before
  public void setUp() {
    service = mock(TypedResourceDelegate.class);
    resource = new AllegationListResource(service);
  }

  @Test
  public void callAllegationtListServiceOnFind() throws Exception {
    resource.find(cmsId);
    verify(service).get(cmsId);
  }

}
