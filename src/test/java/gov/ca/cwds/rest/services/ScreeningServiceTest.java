package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.NotImplementedException;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.api.domain.ScreeningResponseCreated;

public class ScreeningServiceTest {
  private ScreeningService screeningService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    screeningService = new ScreeningService();
  }

  /*
   * find tests
   */
  @Test
  public void findReturnsCorrectScreeningWhenFoundWhenFound() throws Exception {
    Address address = new Address("10 main st", "Sacramento", "CA", 95814);
    ImmutableList.Builder<Person> builder = ImmutableList.builder();
    ImmutableList<Person> people =
        builder.add(new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address))
            .add(new Person("Maggie", "Simpson", "M", "05/21/1991", "123456789", address)).build();
    ScreeningResponse expected = new ScreeningResponse("X5HNJK", "2016-10-13", "Amador",
        "2016-10-13", "Home", "email", "First screening", "immediate", "accept_for_investigation",
        "10/11/2016", "first narrative", address, people);

    Response found = screeningService.find(123L);
    assertThat(found.getClass(), is(ScreeningResponse.class));

    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = screeningService.find("notfound");

    assertThat(found, is(nullValue()));
  }

  /*
   * create tests
   */
  @Test
  public void createThrowsExceptionOnWrongType() throws Exception {
    thrown.expect(ServiceException.class);
    Address address = new Address("10 main st", "Sacramento", "CA", 95814);
    ImmutableList.Builder<Person> builder = ImmutableList.builder();
    ImmutableList<Person> people =
        builder.add(new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address))
            .add(new Person("Maggie", "Simpson", "M", "05/21/1991", "123456789", address)).build();
    ScreeningResponse toCreate = new ScreeningResponse("X5HNJK", "2016-10-13", "Amador",
        "2016-10-13", "Home", "email", "First screening", "immediate", "accept_for_investigation",
        "10/11/2016", "first narrative", address, people);
    Response response = screeningService.create(toCreate);

    assertThat(response, is(notNullValue()));
    assertThat(response.getClass(), is(ScreeningResponseCreated.class));
  }

  @Test
  public void createReturnsScreeningResponseCreatedOnCreate() throws Exception {
    ScreeningReference toCreate = new ScreeningReference("success");
    Response response = screeningService.create(toCreate);

    assertThat(response, is(notNullValue()));
    assertThat(response.getClass(), is(ScreeningResponseCreated.class));
  }

  @Test
  public void createThrowsExceptionWhenAlreadyExists() throws Exception {
    thrown.expect(ServiceException.class);
    thrown.expectCause(Is.isA(EntityExistsException.class));

    ScreeningReference toCreate = new ScreeningReference("failure");
    screeningService.create(toCreate);
  }

  /*
   * delete tests
   */
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningService.delete("someid");
  }

  /*
   * update tests
   */
  @Test
  public void updateReturnsScreeningResponseOnSuccess() throws Exception {
    Address address = new Address("10 main st", "Sacramento", "CA", 95814);
    ImmutableList.Builder<Long> builder = ImmutableList.builder();
    ImmutableList<Long> ids = builder.add(new Long(123)).add(new Long(345)).build();
    ScreeningRequest toUpdate = new ScreeningRequest("X5HNJK", "11/22/1973", "Amador", "11/22/1973",
        "Home", "email", "First screening", "immediate", "accept_for_investigation", "10/11/2016",
        "first narrative", address, ids);
    screeningService.update(123L, toUpdate);
  }

  @Test
  public void updateThrowsExceptionWhenNotFound() throws Exception {
    thrown.expect(ServiceException.class);
    thrown.expectCause(Is.isA(EntityNotFoundException.class));
    Address address = new Address("10 main st", "Sacramento", "CA", 95814);
    ImmutableList.Builder<Long> builder = ImmutableList.builder();
    ImmutableList<Long> ids = builder.add(new Long(123)).add(new Long(345)).build();
    ScreeningRequest toUpdate = new ScreeningRequest("X5HNJK", "11/22/1973", "Amador", "11/22/1973",
        "Home", "email", "First screening", "immediate", "accept_for_investigation", "10/11/2016",
        "first narrative", address, ids);
    screeningService.update(345L, toUpdate);
  }
}
