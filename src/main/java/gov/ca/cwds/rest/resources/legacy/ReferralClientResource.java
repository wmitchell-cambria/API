package gov.ca.cwds.rest.resources.legacy;

import static gov.ca.cwds.rest.core.ApiPoc.RESOURCE_REFERRAL_CLIENT;

import javax.ws.rs.Path;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;
import io.swagger.annotations.Api;

/**
 * A resource providing a RESTful interface for {@link ReferralClient}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(hidden=true,  value = RESOURCE_REFERRAL_CLIENT, tags = RESOURCE_REFERRAL_CLIENT, produces=gov.ca.cwds.rest.core.ApiPoc.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.ApiPoc.MEDIA_TYPE_JSON_V1)
@Path(value =  RESOURCE_REFERRAL_CLIENT)
@Deprecated
public interface ReferralClientResource {}
