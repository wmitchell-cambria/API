package gov.ca.cwds.rest.api;

import io.swagger.annotations.ApiModel;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A response sent back by the CWDS API
 * 
 * @author CWDS API Team
 */
@ApiModel
public class ApiResponse {
	
	private boolean success;
	private Map<String, Object> data;

	/**
	 * Constructor 
	 * 
	 * @param success	true if the requeset was successfully handled, false otherwise
	 */
	@JsonCreator
	public ApiResponse(@JsonProperty("success") boolean success) {
		this.success = success;
	}

	/**
	 * @return "true" if successful, "false" otherwise
	 */
	public String getSuccess() {
		return success ? "true" : "false" ;
	}

	/**
	 * @return the map of objects to return
	 */
	public Map<String, Object> getData() {
		return data;
	}
	
	/**
	 * Add an object to be returned with the response
	 * 
	 * @param name	The name of the object
	 * @param object	The object
	 */
	public void addData(String name, Object object) {
		if( data == null ) {
			data = new HashMap<String, Object>();
		}
		data.put(name, object);
	}
}
