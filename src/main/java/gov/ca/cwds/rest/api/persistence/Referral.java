package gov.ca.cwds.rest.api.persistence;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class Referral {

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
	public Referral(String id, String referralName, Date receivedDate) {
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
