package gov.ca.cwds.rest.resources.intake;

import javax.validation.Valid;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

/**
 * A resource providing a RESTful interface for {@link IntakeReferral}.
 * 
 * @author CWDS API Team
 */
public interface ReferralResource {

	@POST
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Unable to process JSON"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported"),
			@ApiResponse(code = 409, message = "Conflict - already exists"),
			@ApiResponse(code = 422, message = "Unable to process entity")
	})
	@ApiOperation(value = "Create Logical Referral", code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Array of links to the newly created objects", response = Object.class))
	public Response create(@Valid  @ApiParam(required = true, value = "Referral Objects to be created") IntakeReferral intakeReferral, @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader, @Context UriInfo uriInfo);
}
