package gov.ca.cwds.rest.resources.legacy;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CROSS_REPORT;

import javax.ws.rs.Path;

import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;
import io.swagger.annotations.Api;

/**
 * A resource providing a RESTful interface for {@link CrossReport}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CROSS_REPORT, produces=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Path(value =  RESOURCE_CROSS_REPORT)
public interface CrossReportResource {

}
