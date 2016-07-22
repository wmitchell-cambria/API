package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_STAFF_PERSON;
import gov.ca.cwds.rest.api.persistence.StaffPerson;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * A resource providing a RESTful interface for {@link StaffPerson}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_STAFF_PERSON, produces=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Path(value =  RESOURCE_STAFF_PERSON)
public interface StaffPersonResource {}

