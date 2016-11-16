package gov.ca.cwds.rest.resources.cms;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author CWDS API Team
 */
public interface CmsReferralResource {

  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header/Version not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to process entity")})
  @ApiOperation(value = "Create Logical Referral", code = HttpStatus.SC_CREATED)
  public Response create(@Valid @ApiParam(required = true,
      value = "Referral Objects to be created") CmsReferral cmsReferral);
}
