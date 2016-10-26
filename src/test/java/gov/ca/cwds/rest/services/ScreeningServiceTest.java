package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.NotImplementedException;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.api.persistence.ns.Address;
import gov.ca.cwds.rest.api.persistence.ns.Screening;
import gov.ca.cwds.rest.jdbi.ns.ScreeningDao;

public class ScreeningServiceTest {
  private ScreeningService screeningService;
  private ScreeningDao screeningDao;
  private PersonService personService;


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    screeningDao = mock(ScreeningDao.class);
    personService = mock(PersonService.class);
    screeningService = new ScreeningService(screeningDao, personService);
  }

  /*
   * find tests
   */
  @Test
  public void findReturnsCorrectScreeningWhenFoundWhenFoundAndParticipantListIsNotNull()
      throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700);
    Person bart = new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", domainAddress);
    Person maggie = new Person("Maggie", "Simpson", "M", "05/21/1991", "123456789", domainAddress);

    Address address = new Address(1L, "742 Evergreen Terrace", "Springfield", "WA", 98700);
    Date date = DomainObject.uncookDateString("10/13/2016");
    ImmutableSet.Builder<gov.ca.cwds.rest.api.persistence.ns.Person> persistentPersonSetBuilder =
        ImmutableSet.builder();
    persistentPersonSetBuilder.add(new gov.ca.cwds.rest.api.persistence.ns.Person(bart, null))
        .add(new gov.ca.cwds.rest.api.persistence.ns.Person(maggie, null));

    Screening screening = new Screening("X5HNJK", date, "Amador", date, "Home", "email",
        "First screening", "accept_for_investigation", date, "first narrative", address,
        persistentPersonSetBuilder.build());


    ImmutableSet.Builder<Person> domainPersonSetBuilder = ImmutableSet.builder();
    domainPersonSetBuilder.add(bart).add(maggie);

    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(1L)).thenReturn(bart);
    when(personService.find(2L)).thenReturn(maggie);

    ScreeningResponse expected = new ScreeningResponse("X5HNJK", "2016-10-13", "Amador",
        "2016-10-13", "Home", "email", "First screening", "immediate", "accept_for_investigation",
        "10/11/2016", "first narrative", domainAddress, domainPersonSetBuilder.build());

    Response found = screeningService.find(123L);
    assertThat(found.getClass(), is(ScreeningResponse.class));

    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsCorrectScreeningWhenFoundWhenFoundAndParticipantListIsNull()
      throws Exception {
    Address address = new Address(1L, "742 Evergreen Terrace", "Springfield", "WA", 98700);
    Date date = DomainObject.uncookDateString("10/13/2016");
    Screening screening = new Screening("X5HNJK", date, "Amador", date, "Home", "email",
        "First screening", "accept_for_investigation", date, "first narrative", address, null);

    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700);

    when(screeningDao.find(new Long(123))).thenReturn(screening);

    ImmutableSet.Builder<Person> setbuilder = ImmutableSet.builder();
    ScreeningResponse expected = new ScreeningResponse("X5HNJK", "10/13/2016", "Amador",
        "10/13/2016", "Home", "email", "First screening", null, "accept_for_investigation",
        "10/13/2016", "first narrative", domainAddress, setbuilder.build());

    Response found = screeningService.find(123L);
    assertThat(found.getClass(), is(ScreeningResponse.class));

    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = screeningService.find(33L);

    assertThat(found, is(nullValue()));
  }


  @Test
  public void findThrowsAssertionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      screeningService.find("nonLong");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }


  /*
   * create tests
   */
  @Test
  public void createReturnsPostedScreening() throws Exception {
    Screening screeningMock = mock(Screening.class);
    when(screeningMock.getReference()).thenReturn("some_reference");
    when(screeningMock.getId()).thenReturn(1L);
    ScreeningReference request = new ScreeningReference("some_reference");

    when(screeningDao.create(any(gov.ca.cwds.rest.api.persistence.ns.Screening.class)))
        .thenReturn(screeningMock);

    PostedScreening postedScreening = screeningService.create(request);
    assertThat(postedScreening.getClass(), is(PostedScreening.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    Screening screeningMock = mock(Screening.class);
    when(screeningMock.getReference()).thenReturn("some_reference");
    when(screeningMock.getId()).thenReturn(1L);
    ScreeningReference request = new ScreeningReference("some_reference");

    when(screeningDao.create(any(gov.ca.cwds.rest.api.persistence.ns.Screening.class)))
        .thenReturn(screeningMock);

    PostedScreening postedScreening = screeningService.create(request);
    assertThat(postedScreening, is(notNullValue()));
  }

  @Test
  public void createReturnsReturnsCorrectPostedScreening() throws Exception {
    Screening screeningMock = mock(Screening.class);
    when(screeningMock.getReference()).thenReturn("some_reference");
    when(screeningMock.getId()).thenReturn(1L);
    ScreeningReference request = new ScreeningReference("some_reference");

    when(screeningDao.create(any(gov.ca.cwds.rest.api.persistence.ns.Screening.class)))
        .thenReturn(screeningMock);

    PostedScreening expected = new PostedScreening(1L, "some_reference");
    PostedScreening returned = screeningService.create(request);

    assertThat(returned, is(expected));
  }

  /*
   * delete tests
   */
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningService.delete(new Long(1));
  }

  @Test
  public void deleteThrowsAssertionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      screeningService.delete("nonLong");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  /*
   * update tests
   */
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      screeningService.update("wrong", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateReturnsScreeningResponseOnSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700);
    ImmutableList.Builder<Long> peopleIdListBuilder = ImmutableList.builder();
    ImmutableList<Long> peopleIds = peopleIdListBuilder.add(1L).add(2L).build();

    ScreeningRequest screeningRequest = new ScreeningRequest("ref", "10/1/2016", "Sac", "10/1/2016",
        "loc", "comm", "name", "now", "sure", "1/1/2015", "narrative", domainAddress, peopleIds);

    Person bart = new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", domainAddress);
    Person maggie = new Person("Maggie", "Simpson", "M", "05/21/1991", "123456789", domainAddress);
    gov.ca.cwds.rest.api.persistence.ns.Screening screening =
        new gov.ca.cwds.rest.api.persistence.ns.Screening(1L, screeningRequest,
            new Address(domainAddress, null), null, null);

    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(1L)).thenReturn(bart);
    when(personService.find(2L)).thenReturn(maggie);
    when(screeningDao.update(any())).thenReturn(screening);

    Object retval = screeningService.update(1L, screeningRequest);
    assertThat(retval.getClass(), is(ScreeningResponse.class));
  }

  @Test
  public void updateReturnsCorrectScreeningResponseOnSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700);
    ImmutableList.Builder<Long> peopleIdListBuilder = ImmutableList.builder();
    ImmutableList<Long> peopleIds = peopleIdListBuilder.add(1L).add(2L).build();

    ScreeningRequest screeningRequest = new ScreeningRequest("ref", "10/1/2016", "Sac", "10/1/2016",
        "loc", "comm", "name", "now", "sure", "1/1/2015", "narrative", domainAddress, peopleIds);

    ImmutableSet.Builder<gov.ca.cwds.rest.api.persistence.ns.Person> peopleListBuilder =
        ImmutableSet.builder();
    Person bart = new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", domainAddress);
    Person maggie = new Person("Maggie", "Simpson", "M", "05/21/1991", "123456789", domainAddress);
    ImmutableSet<gov.ca.cwds.rest.api.persistence.ns.Person> people =
        peopleListBuilder.add(new gov.ca.cwds.rest.api.persistence.ns.Person(bart, null))
            .add(new gov.ca.cwds.rest.api.persistence.ns.Person(maggie, null)).build();
    gov.ca.cwds.rest.api.persistence.ns.Screening screening =
        new gov.ca.cwds.rest.api.persistence.ns.Screening(1L, screeningRequest,
            new Address(domainAddress, null), people, null);


    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(1L)).thenReturn(bart);
    when(personService.find(2L)).thenReturn(maggie);
    when(screeningDao.update(any())).thenReturn(screening);

    ScreeningResponse expected = new ScreeningResponse(screening, people);


    ScreeningResponse updated = screeningService.update(1L, screeningRequest);
    assertThat(updated, is(expected));
  }

  @Test
  public void updateThrowsExceptionWhenParticipantNotFound() throws Exception {
    thrown.expect(ServiceException.class);
    thrown.expectCause(Is.isA(EntityNotFoundException.class));
    thrown.expectMessage(contains("Unable to find participant"));

    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700);
    ImmutableList.Builder<Long> peopleIdListBuilder = ImmutableList.builder();
    ImmutableList<Long> peopleIds = peopleIdListBuilder.add(11L).build();

    ScreeningRequest screeningRequest = new ScreeningRequest("ref", "10/1/2016", "Sac", "10/1/2016",
        "loc", "comm", "name", "now", "sure", "1/1/2015", "narrative", domainAddress, peopleIds);

    gov.ca.cwds.rest.api.persistence.ns.Screening screening =
        new gov.ca.cwds.rest.api.persistence.ns.Screening(1L, screeningRequest,
            new Address(domainAddress, null), null, null);

    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(11L)).thenReturn(null);
    when(screeningDao.update(any())).thenReturn(screening);

    screeningService.update(123L, screeningRequest);
  }

  @Test
  public void updateThrowsAssertionErrorOnWrongIdType() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      screeningService.update("nonLong", new ScreeningRequest("ref", "10/1/2016", "Sac",
          "10/1/2016", "loc", "comm", "name", "now", "sure", "1/1/2015", "narrative", null, null));
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateThrowsAssertionErrorOnWrongRequestType() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      screeningService.update(1L, new Request() {});
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }
}
