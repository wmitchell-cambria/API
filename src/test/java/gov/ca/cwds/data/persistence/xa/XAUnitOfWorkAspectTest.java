package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.rest.core.Api;
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

    final String[] values = {Api.DATASOURCE_XA_CMS, Api.DATASOURCE_XA_NS};
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
  public void beforeStart_A1() throws Exception {
    final Method method =
        MethodUtils.getMatchingMethod(this.getClass(), "beforeStart_A1", new Class[0]);
    target.beforeStart(method, xaUnitOfWork);
  }

  @Test
  public void afterEnd_A1() throws Exception {
    final Method method = MethodUtils.getMatchingMethod(getClass(), "afterEnd_A1", new Class[0]);
    target.beforeStart(method, xaUnitOfWork);
  }

  @Test
  public void onError_A$() throws Exception {
    target.onError();
  }

  @Test
  public void onFinish_A$() throws Exception {
    target.onFinish();
  }

  @Test
  public void grabSession_A$SessionFactory() throws Exception {
    Session actual = target.grabSession(Api.DATASOURCE_XA_CMS, sessionFactory);
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

  @Test
  public void rollbackTransaction_A$() throws Exception {
    target.rollback();
  }

  @Test
  public void commitTransaction_A$() throws Exception {
    target.commit();
  }

  @Test
  public void getSessionFactories_A$() throws Exception {
    Map<String, SessionFactory> actual = target.getSessionFactories();
    Map<String, SessionFactory> expected = this.sessionFactories;
    assertThat(actual, is(equalTo(expected)));
  }

}
