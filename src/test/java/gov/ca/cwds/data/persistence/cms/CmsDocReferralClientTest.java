package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.CmsDocReferralClientEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

@SuppressWarnings("javadoc")
public class CmsDocReferralClientTest {

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

  @Test
  public void defaultConstructorTest() throws Exception {
    CmsDocReferralClient document = new CmsDocReferralClient();
    assertThat(document.getClass(), is(equalTo(CmsDocReferralClient.class)));
  }

  @Test
  public void constructorTest() throws Exception {
    CmsDocReferralClient document =
        new CmsDocReferralClient(docHandle, referralId, clientId, commonFirstName, commonMiddleName,
            commonLastName, birthDate, otherName, nameType, address, addressType);
    assertThat(document.getDocHandle(), is(equalTo(docHandle)));
    assertThat(document.getReferlId(), is(equalTo(referralId)));
    assertThat(document.getClientId(), is(equalTo(clientId)));
    assertThat(document.getCommonFirstName(), is(equalTo(commonFirstName)));
    assertThat(document.getCommonMiddleName(), is(equalTo(commonMiddleName)));
    assertThat(document.getCommonLastName(), is(equalTo(commonLastName)));
    assertThat(document.getBirthDate(), is(equalTo(birthDate)));
    assertThat(document.getOtherName(), is(equalTo(otherName)));
    assertThat(document.getNameType(), is(equalTo(nameType)));
    assertThat(document.getAddress(), is(equalTo(address)));
    assertThat(document.getAddressType(), is(equalTo(addressType)));
  }

  @Test
  public void getPrimaryKeyTest() throws Exception {
    CmsDocReferralClient document =
        new CmsDocReferralClient(docHandle, referralId, clientId, commonFirstName, commonMiddleName,
            commonLastName, birthDate, otherName, nameType, address, addressType);
    VarargPrimaryKey primaryKey = new VarargPrimaryKey(docHandle, referralId, clientId);
    assertThat(document.getPrimaryKey(), is(equalTo(primaryKey)));

  }

  @Test
  public void settersTest() throws Exception {
    String newDocHandle = "0131351421120020*BRIAMF 00004";
    String newReferralId = "1234567XYZ";
    String newClientId = "2345678XYZ";
    String newCommonFirstName = "Bart";
    String newCommonMiddleName = "Micheal";
    String newCommonLastName = "Jerry";
    Date newBirthDate = DomainChef.uncookDateString("2002-10-31");
    String newOtherName = "Jerray Micheal";
    String newNameType = "also know as";
    String newAddress = "123 First St";
    String newAddressType = "School";
    String newDocName = "new doc name";
    Date newDocAddedDate = DomainChef.uncookDateString("2002-10-31");

    CmsDocReferralClient document =
        new CmsDocReferralClient(docHandle, referralId, clientId, commonFirstName, commonMiddleName,
            commonLastName, birthDate, otherName, nameType, address, addressType);
    document.setDocHandle(newDocHandle);
    assertThat(document.getDocHandle(), is(equalTo(newDocHandle)));
    document.setDocName(newDocName);
    assertThat(document.getDocName(), is(equalTo(newDocName)));
    document.setDocAddedDate(newDocAddedDate);
    assertThat(document.getDocAddedDate(), is(equalTo(newDocAddedDate)));
    document.setReferlId(newReferralId);
    assertThat(document.getReferlId(), is(equalTo(newReferralId)));
    document.setClientId(newClientId);
    assertThat(document.getClientId(), is(equalTo(newClientId)));
    document.setCommonFirstName(newCommonFirstName);
    assertThat(document.getCommonFirstName(), is(equalTo(newCommonFirstName)));
    document.setCommonMiddleName(newCommonMiddleName);
    assertThat(document.getCommonMiddleName(), is(equalTo(newCommonMiddleName)));
    document.setCommonLastName(newCommonLastName);
    assertThat(document.getCommonLastName(), is(equalTo(newCommonLastName)));
    document.setBirthDate(newBirthDate);
    assertThat(document.getBirthDate(), is(equalTo(newBirthDate)));
    document.setOtherName(newOtherName);
    assertThat(document.getOtherName(), is(equalTo(newOtherName)));
    document.setNameType(newNameType);
    assertThat(document.getNameType(), is(equalTo(newNameType)));
    document.setAddress(newAddress);
    assertThat(document.getOtherName(), is(equalTo(newOtherName)));
    document.setAddressType(newAddressType);
    assertThat(document.getAddressType(), is(equalTo(newAddressType)));
  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
	CmsDocReferralClient cmsDocReferralClient = new CmsDocReferralClient();	  
    assertTrue(cmsDocReferralClient.equals(cmsDocReferralClient));
  }
  
  @Test
  public void shouldHaveSameHashCodesForCmsDocReferralClientWithSameValues() {
	CmsDocReferralClient cmsDocReferralClient = new CmsDocReferralClientEntityBuilder().build();
	CmsDocReferralClient cmsDocReferralClient1 = new CmsDocReferralClientEntityBuilder().build();
	assertEquals("Expecting CmsDocReferralClient objects to have same hash code", cmsDocReferralClient.hashCode(), cmsDocReferralClient1.hashCode());
  }
}
