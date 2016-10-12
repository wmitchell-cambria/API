package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENING;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.ApiResponse;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.Screening;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

/**
 * A resource providing a RESTful interface for {@link Screening}. It delegates
 * CRUD functions to {@link CrudsResourceImpl}
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_SCREENING, tags = RESOURCE_SCREENING, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
@Path(value = RESOURCE_SCREENING)
public class ScreeningResource implements CrudsResource<Screening> {
	
	private CrudsResourceImpl<Screening> crudsResourceImpl;

	/**
	 * Constructor
	 */
	public ScreeningResource() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Find Screening by id", response = Screening.class)
	public Response get(String id, String acceptHeader) {
		return Response.status(Response.Status.OK).entity(new ApiResponse<Screening>("1",
			new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative"))).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@ApiOperation(hidden = true, value = "Delete Screening - not currently implemented", code = HttpStatus.SC_OK, response = Object.class)
	public Response delete(String id, String acceptHeader) {
		return Response.status(Response.Status.FORBIDDEN).entity(null).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.
	 * domain.DomainObject, java.lang.String, javax.ws.rs.core.UriInfo,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 400, message = "Unable to process JSON"),
			@io.swagger.annotations.ApiResponse(code = 406, message = "Accept Header not supported"),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Unable to process entity") })
	@ApiOperation(value = "Create Screening", code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created Screening", response = Screening.class))
	public ApiResponse<Screening> create(Screening domainObject, String acceptHeader, UriInfo uriInfo,
			HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
	    try {
	        response.flushBuffer();
	    }catch(Exception e){}
		return new ApiResponse<Screening>("1", 
				new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.
	 * domain.DomainObject, java.lang.String)
	 */
	@Override
	@ApiOperation(hidden = true, value = "Update Screening", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
	public Response update(Screening domainObject, String acceptHeader) {
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
	}
}
