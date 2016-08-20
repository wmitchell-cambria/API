package gov.ca.cwds.rest.resources;
import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;
import gov.ca.cwds.rest.services.CrossReportService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link CrossReportResource} delegating work to {@link CrossReportService}
 * 
 * @author CWDS API Team
 */
public class CrossReportResourceImpl extends BaseResource<CrossReportService>
		implements CrossReportResource, CrudsResource<CrossReport> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CrossReportResourceImpl.class);
	
	private CrudsResource<CrossReport> crudsResource;
	
	public CrossReportResourceImpl(ServiceEnvironment serviceEnvironment, CrudsResource<CrossReport> crudsResource) {
		super(serviceEnvironment, CrossReportService.class);
		this.crudsResource = crudsResource;
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Find CrossReport by id", response = CrossReport.class)
	public Response get(String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@ApiOperation(value = "Delete CrossReport", code = HttpStatus.SC_NO_CONTENT)
	@Override
	public Response delete(String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update CrossReport", code = 204, response = CrossReport.class)
	public Response update(@Valid CrossReport persistentObject, String acceptHeader) {
		return crudsResource.update(persistentObject, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.persistence.PersistentObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create CrossReport", response = CrossReport.class, code = 201, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(@Valid CrossReport persistentObject, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(persistentObject, acceptHeader, uriInfo);
	}
}
