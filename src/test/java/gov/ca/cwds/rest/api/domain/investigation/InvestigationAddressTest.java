package gov.ca.cwds.rest.api.domain.investigation;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.fixture.ClientAddressEntityBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.fixture.investigation.InvestigationAddressEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class InvestigationAddressTest {

  private String streetAddress = "741 Evergreen Ct";
  private String city = "Springfield";
  private Short state = 1828;
  private String zip = "95757";
  private Short type = 32;
  private String id = "1234567ABC";
  private String tableName = "ADDRS_T";
  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", tableName, "Address");

  @Test
  public void testEmptyConstructorSuccess() {
    InvestigationAddress investigationAddress = new InvestigationAddress();
    assertNotNull(investigationAddress);
  }

  @Test
  public void testDomainConstructorSuccess() {
    InvestigationAddress investigationAddress =
        new InvestigationAddress(cmsRecordDescriptor, streetAddress, city, state, zip, type);
    assertThat(cmsRecordDescriptor, is(equalTo(investigationAddress.getCmsRecordDescriptor())));
    assertThat(streetAddress, is(equalTo(investigationAddress.getStreetAddress())));
    assertThat(city, is(equalTo(investigationAddress.getCity())));
    assertThat(state, is(equalTo(investigationAddress.getState())));
    assertThat(type, is(equalTo(investigationAddress.getType())));

  }

  @Test
  public void testAddressConstructorUsingClientSuccess() {
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new ClientAddressEntityBuilder().buildClientAddress();
    gov.ca.cwds.data.persistence.cms.Address address = clientAddress.getAddresses();
    InvestigationAddress investigationAddress =
        new InvestigationAddress(clientAddress, cmsRecordDescriptor);
    assertThat(investigationAddress.getStreetAddress(),
        is(equalTo(trim(address.getStreetAddress()))));
    assertThat(investigationAddress.getCity(), is(equalTo(trim(address.getCity()))));
    assertThat(investigationAddress.getZip(), is(equalTo(trim(address.getZip()))));
    assertThat(investigationAddress.getType(), is(equalTo(clientAddress.getAddressType())));
    assertThat(investigationAddress.getCmsRecordDescriptor(), is(equalTo(cmsRecordDescriptor)));
  }

  @Test
  public void testAddressConstructorUsingReporterSuccess() {
    Reporter reporter = new ReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "OX5", new Date());
    InvestigationAddress investigationAddress =
        new InvestigationAddress(persistentReporter, cmsRecordDescriptor);
    assertThat(investigationAddress.getStreetAddress(),
        is(equalTo(trim(persistentReporter.getStreetAddress()))));
    assertThat(investigationAddress.getCity(), is(equalTo(trim(persistentReporter.getCity()))));
    assertThat(investigationAddress.getState(), is(equalTo(persistentReporter.getStateCd())));
    assertThat(investigationAddress.getZip(), is(equalTo(trim(persistentReporter.getZip()))));
    assertThat(investigationAddress.getType(),
        is(equalTo(persistentReporter.getApiAdrAddressType())));
    assertThat(investigationAddress.getCmsRecordDescriptor(), is(equalTo(cmsRecordDescriptor)));
  }

  @Test
  public void testAddressConstructorUsingReferralSuccess() {
    Referral referral = new ReferralEntityBuilder().build();
    gov.ca.cwds.data.persistence.cms.Address address = referral.getAddresses();
    InvestigationAddress investigationAddress =
        new InvestigationAddress(referral, cmsRecordDescriptor);
    assertThat(investigationAddress.getStreetAddress(),
        is(equalTo(trim(address.getStreetAddress()))));
    assertThat(investigationAddress.getCity(), is(equalTo(trim(address.getCity()))));
    assertThat(investigationAddress.getZip(), is(equalTo(trim(address.getZip()))));
    assertThat(investigationAddress.getType(), is(equalTo(address.getApiAdrAddressType())));
    assertThat(investigationAddress.getCmsRecordDescriptor(), is(equalTo(cmsRecordDescriptor)));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    InvestigationAddress investigationAddress = new InvestigationAddressEntityBuilder().build();
    InvestigationAddress otherInvestigationAddress =
        new InvestigationAddressEntityBuilder().build();
    assertEquals(investigationAddress, otherInvestigationAddress);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    InvestigationAddress investigationAddress = new InvestigationAddressEntityBuilder().build();
    InvestigationAddress otherInvestigationAddress =
        new InvestigationAddressEntityBuilder().setCity("Fresno").build();
    assertThat(investigationAddress, is(not(equals(otherInvestigationAddress))));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(InvestigationAddress.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }
}
