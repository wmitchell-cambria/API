package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class AssigneeTest {

  private String name = "CWS Madera Staff Person";
  private String countyCode = "20";
  private String office = "Madera CWS Office";
  private String staffId = "0X5";

  private StaffPerson staffPerson = new StaffPersonEntityBuilder().setLastName(name).build();

  @Test
  public void testEmptyConsturctorSuccess() {
    Assignee assignee = new Assignee();
    assertNotNull(assignee);
  }

  @Test
  public void testConstructorSuccess() {
    Assignee assignee = new Assignee(name, countyCode, office, staffId);
    assertThat(assignee.getName(), is(equalTo(name)));
    assertThat(assignee.getCountyCode(), is(equalTo(countyCode)));
    assertThat(assignee.getOffice(), is(equalTo(office)));
    assertThat(assignee.getStaffId(), is(equalTo(staffId)));
  }


  @Test
  // @Ignore
  public void testStaffPersonConstructorSuccess() {
    String name = StringUtils.trim(staffPerson.getFirstName())
        + StringUtils.trim(staffPerson.getMiddleInitial())
        + StringUtils.trim(staffPerson.getLastName());
    Assignee assignee = new Assignee(staffPerson);
    assertThat(assignee.getName(), is(equalTo(name)));
    assertThat(assignee.getCountyCode(), is(equalTo(staffPerson.getCountyCode())));
    assertThat(assignee.getOffice(), is(equalTo(staffPerson.getCwsOffice())));
    assertThat(assignee.getStaffId(), is(equalTo(staffPerson.getId())));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Assignee assignee = new Assignee(name, countyCode, office, staffId);
    Assignee otherAssignee = new Assignee(name, countyCode, office, staffId);
    assertEquals(assignee, otherAssignee);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Assignee assignee = new Assignee(name, countyCode, office, staffId);
    Assignee otherAssignee = new Assignee(name, "22", office, staffId);
    assertThat(assignee, is(not(equals(otherAssignee))));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Assignee.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }
}
