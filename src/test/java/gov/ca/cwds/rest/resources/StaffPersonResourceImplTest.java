package gov.ca.cwds.rest.resources;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.StaffPersonService;
import gov.ca.cwds.rest.services.StaffPersonServiceImpl;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class StaffPersonResourceImplTest {
	private static final String ID_FOUND = "1";
	private static final String ID_VERIFY = "verify";
	
	private static final String ROOT_RESOURCE = "/staffperson/";
	
	private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND ;
	private static final String VERIFY_RESOURCE = ROOT_RESOURCE + ID_VERIFY ;
	
	private static final StaffPersonServiceImpl staffPersonService = mock(StaffPersonServiceImpl.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);
	@SuppressWarnings("unchecked")
	private static final CrudsResource<StaffPerson> crudsResource = mock(CrudsResource.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new StaffPersonResourceImpl(serviceEnvironment, crudsResource)).build();

	@Before
	public void setup() {
		when(serviceEnvironment.getService(StaffPersonService.class, Api.Version.JSON_VERSION_1.getMediaType())).thenReturn(staffPersonService);
	}

	/*
	 *  delegation check tests
	 */
	@Test
	public void getDelegatestoCrudsResource() {
		StaffPerson verify = createStaffPersonForVerify();
		resources.client().target(VERIFY_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get();
		verify(crudsResource, times(1)).get(verify.getId(), Api.MEDIA_TYPE_JSON_V1);

	}

	@Test
	public void deleteDelegatestoCrudsResource() {
		resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).delete();
		verify(crudsResource, times(1)).delete(any(String.class), any(String.class));
	}

	@Test
	public void createDelegatestoCrudsResource() {
		StaffPerson toCreate = new StaffPerson();
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).create(any(StaffPerson.class), any(String.class), any(UriInfo.class) );
	}

	
	@Test
	public void updateDelegatestoCrudsResource() {
		StaffPerson toCreate = new StaffPerson();
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).put(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).update(any(StaffPerson.class), any(String.class));
	}
	
	/*
	 * Helpers
	 */
	private static StaffPerson createStaffPersonForVerify() {
		StaffPerson toVerify = mock(StaffPerson.class);
		when(toVerify.getId()).thenReturn(ID_VERIFY);
		return toVerify;
	}
}	
