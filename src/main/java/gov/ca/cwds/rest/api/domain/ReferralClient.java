package gov.ca.cwds.rest.api.domain;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ReferralClient
 * 
 * @author CWDS API Team
 */
@ApiModel
public class ReferralClient extends DomainObject {

	@NotEmpty
 	private String id;
	
	@JsonCreator
	public ReferralClient(@JsonProperty("id") String id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Aaeae9r0F4")
	public String getId() {
		return id;
	}
}
