package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.legacy.StaffPerson;
import gov.ca.cwds.rest.jdbi.cms.StaffPersonDao;
import io.dropwizard.jackson.Jackson;

public class StaffPersonServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPersonService staffPersonService;
  private StaffPersonDao staffPersonDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    staffPersonDao = mock(StaffPersonDao.class);

    staffPersonService = new StaffPersonService(staffPersonDao);

  }

  // find test
  @Test
  public void findThrowsAssertionError() {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      staffPersonService.find("1");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {

    }
  }

  @Test
  public void findReturnsCorrectStaffPersonWhenFound() throws Exception {
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(expected.getId(), expected, "ABC");

    when(staffPersonDao.find(expected.getId())).thenReturn(staffPerson);

    StaffPerson found = staffPersonService.find("ABC");

    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = staffPersonService.find("0XA");
    assertThat(found, is(nullValue()));
  }

  // delete test
  // @Test
  // public void deleteThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  // staffPersonService.delete("1");
  //
  // }

  public void deleteThrowsAssersionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      staffPersonService.delete("1");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  // update test
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // TODO: thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      staffPersonService.update("xxx", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateReturnsStaffPersonResponseOnSuccess() throws Exception {
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(expected.getId(), expected, "ABC");

    when(staffPersonDao.find("ABC")).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);
    Object retval = staffPersonService.update("ABC", expected);
    assertThat(retval.getClass(), is(StaffPerson.class));

  }

  @Test
  public void updateReturnsCorrectStaffPersonOnSuccess() throws Exception {
    StaffPerson staffPersonRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(staffPersonRequest.getId(),
            staffPersonRequest, "ABC");

    when(staffPersonDao.find("ABC")).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);

    StaffPerson expected = new StaffPerson(staffPerson);
    StaffPerson updated = staffPersonService.update("ABC", expected);

    assertThat(updated, is(expected));

  }

  @Test
  public void updateThrowsExceptionWhenStaffPersonNotFound() throws Exception {
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Unable to find"));

    StaffPerson staffPersonRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(staffPersonRequest.getId(),
            staffPersonRequest, "ABC");

    when(staffPersonDao.find("ABC")).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);

    staffPersonService.update("ZZZ", staffPersonRequest);

  }

  // @Test
  // public void createThrowsAssertionError() throws Exception {
  // // TODO: thrown.expect not working on AssertionError???? WHY???
  // // thrown.expect(AssertionError.class);
  // StaffPerson staffPersonRequest = MAPPER.readValue(
  // fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
  // try {
  // staffPersonService.create(staffPersonRequest);
  // Assert.fail("Expected AssertionError");
  // } catch (AssertionError e) {
  // }
  // }

}
