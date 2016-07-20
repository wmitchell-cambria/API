package gov.ca.cwds.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

/**
 * Marker for Resources
 * 
 * @author CWDS API Team 
 */
@Produces(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Consumes(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
public interface Resource {}
