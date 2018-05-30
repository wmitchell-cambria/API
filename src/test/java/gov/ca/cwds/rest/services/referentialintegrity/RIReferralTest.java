package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDaoImpl;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * 
 * @author CWDS API Team
 * 
 */
public class RIReferralTest {

  private AddressDao addressDao;
  private StaffPersonDao staffPersonDao;
  private DrmsDocumentDao drmsDocumentDao;
  private LongTextDaoImpl longTextDao;
  private ReferralDao referralDao;

  /**
   * 
   */
  @Before
  public void setup() {
    addressDao = mock(AddressDao.class);
    staffPersonDao = mock(StaffPersonDao.class);
    drmsDocumentDao = mock(DrmsDocumentDao.class);
    longTextDao = mock(LongTextDaoImpl.class);
    referralDao = mock(ReferralDao.class);
  }

  /**
   * @throws Exception generic error
   */
  @Test
  public void type() throws Exception {
    assertThat(RIReferral.class, notNullValue());
  }

  /**
   * @throws Exception generic error
   */
  @Test
  public void instantiation() throws Exception {
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    assertThat(target, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenPrimaryContactStaffPersonIdNotFound() throws Exception {

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    when(staffPersonDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenAllegesAbuseOccurredAtAddressIdNotFound() throws Exception {

    Referral referralDomain =
        new ReferralResourceBuilder().setAllegesAbuseOccurredAtAddressId("ABc1234567").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(addressDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenDrmsAllegationDescriptionDocNotFound() throws Exception {

    Referral referralDomain =
        new ReferralResourceBuilder().setDrmsAllegationDescriptionDoc("ABC1234567").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(drmsDocumentDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenDrmsErReferralDocNotFound() throws Exception {

    Referral referralDomain =
        new ReferralResourceBuilder().setDrmsErReferralDoc("ABC0987h5t").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(drmsDocumentDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenDrmsInvestigationDocNotFound() throws Exception {

    Referral referralDomain =
        new ReferralResourceBuilder().setDrmsInvestigationDoc("ABC0987h5t").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(drmsDocumentDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenCurrentLocationOfChildrenNotFound() throws Exception {

    Referral referralDomain =
        new ReferralResourceBuilder().setCurrentLocationOfChildren("ABC1234567").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(longTextDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenResponseRationaleTextNotFound() throws Exception {

    Referral referralDomain =
        new ReferralResourceBuilder().setResponseRationaleText("ABC1234567").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(longTextDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenScreenerNoteTextNotFound() throws Exception {

    Referral referralDomain = new ReferralResourceBuilder().setResponseRationaleText(null)
        .setScreenerNoteText("ABC1234567").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(longTextDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenLinkToPrimaryReferralIdNotFound() throws Exception {

    Referral referralDomain =
        new ReferralResourceBuilder().setLinkToPrimaryReferralId("ABc123459").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.find(any(String.class))).thenReturn(null);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    target.apply(referral);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void riCheckPass() throws Exception {

    Referral referralDomain = new ReferralResourceBuilder().setResponseRationaleText(null).build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId(null).build();

    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    Boolean result = target.apply(referral);
    assertThat(result, is(equalTo(true)));
  }
}
