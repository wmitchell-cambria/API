package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.AssignmentUnitEntityBuilder;

public class AssignmentUnitTest {

	private String id = "1234567ABC";
	private String assignmentDeskUnitIndicator = "N";
	private Date endDate = null;
	private Long phoneNumber = (long) 4567890;
	private int phoneExtensionNumber = 123;
	private Date startDate = new Date();
	private String fkCwsOffice = "2345678ABC";
	private String  assignmentUnitName = "assignment unit name";
	private String countySpecificCode = "20";
	private String newId = "1234567DFG";
	private String newAssignmentDeskUnitIndicator = "Y";
	private Date newEndDate = new Date();
	private Long newPhoneNumber = (long) 3456789;
	private int newPhoneExtensionNumber = 122;
	private Date newStartDate = new Date();
	private String newFkCwsOffice = "2345678DFG";
	private String  newAssignmentUnitName = "new assignment unit name";
	private String newCountySpecificCode = "34";
	
	@Test
	public void testEmptyConstructor() throws Exception {
	    assertThat(AssignmentUnit.class.newInstance(), is(notNullValue()));		
	}
	
	@Test
	public void testConstructor() {
		AssignmentUnit assignmentUnit = new AssignmentUnit(id, assignmentDeskUnitIndicator, endDate,
			phoneNumber, phoneExtensionNumber, startDate,
			fkCwsOffice, assignmentUnitName, countySpecificCode);
		
		assertThat(assignmentUnit.getId(), is(equalTo(id)));
		assertThat(assignmentUnit.getAssignmentDeskUnitIndicator(), is(equalTo(assignmentDeskUnitIndicator)));
		assertThat(assignmentUnit.getEndDate(), is(equalTo(endDate)));
		assertThat(assignmentUnit.getPhoneNumber(), is(equalTo(phoneNumber)));
		assertThat(assignmentUnit.getPhoneExtensionNumber(), is(equalTo(phoneExtensionNumber)));
		assertThat(assignmentUnit.getStartDate(), is(equalTo(startDate)));
		assertThat(assignmentUnit.getFkCwsOffice(), is(equalTo(fkCwsOffice)));
		assertThat(assignmentUnit.getAssignmentUnitName(), is(equalTo(assignmentUnitName)));
		assertThat(assignmentUnit.getCountySpecificCode(), is(equalTo(countySpecificCode)));
		
	}
	
	@Test
	public void shouldModifyValuesWithSetters() {
		AssignmentUnit assignmentUnit = new AssignmentUnit(id, assignmentDeskUnitIndicator, endDate,
			phoneNumber, phoneExtensionNumber, startDate,
			fkCwsOffice, assignmentUnitName, countySpecificCode);
		
		assignmentUnit.setId(newId);
		assertThat(assignmentUnit.getId(), is(equalTo(newId)));
		assignmentUnit.setAssignmentDeskUnitIndicator(newAssignmentDeskUnitIndicator);
		assertThat(assignmentUnit.getAssignmentDeskUnitIndicator(), is(equalTo(newAssignmentDeskUnitIndicator)));
		assignmentUnit.setEndDate(newEndDate);
		assertThat(assignmentUnit.getEndDate(), is(equalTo(newEndDate)));
		assignmentUnit.setPhoneNumber(newPhoneNumber);
		assertThat(assignmentUnit.getPhoneNumber(), is(equalTo(newPhoneNumber)));
		assignmentUnit.setPhoneExtensionNumber(newPhoneExtensionNumber);
		assertThat(assignmentUnit.getPhoneExtensionNumber(), is(equalTo(newPhoneExtensionNumber)));
		assignmentUnit.setStartDate(newStartDate);
		assertThat(assignmentUnit.getStartDate(), is(equalTo(newStartDate)));
		assignmentUnit.setFkCwsOffice(newFkCwsOffice);
		assertThat(assignmentUnit.getFkCwsOffice(), is(equalTo(newFkCwsOffice)));
		assignmentUnit.setCountySpecificCode(newCountySpecificCode);
		assertThat(assignmentUnit.getCountySpecificCode(), is(equalTo(newCountySpecificCode)));
		assignmentUnit.setAssignmentUnitName(newAssignmentUnitName);
		assertThat(assignmentUnit.getAssignmentUnitName(), is(equalTo(newAssignmentUnitName)));
	  
	}
	  @Test
	  public void equalsShouldBeTrueWhenSameObject() throws Exception {
		AssignmentUnit assignmentUnit = new AssignmentUnit();	  
	    assertTrue(assignmentUnit.equals(assignmentUnit));
	  }
	  @Test
	  public void shouldHaveSameHashCodesForAssignmentUnitWithSameValues() {
		AssignmentUnit assignmentUnit1 = new AssignmentUnitEntityBuilder().build();
		AssignmentUnit assignmentUnit2 = new AssignmentUnitEntityBuilder().setEndDate(assignmentUnit1.getEndDate())
			.setStartDate(assignmentUnit1.getStartDate()).build();
		assertEquals("Expecting AssignmentUnits to have same hash code", assignmentUnit1.hashCode(), assignmentUnit2.hashCode());
	  }
	
}
