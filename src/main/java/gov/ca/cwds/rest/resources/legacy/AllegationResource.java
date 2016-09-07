package gov.ca.cwds.rest.resources.legacy;

import static gov.ca.cwds.rest.core.Api.RESOURCE_ALLEGATION;
import gov.ca.cwds.rest.api.persistence.legacy.Allegation;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * A resource providing a RESTful interface for {@link Allegation}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_ALLEGATION, produces = gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1, consumes = gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Path(value = RESOURCE_ALLEGATION)
public interface AllegationResource {

}
