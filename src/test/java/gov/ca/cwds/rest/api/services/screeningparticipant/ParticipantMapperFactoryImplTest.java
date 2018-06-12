package gov.ca.cwds.rest.api.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;

import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.screeningparticipant.CollateralIndividualTranformer;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapper;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapperFactoryImpl;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantMapperFactoryImplTest {

  private ParticipantMapperFactoryImpl participantMapperFactoryImpl =
      new ParticipantMapperFactoryImpl();
  private Injector injector;

  @Before
  public void setUp() {
    injector = mock(Injector.class);
  }

  /**
   * 
   */
  @Test(expected = ServiceException.class)
  public void testExceptionWhenTableNotFound() {
    String tableName = LegacyTable.ADDRESS.getName();
    participantMapperFactoryImpl.create(tableName);
  }

  /**
   * 
   */
  @Test
  public void testCreateMapperNotNull() {
    String tableName = LegacyTable.COLLATERAL_INDIVIDUAL.getName();
    when(injector.getInstance(CollateralIndividualTranformer.class))
        .thenReturn(new CollateralIndividualTranformer());
    participantMapperFactoryImpl.setInjector(injector);
    ParticipantMapper participantMapper = participantMapperFactoryImpl.create(tableName);
    assertThat(participantMapper, is(notNullValue()));
  }

}
