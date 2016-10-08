package gov.ca.cwds.rest.setup;

import gov.ca.cwds.rest.core.ApiPoc;
import gov.ca.cwds.rest.services.Service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CWDS API Service Environment
 * 
 * @author CWDS API Team
 */
public class ServiceEnvironment {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnvironment.class);
	

	Map<Class<? extends Service>, Map<ApiPoc.Version, Service>> services;
	
	/**
	 * Constructor
	 */
	public ServiceEnvironment() {
		services = new HashMap<Class<? extends Service>, Map<ApiPoc.Version, Service>>();
	}
	
	/**
	 * Registers a new service.
	 * 
	 * @param clazz		The common superclass/interface the {@link Service} should be registered as
	 * @param version	The {@link ApiPoc.Version} this service is implemented under
	 * @param service	The {@link Service} implementation which implements the given {@link ApiPoc.Version}
	 * 
	 * @param <T>	The type of {@link Service} being registered
	 */
	public <T extends Service> void register(Class<T> clazz, ApiPoc.Version version, Service service ) {
		if( !clazz.isAssignableFrom(service.getClass())) {
			LOGGER.warn("{} is not assignalbeFrom from {}", clazz.getName(), service.getClass().getName());
			throw new IllegalArgumentException("Illegal Superclass and service combination");
		}
		Map<ApiPoc.Version, Service> specificServiceMap = services.get(clazz);
		if( specificServiceMap == null ) {
			LOGGER.debug("Tracking Services of type {}" + clazz.getName());
			specificServiceMap = new HashMap<ApiPoc.Version, Service>();
			services.put(clazz, specificServiceMap);
		}
		LOGGER.debug("Tracking Service {} - {} - {}", clazz.getName(), version.getMediaType(), service.getClass().getName());
		specificServiceMap.put(version, service);
	}
	
	/**
	 * Get the {@link Service} implementation by {@link ApiPoc.Version}
	 * 
	 * @param clazz   The common superclass/interface the {@link Service} was registered under
	 * @param mediaType  The mediaType of the {@link ApiPoc.Version} requested
	 * 
	 * @return	The corresponding {@link Service} implementation
	 * 
	 * @param <T>	The type of {@link Service} being requested
	 */
	public <T extends Service> Service getService(Class<T> clazz, String mediaType) {
		Service service = null;
		
		Map<ApiPoc.Version, Service> specificServiceMap = services.get(clazz);
		if( specificServiceMap == null ) {
			LOGGER.warn("Unable to find services tracked for {}", clazz.getName());
		} else {
			ApiPoc.Version version = ApiPoc.Version.findByMediaType(mediaType);
			if( version != null ) {
				service = specificServiceMap.get(version);
			}
			if( service == null ) {
				LOGGER.warn("Unable to find service tracked for {} - {}", clazz.getName(), mediaType);
			}
		}
		
		return service;
	}
}
