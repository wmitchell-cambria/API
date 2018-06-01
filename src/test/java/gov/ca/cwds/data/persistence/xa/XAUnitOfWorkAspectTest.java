package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class XAUnitOfWorkAspectTest extends Doofenshmirtz<Addresses> {

  ImmutableMap<String, SessionFactory> sessionFactories;
  XAUnitOfWorkAspect target;
  XAUnitOfWork xaUnitOfWork;

  @Override
  public void setup() throws Exception {
    super.setup();
    sessionFactories = ImmutableMap.<String, SessionFactory>of("cms", sessionFactory);
    xaUnitOfWork = mock(XAUnitOfWork.class);
    target = new XAUnitOfWorkAspect(sessionFactories);
    target.setXaUnitOfWork(xaUnitOfWork);

    final String[] values = {"cms", "ns"};
    when(xaUnitOfWork.value()).thenReturn(values);
    when(xaUnitOfWork.cacheMode()).thenReturn(CacheMode.NORMAL);
    when(xaUnitOfWork.flushMode()).thenReturn(FlushMode.MANUAL);
    when(xaUnitOfWork.readOnly()).thenReturn(false);
    when(xaUnitOfWork.transactional()).thenReturn(true);
  }

  @Test
  public void type() throws Exception {
    assertThat(XAUnitOfWorkAspect.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void beforeStart_A$XAUnitOfWork() throws Exception {
    target.beforeStart(xaUnitOfWork);
  }

  // @Test
  // public void beforeStart_A$XAUnitOfWork_T$Exception() throws Exception {
  // try {
  // target.beforeStart(xaUnitOfWork);
  // fail("Expected exception was not thrown!");
  // } catch (Exception e) {
  // }
  // }

  @Test
  public void afterEnd_A$() throws Exception {
    target.beforeStart(xaUnitOfWork);
    target.afterEnd();
  }

  // @Test
  // public void afterEnd_A$_T$Exception() throws Exception {
  // try {
  // doThrow(SQLException.class).when(session).close();
  // target.beforeStart(xaUnitOfWork);
  // target.afterEnd();
  // fail("Expected exception was not thrown!");
  // } catch (Exception e) {
  // }
  // }

  @Test
  public void onError_A$() throws Exception {
    target.onError();
  }

  // @Test
  // public void onError_A$_T$Exception() throws Exception {
  // try {
  // doThrow(SQLException.class).when(session).close();
  // target.beforeStart(xaUnitOfWork);
  // target.onError();
  // fail("Expected exception was not thrown!");
  // } catch (Exception e) {
  // }
  // }

  @Test
  public void onFinish_A$() throws Exception {
    target.onFinish();
  }

  // @Test
  // public void onFinish_A$_T$Exception() throws Exception {
  // try {
  // doThrow(SQLException.class).when(session).close();
  // target.beforeStart(xaUnitOfWork);
  // target.onFinish();
  // fail("Expected exception was not thrown!");
  // } catch (Exception e) {
  // }
  // }

  @Test
  public void grabSession_A$SessionFactory() throws Exception {
    Session actual = target.grabSession(sessionFactory);
    Session expected = session;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void openSessions_A$() throws Exception {
    target.openSessions();
  }

  @Test
  public void closeSessions_A$() throws Exception {
    target.closeSessions();
  }

  @Test
  public void closeSession_A$Session() throws Exception {
    target.closeSession(session);
  }

  @Test
  public void configureSession_A$Session() throws Exception {
    target.configureSession(session);
  }

  @Test
  public void beginTransaction_A$() throws Exception {
    target.beginTransaction();
  }

  // @Test
  // public void beginTransaction_A$_T$IllegalStateException() throws Exception {
  // try {
  // target.beginTransaction();
  // fail("Expected exception was not thrown!");
  // } catch (Exception e) {
  // }
  // }

  @Test
  public void rollbackTransaction_A$() throws Exception {
    target.rollbackTransaction();
  }

  // @Test
  // public void rollbackTransaction_A$_T$IllegalStateException() throws Exception {
  // try {
  // target.rollbackTransaction();
  // fail("Expected exception was not thrown!");
  // } catch (IllegalStateException e) {
  // }
  // }

  @Test
  public void commitTransaction_A$() throws Exception {
    target.commitTransaction();
  }

  @Test(expected = CaresXAException.class)
  public void commitTransaction_A$_T$Exception() throws Exception {
    target.commitTransaction();
  }

  @Test
  public void getSessionFactories_A$() throws Exception {
    Map<String, SessionFactory> actual = target.getSessionFactories();
    Map<String, SessionFactory> expected = this.sessionFactories;
    assertThat(actual, is(equalTo(expected)));
  }

}
