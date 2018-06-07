package gov.ca.cwds.inject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import com.google.inject.AbstractModule;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AgencyDao;
import gov.ca.cwds.data.ns.AllegationIntakeDao;
import gov.ca.cwds.data.ns.CrossReportDao;
import gov.ca.cwds.data.ns.ScreeningAddressDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.ns.xa.XaNsScreeningDaoImpl;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.services.ParticipantIntakeApiService;
import gov.ca.cwds.rest.services.mapper.AddressMapper;
import gov.ca.cwds.rest.services.mapper.AgencyMapper;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;

public class DoofenshmirtzModule extends AbstractModule {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure() {
    final ElasticsearchDao esDao = mock(ElasticsearchDao.class);
    final ElasticsearchConfiguration esConfig = mock(ElasticsearchConfiguration.class);
    final Client esClient = mock(Client.class);
    final IndexRequestBuilder indexRequestBuilder = mock(IndexRequestBuilder.class);
    final XaNsScreeningDaoImpl screeningDao = mock(XaNsScreeningDaoImpl.class);
    final IndexResponse indexResponse = mock(IndexResponse.class);
    final ScreeningEntity screeningEntity = new ScreeningEntity();
    final ScreeningMapper screeningMapper = mock(ScreeningMapper.class);

    when(esDao.getConfig()).thenReturn(esConfig);
    when(esDao.getClient()).thenReturn(esClient);
    when(esConfig.getElasticsearchAlias()).thenReturn("screenings");
    when(esConfig.getElasticsearchDocType()).thenReturn("screening");
    when(esClient.prepareIndex(any(), any(), any())).thenReturn(indexRequestBuilder);
    when(indexRequestBuilder.get()).thenReturn(indexResponse);

    when(screeningDao.find(any(String.class))).thenReturn(screeningEntity);

    bind(Client.class).toInstance(esClient);
    bind(ElasticsearchConfiguration.class).toInstance(esConfig);
    bind(ElasticsearchDao.class).toInstance(esDao);
    bind(IndexRequestBuilder.class).toInstance(indexRequestBuilder);
    bind(IndexResponse.class).toInstance(mock(IndexResponse.class));
    bind(XaNsScreeningDaoImpl.class).toInstance(mock(XaNsScreeningDaoImpl.class));
    bind(ScreeningMapper.class).toInstance(screeningMapper);

    bind(AddressMapper.class).toInstance(mock(AddressMapper.class));
    bind(AgencyDao.class).toInstance(mock(AgencyDao.class));
    bind(AgencyMapper.class).toInstance(mock(AgencyMapper.class));
    bind(AllegationIntakeDao.class).toInstance(mock(AllegationIntakeDao.class));
    bind(AllegationMapper.class).toInstance(mock(AllegationMapper.class));
    bind(CrossReportDao.class).toInstance(mock(CrossReportDao.class));
    bind(ParticipantIntakeApiService.class).toInstance(mock(ParticipantIntakeApiService.class));
    bind(ScreeningAddressDao.class).toInstance(mock(ScreeningAddressDao.class));
    bind(ScreeningDao.class).toInstance(mock(ScreeningDao.class));

    bind(CrossReportMapper.class).toInstance(mock(CrossReportMapper.class));
    bind(ScreeningMapper.class).toInstance(mock(ScreeningMapper.class));
  }

}
