package gov.ca.cwds.rest.api.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantDaoFactoryImpl;

public class ParticipantDaoFactoryImplTest {

  private ParticipantDaoFactoryImpl participantDaoFactoryImpl = new ParticipantDaoFactoryImpl();
  private Injector injector;
  private SessionFactory sessionFactory;

  @Before
  public void setup() {
    injector = mock(Injector.class);
    sessionFactory = mock(SessionFactory.class);
  }

  /**
   * 
   */
  @Test
  public void testDaoClassReturnedCorrect() {
    String tableName = LegacyTable.REPORTER.getName();
    when(injector.getInstance(ReporterDao.class)).thenReturn(new ReporterDao(sessionFactory));
    participantDaoFactoryImpl.setInjector(injector);
    CrudsDao<CmsPersistentObject> crudsDao = participantDaoFactoryImpl.create(tableName);
    assertThat(crudsDao, is(notNullValue()));
  }

  /**
   * 
   */
  @Test
  public void testDaoClassReturnedNull() {
    String tableName = LegacyTable.REPORTER.getName();
    when(injector.getInstance(ClientDao.class)).thenReturn(new ClientDao(sessionFactory));
    participantDaoFactoryImpl.setInjector(injector);
    CrudsDao<CmsPersistentObject> crudsDao = participantDaoFactoryImpl.create(tableName);
    assertThat(crudsDao, is(nullValue()));
  }

}
