package gov.ca.cwds.rest;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SwaggerConfiguration {

    @JsonProperty
    @NotEmpty
    private String resourcePackage;
    
    @JsonProperty
    @NotEmpty
    private String title;
    
    @JsonProperty
    @NotEmpty
    private String description;

	/**
	 * @return the resourcePackage
	 */
	public String getResourcePackage() {
		return resourcePackage;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	

}
