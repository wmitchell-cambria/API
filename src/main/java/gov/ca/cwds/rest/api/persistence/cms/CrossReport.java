package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.cms.CrossReport.PrimaryKey;

/**
 * {@link PersistentObject} representing a CrossReport
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CRSS_RPT")
@IdClass(PrimaryKey.class)
public class CrossReport extends PersistentObject {
	@Id
	@Column(name = "FKREFERL_T")
	private String referralId;

	@Id
	@Column(name = "THIRD_ID")
	private String thirdId;

	@Type(type = "short")
	@Column(name = "XRPT_MTC")
	private Short crossReportMethodType;

	@Column(name = "OUT_ST_IND")
	private String filedOutOfStateIndicator;

	@Column(name = "GV_XRPT_B")
	private String governmentOrgCrossRptIndicatorVar;

	@Type(type = "time")
	@Column(name = "INFORM_TM")
	private Date informTime;

	@Column(name = "RCPT_BDGNO")
	private String recipientBadgeNumber;

	@Type(type = "integer")
	@Column(name = "RCPT_EXTNO")
	private Integer recipientPhoneExtensionNumber;

	@Column(name = "RCPT_TELNO")
	private BigDecimal recipientPhoneNumber;

	@Type(type = "date")
	@Column(name = "INFORM_DT")
	private Date informDate;

	@Column(name = "POS_TILDSC")
	private String recipientPositionTitleDesc;

	@Column(name = "REFERNC_NO")
	private String referenceNumber;

	@Column(name = "FKLAW_ENFT")
	private String lawEnforcementId;

	@Column(name = "FKSTFPERST")
	private String staffPersonId;

	@Column(name = "XRPT_DSC")
	private String description;

	@Column(name = "RECIPNT_NM")
	private String recipientName;

	@Column(name = "OSLWNFADDR")
	private String outstateLawEnforcementAddr;

	@Column(name = "CNTY_SPFCD")
	private String countySpecificCode;

	@Column(name = "LAW_IND")
	private String lawEnforcementIndicator;

	@Column(name = "OS_LAW_IND")
	private String outStateLawEnforcementIndicator;

	@Column(name = "SXRPT_IND")
	private String satisfyCrossReportIndicator;

	/**
	 * Default constructor
	 * 
	 * Required for Hibernate
	 */
	public CrossReport() {
		super();
	}

	public CrossReport(String referralId, String thirdId, Short crossReportMethodType, String filedOutOfStateIndicator,
			String governmentOrgCrossRptIndicatorVar, Date informTime, String recipientBadgeNumber,
			Integer recipientPhoneExtensionNumber, BigDecimal recipientPhoneNumber, Date informDate,
			String recipientPositionTitleDesc, String referenceNumber, String lawEnforcementId, String staffPersonId,
			String description, String recipientName, String outstateLawEnforcementAddr, String countySpecificCode,
			String lawEnforcementIndicator, String outStateLawEnforcementIndicator,
			String satisfyCrossReportIndicator) {
		super();
		this.referralId = referralId;
		this.thirdId = thirdId;
		this.crossReportMethodType = crossReportMethodType;
		this.filedOutOfStateIndicator = filedOutOfStateIndicator;
		this.governmentOrgCrossRptIndicatorVar = governmentOrgCrossRptIndicatorVar;
		this.informTime = informTime;
		this.recipientBadgeNumber = recipientBadgeNumber;
		this.recipientPhoneExtensionNumber = recipientPhoneExtensionNumber;
		this.recipientPhoneNumber = recipientPhoneNumber;
		this.informDate = informDate;
		this.recipientPositionTitleDesc = recipientPositionTitleDesc;
		this.referenceNumber = referenceNumber;
		this.lawEnforcementId = lawEnforcementId;
		this.staffPersonId = staffPersonId;
		this.description = description;
		this.recipientName = recipientName;
		this.outstateLawEnforcementAddr = outstateLawEnforcementAddr;
		this.countySpecificCode = countySpecificCode;
		this.lawEnforcementIndicator = lawEnforcementIndicator;
		this.outStateLawEnforcementIndicator = outStateLawEnforcementIndicator;
		this.satisfyCrossReportIndicator = satisfyCrossReportIndicator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
	 */
	@Override
	public Serializable getPrimaryKey() {
		return new PrimaryKey(this.getReferralId(), this.getThirdId());
	}

	/**
	 * @return the referralId
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * @return the thirdId
	 */
	public String getThirdId() {
		return thirdId;
	}

	/**
	 * @return the crossReportMethodType
	 */
	public Short getCrossReportMethodType() {
		return crossReportMethodType;
	}

	/**
	 * @return the filedOutOfStateIndicator
	 */
	public String getFiledOutOfStateIndicator() {
		return filedOutOfStateIndicator;
	}

	/**
	 * @return the governmentOrgCrossRptIndicatorVar
	 */
	public String getGovernmentOrgCrossRptIndicatorVar() {
		return governmentOrgCrossRptIndicatorVar;
	}

	/**
	 * @return the informTime
	 */
	public Date getInformTime() {
		return informTime;
	}

	/**
	 * @return the recipientBadgeNumber
	 */
	public String getRecipientBadgeNumber() {
		return recipientBadgeNumber;
	}

	/**
	 * @return the recipientPhoneExtensionNumber
	 */
	public Integer getRecipientPhoneExtensionNumber() {
		return recipientPhoneExtensionNumber;
	}

	/**
	 * @return the recipientPhoneNumber
	 */
	public BigDecimal getRecipientPhoneNumber() {
		return recipientPhoneNumber;
	}

	/**
	 * @return the informDate
	 */
	public Date getInformDate() {
		return informDate;
	}

	/**
	 * @return the recipientPositionTitleDesc
	 */
	public String getRecipientPositionTitleDesc() {
		return recipientPositionTitleDesc;
	}

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @return the lawEnforcementId
	 */
	public String getLawEnforcementId() {
		return lawEnforcementId;
	}

	/**
	 * @return the staffPersonId
	 */
	public String getStaffPersonId() {
		return staffPersonId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}

	/**
	 * @return the outstateLawEnforcementAddr
	 */
	public String getOutstateLawEnforcementAddr() {
		return outstateLawEnforcementAddr;
	}

	/**
	 * @return the countySpecificCode
	 */
	public String getCountySpecificCode() {
		return countySpecificCode;
	}

	/**
	 * @return the lawEnforcementIndicator
	 */
	public String getLawEnforcementIndicator() {
		return lawEnforcementIndicator;
	}

	/**
	 * @return the outStateLawEnforcementIndicator
	 */
	public String getOutStateLawEnforcementIndicator() {
		return outStateLawEnforcementIndicator;
	}

	/**
	 * @return the satisfyCrossReportIndicator
	 */
	public String getSatisfyCrossReportIndicator() {
		return satisfyCrossReportIndicator;
	}

	public static final class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;
		private String referralId;
		private String thirdId;

		public PrimaryKey() {
		}

		public PrimaryKey(String referralId, String thirdId) {
			this.referralId = referralId;
			this.thirdId = thirdId;
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
			result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
			result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
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
			PrimaryKey other = (PrimaryKey) obj;
			if (referralId == null) {
				if (other.referralId != null)
					return false;
			} else if (!referralId.equals(other.referralId))
				return false;
			if (thirdId == null) {
				if (other.thirdId != null)
					return false;
			} else if (!thirdId.equals(other.thirdId))
				return false;
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "referralId=" + referralId.trim() + ",thirdId=" + thirdId.trim();
		}
	}
}
