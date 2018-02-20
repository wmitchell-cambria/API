package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gov.ca.cwds.fixture.CwsOfficeEntityBuilder;

public class CwsOfficeTest {
  private String id = "1234567ABC";
  private long faxNumber = 1234567;
  private String geographicRegionCode = "111";
  private short governmentEntityType = 1034;
  private String headquarterIndicator = "Y";
  private String inactiveIndicator = "Y";
  private String mailStopDescription = "office mail stop";
  private long messagePhoneNumber = 2345678;
  private int messagePhoneExtensionNumber = 123;
  private String cwsOfficeNumber = "office number";
  private long primaryPhoneNumber = 3456789;
  private int primaryPhoneExtensionNumber = 234;
  private String fkStaffPerson = "0X5";
  private String commentDescription = "description of comment";
  private String agencyName = "county child welfare";
  private String departmentDivisionName = "county department";
  private String cwsOfficeName = "county office";
  private String countySpecificCode = "34";
  private short agencyCodeNumber = 1122;
  private short locationCountyType = 2233;
  private String directorsNameTitle = "director title";
  
  private String newId = "234567ABC";
  private long newFaxNumber = 234567;
  private String newGeographicRegionCode = "112";
  private short newGovernmentEntityType = 1036;
  private String newHeadquarterIndicator = "N";
  private String newInactiveIndicator = "N";
  private String newMailStopDescription = "new office mail stop";
  private long newMessagePhoneNumber = 9345678;
  private int newMessagePhoneExtensionNumber = 234;
  private String newCwsOfficeNumber = "new office number";
  private long newPrimaryPhoneNumber = 3456777;
  private int newPrimaryPhoneExtensionNumber = 222;
  private String newFkStaffPerson = "aab";
  private String newCommentDescription = "new description of comment";
  private String newAgencyName = "new county child welfare";
  private String newDepartmentDivisionName = "new county department";
  private String newCwsOfficeName = "new county office";
  private String newCountySpecificCode = "31";
  private short newAgencyCodeNumber = 1133;
  private short newLocationCountyType = 2244;
  private String newDirectorsNameTitle = "new director title";
	  
  /**
  * Constructor test
  * 
  * @throws Exception - exception
  */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CwsOffice.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testPersistentConstructor() throws Exception {
	  CwsOffice pco = new CwsOfficeEntityBuilder().build();
	  assertThat(pco.getId(), is(equalTo(id)));
	  assertThat(pco.getFaxNumber(), is(equalTo(faxNumber)));
	  assertThat(pco.getGeographicRegionCode(), is(equalTo(geographicRegionCode)));
	  assertThat(pco.getGovernmentEntityType(), is(equalTo(governmentEntityType)));
	  assertThat(pco.getHeadquarterIndicator(), is(equalTo(headquarterIndicator)));
	  assertThat(pco.getInactiveIndicator(), is(equalTo(inactiveIndicator)));
	  assertThat(pco.getMailStopDescription(), is(equalTo(mailStopDescription)));
	  assertThat(pco.getMessagePhoneExtensionNumber(), is(equalTo(messagePhoneExtensionNumber)));
	  assertThat(pco.getMessagePhoneNumber(), is(equalTo(messagePhoneNumber)));
	  assertThat(pco.getCwsOfficeNumber(), is(equalTo(cwsOfficeNumber)));
	  assertThat(pco.getPrimaryPhoneNumber(), is(equalTo(primaryPhoneNumber)));
	  assertThat(pco.getPrimaryPhoneExtensionNumber(), is(equalTo(primaryPhoneExtensionNumber)));
	  assertThat(pco.getFkStaffPerson(), is(equalTo(fkStaffPerson)));
	  assertThat(pco.getCommentDescription(), is(equalTo(commentDescription)));
	  assertThat(pco.getAgencyName(), is(equalTo(agencyName)));
	  assertThat(pco.getDepartmentDivisionName(), is(equalTo(departmentDivisionName)));
	  assertThat(pco.getCwsOfficeName(), is(equalTo(cwsOfficeName)));
	  assertThat(pco.getLocationCountyType(), is(equalTo(locationCountyType)));
	  assertThat(pco.getCountySpecificCode(), is(equalTo(countySpecificCode)));
	  assertThat(pco.getAgencyCodeNumber(), is(equalTo(agencyCodeNumber)));
	  assertThat(pco.getDirectorsNameTitle(), is(equalTo(directorsNameTitle)));
	  
	  assertThat(pco.getPrimaryKey(), is(equalTo(id)));
	  
  }
  
  @Test
  public void shouldModifyValuesUsingSetters() throws Exception {
	CwsOffice co = new CwsOfficeEntityBuilder().build();
	co.setId(newId);
	assertThat(co.getId(), is(equalTo(newId)));
	co.setFaxNumber(newFaxNumber);
	assertThat(co.getFaxNumber(), is(equalTo(newFaxNumber)));
	co.setGeographicRegionCode(newGeographicRegionCode);
	assertThat(co.getGeographicRegionCode(), is(equalTo(newGeographicRegionCode)));
	co.setGovernmentEntityType(newGovernmentEntityType);
	assertThat(co.getGovernmentEntityType(), is(equalTo(newGovernmentEntityType)));
	co.setHeadquarterIndicator(newHeadquarterIndicator);
	assertThat(co.getHeadquarterIndicator(), is(equalTo(newHeadquarterIndicator)));
	co.setInactiveIndicator(newInactiveIndicator);
	assertThat(co.getInactiveIndicator(), is(equalTo(newInactiveIndicator)));
	co.setMailStopDescription(newMailStopDescription);
	assertThat(co.getMailStopDescription(), is(equalTo(newMailStopDescription)));
	co.setMessagePhoneExtensionNumber(newMessagePhoneExtensionNumber);
	assertThat(co.getMessagePhoneExtensionNumber(), is(equalTo(newMessagePhoneExtensionNumber)));
	co.setMessagePhoneNumber(newMessagePhoneNumber);
	assertThat(co.getMessagePhoneNumber(), is(equalTo(newMessagePhoneNumber)));
	co.setCwsOfficeNumber(newCwsOfficeNumber);
	assertThat(co.getCwsOfficeNumber(), is(equalTo(newCwsOfficeNumber)));
	co.setCwsOfficeName(newCwsOfficeName);
	assertThat(co.getCwsOfficeName(), is(equalTo(newCwsOfficeName)));
	co.setPrimaryPhoneNumber(newPrimaryPhoneNumber);
	assertThat(co.getPrimaryPhoneNumber(), is(equalTo(newPrimaryPhoneNumber)));
	co.setPrimaryPhoneExtensionNumber(newPrimaryPhoneExtensionNumber);
	assertThat(co.getPrimaryPhoneExtensionNumber(), is(equalTo(newPrimaryPhoneExtensionNumber)));
	co.setFkStaffPerson(newFkStaffPerson);
	assertThat(co.getFkStaffPerson(), is(equalTo(newFkStaffPerson)));
	co.setCommentDescription(newCommentDescription);
	assertThat(co.getCommentDescription(), is(equalTo(newCommentDescription)));
	co.setAgencyName(newAgencyName);
	assertThat(co.getAgencyName(), is(equalTo(newAgencyName)));
	co.setDepartmentDivisionName(newDepartmentDivisionName);
	assertThat(co.getDepartmentDivisionName(), is(equalTo(newDepartmentDivisionName)));
	co.setCwsOfficeName(newCwsOfficeName);
	assertThat(co.getCwsOfficeName(), is(equalTo(newCwsOfficeName)));
	co.setLocationCountyType(newLocationCountyType);
	assertThat(co.getLocationCountyType(), is(equalTo(newLocationCountyType)));
	co.setCountySpecificCode(newCountySpecificCode);
	assertThat(co.getCountySpecificCode(), is(equalTo(newCountySpecificCode)));
	co.setAgencyCodeNumber(newAgencyCodeNumber);
	assertThat(co.getAgencyCodeNumber(), is(equalTo(newAgencyCodeNumber)));
	co.setDirectorsNameTitle(newDirectorsNameTitle);
	assertThat(co.getDirectorsNameTitle(), is(equalTo(newDirectorsNameTitle)));	
  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
	CwsOffice cwsOffice = new CwsOffice();	  
    assertTrue(cwsOffice.equals(cwsOffice));
  }

  @Test
  public void shouldHaveSameHashCodesForCwsOfficeWithSameValues() {
	CwsOffice cwsOffice = new CwsOfficeEntityBuilder().build();
	CwsOffice cwsOffice1 = new CwsOfficeEntityBuilder().build();
	assertEquals("Expecting CwsOffice object to have same hash code", cwsOffice.hashCode(), cwsOffice1.hashCode());
  }
}
