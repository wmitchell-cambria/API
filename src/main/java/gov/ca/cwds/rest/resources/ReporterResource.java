package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REPORTER;

import javax.ws.rs.Path;

import gov.ca.cwds.rest.api.persistence.legacy.Reporter;
import io.swagger.annotations.Api;

/**
 * A resource providing a RESTful interface for {@link Reporter}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_REPORTER, produces=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Path(value =  RESOURCE_REPORTER)
public interface ReporterResource {}