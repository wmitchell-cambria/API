package gov.ca.cwds.rest.resources;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.legacy.CrossReport;
import gov.ca.cwds.rest.services.CrossReportService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ResponseHeader;

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
	@ApiOperation(value = "Find CrossReport by composite id of referralId and thirdId", response = CrossReport.class)
	public Response get(@ApiParam(required = true, allowMultiple=true, value = "CrossReport has a composite key of referralId and thirdId", example="referralId=abcdefgh,thirdId=td89slaz") String id, String acceptHeader) {
		return crudsResource.get(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@ApiOperation(value = "Delete CrossReport by composite id of referralId and thirdId", code = HttpStatus.SC_OK, response = Object.class)
	@Override
	public Response delete(@ApiParam(required = true, allowMultiple=true, value = "CrossReport has a composite key of referralId and thirdId", example="referralId=abcdefgh,thirdId=td89slaz") String id, String acceptHeader) {
		return crudsResource.delete(id, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String)
	 */
	@Override
	@ApiOperation(value = "Update CrossReport", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
	public Response update(@Valid CrossReport object, String acceptHeader) {
		return crudsResource.update(object, acceptHeader);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@ApiOperation(value = "Create CrossReport", response = CrossReport.class, code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public Response create(@Valid CrossReport object, String acceptHeader, UriInfo uriInfo) {
		return crudsResource.create(object, acceptHeader, uriInfo);
	}
}
