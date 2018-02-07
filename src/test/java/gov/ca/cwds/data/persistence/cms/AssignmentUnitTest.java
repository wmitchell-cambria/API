package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.Test;

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
	
	@Test
	public void testEmptyConstructor() {
		AssignmentUnit assignmentUnit = new AssignmentUnit();
		
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
	
}
