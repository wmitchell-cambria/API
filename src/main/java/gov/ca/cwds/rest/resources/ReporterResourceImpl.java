package gov.ca.cwds.rest.resources;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.Reporter;
import gov.ca.cwds.rest.services.ReporterService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

/**
 * Implementation of {@link ReporterResource} delegating work to {@link ReporterService}
 * 
 * @author CWDS API Team
 */
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
	@ApiOperation(value = "Find Reporter by id", response = Reporter.class)
	public Response get(String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@ApiOperation(value = "Delete Reporter", code = HttpStatus.SC_NO_CONTENT)
	@Override
	public Response delete(String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update Reporter", code = 204, response = Reporter.class)
	public Response update(@Valid Reporter persistentObject, String acceptHeader) {
		return crudsResource.update(persistentObject, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create Reporter", response = Reporter.class, code = 201, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(@Valid Reporter persistentObject, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(persistentObject, acceptHeader, uriInfo);
	}
}
