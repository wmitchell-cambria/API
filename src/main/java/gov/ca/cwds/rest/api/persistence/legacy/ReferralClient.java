package gov.ca.cwds.rest.api.persistence.legacy;

import javax.persistence.Column;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import io.swagger.annotations.ApiModelProperty;

public class ReferralClient extends PersistentObject {

	@Id
	@Column(name = "IDENTIFIER")
	@NotEmpty
 	private String id;
	
	@JsonCreator
	public ReferralClient(@JsonProperty("id") String id) {
		super();
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		return getId();
	}
	
	/**
	 * @return the id
	 */
	@ApiModelProperty(required=true, readOnly=false, value="", example="Aaeae9r0F4")
	public String getId() {
		return id;
	}
}
