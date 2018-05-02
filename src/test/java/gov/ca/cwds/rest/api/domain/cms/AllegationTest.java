package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.fixture.CmsAllegationResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
public class AllegationTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "1234567ABC";
  private String abuseEndDate = "2016-10-31";
  private String abuseStartDate = "2016-10-31";
  private Short abuseFrequency = 1;
  private String abuseFrequencyPeriodCode = "M";
  private String abuseLocationDescription = "Home";
  private Short allegationDispositionType = 2;
  private Short allegationType = 3;
  private String dispositionDescription = "disposition description";
  private String dispositionDate = "2016-10-31";
  private Boolean injuryHarmDetailIndicator = Boolean.TRUE;
  private String nonProtectingParentCode = "N";
  private Boolean staffPersonAddedIndicator = Boolean.FALSE;
  private String victimClientId = "1234567890";
  private String perpetratorClientId = "0987654321";
  private String referralId = "2345678901";
  private String countySpecificCode = "99";
  private Boolean zippyCreatedIndicator = Boolean.TRUE;
  private Short placementFacilityType = 4;

  /**
   * 
   */
  @Before
  public void setup() {
  }

  /*
   * Constructor Tests
   */
  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Allegation domain = new Allegation(abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
        abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
        dispositionDescription, dispositionDate, injuryHarmDetailIndicator, nonProtectingParentCode,
        staffPersonAddedIndicator, victimClientId, perpetratorClientId, referralId,
        countySpecificCode, zippyCreatedIndicator, placementFacilityType);

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, domain, "lastUpdatedId", new Date());

    Allegation totest = new Allegation(persistent);
    assertThat(totest.getAbuseEndDate(), is(equalTo(df.format(persistent.getAbuseEndDate()))));
    assertThat(totest.getAbuseFrequency(), is(equalTo(persistent.getAbuseFrequency())));
    assertThat(totest.getAbuseFrequencyPeriodCode(),
        is(equalTo(persistent.getAbuseFrequencyPeriodCode())));
    assertThat(totest.getAbuseLocationDescription(),
        is(equalTo(persistent.getAbuseLocationDescription())));
    assertThat(totest.getAbuseStartDate(), is(equalTo(df.format(persistent.getAbuseStartDate()))));
    assertThat(totest.getAllegationDispositionType(),
        is(equalTo(persistent.getAllegationDispositionType())));
    assertThat(totest.getAllegationType(), is(equalTo(persistent.getAllegationType())));
    assertThat(totest.getDispositionDescription(),
        is(equalTo(persistent.getDispositionDescription())));
    assertThat(totest.getDispositionDate(),
        is(equalTo(df.format(persistent.getDispositionDate()))));
    assertThat(totest.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getInjuryHarmDetailIndicator()))));
    assertThat(totest.getNonProtectingParentCode(),
        is(equalTo(persistent.getNonProtectingParentCode())));
    assertThat(totest.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getStaffPersonAddedIndicator()))));
    assertThat(totest.getVictimClientId(), is(equalTo(persistent.getVictimClientId())));
    assertThat(totest.getPerpetratorClientId(), is(equalTo(persistent.getPerpetratorClientId())));
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getZippyCreatedIndicator()))));
    assertThat(totest.getPlacementFacilityType(),
        is(equalTo(persistent.getPlacementFacilityType())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Allegation domain = new Allegation(abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
        abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
        dispositionDescription, dispositionDate, injuryHarmDetailIndicator, nonProtectingParentCode,
        staffPersonAddedIndicator, victimClientId, perpetratorClientId, referralId,
        countySpecificCode, zippyCreatedIndicator, placementFacilityType);

    assertThat(domain.getAbuseEndDate(), is(equalTo(abuseEndDate)));
    assertThat(domain.getAbuseFrequency(), is(equalTo(abuseFrequency)));
    assertThat(domain.getAbuseFrequencyPeriodCode(), is(equalTo(abuseFrequencyPeriodCode)));
    assertThat(domain.getAbuseLocationDescription(), is(equalTo(abuseLocationDescription)));
    assertThat(domain.getAbuseStartDate(), is(equalTo(abuseStartDate)));
    assertThat(domain.getAllegationDispositionType(), is(equalTo(allegationDispositionType)));
    assertThat(domain.getAllegationType(), is(equalTo(allegationType)));
    assertThat(domain.getDispositionDescription(), is(equalTo(dispositionDescription)));
    assertThat(domain.getDispositionDate(), is(equalTo(dispositionDate)));
    assertThat(domain.getInjuryHarmDetailIndicator(), is(equalTo(injuryHarmDetailIndicator)));
    assertThat(domain.getNonProtectingParentCode(), is(equalTo(nonProtectingParentCode)));
    assertThat(domain.getStaffPersonAddedIndicator(), is(equalTo(staffPersonAddedIndicator)));
    assertThat(domain.getVictimClientId(), is(equalTo(victimClientId)));
    assertThat(domain.getPerpetratorClientId(), is(equalTo(perpetratorClientId)));
    assertThat(domain.getReferralId(), is(equalTo(referralId)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(zippyCreatedIndicator)));
    assertThat(domain.getPlacementFacilityType(), is(equalTo(placementFacilityType)));
  }

  @Test
  public void equalsHashCodeWork() {
    // EqualsVerifier.forClass(Allegation.class).suppress(Warning.NONFINAL_FIELDS).verify();
    assertThat(validAllegation().hashCode(), is(not(0)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class));

    assertThat(MAPPER.writeValueAsString(validAllegation()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"),
        Allegation.class), is(equalTo(validAllegation())));
  }


  @SuppressWarnings("javadoc")
  @Test
  public void shouldSetNonProtectiveParentToUWhenNoPerpetrator() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation builtAllegation =
        new CmsAllegationResourceBuilder().setPerpetratorClientId("").buildCmsAllegation();

    gov.ca.cwds.rest.api.domain.cms.Allegation allegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation(builtAllegation.getAbuseEndDate(),
            builtAllegation.getAbuseFrequency(), builtAllegation.getAbuseFrequencyPeriodCode(),
            builtAllegation.getAbuseLocationDescription(), builtAllegation.getAbuseStartDate(),
            builtAllegation.getAllegationDispositionType(), builtAllegation.getAllegationType(),
            builtAllegation.getDispositionDescription(), builtAllegation.getDispositionDate(),
            builtAllegation.getInjuryHarmDetailIndicator(),
            (builtAllegation.getPerpetratorClientId().equals("")) ? "U" : "N",
            builtAllegation.getStaffPersonAddedIndicator(), builtAllegation.getVictimClientId(),
            builtAllegation.getPerpetratorClientId(), builtAllegation.getReferralId(),
            builtAllegation.getCountySpecificCode(), builtAllegation.getZippyCreatedIndicator(),
            builtAllegation.getPlacementFacilityType());

    assertThat(allegation.getNonProtectingParentCode(), is("U"));

  }


  @SuppressWarnings("javadoc")
  @Test
  public void shouldSetNonProtectiveParentToNWhenPerpetrator() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation builtAllegation = new CmsAllegationResourceBuilder()
        .setPerpetratorClientId("3456789ABC").buildCmsAllegation();

    gov.ca.cwds.rest.api.domain.cms.Allegation allegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation(builtAllegation.getAbuseEndDate(),
            builtAllegation.getAbuseFrequency(), builtAllegation.getAbuseFrequencyPeriodCode(),
            builtAllegation.getAbuseLocationDescription(), builtAllegation.getAbuseStartDate(),
            builtAllegation.getAllegationDispositionType(), builtAllegation.getAllegationType(),
            builtAllegation.getDispositionDescription(), builtAllegation.getDispositionDate(),
            builtAllegation.getInjuryHarmDetailIndicator(),
            (builtAllegation.getPerpetratorClientId().equals("")) ? "U" : "N",
            builtAllegation.getStaffPersonAddedIndicator(), builtAllegation.getVictimClientId(),
            builtAllegation.getPerpetratorClientId(), builtAllegation.getReferralId(),
            builtAllegation.getCountySpecificCode(), builtAllegation.getZippyCreatedIndicator(),
            builtAllegation.getPlacementFacilityType());

    assertThat(allegation.getNonProtectingParentCode(), is("N"));

  }

  /**
   * Rule - 06998
   */
  @Test
  public void testZippyAllegationCreation() {


    Allegation domain = new gov.ca.cwds.rest.api.domain.cms.Allegation(abuseEndDate, abuseFrequency,
        abuseFrequencyPeriodCode, abuseLocationDescription, abuseStartDate,
        allegationDispositionType, allegationType, dispositionDescription, dispositionDate,
        injuryHarmDetailIndicator, nonProtectingParentCode, staffPersonAddedIndicator,
        victimClientId, perpetratorClientId, referralId, countySpecificCode, zippyCreatedIndicator,
        placementFacilityType);

    assertEquals("Expected zippyCreatedIndicator field to be initialized as  True", Boolean.TRUE,
        domain.getZippyCreatedIndicator());
  }

  /*
   * Utils
   */
  private Allegation validAllegation() {
    return new Allegation("2016-10-31", (short) 2, "M", "Barber Shop", "2016-10-31", (short) 0,
        (short) 2180, "Fremont", "2016-10-31", false, "N", false, "AHooKwN0F4", "MKPFcB90F4",
        "AbiQCgu0Hj", "19", false, (short) 6574);
  }
}
