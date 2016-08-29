package gov.ca.cwds.rest.api.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReferralClientPk implements Serializable {
	private static final long serialVersionUID = 1L;
	private String referralId;
	private String clientId;

	@JsonCreator
	public ReferralClientPk(@JsonProperty String referralId, @JsonProperty String clientId) {
		this.referralId = referralId;
		this.clientId = clientId;
	}

	/**
	 * @return the referralId
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

}
