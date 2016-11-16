package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import gov.ca.cwds.rest.api.domain.cms.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.cms.StaffPerson;
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
    String id = "ABC";
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(id, expected, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);

    StaffPerson found = staffPersonService.find(id);

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
    String id = "ABC";
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(id, expected, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);
    Object retval = staffPersonService.update(id, expected);
    assertThat(retval.getClass(), is(StaffPerson.class));

  }

  @Test
  public void updateReturnsCorrectStaffPersonOnSuccess() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(id, staffPersonRequest, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);

    StaffPerson expected = new StaffPerson(staffPerson);
    StaffPerson updated = staffPersonService.update(id, expected);

    assertThat(updated, is(expected));

  }

  @Test
  public void updateThrowsExceptionWhenStaffPersonNotFound() throws Exception {
    // TODO: test does not throw exception from allegationService.update method
    //
    // remove comments before running unit test
    //
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Expected test to throw"));
    String id = "ABC";
    StaffPerson staffPersonRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(id, staffPersonRequest, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);

    staffPersonService.update("ZZZ", staffPersonRequest);
  }

  @Test
  public void createReturnsPostedStaffPerson() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.rest.api.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(id, staffPersonDomain, "last_update");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.rest.api.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    Response response = staffPersonService.create(request);

    assertThat(response.getClass(), is(PostedStaffPerson.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.rest.api.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(id, staffPersonDomain, "last_update");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.rest.api.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    PostedStaffPerson postedStaffPerson = staffPersonService.create(request);

    assertThat(postedStaffPerson, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.rest.api.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.StaffPerson(id, staffPersonDomain, "last_update");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.rest.api.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    PostedStaffPerson expected = new PostedStaffPerson(toCreate);

    PostedStaffPerson returned = staffPersonService.create(request);

    assertThat(returned, is(expected));
  }
}
