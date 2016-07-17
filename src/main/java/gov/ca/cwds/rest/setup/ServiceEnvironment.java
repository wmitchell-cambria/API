package gov.ca.cwds.rest.setup;

import gov.ca.cwds.rest.core.Api;
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
	
	Map<Class<? extends Service>, Map<Api.Version, Service>> services;
	
	/**
	 * Constructor
	 */
	public ServiceEnvironment() {
		services = new HashMap<Class<? extends Service>, Map<Api.Version, Service>>();
	}
	
	/**
	 * Registers a new service.
	 * 
	 * @param clazz		The common superclass/interface the {@link Service} should be registered as
	 * @param version	The {@link Api.Version} this service is implemented under
	 * @param service	The {@link Service} implementation which implements the given {@link Api.Version}
	 */
	public <T extends Service, S extends Service> void register(Class<T> clazz, Api.Version version, Service service ) {
		if( !clazz.isAssignableFrom(service.getClass())) {
			LOGGER.warn("{} is not assignalbeFrom from {}", clazz.getName(), service.getClass().getName());
			throw new IllegalArgumentException("Illegal Superclass and service combination");
		}
		Map<Api.Version, Service> specificServiceMap = services.get(clazz);
		if( specificServiceMap == null ) {
			LOGGER.debug("Tracking Services of type {}" + clazz.getName());
			specificServiceMap = new HashMap<Api.Version, Service>();
			services.put(clazz, specificServiceMap);
		}
		LOGGER.debug("Tracking Service {} - {} - {}", clazz.getName(), version.getMediaType(), service.getClass().getName());
		specificServiceMap.put(version, service);
	}
	
	/**
	 * Get the {@link Service} implementation by {@link Api.Version}
	 * 
	 * @param clazz   The common superclass/interface the {@link Service} was registered under
	 * @param version  The {@link Api.Version} this service is implemented under
	 * @return
	 */
	public <T extends Service> Service getService(Class<T> clazz, Api.Version version) {
		Service service = null;
		
		Map<Api.Version, Service> specificServiceMap = services.get(clazz);
		if( specificServiceMap == null ) {
			LOGGER.warn("Unable to find services tracked for {}", clazz.getName());
		} else {
			service = specificServiceMap.get(version);
			if( service == null ) {
				LOGGER.warn("Unable to find service tracked for {} - {}", clazz.getName(), version.getMediaType());
			}
		}
		
		return service;
	}
}
