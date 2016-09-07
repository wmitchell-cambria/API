package gov.ca.cwds.rest.resources.intake;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import gov.ca.cwds.rest.resources.BaseResource;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.intake.ReferralService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;

public class ReferralResourceImpl extends BaseResource<ReferralService> implements ReferralResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferralResourceImpl.class);
	
	public ReferralResourceImpl(ServiceEnvironment serviceEnvironment) {
		super(serviceEnvironment, ReferralService.class);
	}

	@Override
	public Response create(IntakeReferral intakeReferral, String acceptHeader, UriInfo uriInfo) {
		ReferralService service = (ReferralService)super.versionedService(acceptHeader);
		if(service == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		try {
			Map<String, Serializable> objects = service.create(intakeReferral);

			//TODO : abstract out the location header creation to something which can be reused for our domain services
			//       maybe follow the model of InjectLinks
//			URI[] locations = new URI[objects.size()];
//			int count = 0;
//			UriBuilder ub = uriInfo.getAbsolutePathBuilder();
//			for(String name : objects.keySet() ) {
//				URI referralUri = ub.
//	                    path(createdIntakeReferral.getReferral().getPr).
//	                    build();
//				
//				
//				count++;
//			}
//			
//			
//
//			
//	        URI referralUri = ub.
//	                    path(createdIntakeReferral.getReferral().getPr).
//	                    build();
			return Response.status(Response.Status.CREATED).build();
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
