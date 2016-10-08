package gov.ca.cwds.rest.resources.legacy;

import static gov.ca.cwds.rest.core.ApiPoc.RESOURCE_REPORTER;

import javax.ws.rs.Path;

import gov.ca.cwds.rest.api.persistence.legacy.Reporter;
import io.swagger.annotations.Api;

/**
 * A resource providing a RESTful interface for {@link Reporter}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(hidden=true,  value = RESOURCE_REPORTER, tags = RESOURCE_REPORTER, produces=gov.ca.cwds.rest.core.ApiPoc.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.ApiPoc.MEDIA_TYPE_JSON_V1)
@Path(value =  RESOURCE_REPORTER)
@Deprecated
public interface ReporterResource {}