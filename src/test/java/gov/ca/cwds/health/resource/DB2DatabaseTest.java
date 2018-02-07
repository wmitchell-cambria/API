package gov.ca.cwds.health.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.Before;
import org.junit.Test;

public class DB2DatabaseTest {
  DB2Database db2;
  SessionFactory sessionFactory;
  NativeQuery query;

  @Before
  public void setup() {
    String sql = "select 1 from sysibm.sysdummy1";
    List results = new ArrayList();
    results.add("a result");

    query = mock(NativeQuery.class);
    when(query.list()).thenReturn(results);

    Session session = mock(Session.class);
    when(session.createNativeQuery(sql)).thenReturn(query);

    sessionFactory = mock(SessionFactory.class);
    when(sessionFactory.openSession()).thenReturn(session);

    db2 = new DB2Database(sessionFactory);
  }

  @Test
  public void shouldReturnTrueWhenA200StatusIsReceived() {
    assertTrue("Expected a valid ping", db2.ping());
  }

  @Test
  public void shouldReturnfalseWhenA500StatusIsReceived() {
    when(query.list()).thenReturn(new ArrayList());
    assertFalse("Expected a invalid ping", db2.ping());
  }

  @Test
  public void shouldContainMessageWhenUnsuccessful() {
    String message = "Unable to retrieve test query";
    List results = new ArrayList();
    results.add(null);

    when(query.list()).thenReturn(results);
    db2.ping();

    assertEquals("Expected error message to contain the status code", message, db2.getMessage());
  }

  @Test
  public void shouldReturnFalseWhenAnExceptionIsThrown() {
    when(sessionFactory.openSession()).thenThrow(new HibernateException("Error Occured"));

    assertFalse("Expected error message to contain the status code", db2.ping());
  }

  @Test
  public void shouldContainMessageWhenExceptionIsThrown() {
    String exceptionMessage = "Error Occured";
    String message = "Exception occurred while connecting to DB: " + exceptionMessage;

    when(sessionFactory.openSession()).thenThrow(new HibernateException(exceptionMessage));

    db2.ping();

    assertEquals("Expected error message to contain the exception message", message,
        db2.getMessage());
  }
}
