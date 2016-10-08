package gov.ca.cwds.rest.resources.legacy;

import static gov.ca.cwds.rest.core.ApiPoc.RESOURCE_STAFF_PERSON;
import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * A resource providing a RESTful interface for {@link StaffPerson}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(hidden=true,  value = RESOURCE_STAFF_PERSON, tags = RESOURCE_STAFF_PERSON, produces=gov.ca.cwds.rest.core.ApiPoc.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.ApiPoc.MEDIA_TYPE_JSON_V1)
@Path(value =  RESOURCE_STAFF_PERSON)
@Deprecated
public interface StaffPersonResource {}

