package gov.ca.cwds.rest.resources.legacy;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.glassfish.jersey.server.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.legacy.StaffPerson;
import gov.ca.cwds.rest.resources.BaseResource;
import gov.ca.cwds.rest.resources.CrudsResource;
import gov.ca.cwds.rest.services.legacy.StaffPersonService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

/**
 * Implementation of {@link StaffPersonResource} delegating work to {@link StaffPersonService}
 * 
 * @author CWDS API Team
 */
@Deprecated
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
	@ApiOperation(value = "Find StaffPerson by id", response = StaffPerson.class, responseHeaders = @ResponseHeader(name = "Link", description = "Hypermedia links to contained objects", response = Uri.class))
	public Response get(String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@ApiOperation(value = "Delete StaffPerson", code = HttpStatus.SC_OK)
	@Override
	public Response delete(String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update StaffPerson", code = 204, response = StaffPerson.class)
	public Response update(@Valid StaffPerson object, String acceptHeader) {
		return crudsResource.update(object, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create StaffPerson", response = Object.class, code = 201, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(@Valid StaffPerson object, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(object, acceptHeader, uriInfo);
	}
}
