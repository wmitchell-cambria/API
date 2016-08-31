package gov.ca.cwds.rest.views;

import com.google.common.base.Charsets;

import gov.ca.cwds.rest.SwaggerConfiguration;
import io.dropwizard.views.View;

public class SwaggerView extends View {

	SwaggerConfiguration swaggerConfiguration;
	String swaggerJsonUrl;
	
	public SwaggerView(SwaggerConfiguration swaggerConfiguration, String swaggerJsonUrl) {
		super(swaggerConfiguration.getTemplateName(), Charsets.UTF_8);
		this.swaggerConfiguration = swaggerConfiguration;
		this.swaggerJsonUrl = swaggerJsonUrl;
	}

	public String getAssetsPath() {
		return swaggerConfiguration.getAssetsPath();
	}
	
	public String getTitle() {
		return swaggerConfiguration.getTitle();
	}

	public String getJsonUrl() {
		return swaggerJsonUrl;
	}

}
