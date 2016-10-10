package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_ADDRESSES;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.Address;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

/**
 * A resource providing a RESTful interface for {@link Address}. It delegates
 * CRUD functions to {@link CrudsResourceImpl}
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_ADDRESSES, tags = RESOURCE_ADDRESSES, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
@Path(value = RESOURCE_ADDRESSES)
public class AddressResource implements CrudsResource<Address> {
	private CrudsResourceImpl<Address> crudsResource;

	/**
	 * Constructor
	 * 
	 * @param crudsResource
	 *            The CrudsResource to delegate to.
	 */
	public AddressResource(CrudsResourceImpl<Address> crudsResource) {
		this.crudsResource = crudsResource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Find Address by id", response = Address.class)
	public Response get(String id, String acceptHeader) {
		// return crudsResource.get(id, acceptHeader);
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@ApiOperation(hidden = true, value = "Delete Address - not currently implemented", code = HttpStatus.SC_OK, response = Object.class)
	public Response delete(String id, String acceptHeader) {
		return Response.status(Response.Status.FORBIDDEN).entity(null).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.
	 * domain.DomainObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create Address", code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created Address", response = Address.class))
	public gov.ca.cwds.rest.api.domain.ApiResponse<Address> create(@Valid Address domainObject, String acceptHeader, UriInfo uriInfo, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
	    try {
	        response.flushBuffer();
	    }catch(Exception e){}
		return new gov.ca.cwds.rest.api.domain.ApiResponse<Address>("1", domainObject);
		//return crudsResource.create(domainObject, acceptHeader, uriInfo, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.
	 * domain.DomainObject, java.lang.String)
	 */
	@Override
	@ApiOperation(hidden = true, value = "Update Address", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
	public Response update(Address domainObject, String acceptHeader) {
		// return
		// Response.status(Response.Status.FORBIDDEN).entity(null).build();
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
	}
}
