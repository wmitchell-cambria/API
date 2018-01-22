package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.LawEnforcementEntityBuilder;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author CWDS API Team
 *
 */
public class LawEnforcementTest {

  private String id = "1234567ABC";
  private Validator validator;
  private final static String STATE_OF_CALIFORNIA_COUNTY_ID = "1126";

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(LawEnforcementEntity.class.newInstance(), is(notNullValue()));
  }

  /**
   *  R - 02366 County drop douwns
   */
  @Test
  public void testCmsCaseCounty(){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    LawEnforcementEntity validLawEnforcemrnt = new LawEnforcementEntityBuilder().build();
    assertThat(validator.validate(validLawEnforcemrnt).isEmpty(), is(true));
    validLawEnforcemrnt = new LawEnforcementEntityBuilder()
            .setGovernmentEntityType(new Short(STATE_OF_CALIFORNIA_COUNTY_ID))
            .build();
    assertThat(validator.validate(validLawEnforcemrnt).isEmpty(), is(false));
  }

  /**
   * persistent Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    LawEnforcementEntity validLawEnforcemrnt = new LawEnforcementEntityBuilder().build();

    LawEnforcementEntity persistent = new LawEnforcementEntity(validLawEnforcemrnt.getArchiveAssociationInd(),
        validLawEnforcemrnt.getCityName(), validLawEnforcemrnt.getContactPhoneExtensionNumber(),
        validLawEnforcemrnt.getContactPersonName(), validLawEnforcemrnt.getContactPhoneNumber(),
        validLawEnforcemrnt.getContactPositionTitleDescription(),
        validLawEnforcemrnt.getEmailAddress(), validLawEnforcemrnt.getFaxNumber(),
        validLawEnforcemrnt.getGovernmentEntityType(), id,
        validLawEnforcemrnt.getLawEnforcementName(), validLawEnforcemrnt.getReferenceNumber(),
        validLawEnforcemrnt.getStationName(), validLawEnforcemrnt.getStreetName(),
        validLawEnforcemrnt.getStreetNumber(), validLawEnforcemrnt.getZipNumber(),
        validLawEnforcemrnt.getZipSuffixNumber(), validLawEnforcemrnt.getLastUpdatedId());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getArchiveAssociationInd(),
        is(equalTo(validLawEnforcemrnt.getArchiveAssociationInd())));
    assertThat(persistent.getCityName(), is(equalTo(validLawEnforcemrnt.getCityName())));
    assertThat(persistent.getContactPhoneExtensionNumber(),
        is(equalTo(validLawEnforcemrnt.getContactPhoneExtensionNumber())));
    assertThat(persistent.getContactPersonName(),
        is(equalTo(validLawEnforcemrnt.getContactPersonName())));
    assertThat(persistent.getContactPhoneNumber(),
        is(equalTo(validLawEnforcemrnt.getContactPhoneNumber())));
    assertThat(persistent.getContactPositionTitleDescription(),
        is(equalTo(validLawEnforcemrnt.getContactPositionTitleDescription())));
    assertThat(persistent.getEmailAddress(), is(equalTo(validLawEnforcemrnt.getEmailAddress())));
    assertThat(persistent.getFaxNumber(), is(equalTo(validLawEnforcemrnt.getFaxNumber())));
    assertThat(persistent.getGovernmentEntityType(),
        is(equalTo(validLawEnforcemrnt.getGovernmentEntityType())));
    assertThat(persistent.getLawEnforcementName(),
        is(equalTo(validLawEnforcemrnt.getLawEnforcementName())));
    assertThat(persistent.getReferenceNumber(),
        is(equalTo(validLawEnforcemrnt.getReferenceNumber())));
    assertThat(persistent.getStationName(), is(equalTo(validLawEnforcemrnt.getStationName())));
    assertThat(persistent.getStreetName(), is(equalTo(validLawEnforcemrnt.getStreetName())));
    assertThat(persistent.getStreetNumber(), is(equalTo(validLawEnforcemrnt.getStreetNumber())));
    assertThat(persistent.getZipNumber(), is(equalTo(validLawEnforcemrnt.getZipNumber())));
    assertThat(persistent.getZipSuffixNumber(),
        is(equalTo(validLawEnforcemrnt.getZipSuffixNumber())));
  }

}
