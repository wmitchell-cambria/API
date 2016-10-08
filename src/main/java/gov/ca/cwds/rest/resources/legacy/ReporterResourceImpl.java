package gov.ca.cwds.rest.resources.legacy;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.legacy.Reporter;
import gov.ca.cwds.rest.resources.BaseResource;
import gov.ca.cwds.rest.resources.CrudsResource;
import gov.ca.cwds.rest.services.legacy.ReporterService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

/**
 * Implementation of {@link ReporterResource} delegating work to {@link ReporterService}
 * 
 * @author CWDS API Team
 */
@Deprecated
public class ReporterResourceImpl extends BaseResource<ReporterService>
		implements ReporterResource, CrudsResource<Reporter> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ReporterResourceImpl.class);
	
	private CrudsResource<Reporter> crudsResource;
	
	public ReporterResourceImpl(ServiceEnvironment serviceEnvironment, CrudsResource<Reporter> crudsResource) {
		super(serviceEnvironment, ReporterService.class);
		this.crudsResource = crudsResource;
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Find Reporter by referralId", response = Reporter.class)
	public Response get(String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Delete Reporter", code = HttpStatus.SC_OK, response = Object.class)
	public Response delete(String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update Reporter", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
	public Response update(@Valid Reporter object, String acceptHeader) {
		return crudsResource.update(object, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create Reporter", response = Object.class, code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(@Valid Reporter object, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(object, acceptHeader, uriInfo);
	}
}
