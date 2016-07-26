package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.persistence.StaffPerson;
import gov.ca.cwds.rest.services.StaffPersonService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link StaffPersonResource} delegating work to {@link StaffPersonService}
 * 
 * @author CWDS API Team
 */
public class StaffPersonResourceImpl extends BaseResource<StaffPersonService>
		implements StaffPersonResource, CrudsResource<StaffPerson> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(StaffPersonResourceImpl.class);
	
	private CrudsResource<StaffPerson> crudsResource;
	
	public StaffPersonResourceImpl(ServiceEnvironment serviceEnvironment, CrudsResource<StaffPerson> crudsResource) {
		super(serviceEnvironment, StaffPersonService.class);
		this.crudsResource = crudsResource;
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Find Referral by id", response = StaffPerson.class)
	public Response get(String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@ApiOperation(value = "Delete StaffPerson", code = HttpStatus.SC_NO_CONTENT)
	@Override
	public Response delete(String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update StaffPerson", code = 204, response = StaffPerson.class)
	public Response update(StaffPerson persistentObject, String acceptHeader) {
		return crudsResource.update(persistentObject, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create StaffPerson", response = StaffPerson.class, code = 201, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(StaffPerson persistentObject, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(persistentObject, acceptHeader, uriInfo);
	}
}
