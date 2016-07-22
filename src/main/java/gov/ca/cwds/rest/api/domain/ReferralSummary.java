package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.persistence.Referral;
import io.swagger.annotations.ApiModel;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
* The ReferralSummary represents a subset of data of a {@link Referral} 
*
* @author  CWDS API Team
*/
@ApiModel
public class ReferralSummary {

	private String id;
	private String referralName;
	private Date receivedDate;

	/**
	 * Constructor 
	 * 
	 * @param id	The id of the summarized referral
	 * @param referralName	The name of the summarized referral
	 * @param receivedDate	The date the summarized referral was received
	 */
	@JsonCreator
	public ReferralSummary(@JsonProperty("id")String id, @JsonProperty("referralName")String referralName, @JsonProperty("receivedDate")Date receivedDate) {
		super();
		this.id = id;
		this.referralName = referralName;
		this.receivedDate = receivedDate;
	}

	/**
	 * Get the id of the summarized referral.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the name of the summarized referral.
	 *
	 * @return the referralName
	 */
	public String getReferralName() {
		return referralName;
	}

	/**
	 * Get the date the summarized referral was received.
	 *  
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}
	

}
