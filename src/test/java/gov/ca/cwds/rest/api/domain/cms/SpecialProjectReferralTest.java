package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import javax.validation.Validator;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gov.ca.cwds.fixture.SpecialProjectReferralEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.messages.MessageBuilder;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SpecialProjectReferralTest {
  private static final ObjectMapper MAPPER = 
      Jackson.newObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

  private String countySpecificCode = "27";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private String participationEndDate = null;
  private String participationStartDate = "2018-05-30";
  private Boolean safelySurrenderedBabiesIndicator = Boolean.FALSE;
  private String id = "3456789ABC";

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Test
  public void testConstructor() throws Exception {
    SpecialProjectReferral spr = new SpecialProjectReferral(countySpecificCode, 
        referralId, specialProjectId, participationEndDate, participationStartDate,
        safelySurrenderedBabiesIndicator);
    assertThat(spr.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(spr.getReferralId(), is(equalTo(referralId)));
    assertThat(spr.getSpecialProjectId(), is(equalTo(specialProjectId)));
    assertThat(spr.getParticipationEndDate(), is(equalTo(participationEndDate)));
    assertThat(spr.getParticipationStartDate(), is(equalTo(participationStartDate)));
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(safelySurrenderedBabiesIndicator)));
  }
  
  @Test
  public void testConstructorWithPersistentObject() throws Exception {
    gov.ca.cwds.data.persistence.cms.SpecialProjectReferral persistent = 
        new SpecialProjectReferralEntityBuilder().build();
    SpecialProjectReferral spr = new SpecialProjectReferral(persistent);
    assertThat(spr.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(spr.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(spr.getSpecialProjectId(), is(equalTo(persistent.getSpecialProjectId())));
    assertThat(spr.getParticipationEndDate(), is(equalTo(DomainChef.cookDate(persistent.getParticipationEndDate()))));
    assertThat(spr.getParticipationStartDate(), is(equalTo(DomainChef.cookDate(persistent.getParticipationStartDate()))));
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(DomainChef.uncookBooleanString(persistent.getSafelySurrenderedBabiesIndicator()))));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SpecialProjectReferral.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder().build();
    String sprString = MAPPER.writeValueAsString(spr);
    SpecialProjectReferral deserialized = MAPPER.readValue(fixture("fixtures/domain/cms/SpecialProjectReferral/valid.json"), SpecialProjectReferral.class);
    String deserializedString = MAPPER.writeValueAsString(deserialized);
    assertThat(sprString, is(equalTo(deserializedString)));
  }

  @Test
  public void deserializeFromJSON() throws Exception {
    SpecialProjectReferral spr = new gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder().build();
    SpecialProjectReferral deserialized = MAPPER.readValue(fixture("fixtures/domain/cms/SpecialProjectReferral/valid.json"), SpecialProjectReferral.class);
    assertThat(spr, is(equalTo(deserialized)));
  }
}
