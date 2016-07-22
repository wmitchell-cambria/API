package gov.ca.cwds.rest.api.persistence;

import io.swagger.annotations.ApiModel;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link PersistentObject} representing a Referral
 * 
 * @author CWDS API Team
 */
@ApiModel
public class Referral extends PersistentObject {

	private String referralName;
	private Date receivedDate;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The id of the referral
	 * @param referralName
	 *            The name of the referral
	 * @param receivedDate
	 *            The date the referral was received
	 */
	@JsonCreator
	public Referral(@JsonProperty("id") String id,
			@JsonProperty("referralName") String referralName,
			@JsonProperty("receivedDate") Date receivedDate) {
		super(id);
		this.referralName = referralName;
		this.receivedDate = receivedDate;
	}

	/**
	 * Get the name of the referral.
	 *
	 * @return the referralName
	 */
	public String getReferralName() {
		return referralName;
	}

	/**
	 * Get the date the referral was received.
	 * 
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((receivedDate == null) ? 0 : receivedDate.hashCode());
		result = prime * result
				+ ((referralName == null) ? 0 : referralName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.ca.cwds.rest.api.persistence.PersistentObject#copy(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public PersistentObject copy(String id, Object from) {
		if (!(from instanceof Referral)) {
			throw new IllegalArgumentException(from.getClass()
					+ " not of type " + Referral.class);
		}
		Referral fromCasted = (Referral) from;
		return new Referral(id, fromCasted.getReferralName(),
				fromCasted.getReceivedDate());
	}
}
