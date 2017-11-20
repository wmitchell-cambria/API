package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

@SuppressWarnings("javadoc")
public class CaseAssignmentTest {
  private static final String FOLDED_KEY_CODE = "C";

  private String id = "SlCAr46088";
  private String countySpecificCode = "99";
  private Date endDate = new Date();
  private Date endTime = new Date();
  private String fkCaseLoad = "Aci1N2I00E";
  private String fkOutOfStateContactParty = null;
  private String responsibilityDescription = "some kind of descrption";
  private Short secondaryAssignmentRoleType = (short) 0;
  private Date startDate = new Date();
  private Date startTime = new Date();
  private String typeOfAssignmentCode = "P";
  private BigDecimal weightingNumber = new BigDecimal(0.00);

  private String newId = "1234567ABC";
  private String newCountySpecificCode = "20";
  private Date newEndDate = new Date();
  private Date newEndTime = new Date();
  private String newFkCaseLoad = "2345678ABC";
  private String newFkOutOfStateContactParty = "345678ABC";
  private String newResponsibilityDescription = "some kind of new descrption";
  private Short newSecondaryAssignmentRoleType = (short) 1123;
  private Date newStartDate = new Date();
  private Date newStartTime = new Date();
  private String newTypeOfAssignmentCode = "X";
  private BigDecimal newWeightingNumber = new BigDecimal(1.00);

  private String staffId = "0X5";

  @Test
  public void testDefaultConstructor() throws Exception {
    CaseAssignment assignment = new CaseAssignment();
    assertThat(assignment.getEstablishedForCode(), is(equalTo(FOLDED_KEY_CODE)));
  }

  @Test
  public void testConstructor() throws Exception {
    CaseAssignment caseAssignment = new CaseAssignment(countySpecificCode, endDate, endTime, id,
        fkCaseLoad, fkOutOfStateContactParty, responsibilityDescription,
        secondaryAssignmentRoleType, startDate, startTime, typeOfAssignmentCode, weightingNumber);
    assertThat(caseAssignment.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(caseAssignment.getEndDate(), is(equalTo(endDate)));
    assertThat(caseAssignment.getEndTime(), is(equalTo(endTime)));
    assertThat(caseAssignment.getFkCaseLoad(), is(equalTo(fkCaseLoad)));
    assertThat(caseAssignment.getFkOutOfStateContactParty(), is(equalTo(fkOutOfStateContactParty)));
    assertThat(caseAssignment.getResponsibilityDescription(),
        is(equalTo(responsibilityDescription)));
    assertThat(caseAssignment.getSecondaryAssignmentRoleType(),
        is(equalTo(secondaryAssignmentRoleType)));
    assertThat(caseAssignment.getStartDate(), is(equalTo(startDate)));
    assertThat(caseAssignment.getStartTime(), is(equalTo(startTime)));
    assertThat(caseAssignment.getTypeOfAssignmentCode(), is(equalTo(typeOfAssignmentCode)));
    assertThat(caseAssignment.getWeightingNumber(), is(equalTo(weightingNumber)));
  }

  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Assignment domainAssignment =
        new AssignmentResourceBuilder().buildAssignment();
    CaseAssignment caseAssignment = new CaseAssignment(id, domainAssignment, staffId);
    assertThat(caseAssignment.getCountySpecificCode(),
        is(equalTo(domainAssignment.getCountySpecificCode())));
    assertThat(caseAssignment.getEndDate(),
        is(equalTo(DomainChef.uncookDateString(domainAssignment.getEndDate()))));
    assertThat(caseAssignment.getEndTime(),
        is(equalTo(DomainChef.uncookTimeString(domainAssignment.getEndTime()))));
    assertThat(caseAssignment.getFkCaseLoad(), is(equalTo(domainAssignment.getCaseLoadId())));
    assertThat(caseAssignment.getFkOutOfStateContactParty(),
        is(equalTo(domainAssignment.getOutOfStateContactId())));
    assertThat(caseAssignment.getResponsibilityDescription(),
        is(equalTo(domainAssignment.getResponsibilityDescription())));
    assertThat(caseAssignment.getSecondaryAssignmentRoleType(),
        is(equalTo(domainAssignment.getSecondaryAssignmentRoleType())));
    assertThat(caseAssignment.getStartDate(),
        is(equalTo(DomainChef.uncookDateString(domainAssignment.getStartDate()))));
    assertThat(caseAssignment.getStartTime(),
        is(equalTo(DomainChef.uncookTimeString(domainAssignment.getStartTime()))));
    assertThat(caseAssignment.getTypeOfAssignmentCode(),
        is(equalTo(domainAssignment.getTypeOfAssignmentCode())));
    assertThat(caseAssignment.getWeightingNumber(),
        is(equalTo(domainAssignment.getWeightingNumber())));
    assertThat(caseAssignment.getPrimaryKey(), is(equalTo(id)));
    assertThat(caseAssignment.getId(), is(equalTo(id)));
  }

  @Test
  public void testSetters() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Assignment domainAssignment =
        new AssignmentResourceBuilder().buildAssignment();
    CaseAssignment caseAssignment = new CaseAssignment(id, domainAssignment, staffId);
    caseAssignment.setId(newId);
    assertThat(caseAssignment.getId(), is(equalTo(newId)));
    caseAssignment.setCountySpecificCode(newCountySpecificCode);
    assertThat(caseAssignment.getCountySpecificCode(), is(equalTo(newCountySpecificCode)));
    caseAssignment.setEndDate(newEndDate);
    assertThat(caseAssignment.getEndDate(), is(equalTo(newEndDate)));
    caseAssignment.setEndTime(newEndTime);
    assertThat(caseAssignment.getEndTime(), is(equalTo(newEndTime)));
    caseAssignment.setFkCaseLoad(newFkCaseLoad);
    assertThat(caseAssignment.getFkCaseLoad(), is(equalTo(newFkCaseLoad)));
    caseAssignment.setFkOutOfStateContactParty(newFkOutOfStateContactParty);
    assertThat(caseAssignment.getFkOutOfStateContactParty(),
        is(equalTo(newFkOutOfStateContactParty)));
    caseAssignment.setResponsibilityDescription(newResponsibilityDescription);
    assertThat(caseAssignment.getResponsibilityDescription(),
        is(equalTo(newResponsibilityDescription)));
    caseAssignment.setSecondaryAssignmentRoleType(newSecondaryAssignmentRoleType);
    assertThat(caseAssignment.getSecondaryAssignmentRoleType(),
        is(equalTo(newSecondaryAssignmentRoleType)));
    caseAssignment.setStartDate(newStartDate);
    assertThat(caseAssignment.getStartDate(), is(equalTo(newStartDate)));
    caseAssignment.setStartTime(newStartTime);
    assertThat(caseAssignment.getStartTime(), is(equalTo(newStartTime)));
    caseAssignment.setTypeOfAssignmentCode(newTypeOfAssignmentCode);
    assertThat(caseAssignment.getTypeOfAssignmentCode(), is(equalTo(newTypeOfAssignmentCode)));
    caseAssignment.setWeightingNumber(newWeightingNumber);
    assertThat(caseAssignment.getWeightingNumber(), is(equalTo(newWeightingNumber)));
  }
}
