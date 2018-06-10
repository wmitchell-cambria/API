package gov.ca.cwds.inject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.rest.RestStatus;
import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AgencyDao;
import gov.ca.cwds.data.ns.AllegationIntakeDao;
import gov.ca.cwds.data.ns.CrossReportDao;
import gov.ca.cwds.data.ns.ScreeningAddressDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.ns.xa.XaNsScreeningDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import gov.ca.cwds.fixture.ScreeningResourceBuilder;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.services.ParticipantIntakeApiService;
import gov.ca.cwds.rest.services.mapper.AddressMapper;
import gov.ca.cwds.rest.services.mapper.AgencyMapper;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.CsecMapper;
import gov.ca.cwds.rest.services.mapper.SafelySurrenderedBabiesMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * Guice module injects dependencies into test classes.
 * 
 * @author CWDS API Team
 */
public class DoofenshmirtzModule extends AbstractModule {

  final Doofenshmirtz<ClientAddress> inator = new Doofenshmirtz<>();

  final Client esClient = mock(Client.class);
  final IndexRequestBuilder indexRequestBuilder = mock(IndexRequestBuilder.class);
  final XaNsScreeningDaoImpl screeningDao = mock(XaNsScreeningDaoImpl.class);
  final IndexResponse indexResponse = mock(IndexResponse.class);

  final ScreeningMapper screeningMapper = mock(ScreeningMapper.class);
  final CrossReportMapper crossReportMapper = mock(CrossReportMapper.class);
  final CsecMapper csecMapper = mock(CsecMapper.class);
  final SafelySurrenderedBabiesMapper safelySurrenderedBabiesMapper =
      mock(SafelySurrenderedBabiesMapper.class);

  final ScreeningEntity screeningEntity = new ScreeningEntityBuilder().build();
  final Screening screening = inator.inatorMakeScreening();
  CrossReportIntake crossReportIntake;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure() {
    try {
      Doofenshmirtz.setupClass();
      inator.setup();

      // Allegation allegation = new AllegationResourceBuilder().createAllegation();
      // Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
      // Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
      // screeningEntity.setAllegations(allegation);
    } catch (Exception e) {
      throw new IllegalStateException("Oops!", e);
    }

    when(inator.esDao.getConfig()).thenReturn(inator.esConfig);
    when(inator.esDao.getClient()).thenReturn(esClient);
    when(inator.esConfig.getElasticsearchAlias()).thenReturn("screenings");
    when(inator.esConfig.getElasticsearchDocType()).thenReturn("screening");
    when(esClient.prepareIndex(any(), any(), any())).thenReturn(indexRequestBuilder);
    when(indexRequestBuilder.get()).thenReturn(indexResponse);
    when(indexResponse.status()).thenReturn(RestStatus.OK);

    Set<GovernmentAgencyIntake> agencies =
        Stream.of(new GovernmentAgencyIntake("12", "Ad4ATcY00E", "LAW_ENFORCEMENT"))
            .collect(Collectors.toSet());
    crossReportIntake = new CrossReportIntake();
    crossReportIntake.setId("");
    crossReportIntake.setLegacyId("");
    crossReportIntake.setLegacySourceTable("");
    crossReportIntake.setMethod("Electronic Report");
    crossReportIntake.setFiledOutOfState(false);
    crossReportIntake.setInformDate("2017-03-15");
    crossReportIntake.setAgencies(agencies);
    crossReportIntake.setCountyId("1101");

    final List<CrossReportIntake> crossReportEntities = new ArrayList<>();
    crossReportEntities.add(crossReportIntake);
    when(crossReportMapper.map(any(CrossReportEntity.class))).thenReturn(crossReportIntake);

    final Screening screening = new ScreeningResourceBuilder().build();
    when(screeningDao.find(any(String.class))).thenReturn(screeningEntity);
    when(screeningMapper.map(any(ScreeningEntity.class))).thenReturn(screening);
    when(inator.allegationIntakeDao.findByScreeningId(any(String.class)))
        .thenReturn(Arrays.asList(screening.getAllegations().toArray(new AllegationEntity[0])));

    bind(Client.class).toInstance(esClient);
    bind(CrossReportMapper.class).toInstance(crossReportMapper);
    bind(CsecMapper.class).toInstance(csecMapper);
    bind(ElasticsearchConfiguration.class).toInstance(inator.esConfig);
    bind(ElasticsearchDao.class).toInstance(inator.esDao);
    bind(IndexRequestBuilder.class).toInstance(indexRequestBuilder);
    bind(SafelySurrenderedBabiesMapper.class).toInstance(safelySurrenderedBabiesMapper);
    bind(ScreeningDao.class).toInstance(screeningDao);
    bind(ScreeningMapper.class).toInstance(screeningMapper);
    bind(XaNsScreeningDaoImpl.class).toInstance(screeningDao);

    bind(AddressMapper.class).toInstance(mock(AddressMapper.class));
    bind(AgencyDao.class).toInstance(mock(AgencyDao.class));
    bind(AgencyMapper.class).toInstance(mock(AgencyMapper.class));
    bind(AllegationIntakeDao.class).toInstance(mock(AllegationIntakeDao.class));
    bind(AllegationMapper.class).toInstance(mock(AllegationMapper.class));
    bind(CrossReportDao.class).toInstance(mock(CrossReportDao.class));
    bind(IndexResponse.class).toInstance(mock(IndexResponse.class));
    bind(ParticipantIntakeApiService.class).toInstance(mock(ParticipantIntakeApiService.class));
    bind(ScreeningAddressDao.class).toInstance(mock(ScreeningAddressDao.class));
  }

  @Provides
  @CmsSessionFactory
  public SessionFactory cmsSessionFactory() {
    return inator.sessionFactory;
  }

  @Provides
  @NsSessionFactory
  public SessionFactory nsSessionFactory() {
    return inator.sessionFactory;
  }

  @Provides
  @CwsRsSessionFactory
  public SessionFactory rsSessionFactory() {
    return inator.sessionFactory;
  }

  @Provides
  @XaNsSessionFactory
  public SessionFactory xaNsSessionFactory() {
    return inator.sessionFactory;
  }

  @Provides
  @XaCmsSessionFactory
  public SessionFactory xaCmsSessionFactory() {
    return inator.sessionFactory;
  }

  @Provides
  @Named("screenings.index")
  public ElasticsearchDao makeEsDao() {
    return inator.esDao;
  }

}
