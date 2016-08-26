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

	public Allegation(gov.ca.cwds.rest.api.domain.Allegation allegation, String lastUpdatedId) {
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
}
