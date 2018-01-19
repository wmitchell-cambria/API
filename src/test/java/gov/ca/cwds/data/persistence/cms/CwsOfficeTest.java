package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

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
}
