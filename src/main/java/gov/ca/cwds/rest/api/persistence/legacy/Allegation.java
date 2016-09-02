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

@Entity
@Table(schema = "CWSINT", name = "ALLGTN_T")
public class Allegation extends PersistentObject {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	@Id
	@Column(name = "IDENTIFIER")
	private String id;

	@Type(type = "date")
	@Column(name = "ABUSE_ENDT")
	private Date abuseEndDate;

	@Type(type = "date")
	@Column(name = "ABUSE_STDT")
	private Date abuseStartDate;

	@Column(name = "ABUSE_FREQ")
	private Integer abuseFrequency;

	@Column(name = "ABUSE_PDCD")
	private String abuseFrequencyPeriodCode;

	@Column(name = "LOC_DSC")
	private String abuseLocationDescription;

	@Column(name = "ALG_DSPC")
	private Integer allegationDispositionType;

	@Column(name = "ALG_TPC")
	private Integer allegationType;

	@Column(name = "DISPSN_DSC")
	private String dispositionDescription;

	@Type(type = "date")
	@Column(name = "DISPSN_DT")
	private Date dispositionDate;

	@Column(name = "IJHM_DET_B")
	private String injuryHarmDetailIndicator;

	@Column(name = "NON_PRT_CD")
	private String nonProtectingParentCode;

	@Column(name = "STFADD_IND")
	private String staffPersonAddedIndicator;

	@Column(name = "FKCLIENT_T")
	private String fkClientT;

	@Column(name = "FKCLIENT_0")
	private String fkClient0;

	@Column(name = "FKREFERL_T")
	private String fkReferralT;

	@Column(name = "CNTY_SPFCD")
	private String countySpecificCode;

	@Column(name = "ZIPPY_IND")
	private String zippyCrestedIndicator;

	@Column(name = "PLC_FCLC")
	private Integer placementFacilityType;
	
	/*
	 * Constructor - needed by Hibernate
	 */
	public Allegation() {}

	public Allegation(gov.ca.cwds.rest.api.domain.legacy.Allegation allegation, String lastUpdatedId) {
		super(lastUpdatedId);
		try {
			this.id = allegation.getId();
			this.abuseFrequency = allegation.getAbuseFrequency();
			this.abuseEndDate = DomainObject.uncookDateString(allegation.getAbuseEndDate());
			this.abuseStartDate = DomainObject.uncookDateString(allegation.getAbuseStartDate());
			this.abuseFrequency = allegation.getAbuseFrequency();
			this.abuseFrequencyPeriodCode = allegation.getAbuseFrequencyPeriodCode();
			this.abuseLocationDescription = allegation.getAbuseLocationDescription();
			this.allegationDispositionType = allegation.getAllegationDispositionType();
			this.allegationType = allegation.getAllegationType();
			this.dispositionDescription = allegation.getDispositionDescription();
			this.dispositionDate = DomainObject.uncookDateString(allegation.getDispositionDate());
			this.injuryHarmDetailIndicator = DomainObject.cookBoolean(allegation.getInjuryHarmDetailIndicator());
			this.nonProtectingParentCode = allegation.getNonProtectingParentCode();
			this.staffPersonAddedIndicator = DomainObject.cookBoolean(allegation.getStaffPersonAddedIndicator());
			this.fkClientT = allegation.getFkClientT();
			this.fkClient0 = allegation.getFkClient0();
			this.fkReferralT = allegation.getFkReferralT();
			this.countySpecificCode = allegation.getCountySpecificCode();
			this.zippyCrestedIndicator = DomainObject.cookBoolean(allegation.getZippyCrestedIndicator());
			this.placementFacilityType = allegation.getPlacementFacilityType();
		} catch (DomainException e) {
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
	 * @return the abuseEndDate
	 */
	public Date getAbuseEndDate() {
		return abuseEndDate;
	}

	/**
	 * @return the abuseStartDate
	 */
	public Date getAbuseStartDate() {
		return abuseStartDate;
	}

	/**
	 * @return the abuseFrequency
	 */
	public Integer getAbuseFrequency() {
		return abuseFrequency;
	}

	/**
	 * @return the abuseFrequencyPeriodCode
	 */
	public String getAbuseFrequencyPeriodCode() {
		return abuseFrequencyPeriodCode;
	}

	/**
	 * @return the abuseLocationDescription
	 */
	public String getAbuseLocationDescription() {
		return abuseLocationDescription;
	}

	/**
	 * @return the allegationDispositionType
	 */
	public Integer getAllegationDispositionType() {
		return allegationDispositionType;
	}

	/**
	 * @return the allegationType
	 */
	public Integer getAllegationType() {
		return allegationType;
	}

	/**
	 * @return the dispositionDescription
	 */
	public String getDispositionDescription() {
		return dispositionDescription;
	}

	/**
	 * @return the dispositionDate
	 */
	public Date getDispositionDate() {
		return dispositionDate;
	}

	/**
	 * @return the injuryHarmDetailIndVar
	 */
	public String getInjuryHarmDetailIndicator() {
		return injuryHarmDetailIndicator;
	}

	/**
	 * @return the nonProtectingParentCode
	 */
	public String getNonProtectingParentCode() {
		return nonProtectingParentCode;
	}

	/**
	 * @return the staffPersonAddedInd
	 */
	public String getStaffPersonAddedIndicator() {
		return staffPersonAddedIndicator;
	}

	/**
	 * @return the fkClientT
	 */
	public String getFkClientT() {
		return fkClientT;
	}

	/**
	 * @return the fkClient0
	 */
	public String getFkClient0() {
		return fkClient0;
	}

	/**
	 * @return the fkReferralT
	 */
	public String getFkReferralT() {
		return fkReferralT;
	}

	/**
	 * @return the countySpecificCode
	 */
	public String getCountySpecificCode() {
		return countySpecificCode;
	}

	/**
	 * @return the zippyCrestedIndicator
	 */
	public String getZippyCrestedIndicator() {
		return zippyCrestedIndicator;
	}

	/**
	 * @return the placementFacilityType
	 */
	public Integer getPlacementFacilityType() {
		return placementFacilityType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAbuseEndDate(Date abuseEndDate) {
		this.abuseEndDate = abuseEndDate;
	}

	public void setAbuseStartDate(Date abuseStartDate) {
		this.abuseStartDate = abuseStartDate;
	}

	public void setAbuseFrequency(Integer abuseFrequency) {
		this.abuseFrequency = abuseFrequency;
	}

	public void setAbuseFrequencyPeriodCode(String abuseFrequencyPeriodCode) {
		this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
	}

	public void setAbuseLocationDescription(String abuseLocationDescription) {
		this.abuseLocationDescription = abuseLocationDescription;
	}

	public void setAllegationDispositionType(Integer allegationDispositionType) {
		this.allegationDispositionType = allegationDispositionType;
	}

	public void setAllegationType(Integer allegationType) {
		this.allegationType = allegationType;
	}

	public void setDispositionDescription(String dispositionDescription) {
		this.dispositionDescription = dispositionDescription;
	}

	public void setDispositionDate(Date dispositionDate) {
		this.dispositionDate = dispositionDate;
	}

	public void setInjuryHarmDetailIndicator(String injuryHarmDetailIndicator) {
		this.injuryHarmDetailIndicator = injuryHarmDetailIndicator;
	}

	public void setNonProtectingParentCode(String nonProtectingParentCode) {
		this.nonProtectingParentCode = nonProtectingParentCode;
	}

	public void setStaffPersonAddedIndicator(String staffPersonAddedIndicator) {
		this.staffPersonAddedIndicator = staffPersonAddedIndicator;
	}

	public void setFkClientT(String fkClientT) {
		this.fkClientT = fkClientT;
	}

	public void setFkClient0(String fkClient0) {
		this.fkClient0 = fkClient0;
	}

	public void setFkReferralT(String fkReferralT) {
		this.fkReferralT = fkReferralT;
	}

	public void setCountySpecificCode(String countySpecificCode) {
		this.countySpecificCode = countySpecificCode;
	}

	public void setZippyCrestedIndicator(String zippyCrestedIndicator) {
		this.zippyCrestedIndicator = zippyCrestedIndicator;
	}

	public void setPlacementFacilityType(Integer placementFacilityType) {
		this.placementFacilityType = placementFacilityType;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abuseEndDate == null) ? 0 : abuseEndDate.hashCode());
		result = prime * result + ((abuseFrequency == null) ? 0 : abuseFrequency.hashCode());
		result = prime * result + ((abuseFrequencyPeriodCode == null) ? 0 : abuseFrequencyPeriodCode.hashCode());
		result = prime * result + ((abuseLocationDescription == null) ? 0 : abuseLocationDescription.hashCode());
		result = prime * result + ((abuseStartDate == null) ? 0 : abuseStartDate.hashCode());
		result = prime * result + ((allegationDispositionType == null) ? 0 : allegationDispositionType.hashCode());
		result = prime * result + ((allegationType == null) ? 0 : allegationType.hashCode());
		result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
		result = prime * result + ((dispositionDate == null) ? 0 : dispositionDate.hashCode());
		result = prime * result + ((dispositionDescription == null) ? 0 : dispositionDescription.hashCode());
		result = prime * result + ((fkClient0 == null) ? 0 : fkClient0.hashCode());
		result = prime * result + ((fkClientT == null) ? 0 : fkClientT.hashCode());
		result = prime * result + ((fkReferralT == null) ? 0 : fkReferralT.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((injuryHarmDetailIndicator == null) ? 0 : injuryHarmDetailIndicator.hashCode());
		result = prime * result + ((nonProtectingParentCode == null) ? 0 : nonProtectingParentCode.hashCode());
		result = prime * result + ((placementFacilityType == null) ? 0 : placementFacilityType.hashCode());
		result = prime * result + ((staffPersonAddedIndicator == null) ? 0 : staffPersonAddedIndicator.hashCode());
		result = prime * result + ((zippyCrestedIndicator == null) ? 0 : zippyCrestedIndicator.hashCode());
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
		Allegation other = (Allegation) obj;
		if (abuseEndDate == null) {
			if (other.abuseEndDate != null)
				return false;
		} else if (!abuseEndDate.equals(other.abuseEndDate))
			return false;
		if (abuseFrequency == null) {
			if (other.abuseFrequency != null)
				return false;
		} else if (!abuseFrequency.equals(other.abuseFrequency))
			return false;
		if (abuseFrequencyPeriodCode == null) {
			if (other.abuseFrequencyPeriodCode != null)
				return false;
		} else if (!abuseFrequencyPeriodCode.equals(other.abuseFrequencyPeriodCode))
			return false;
		if (abuseLocationDescription == null) {
			if (other.abuseLocationDescription != null)
				return false;
		} else if (!abuseLocationDescription.trim().equals(other.abuseLocationDescription.trim()))
			return false;
		if (abuseStartDate == null) {
			if (other.abuseStartDate != null)
				return false;
		} else if (!abuseStartDate.equals(other.abuseStartDate))
			return false;
		if (allegationDispositionType == null) {
			if (other.allegationDispositionType != null)
				return false;
		} else if (!allegationDispositionType.equals(other.allegationDispositionType))
			return false;
		if (allegationType == null) {
			if (other.allegationType != null)
				return false;
		} else if (!allegationType.equals(other.allegationType))
			return false;
		if (countySpecificCode == null) {
			if (other.countySpecificCode != null)
				return false;
		} else if (!countySpecificCode.equals(other.countySpecificCode))
			return false;
		if (dispositionDate == null) {
			if (other.dispositionDate != null)
				return false;
		} else if (!dispositionDate.equals(other.dispositionDate))
			return false;
		if (dispositionDescription == null) {
			if (other.dispositionDescription != null)
				return false;
		} else if (!dispositionDescription.trim().equals(other.dispositionDescription.trim()))
			return false;
		if (fkClient0 == null) {
			if (other.fkClient0 != null)
				return false;
		} else if (!fkClient0.equals(other.fkClient0))
			return false;
		if (fkClientT == null) {
			if (other.fkClientT != null)
				return false;
		} else if (!fkClientT.equals(other.fkClientT))
			return false;
		if (fkReferralT == null) {
			if (other.fkReferralT != null)
				return false;
		} else if (!fkReferralT.equals(other.fkReferralT))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (injuryHarmDetailIndicator == null) {
			if (other.injuryHarmDetailIndicator != null)
				return false;
		} else if (!injuryHarmDetailIndicator.equals(other.injuryHarmDetailIndicator))
			return false;
		if (nonProtectingParentCode == null) {
			if (other.nonProtectingParentCode != null)
				return false;
		} else if (!nonProtectingParentCode.equals(other.nonProtectingParentCode))
			return false;
		if (placementFacilityType == null) {
			if (other.placementFacilityType != null)
				return false;
		} else if (!placementFacilityType.equals(other.placementFacilityType))
			return false;
		if (staffPersonAddedIndicator == null) {
			if (other.staffPersonAddedIndicator != null)
				return false;
		} else if (!staffPersonAddedIndicator.equals(other.staffPersonAddedIndicator))
			return false;
		if (zippyCrestedIndicator == null) {
			if (other.zippyCrestedIndicator != null)
				return false;
		} else if (!zippyCrestedIndicator.equals(other.zippyCrestedIndicator))
			return false;
		return true;
	}
}
