package gov.ca.cwds.rest.api.persistence.legacy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a ReferralClient
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "REFR_CLT")

public class ReferralClient extends PersistentObject {

	@Id
	@Column(name = "IDENTIFIER")
 	private String id;	

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

    @Column(name = "FKREFERL_T")
    private String referralId;

    @Column(name = "FKCLIENT_T")
    private String clientId;

    @Column(name = "DSP_CLSDSC")
    private String dispositionClosureDescription;

    @Type(type = "short")//???
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

	public ReferralClient(gov.ca.cwds.rest.api.domain.ReferralClient referralClient, String lastUpdatedId) {
		super(lastUpdatedId);		
		try{
			this.id = referralClient.getId();
			this.approvalNumber = referralClient.getApprovalNumber();
			this.approvalStatusType = referralClient.getApprovalStatusType();
			this.dispositionClosureReasonType = referralClient.getDispositionClosureReasonType();
			this.dispositionCode = referralClient.getDispositionCode();
			this.dispositionDate = DomainObject.uncookDateString(referralClient.getDispositionDate());
			this.selfReportedIndicator = DomainObject.cookBoolean(referralClient.getSelfReportedIndicator() );
			this.staffPersonAddedIndicator = DomainObject.cookBoolean(referralClient.getStaffPersonAddedIndicator() );
			this.referralId = referralClient.getReferralId();
			this.clientId = referralClient.getClientId();
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
		return getId();
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
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

}
