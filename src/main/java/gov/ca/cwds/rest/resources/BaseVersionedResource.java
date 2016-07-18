package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.Service;
import gov.ca.cwds.rest.setup.ServiceEnvironment;

/**
 * 
 * @author CWDS API Team
 *
 * @param <T>	The Service subinterface the versioned resource delegates to
 */
@SuppressWarnings("rawtypes")
public abstract class BaseVersionedResource<T extends Service> {
	private ServiceEnvironment serviceEnvironment;
	private Class<T> clazz;
	
	/**
	 * Constructor 
	 * 
	 * @param serviceEnvironment The {@link ServiceEnvironment} for this resource
	 * @param clazz	The Service interface the versioned resource delegates to
	 */
	public BaseVersionedResource(ServiceEnvironment serviceEnvironment, Class<T> clazz) {
		this.serviceEnvironment = serviceEnvironment;
		this.clazz = clazz;
	}
	
	/**
	 * Get the implementation of the service for the given {@link Api.Version}
	 * 
	 * @param version	The mediaType
	 * @return	The implementation of the {@link Service} based on the given mediaType
	 */
	@SuppressWarnings("unchecked")
	protected T versionedService(String mediaType) {
		return (T)serviceEnvironment.getService(clazz, mediaType);
	}

}
