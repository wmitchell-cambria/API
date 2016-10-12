package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_ADDRESSES;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ApiResponse;
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
	@SuppressWarnings("unused")
	private CrudsResource<Address> crudsResource;

	/**
	 * Constructor
	 * 
	 * @param crudsResource
	 *            The CrudsResource to delegate to.
	 */
	public AddressResource(CrudsResource<Address> crudsResource) {
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
		if (!Arrays.asList(acceptHeader.split(",")).contains(MediaType.APPLICATION_JSON)) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		if ("1".equals(id)) {
			return Response.status(Response.Status.OK)
					.entity(new Address("742 Evergreen Terrace", "Springfield", "WA", 98700)).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(null).build();
		}
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
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
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
		int status = HttpServletResponse.SC_CREATED;
		if (!Arrays.asList(acceptHeader.split(",")).contains(MediaType.APPLICATION_JSON)) {
			status = HttpServletResponse.SC_NOT_ACCEPTABLE;
		}
		response.addHeader("Location", "some_value");
		response.setStatus(status);
		try {
			response.flushBuffer();
		} catch (Exception e) {
		}
		Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
		return new ApiResponse<Address>("1", address);
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
