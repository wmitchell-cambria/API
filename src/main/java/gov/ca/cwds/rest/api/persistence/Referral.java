package gov.ca.cwds.rest.api.persistence;

import io.swagger.annotations.ApiModel;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@ApiModel
public class Referral implements PersistentObject {

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
	public Referral(@JsonProperty("id")String id, @JsonProperty("referralName")String referralName, @JsonProperty("receivedDate")Date receivedDate) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((receivedDate == null) ? 0 : receivedDate.hashCode());
		result = prime * result
				+ ((referralName == null) ? 0 : referralName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Referral other = (Referral) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (receivedDate == null) {
			if (other.receivedDate != null)
				return false;
		} else if (!receivedDate.equals(other.receivedDate))
			return false;
		if (referralName == null) {
			if (other.referralName != null)
				return false;
		} else if (!referralName.equals(other.referralName))
			return false;
		return true;
	}
	
	
	
}
