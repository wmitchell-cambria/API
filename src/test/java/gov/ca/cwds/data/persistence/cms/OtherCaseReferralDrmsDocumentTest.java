package gov.ca.cwds.data.persistence.cms;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Intake Team 4
 *
 */
public class OtherCaseReferralDrmsDocumentTest implements PersistentTestTemplate {

  private String id = "1234567ABC";
  private String lastUpdatedId = "123";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(OtherCaseReferralDrmsDocument.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    OtherCaseReferralDrmsDocument vp = validOtherCaseReferralDrmsDocumentPersistent();

    OtherCaseReferralDrmsDocument persistent =
        new OtherCaseReferralDrmsDocument(id, vp.getCountySpecificCode(), vp.getTitleName(),
                vp.getReferralId(), vp.getDocumentExtensionTypeId(), vp.getDocumentLengthKb());
    assertThat(persistent.getDrmsDocumentId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(vp.getCountySpecificCode())));
    assertThat(persistent.getTitleName(), is(equalTo(vp.getTitleName())));
    assertThat(persistent.getReferralId(), is(equalTo(vp.getReferralId())));
    assertThat(persistent.getDocumentExtensionTypeId(), is(equalTo(vp.getDocumentExtensionTypeId())));
    assertThat(persistent.getDocumentLengthKb(), is(equalTo(vp.getDocumentLengthKb())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.OtherCaseReferralDrmsDocument domain = validOtherCaseReferralDrmsDocumentDomain();
    Date now = new Date();
    OtherCaseReferralDrmsDocument persistent = new OtherCaseReferralDrmsDocument(domain, lastUpdatedId, now);


    assertThat(persistent.getDrmsDocumentId(), is(equalTo(domain.getDrmsDocumentId())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(persistent.getTitleName(), is(equalTo(domain.getTitleName())));
    assertThat(persistent.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(persistent.getDocumentExtensionTypeId(), is(equalTo(domain.getDocumentExtensionTypeId())));
    assertThat(persistent.getDocumentLengthKb(), is(equalTo(domain.getDocumentLengthKb())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
    assertThat(persistent.getLastUpdatedTime(), is(equalTo(now)));
  }

  private OtherCaseReferralDrmsDocument validOtherCaseReferralDrmsDocumentPersistent()
      throws JsonParseException, JsonMappingException, IOException {

    OtherCaseReferralDrmsDocument validOtherCaseReferralDrmsDocument = MAPPER.readValue(
        fixture("fixtures/persistent/OtherCaseReferralDrmsDocument/valid/valid.json"), OtherCaseReferralDrmsDocument.class);
    return validOtherCaseReferralDrmsDocument;
  }

  private gov.ca.cwds.rest.api.domain.cms.OtherCaseReferralDrmsDocument validOtherCaseReferralDrmsDocumentDomain()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.OtherCaseReferralDrmsDocument validOtherCaseReferralDrmsDocument = MAPPER.readValue(
        fixture("fixtures/domain/legacy/OtherCaseReferralDrmsDocument/valid/valid.json"), gov.ca.cwds.rest.api.domain.cms.OtherCaseReferralDrmsDocument.class);
    return validOtherCaseReferralDrmsDocument;
  }



}
