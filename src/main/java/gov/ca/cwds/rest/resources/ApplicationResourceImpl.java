package gov.ca.cwds.rest.resources;


/**
 * Implementation of {@link ApplicationResource}.
 * 
 * @author CWDS API Team
 */
public class ApplicationResourceImpl implements ApplicationResource {

	private String applicationName;

	/**
	 * Constructor
	 * 
	 * @param applicationName The name of the application
	 */
	public ApplicationResourceImpl(String applicationName) {
		this.applicationName = applicationName;

	}

	/**
	 * Get the name of the application
	 */
	public String getApplicationName() {
		return applicationName;
	}
}
