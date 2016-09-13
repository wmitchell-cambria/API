package gov.ca.cwds.rest.resources.intake;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api(value = gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_REFERRAL, tags = gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_REFERRAL, produces=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Path(gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_REFERRAL)
public interface ReferralResource {

	@POST
	@UnitOfWork
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Unable to process JSON"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported"),
			@ApiResponse(code = 409, message = "Conflict - already exists"),
			@ApiResponse(code = 422, message = "Unable to process entity")
	})
	@ApiOperation(value = "Create Logical Referral", code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Array of links to the newly created objects", response = Object.class))
	public Response create(@ApiParam(required = true, value = "Referral Objects to be created") IntakeReferral intakeReferral, @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader, @Context UriInfo uriInfo);
}
