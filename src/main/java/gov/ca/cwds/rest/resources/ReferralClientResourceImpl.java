package gov.ca.cwds.rest.resources;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.ReferralClient;
import gov.ca.cwds.rest.services.ReferralClientService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

/**
 * Implementation of {@link ReferralClientResource} delegating work to {@link ReferralClientService}
 * 
 * @author CWDS API Team
 */
public class ReferralClientResourceImpl extends BaseResource<ReferralClientService>
		implements ReferralClientResource, CrudsResource<ReferralClient> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferralClientResourceImpl.class);
	
	private CrudsResource<ReferralClient> crudsResource;
	
	public ReferralClientResourceImpl(ServiceEnvironment serviceEnvironment, CrudsResource<ReferralClient> crudsResource) {
		super(serviceEnvironment, ReferralClientService.class);
		this.crudsResource = crudsResource;
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Find ReferralClient by id", response = ReferralClient.class)
	public Response get(String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@ApiOperation(value = "Delete ReferralClient", code = HttpStatus.SC_NO_CONTENT)
	@Override
	public Response delete(String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update ReferralClient", code = 204, response = ReferralClient.class)
	public Response update(@Valid ReferralClient persistentObject, String acceptHeader) {
		return crudsResource.update(persistentObject, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create ReferralClient", response = ReferralClient.class, code = 201, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(@Valid ReferralClient persistentObject, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(persistentObject, acceptHeader, uriInfo);
	}
}
