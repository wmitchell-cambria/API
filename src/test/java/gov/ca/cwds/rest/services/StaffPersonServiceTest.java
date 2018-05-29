package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.xa.XaNsStaffPersonDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.StaffPerson;

/**
 * @author CWDS API Team
 */
public class StaffPersonServiceTest {

  private StaffPersonService staffPersonService;
  private XaNsStaffPersonDao staffPersonDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    staffPersonDao = mock(XaNsStaffPersonDao.class);
    staffPersonService = new StaffPersonService(staffPersonDao);
  }

  @Test
  public void findReturnsCorrectStaffPersonWhenFound() throws Exception {
    String id = "ABC";
    StaffPerson domainStaffPerson = new StaffPerson("2016-10-31", "John", "CEO", "Doe", "C", "Mr",
        new BigDecimal(9165551212L), 22, "2016-10-31", "III", true, "MIZN02k11B", "abc", "def",
        "99", false, "3XPCP92b24", "john.doe@anyco.com");
    PostedStaffPerson expected = new PostedStaffPerson(domainStaffPerson, "ABC");
    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id,
            DomainChef.uncookDateString("2016-10-31"), "John", "CEO", "Doe", "C", "Mr",
            new BigDecimal(9165551212L), 22, DomainChef.uncookDateString("2016-10-31"), "III",
            DomainChef.cookBoolean(true), "MIZN02k11B", "abc", "def", "99",
            DomainChef.cookBoolean(false), "3XPCP92b24", "john.doe@anyco.com");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    StaffPerson found = staffPersonService.find(id);
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = staffPersonService.find("0XA");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    staffPersonService.delete("ABC");
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    staffPersonService.update("ABC", mock(StaffPerson.class));
  }

  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    staffPersonService.create(mock(StaffPerson.class));
  }

}
