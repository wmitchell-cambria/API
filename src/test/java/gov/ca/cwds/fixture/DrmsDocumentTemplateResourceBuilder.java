package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate;

/**
 * @author Intake Team 4
 *
 */
@SuppressWarnings("javadoc")
public class DrmsDocumentTemplateResourceBuilder {

  String thirdId = "1234567890ABC";
  Short applicationContextType = 82;
  String documentDOSFilePrefixName = "INALG_NS";
  Short govermentEntityType = 99;
  String cmsDocumentId = "9831181421090420*SMITHBO 00001";
  Short languageType = 1253;
  String titleName = "Screener Narrative";
  String transactionType = "N";


  public DrmsDocumentTemplate build() {
    return new DrmsDocumentTemplate(thirdId, applicationContextType, documentDOSFilePrefixName,
        govermentEntityType, cmsDocumentId, languageType, titleName, transactionType);
  }

  public String getThirdId() {
    return thirdId;
  }

  public Short getApplicationContextType() {
    return applicationContextType;
  }

  public String getDocumentDOSFilePrefixName() {
    return documentDOSFilePrefixName;
  }

  public Short getGovermentEntityType() {
    return govermentEntityType;
  }

  public String getCmsDocumentId() {
    return cmsDocumentId;
  }

  public Short getLanguageType() {
    return languageType;
  }

  public String getTitleName() {
    return titleName;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public DrmsDocumentTemplateResourceBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  public DrmsDocumentTemplateResourceBuilder setApplicationContextType(Short applicationContextType) {
    this.applicationContextType = applicationContextType;
    return this;
  }

  public DrmsDocumentTemplateResourceBuilder setDocumentDOSFilePrefixName(String documentDOSFilePrefixName) {
    this.documentDOSFilePrefixName = documentDOSFilePrefixName;
    return this;
  }

  public DrmsDocumentTemplateResourceBuilder setGovermentEntityType(Short govermentEntityType) {
    this.govermentEntityType = govermentEntityType;
    return this;
  }

  public DrmsDocumentTemplateResourceBuilder setCmsDocumentId(String cmsDocumentId) {
    this.cmsDocumentId = cmsDocumentId;
    return this;
  }

  public DrmsDocumentTemplateResourceBuilder setLanguageType(Short languageType) {
    this.languageType = languageType;
    return this;
  }

  public DrmsDocumentTemplateResourceBuilder setTitleName(String titleName) {
    this.titleName = titleName;
    return this;
  }

  public DrmsDocumentTemplateResourceBuilder setTransactionType(String transactionType) {
    this.transactionType = transactionType;
    return this;
  }
}
