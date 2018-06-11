package gov.ca.cwds.fixture.investigation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class PersonEntityBuilderTest extends Doofenshmirtz<ClientAddress> {

  PersonEntityBuilder target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new PersonEntityBuilder();
  }

  @Test
  public void type() throws Exception {
    assertThat(PersonEntityBuilder.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void build_A$() throws Exception {
    Person actual = target.build();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getCmsRecordDescriptor_A$() throws Exception {
    CmsRecordDescriptor actual = target.getCmsRecordDescriptor();
    CmsRecordDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCmsRecordDescriptor_A$CmsRecordDescriptor() throws Exception {
    CmsRecordDescriptor cmsRecordDescriptor = mock(CmsRecordDescriptor.class);
    PersonEntityBuilder actual = target.setCmsRecordDescriptor(cmsRecordDescriptor);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastUpdatedBy_A$() throws Exception {
    String actual = target.getLastUpdatedBy();
    String expected = "0X5";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedBy_A$String() throws Exception {
    String lastUpdatedBy = null;
    PersonEntityBuilder actual = target.setLastUpdatedBy(lastUpdatedBy);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastUpdatedAt_A$() throws Exception {
    Date actual = target.getLastUpdatedAt();
    Date expected = DomainChef.uncookStrictTimestampString("2016-04-27T23:30:14.000-0000");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedAt_A$Date() throws Exception {
    Date lastUpdatedAt = mock(Date.class);
    PersonEntityBuilder actual = target.setLastUpdatedAt(lastUpdatedAt);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_A$() throws Exception {
    String actual = target.getFirstName();
    String expected = "Art"; // Heaven help us
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_A$String() throws Exception {
    String firstName = null;
    PersonEntityBuilder actual = target.setFirstName(firstName);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_A$() throws Exception {
    String actual = target.getMiddleName();
    String expected = "Mike";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMiddleName_A$String() throws Exception {
    String middleName = null;
    PersonEntityBuilder actual = target.setMiddleName(middleName);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_A$() throws Exception {
    String actual = target.getLastName();
    String expected = "Griswald"; // really?
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_A$String() throws Exception {
    String lastName = null;
    PersonEntityBuilder actual = target.setLastName(lastName);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitle_A$() throws Exception {
    String actual = target.getSuffixTitle();
    String expected = "bs";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSuffixTitle_A$String() throws Exception {
    String suffixTitle = null;
    PersonEntityBuilder actual = target.setSuffixTitle(suffixTitle);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_A$() throws Exception {
    String actual = target.getGender();
    String expected = "M";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setGender_A$String() throws Exception {
    String gender = null;
    PersonEntityBuilder actual = target.setGender(gender);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_A$() throws Exception {
    String actual = target.getBirthDate();

    final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    final String expected = fmt.format(fmt.parse("1998-10-30"));
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setBirthDate_A$String() throws Exception {
    String birthDate = null;
    PersonEntityBuilder actual = target.setBirthDate(birthDate);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_A$() throws Exception {
    String actual = target.getSsn();
    String expected = "999667777";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSsn_A$String() throws Exception {
    String ssn = null;
    PersonEntityBuilder actual = target.setSsn(ssn);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguages_A$() throws Exception {
    Set<String> actual = target.getLanguages();
    Set<String> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLanguages_A$Set() throws Exception {
    Set<String> languages = mock(Set.class);
    PersonEntityBuilder actual = target.setLanguages(languages);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRaceAndEthnicity_A$() throws Exception {
    RaceAndEthnicity actual = target.getRaceAndEthnicity();
    RaceAndEthnicity expected = new RaceAndEthnicityEntityBuilder().build();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRaceAndEthnicity_A$RaceAndEthnicity() throws Exception {
    RaceAndEthnicity raceAndEthnicity = mock(RaceAndEthnicity.class);
    PersonEntityBuilder actual = target.setRaceAndEthnicity(raceAndEthnicity);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSensitive_A$() throws Exception {
    Boolean actual = target.getSensitive();
    Boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSensitive_A$Boolean() throws Exception {
    Boolean sensitive = null;
    PersonEntityBuilder actual = target.setSensitive(sensitive);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSealed_A$() throws Exception {
    Boolean actual = target.getSealed();
    Boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSealed_A$Boolean() throws Exception {
    Boolean sealed = null;
    PersonEntityBuilder actual = target.setSealed(sealed);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumber_A$() throws Exception {
    Long actual = target.getPhoneNumber();
    Long expected = 1233219876L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhoneNumber_A$Long() throws Exception {
    Long phoneNumber = null;
    PersonEntityBuilder actual = target.setPhoneNumber(phoneNumber);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneCmsRecordDescriptor_A$() throws Exception {
    CmsRecordDescriptor actual = target.getPhoneCmsRecordDescriptor();
    CmsRecordDescriptor expected =
        new CmsRecordDescriptor("1234567ABC", "001-2000-3399-415790", "CLIENT_T", "Client");;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhoneCmsRecordDescriptor_A$CmsRecordDescriptor() throws Exception {
    CmsRecordDescriptor phoneCmsRecordDescriptor = mock(CmsRecordDescriptor.class);
    PersonEntityBuilder actual = target.setPhoneCmsRecordDescriptor(phoneCmsRecordDescriptor);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhone_A$() throws Exception {
    PhoneNumber actual = target.getPhone();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setPhone_A$PhoneNumber() throws Exception {
    PhoneNumber phone = mock(PhoneNumber.class);
    PersonEntityBuilder actual = target.setPhone(phone);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumbers_A$() throws Exception {
    Set<PhoneNumber> actual = target.getPhoneNumbers();
    Set<PhoneNumber> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhoneNumbers_A$Set() throws Exception {
    Set<PhoneNumber> phoneNumbers = mock(Set.class);
    PersonEntityBuilder actual = target.setPhoneNumbers(phoneNumbers);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRoles_A$() throws Exception {
    Set<String> actual = target.getRoles();
    Set<String> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRoles_A$Set() throws Exception {
    Set<String> roles = mock(Set.class);
    PersonEntityBuilder actual = target.setRoles(roles);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAddress_A$() throws Exception {
    InvestigationAddress actual = target.getAddress();
    InvestigationAddress expected = new InvestigationAddressEntityBuilder().build();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAddress_A$InvestigationAddress() throws Exception {
    InvestigationAddress address = mock(InvestigationAddress.class);
    PersonEntityBuilder actual = target.setAddress(address);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAddresses_A$() throws Exception {
    Set<InvestigationAddress> actual = target.getAddresses();
    Set<InvestigationAddress> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAddresses_A$Set() throws Exception {
    Set<InvestigationAddress> addresses = mock(Set.class);
    PersonEntityBuilder actual = target.setAddresses(addresses);
    PersonEntityBuilder expected = target;
    assertThat(actual, is(equalTo(expected)));
  }

}

