package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.IntakeLov;

public class IntakeLovDaoTest {

  private SessionFactory sessionFactory;

  @Before
  public void setup() throws Exception {
    sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);

    Transaction transaction = mock(Transaction.class);
    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);

    sessionFactory = mock(SessionFactory.class);

    SessionFactoryOptions sfo = mock(SessionFactoryOptions.class);
    when(sessionFactory.getSessionFactoryOptions()).thenReturn(sfo);

    StandardServiceRegistry reg = mock(StandardServiceRegistry.class);
    when(sfo.getServiceRegistry()).thenReturn(reg);

    ConnectionProvider cp = mock(ConnectionProvider.class);
    when(reg.getService(ConnectionProvider.class)).thenReturn(cp);

    Connection con = mock(Connection.class);
    when(cp.getConnection()).thenReturn(con);
  }

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    IntakeLovDao target = new IntakeLovDao(sessionFactory);
    assertThat(target, notNullValue());
  }

  @Test
  public void findAll_Args__() throws Exception {
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);
    Transaction tx = mock(Transaction.class);
    Query query = mock(Query.class);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getNamedQuery(any(String.class))).thenReturn(query);
    when(session.beginTransaction()).thenReturn(tx);
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    when(query.list()).thenReturn(Arrays.asList(intakeLov));
    when(query.setString(any(String.class), any(String.class))).thenReturn(query);
    IntakeLovDao target = new IntakeLovDao(sessionFactory);
    Map<String, IntakeLov> response = target.findByLegacyMetaId("LANG_TPC");
    assertThat(response, notNullValue());
  }


}
