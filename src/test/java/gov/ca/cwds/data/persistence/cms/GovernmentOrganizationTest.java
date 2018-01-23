package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.GovernmentOrganizationEntityBuilder;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentOrganizationTest {

  private String id = "1234567ABC";
  private Validator validator;
  private final static String STATE_OF_CALIFORNIA_COUNTY_ID = "1126";

  /**
   *  R - 02366 County drop douwns
   */
  @Test
  public void testCmsCaseCounty(){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    GovernmentOrganizationEntity validGovernmentOrganization =
            new GovernmentOrganizationEntityBuilder().build();
    validGovernmentOrganization.setLastUpdatedId("10");
    assertThat(validator.validate(validGovernmentOrganization).isEmpty(), is(true));
    validGovernmentOrganization = new GovernmentOrganizationEntityBuilder()
            .setGovernmentEntityType(new Short(STATE_OF_CALIFORNIA_COUNTY_ID))
            .build();
    assertThat(validator.validate(validGovernmentOrganization).isEmpty(), is(false));
  }

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(GovernmentOrganizationEntity.class.newInstance(), is(notNullValue()));
  }

  /**
   * persistent Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    GovernmentOrganizationEntity ValidGovernmentOrganization =
        new GovernmentOrganizationEntityBuilder().build();

    GovernmentOrganizationEntity persistent = new GovernmentOrganizationEntity(id,
        ValidGovernmentOrganization.getArchiveAssociationInd(),
        ValidGovernmentOrganization.getCityName(),
        ValidGovernmentOrganization.getContactPersonName(),
        ValidGovernmentOrganization.getContactPhoneNumber(),
        ValidGovernmentOrganization.getContactPhoneExtNumber(),
        ValidGovernmentOrganization.getContactPositionTitleDescription(),
        ValidGovernmentOrganization.getEmailAddress(), ValidGovernmentOrganization.getFaxNumber(),
        ValidGovernmentOrganization.getFederalInd(),
        ValidGovernmentOrganization.getGovernmentEntityType(),
        ValidGovernmentOrganization.getGovernmentOrganizationType(),
        ValidGovernmentOrganization.getGovernmentOrganizationName(),
        ValidGovernmentOrganization.getStateCodeType(), ValidGovernmentOrganization.getStreetName(),
        ValidGovernmentOrganization.getStreetNumber(), ValidGovernmentOrganization.getZipNumber(),
        ValidGovernmentOrganization.getZipSuffixNumber());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getArchiveAssociationInd(),
        is(equalTo(ValidGovernmentOrganization.getArchiveAssociationInd())));
    assertThat(persistent.getCityName(), is(equalTo(ValidGovernmentOrganization.getCityName())));
    assertThat(persistent.getContactPersonName(),
        is(equalTo(ValidGovernmentOrganization.getContactPersonName())));
    assertThat(persistent.getContactPhoneNumber(),
        is(equalTo(ValidGovernmentOrganization.getContactPhoneNumber())));
    assertThat(persistent.getContactPhoneExtNumber(),
        is(equalTo(ValidGovernmentOrganization.getContactPhoneExtNumber())));
    assertThat(persistent.getContactPositionTitleDescription(),
        is(equalTo(ValidGovernmentOrganization.getContactPositionTitleDescription())));
    assertThat(persistent.getEmailAddress(),
        is(equalTo(ValidGovernmentOrganization.getEmailAddress())));
    assertThat(persistent.getFaxNumber(), is(equalTo(ValidGovernmentOrganization.getFaxNumber())));
    assertThat(persistent.getFederalInd(),
        is(equalTo(ValidGovernmentOrganization.getFederalInd())));
    assertThat(persistent.getGovernmentEntityType(),
        is(equalTo(ValidGovernmentOrganization.getGovernmentEntityType())));
    assertThat(persistent.getGovernmentOrganizationType(),
        is(equalTo(ValidGovernmentOrganization.getGovernmentOrganizationType())));
    assertThat(persistent.getGovernmentOrganizationName(),
        is(equalTo(ValidGovernmentOrganization.getGovernmentOrganizationName())));
    assertThat(persistent.getStateCodeType(),
        is(equalTo(ValidGovernmentOrganization.getStateCodeType())));
    assertThat(persistent.getStreetName(),
        is(equalTo(ValidGovernmentOrganization.getStreetName())));
    assertThat(persistent.getStreetNumber(),
        is(equalTo(ValidGovernmentOrganization.getStreetNumber())));
    assertThat(persistent.getZipNumber(), is(equalTo(ValidGovernmentOrganization.getZipNumber())));
    assertThat(persistent.getZipSuffixNumber(),
        is(equalTo(ValidGovernmentOrganization.getZipSuffixNumber())));
  }

}
