package gov.ca.cwds.rest.resources.intake;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.hibernate.FlushMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import gov.ca.cwds.rest.resources.BaseResource;
import gov.ca.cwds.rest.resources.filter.EmptyBody;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.intake.ReferralService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;

/**
 * Implementation of {@link ReferralResource}.
 * 
 * @author CWDS API Team
 */
@Api(tags = gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_REFERRAL, produces=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1, consumes=gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Path(gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_REFERRAL)
public class ReferralResourceImpl extends BaseResource<ReferralService> implements ReferralResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferralResourceImpl.class);
	
	public ReferralResourceImpl(ServiceEnvironment serviceEnvironment) {
		super(serviceEnvironment, ReferralService.class);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.intake.ReferralResource#create(gov.ca.cwds.rest.api.domain.intake.IntakeReferral, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@Override
	@UnitOfWork(flushMode=FlushMode.COMMIT, transactional=true)
	@EmptyBody
	public Response create(@Valid IntakeReferral intakeReferral, String acceptHeader, UriInfo uriInfo) {
		ReferralService service = (ReferralService)super.versionedService(acceptHeader);
		if(service == null) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		try {
			@SuppressWarnings("unused")
			Map<String, Serializable> objects = service.create(intakeReferral);

			return Response.ok(intakeReferral).status(Response.Status.CREATED).build();
		} catch (ServiceException e) {
			if( e.getCause() instanceof EntityExistsException ) {
				return Response.status(Response.Status.CONFLICT).entity(null).build();
			} else {
				LOGGER.error("Unable to handle request", e);
				return Response.status(HttpStatus.SC_SERVICE_UNAVAILABLE).entity(null).build();
			}
		}
	}

}
