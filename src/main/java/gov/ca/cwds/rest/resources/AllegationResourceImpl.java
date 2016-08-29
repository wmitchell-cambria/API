package gov.ca.cwds.rest.resources;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.glassfish.jersey.server.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.services.AllegationService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

/**
 * Implementation of {@link AllegationResource} delegating work to {@link AllegationResource}
 * 
 * @author CWDS API Team
 */
public class AllegationResourceImpl extends BaseResource<AllegationService>
		implements AllegationResource, CrudsResource<Allegation> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AllegationResourceImpl.class);
	
	private CrudsResource<Allegation> crudsResource;
	
	public AllegationResourceImpl(ServiceEnvironment serviceEnvironment, CrudsResource<Allegation> crudsResource) {
		super(serviceEnvironment, AllegationService.class);
		this.crudsResource = crudsResource;
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Find Allegation by id", response = Allegation.class, responseHeaders = @ResponseHeader(name = "Link", description = "Hypermedia links to contained objects", response = Uri.class))
	public Response get(String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@ApiOperation(value = "Delete Allegation", code = HttpStatus.SC_OK, response = Object.class)
	@Override
	public Response delete(String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update Allegation", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
	public Response update(@Valid Allegation object, String acceptHeader) {
		return crudsResource.update(object, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create Allegation", response = Allegation.class, code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(@Valid Allegation object, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(object, acceptHeader, uriInfo);
	}
}
