package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.api.domain.DomainChef;

@SuppressWarnings("javadoc")
public class CmsDocReferralClientEntityBuilder {
  private String docHandle = "0131351421120020*JONESMF 00004";
  private String referralId = "1234567ABC";
  private String clientId = "2345678ABC";
  private String commonFirstName = "Art";
  private String commonMiddleName = "Mike";
  private String commonLastName = "Griswald";
  private Date birthDate = DomainChef.uncookDateString("2000-10-31");
  private String otherName = "AR Griswald";
  private String nameType = "AKA";
  private String address = "123 First St";
  private String addressType = "Home";

  public CmsDocReferralClient build() {
    return new CmsDocReferralClient(docHandle, referralId, clientId, commonFirstName,
        commonMiddleName, commonLastName, birthDate, otherName, nameType, address, addressType);
  }

  public CmsDocReferralClientEntityBuilder setDocHandle(String docHandle) {
    this.docHandle = docHandle;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setcommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setOtherName(String otherName) {
    this.otherName = otherName;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setNameType(String nameType) {
    this.nameType = nameType;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setAddress(String address) {
    this.address = address;
    return this;
  }

  public CmsDocReferralClientEntityBuilder setAddressType(String addressType) {
    this.addressType = addressType;
    return this;
  }
}
