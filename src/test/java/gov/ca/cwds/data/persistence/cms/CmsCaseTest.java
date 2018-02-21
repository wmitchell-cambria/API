package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import gov.ca.cwds.fixture.CaseEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
public class CmsCaseTest {

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
    assertThat(CmsCase.class.newInstance(), is(notNullValue()));
  }

  /**
   *  R - 02366 County drop douwns
   */
  @Test
  public void testCmsCaseCounty(){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    CmsCase validCmsCase = new CaseEntityBuilder().build();
    validCmsCase.setLastUpdatedId("10");
    assertThat(validator.validate(validCmsCase).isEmpty(), is(true));
    validCmsCase.setGovernmentEntityType(new Short(STATE_OF_CALIFORNIA_COUNTY_ID));
    assertThat(validator.validate(validCmsCase).isEmpty(), is(false));
  }

  /**
   * persistent Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    CmsCase validCmsCase = new CaseEntityBuilder().build();

    CmsCase persistent = new CmsCase(id, validCmsCase.getAlertText(),
        validCmsCase.getApprovalNumber(), validCmsCase.getApprovalStatusType(),
        validCmsCase.getCaseClosureReasonType(), validCmsCase.getCaseplanChildrenDetailIndVar(),
        validCmsCase.getClosureStatementText(), validCmsCase.getCountryCodeType(),
        validCmsCase.getCountySpecificCode(), validCmsCase.getDrmsNotesDoc(),
        validCmsCase.getEmancipationDate(), validCmsCase.getEndDate(), validCmsCase.getFkchldClt(),
        validCmsCase.getFkreferlt(), validCmsCase.getFkstfperst(),
        validCmsCase.getGovernmentEntityType(), validCmsCase.getIcpcOutgngPlcmtStatusIndVar(),
        validCmsCase.getIcpcOutgoingRequestIndVar(), validCmsCase.getLimitedAccessCode(),
        validCmsCase.getLimitedAccessDate(), validCmsCase.getLimitedAccessDesc(),
        validCmsCase.getLimitedAccessGovernmentEntityType(), validCmsCase.getCaseName(),
        validCmsCase.getNextTilpDueDate(), validCmsCase.getProjectedEndDate(),
        validCmsCase.getResponsibleAgencyCode(), validCmsCase.getSpecialProjectCaseIndVar(),
        validCmsCase.getStartDate(), validCmsCase.getStateCodeType(),
        validCmsCase.getActiveServiceComponentType(), validCmsCase.getActiveSvcComponentStartDate(),
        validCmsCase.getTickleIndVar(), validCmsCase.getChildClient(),
        validCmsCase.getStaffPerson(), null);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getPrimaryKey(), is(equalTo(id)));
    assertThat(persistent.getAlertText(), is(equalTo(validCmsCase.getAlertText())));
    assertThat(persistent.getApprovalNumber(), is(equalTo(validCmsCase.getApprovalNumber())));
    assertThat(persistent.getApprovalStatusType(),
        is(equalTo(validCmsCase.getApprovalStatusType())));
    assertThat(persistent.getCaseClosureReasonType(),
        is(equalTo(validCmsCase.getCaseClosureReasonType())));
    assertThat(persistent.getCaseplanChildrenDetailIndVar(),
        is(equalTo(validCmsCase.getCaseplanChildrenDetailIndVar())));
    assertThat(persistent.getClosureStatementText(),
        is(equalTo(validCmsCase.getClosureStatementText())));
    assertThat(persistent.getCountryCodeType(), is(equalTo(validCmsCase.getCountryCodeType())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validCmsCase.getCountySpecificCode())));
    assertThat(persistent.getDrmsNotesDoc(), is(equalTo(validCmsCase.getDrmsNotesDoc())));
    assertThat(persistent.getEmancipationDate(), is(equalTo(validCmsCase.getEmancipationDate())));
    assertThat(persistent.getEndDate(), is(equalTo(validCmsCase.getEndDate())));
    assertThat(persistent.getFkchldClt(), is(equalTo(validCmsCase.getFkchldClt())));
    assertThat(persistent.getFkreferlt(), is(equalTo(validCmsCase.getFkreferlt())));
    assertThat(persistent.getFkstfperst(), is(equalTo(validCmsCase.getFkstfperst())));
    assertThat(persistent.getGovernmentEntityType(),
        is(equalTo(validCmsCase.getGovernmentEntityType())));
    assertThat(persistent.getIcpcOutgngPlcmtStatusIndVar(),
        is(equalTo(validCmsCase.getIcpcOutgngPlcmtStatusIndVar())));
    assertThat(persistent.getIcpcOutgoingRequestIndVar(),
        is(equalTo(validCmsCase.getIcpcOutgoingRequestIndVar())));
    assertThat(persistent.getLimitedAccessCode(), is(equalTo(validCmsCase.getLimitedAccessCode())));
    assertThat(persistent.getLimitedAccessDate(), is(equalTo(validCmsCase.getLimitedAccessDate())));
    assertThat(persistent.getLimitedAccessDesc(), is(equalTo(validCmsCase.getLimitedAccessDesc())));
    assertThat(persistent.getLimitedAccessGovernmentEntityType(),
        is(equalTo(validCmsCase.getLimitedAccessGovernmentEntityType())));
    assertThat(persistent.getCaseName(), is(equalTo(validCmsCase.getCaseName())));
    assertThat(persistent.getNextTilpDueDate(), is(equalTo(validCmsCase.getNextTilpDueDate())));
    assertThat(persistent.getProjectedEndDate(), is(equalTo(validCmsCase.getProjectedEndDate())));
    assertThat(persistent.getResponsibleAgencyCode(),
        is(equalTo(validCmsCase.getResponsibleAgencyCode())));
    assertThat(persistent.getSpecialProjectCaseIndVar(),
        is(equalTo(validCmsCase.getSpecialProjectCaseIndVar())));
    assertThat(persistent.getStartDate(), is(equalTo(validCmsCase.getStartDate())));
    assertThat(persistent.getStateCodeType(), is(equalTo(validCmsCase.getStateCodeType())));
    assertThat(persistent.getActiveServiceComponentType(),
        is(equalTo(validCmsCase.getActiveServiceComponentType())));
    assertThat(persistent.getActiveSvcComponentStartDate(),
        is(equalTo(validCmsCase.getActiveSvcComponentStartDate())));
    assertThat(persistent.getTickleIndVar(), is(equalTo(validCmsCase.getTickleIndVar())));
  }

}
