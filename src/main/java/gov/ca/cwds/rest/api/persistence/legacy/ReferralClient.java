package gov.ca.cwds.rest.api.persistence.legacy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient.PrimaryKey;

/**
 * {@link PersistentObject} representing a ReferralClient
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "REFR_CLT")
@IdClass(PrimaryKey.class)
public class ReferralClient extends PersistentObject {

	@Id
    @Column(name = "FKREFERL_T")
    private String referralId;

	@Id
    @Column(name = "FKCLIENT_T")
    private String clientId;

    @Column(name = "APRVL_NO")
    private String approvalNumber;

    @Type(type = "short")
    @Column(name = "APV_STC")
    private Short approvalStatusType;

    @Type(type = "short")
    @Column(name = "DSP_RSNC")
    private Short dispositionClosureReasonType;

    @Column(name = "DISPSTN_CD")
    private String dispositionCode;

    @Type(type = "date")
    @Column(name = "RCL_DISPDT")
    private Date dispositionDate;

    @Column(name = "SLFRPT_IND")
    private String selfReportedIndicator;

    @Column(name = "STFADD_IND")
    private String staffPersonAddedIndicator;

    @Column(name = "DSP_CLSDSC")
    private String dispositionClosureDescription;

    @Type(type = "short")//???
    @Column(name = "RFCL_AGENO")
    private Short ageNumber;

    @Column(name = "AGE_PRD_CD")
    private String agePeriodCode;

    @Column(name = "CNTY_SPFCD")
    private String countySpecificCode;

    @Column(name = "MHLTH_IND")
    private String mentalHealthIssuesIndicator;

    @Column(name = "ALCHL_IND")
    private String alcoholIndicator;

    @Column(name = "DRUG_IND")
    private String drugIndicator;

    public ReferralClient() {}
    
	public ReferralClient(gov.ca.cwds.rest.api.domain.ReferralClient referralClient, String lastUpdatedId) {
		super(lastUpdatedId);		
		try{
			this.referralId = referralClient.getReferralId();
			this.clientId = referralClient.getClientId();
			this.approvalNumber = referralClient.getApprovalNumber();
			this.approvalStatusType = referralClient.getApprovalStatusType();
			this.dispositionClosureReasonType = referralClient.getDispositionClosureReasonType();
			this.dispositionCode = referralClient.getDispositionCode();
			this.dispositionDate = DomainObject.uncookDateString(referralClient.getDispositionDate());
			this.selfReportedIndicator = DomainObject.cookBoolean(referralClient.getSelfReportedIndicator() );
			this.staffPersonAddedIndicator = DomainObject.cookBoolean(referralClient.getStaffPersonAddedIndicator() );
			this.dispositionClosureDescription = referralClient.getDispositionClosureDescription();
			this.ageNumber = referralClient.getAgeNumber();
			this.agePeriodCode = referralClient.getAgePeriodCode();
			this.countySpecificCode = referralClient.getCountySpecificCode();
			this.mentalHealthIssuesIndicator = DomainObject.cookBoolean(referralClient.getMentalHealthIssuesIndicator() );
			this.alcoholIndicator = DomainObject.cookBoolean(referralClient.getAlcoholIndicator() );
			this.drugIndicator = DomainObject.cookBoolean(referralClient.getDrugIndicator() );

		}catch (DomainException e) {
			throw new PersistenceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		//TODO : what to do with this
		return null;
	}
	
    /**
     * @return the approvalNumber
     */
    public String getApprovalNumber() {
        return approvalNumber;
    }
    
    /**
     * @return the approvalStatusType
     */
    public Short getApprovalStatusType() {
        return approvalStatusType;
    }
    
    /**
     * @return the dispositionClosureReasonType
     */
    public Short getDispositionClosureReasonType() {
        return dispositionClosureReasonType;
    }
    
    /**
     * @return the dispositionCode
     */
    public String getDispositionCode() {
        return dispositionCode;
    }
    
    /**
     * @return the dispositionDate
     */
    public Date getDispositionDate() {
        return dispositionDate;
    }
    
    /**
     * @return the selfReportedIndicator
     */
    public String getSelfReportedIndicator() {
        return selfReportedIndicator;
    }
    
    /**
     * @return the staffPersonAddedIndicator
     */
    public String getStaffPersonAddedIndicator() {
        return staffPersonAddedIndicator;
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
    
    /**
     * @return the dispositionClosureDescription
     */
    public String getDispositionClosureDescription() {
        return dispositionClosureDescription;
    }
    
    /**
     * @return the ageNumber
     */
    public Short getAgeNumber() {
        return ageNumber;
    }
    
    /**
     * @return the agePeriodCode
     */
    public String getAgePeriodCode() {
        return agePeriodCode;
    }
    
    /**
     * @return the countySpecificCode
     */
    public String getCountySpecificCode() {
        return countySpecificCode;
    }
    
    /**
     * @return the mentalHealthIssuesIndicator
     */
    public String getMentalHealthIssuesIndicator() {
        return mentalHealthIssuesIndicator;
    }
    
    /**
     * @return the alcoholIndicator
     */
    public String getAlcoholIndicator() {
        return alcoholIndicator;
    }
    
    /**
     * @return the drugIndicator
     */
    public String getDrugIndicator() {
        return drugIndicator;
    }
    
    public static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;
		private String referralId;
    	private String clientId;
    	
    	public PrimaryKey() {
    	}
    	
    	public PrimaryKey(String referralId, String clientId) {
    		this.referralId = referralId;
    		this.clientId = clientId;
    	}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
			result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
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
			PrimaryKey other = (PrimaryKey) obj;
			if (clientId == null) {
				if (other.clientId != null)
					return false;
			} else if (!clientId.equals(other.clientId))
				return false;
			if (referralId == null) {
				if (other.referralId != null)
					return false;
			} else if (!referralId.equals(other.referralId))
				return false;
			return true;
		}
    }

}
