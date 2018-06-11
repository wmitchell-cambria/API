package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AgencyDao;
import gov.ca.cwds.data.ns.AllegationIntakeDao;
import gov.ca.cwds.data.ns.CrossReportDao;
import gov.ca.cwds.data.ns.ScreeningAddressDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.AllegationIntake;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.api.domain.ScreeningDashboardList;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.mapper.AddressMapper;
import gov.ca.cwds.rest.services.mapper.AgencyMapper;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;
import gov.ca.cwds.utils.JsonUtils;

/**
 * Business layer object to work on {@link Screening}.
 * 
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

  @Named("screenings.index")
  @Inject
  private ElasticsearchDao esDao;

  @Inject
  private AllegationIntakeDao allegationDao;

  @Inject
  private AddressesDao addressesDao;

  @Inject
  private ScreeningAddressDao screeningAddressDao;

  @Inject
  private AgencyDao agencyDao;

  @Inject
  private CrossReportDao crossReportDao;

  @Inject
  private ScreeningDao screeningDao;

  @Inject
  private ParticipantIntakeApiService participantIntakeApiService;

  @Inject
  private ScreeningMapper screeningMapper;

  @Inject
  private AddressMapper addressMapper;

  @Inject
  private AgencyMapper agencyMapper;

  @Inject
  private AllegationMapper allegationMapper;

  @Inject
  private CrossReportMapper crossReportMapper;

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    throw new NotImplementedException("Find is not implemented");
  }

  /**
   * Return the screening dashboard of the logged in user.
   * 
   * @return - array of screening dashboard objects
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  public Response findScreeningDashboard() {
    return getScreeningsOfUser(RequestExecutionContext.instance().getStaffId());
  }

  private ScreeningDashboardList getScreeningsOfUser(String staffId) {
    final List<ScreeningWrapper> screenings = screeningDao.findScreeningsByUserId(staffId);
    final List<ScreeningDashboard> screeningDashboard = new ArrayList<>(screenings.size());
    for (ScreeningWrapper screening : screenings) {
      screeningDashboard.add(new ScreeningDashboard(screening));
    }
    return new ScreeningDashboardList(screeningDashboard);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @param request the request
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Screening create(Request request) {
    index(request);
    return (Screening) request;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Screening update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof Screening;
    final Screening screening = (Screening) request;

    if (!primaryKey.equals(screening.getId())) {
      throw new ServiceException(
          "Primary key mismatch, [" + primaryKey + " != " + screening.getId() + "]");
    }

    index(request);
    return (Screening) request;
  }

  /**
   * Convert given screening to JSON.
   * 
   * @param screening Screening to convert to JSON format
   * @return Screening as JSON format
   */
  private String toJson(Screening screening) {
    String screeningJson;
    try {
      screeningJson = JsonUtils.to(screening);
    } catch (JsonProcessingException e) {
      throw new ServiceException(e);
    }
    return screeningJson;
  }

  /**
   * Index given screening request.
   * 
   * @param request The screening request
   * @return The IndexResponse
   */
  private IndexResponse index(Request request) {
    assert request instanceof Screening;
    Screening screening = (Screening) request;
    String screeningJson = toJson(screening);

    final IndexRequestBuilder builder =
        esDao.getClient().prepareIndex(esDao.getConfig().getElasticsearchAlias(),
            esDao.getConfig().getElasticsearchDocType(), screening.getId());
    builder.setSource(screeningJson, XContentType.JSON);

    final IndexResponse response = builder.get();
    final RestStatus status = response.status();
    final boolean success = RestStatus.OK == status || RestStatus.CREATED == status;

    if (!success) {
      throw new ServiceException(
          "Could not index screening. Status: " + status.getStatus() + ", Response: " + response);
    }

    return response;
  }

  public Screening getScreening(String id) {
    ScreeningEntity screeningEntity = screeningDao.find(id);
    if (screeningEntity == null) {
      throw new ServiceException("Screening with id=" + id + " is not found");
    }

    final Screening screening = screeningMapper.map(screeningEntity);
    final String screeningId = screeningEntity.getId();

    final List<AllegationEntity> allegationEntities = allegationDao.findByScreeningId(screeningId);
    final Set<AllegationIntake> allegations = allegationMapper.map(allegationEntities);
    screening.getAllegations().addAll(allegations);

    final List<CrossReportEntity> crossReportEntities =
        crossReportDao.findByScreeningId(screeningId);
    final Set<CrossReportIntake> crossReports = crossReportMapper.map(crossReportEntities);
    screening.getCrossReports().addAll(crossReports);

    for (CrossReportIntake crossReport : crossReports) {
      List<GovernmentAgencyEntity> agencyEntities =
          agencyDao.findByCrossReportId(crossReport.getId());
      Set<gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake> agencies =
          agencyMapper.map(agencyEntities);
      crossReport.getAgencies().addAll(agencies);
    }

    final List<ScreeningAddressEntity> screeningAddressEntities =
        screeningAddressDao.findByScreeningId(screeningId);
    if (screeningAddressEntities.size() > 1) {
      throw new ServiceException("Screening should have no more then 1 address");
    }
    for (ScreeningAddressEntity screeningAddressEntity : screeningAddressEntities) {
      Addresses addressEntity = addressesDao.find(screeningAddressEntity.getAddressId());
      AddressIntakeApi address = addressMapper.map(addressEntity);
      screening.setIncidentAddress(address);
    }

    final List<ParticipantEntity> participantEntities =
        participantIntakeApiService.getByScreeningId(screeningId);

    for (ParticipantEntity participantEntity : participantEntities) {
      ParticipantIntakeApi participantIntakeApi =
          participantIntakeApiService.find(participantEntity.getId());
      screening.getParticipantIntakeApis().add(participantIntakeApi);
    }

    return screening;
  }

  @SuppressWarnings("fb-contrib:CFS_CONFUSING_FUNCTION_SEMANTICS")
  public Screening createScreening(Screening screening) {
    if (screening == null) {
      throw new ServiceException("Screening for create is null");
    }
    validateParticipants(screening);

    final ScreeningEntity screeningEntity = screeningMapper.map(screening);
    final ScreeningEntity createdScreeningEntity = screeningDao.create(screeningEntity);
    final String screeningId = createdScreeningEntity.getId();
    screening.setId(screeningId);

    createOrUpdateAllegations(screening);
    createOrUpdateCrossReports(screening);
    createOrUpdateAddresses(screening);
    createOrUpdateParticipants(screening);

    return screening;
  }

  public Screening updateScreening(String id, Screening screening) {
    if (screening == null) {
      throw new ServiceException("Screening for update is null");
    }
    if (!id.equals(screening.getId())) {
      throw new ServiceException("Primary key mismatch, [" + id + " != " + screening.getId() + "]");
    }

    screeningDao.update(screeningMapper.map(screening));

    createOrUpdateAllegations(screening);
    createOrUpdateCrossReports(screening);
    createOrUpdateAddresses(screening);
    createOrUpdateParticipants(screening);

    return screening;
  }

  protected void validateParticipants(Screening screening) {
    for (ParticipantIntakeApi participantIntakeApi : screening.getParticipantIntakeApis()) {
      if (participantIntakeApi.getScreeningId() != null
          && !Objects.equals(participantIntakeApi.getScreeningId(), screening.getId())) {
        throw new ServiceException("Screening id and participant's screeningId doesn't match");
      }
    }
  }

  protected void createOrUpdateAllegations(Screening screening) {
    final Date now = RequestExecutionContext.instance().getRequestStartTime();
    for (AllegationIntake allegation : screening.getAllegations()) {
      final AllegationEntity allegationEntity = allegationMapper.map(allegation);
      allegationEntity.setScreeningId(screening.getId());
      allegationEntity.setUpdatedAt(now);
      if (allegationEntity.getId() == null) {
        allegationEntity.setCreatedAt(now);
        final AllegationEntity createdAllegationEntity = allegationDao.create(allegationEntity);
        allegation.setId(String.valueOf(createdAllegationEntity.getId()));
      } else {
        final AllegationEntity managedAllegationEntity =
            allegationDao.find(allegationEntity.getId());
        if (managedAllegationEntity == null) {
          throw new ServiceException(
              "Cannot update allegation that doesn't exist. id = " + allegationEntity.getId());
        }
        allegationEntity.setCreatedAt(managedAllegationEntity.getCreatedAt());
        allegationDao.grabSession().detach(managedAllegationEntity);
        allegationDao.update(allegationEntity);
      }
    }
  }

  protected void createOrUpdateCrossReports(Screening screening) {
    final Date now = RequestExecutionContext.instance().getRequestStartTime();
    for (CrossReportIntake crossReport : screening.getCrossReports()) {
      final CrossReportEntity crossReportEntity = crossReportMapper.map(crossReport);
      crossReportEntity.setScreeningId(screening.getId());
      crossReportEntity.setUpdatedAt(now);
      if (crossReportEntity.getId() == null) {
        crossReportEntity.setCreatedAt(now);
        crossReport.setId(crossReportDao.create(crossReportEntity).getId());
      } else {
        final CrossReportEntity managedCrossReportEntity =
            crossReportDao.find(crossReportEntity.getId());
        if (managedCrossReportEntity == null) {
          throw new ServiceException(
              "Cannot update cross report that doesn't exist. id = " + crossReportEntity.getId());
        }
        crossReportEntity.setCreatedAt(managedCrossReportEntity.getCreatedAt());
        crossReportDao.grabSession().detach(managedCrossReportEntity);
        crossReportDao.update(crossReportEntity);
      }

      createOrUpdateAgencies(crossReport);
    }
  }

  protected void createOrUpdateAgencies(CrossReportIntake crossReport) {
    final Date now = RequestExecutionContext.instance().getRequestStartTime();
    for (GovernmentAgencyIntake agency : crossReport.getAgencies()) {
      final GovernmentAgencyEntity agencyEntity = agencyMapper.map(agency);
      agencyEntity.setCrossReportId(crossReport.getId());
      agencyEntity.setUpdatedAt(now);
      if (agencyEntity.getId() == null) {
        agencyEntity.setCreatedAt(now);
        final GovernmentAgencyEntity createdAgencyEntity = agencyDao.create(agencyEntity);
        agency.setId(createdAgencyEntity.getId());
      } else {
        final GovernmentAgencyEntity managedAgencyEntity = agencyDao.find(agencyEntity.getId());
        if (managedAgencyEntity == null) {
          throw new ServiceException(
              "Cannot update agency that doesn't exist. id = " + agency.getId());
        }
        agencyEntity.setCreatedAt(managedAgencyEntity.getCreatedAt());
        agencyDao.grabSession().detach(managedAgencyEntity);
        agencyDao.update(agencyEntity);
      }
    }
  }

  protected void createOrUpdateAddresses(Screening screening) {
    final AddressIntakeApi address = screening.getIncidentAddress();
    if (address == null) {
      return;
    }
    final Addresses addressEntity = addressMapper.map(address);
    if (addressEntity.getId() == null) {
      final Addresses createdAddress = addressesDao.create(addressEntity);
      final ScreeningAddressEntity screeningAddressesEntity = new ScreeningAddressEntity();
      screeningAddressesEntity.setScreeningId(screening.getId());
      screeningAddressesEntity.setAddressId(createdAddress.getId());
      address.setId(String.valueOf(addressEntity.getId()));
      screeningAddressDao.create(screeningAddressesEntity);
    } else {
      addressesDao.update(addressEntity);
    }
  }

  protected void createOrUpdateParticipants(Screening screening) {
    final Set<ParticipantIntakeApi> participantIntakeApis = new HashSet<>();

    for (ParticipantIntakeApi participantIntakeApi : screening.getParticipantIntakeApis()) {
      final String participantIntakeApiId = participantIntakeApi.getId();
      if (participantIntakeApiId == null) {
        participantIntakeApi.setScreeningId(screening.getId());
        participantIntakeApis.add(participantIntakeApiService.create(participantIntakeApi));
      } else {
        participantIntakeApis
            .add(participantIntakeApiService.update(participantIntakeApiId, participantIntakeApi));
      }
    }

    screening.setParticipantIntakeApis(participantIntakeApis);
  }

  void setEsDao(ElasticsearchDao esDao) {
    this.esDao = esDao;
  }

  void setScreeningDao(ScreeningDao screeningDao) {
    this.screeningDao = screeningDao;
  }

  public void setScreeningMapper(ScreeningMapper screeningMapper) {
    this.screeningMapper = screeningMapper;
  }

}
