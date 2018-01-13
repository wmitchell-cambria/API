package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate;

public class DrmsDocumentTemplateEntityBuilder {
	private String thirdId = "1234567ABC";
	private Short applicationContextType = 1234;
	private String documentDOSFilePrefixName = "word";
	private Short governmentEntityType = 2345;
	private String cmsDocumentId = "cmsdocument";
	private String inactive = "N";
	private Short languageType = 1253;
	private Date lastUpdatedTime = new Date();
	private String titleName = "Title of Person";
	private String transactionType = "";
	
	public DrmsDocumentTemplate build() {
		return new DrmsDocumentTemplate( thirdId,  applicationContextType,  documentDOSFilePrefixName,
                 governmentEntityType,  cmsDocumentId,  inactive,  languageType,
                 lastUpdatedTime,  titleName,  transactionType);
	}

	public String getThirdId() {
		return thirdId;
	}
	
	public DrmsDocumentTemplateEntityBuilder setThirdId(String thirdId) {
		this.thirdId = thirdId;
		return this;
	}
	
	public Short getApplicationContextType() {
		return applicationContextType;
	}
	
	public DrmsDocumentTemplateEntityBuilder setApplicationContextType( Short applicationContextType) {
		this.applicationContextType = applicationContextType;
		return this;
	}
	
	public String getDocumentDOSFilePrefixName() {
		return documentDOSFilePrefixName;
	}
	
	public DrmsDocumentTemplateEntityBuilder setDocumentDOSFilePrefixName(String documentDOSFilePrefixName) {
		this.documentDOSFilePrefixName = documentDOSFilePrefixName;
		return this;
	}

	public Short getGovernmentEntityType() {
		return governmentEntityType;
	}
	
	public DrmsDocumentTemplateEntityBuilder setGovernmentEntityType(Short governmentEntityType) {
		this.governmentEntityType = governmentEntityType;
		return this;
	}
	
	public String getCmsDocumentId() {
		return documentDOSFilePrefixName;
	}
	
	public DrmsDocumentTemplateEntityBuilder setCmsDocumentId(String cmsDocumentId) {
		this.cmsDocumentId = cmsDocumentId;
		return this;
	}
	
	public String getInactive() {
		return inactive;
	}

	public DrmsDocumentTemplateEntityBuilder setInactive(String inactive) {
		this.inactive = inactive;
		return this;
	}

	public Short getLanguageType() {
		return languageType;
	}
	
	public DrmsDocumentTemplateEntityBuilder setLanguageType(Short languageType) {
		this.languageType = languageType;
		return this;
	}
	
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	
	public DrmsDocumentTemplateEntityBuilder setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
		return this;
	}
	
	public String getTitleName() {
		return titleName;
	}
	
	public DrmsDocumentTemplateEntityBuilder setTitleName(String titleName) {
		this.titleName = titleName;
		return this;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public DrmsDocumentTemplateEntityBuilder setTransactionType(String transactionType) {
		this.transactionType = transactionType;
		return this;
	}
}
