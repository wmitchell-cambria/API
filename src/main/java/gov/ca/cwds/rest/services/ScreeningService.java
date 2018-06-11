package gov.ca.cwds.rest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import gov.ca.cwds.ObjectMapperUtils;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

/**
 * Business layer object to work on {@link Screening}
 *
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.createObjectMapper();

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
    List<ScreeningWrapper> screenings = screeningDao.findScreeningsByUserId(staffId);
    List<ScreeningDashboard> screeningDashboard = new ArrayList<>(screenings.size());
    for (ScreeningWrapper screening : screenings) {
      ScreeningDashboard thisScreening = new ScreeningDashboard(screening);
      screeningDashboard.add(thisScreening);
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
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Screening update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof Screening;
    Screening screening = (Screening) request;

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
      screeningJson = OBJECT_MAPPER.writeValueAsString(screening);
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

    IndexRequestBuilder builder =
        esDao.getClient().prepareIndex(esDao.getConfig().getElasticsearchAlias(),
            esDao.getConfig().getElasticsearchDocType(), screening.getId());
    builder.setSource(screeningJson, XContentType.JSON);

    IndexResponse response = builder.get();
    RestStatus status = response.status();
    boolean success = RestStatus.OK == status || RestStatus.CREATED == status;

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
    Screening screening = screeningMapper.map(screeningEntity);

    String screeningId = screeningEntity.getId();

    List<AllegationEntity> allegationEntities = allegationDao.findByScreeningId(screeningId);
    Set<AllegationIntake> allegations = allegationMapper.map(allegationEntities);
    screening.getAllegations().addAll(allegations);

    List<CrossReportEntity> crossReportEntities = crossReportDao.findByScreeningId(screeningId);
    Set<CrossReportIntake> crossReports = crossReportMapper.map(crossReportEntities);
    screening.getCrossReports().addAll(crossReports);

    for (CrossReportIntake crossReport : crossReports) {
      List<GovernmentAgencyEntity> agencyEntities =
          agencyDao.findByCrossReportId(crossReport.getId());
      Set<gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake> agencies =
          agencyMapper.map(agencyEntities);
      crossReport.getAgencies().addAll(agencies);
    }

    List<ScreeningAddressEntity> screeningAddressEntities =
        screeningAddressDao.findByScreeningId(screeningId);
    if (screeningAddressEntities.size() > 1) {
      throw new ServiceException("Screening should have no more then 1 address");
    }
    for (ScreeningAddressEntity screeningAddressEntity : screeningAddressEntities) {
      Addresses addressEntity = addressesDao.find(screeningAddressEntity.getAddressId());
      AddressIntakeApi address = addressMapper.map(addressEntity);
      screening.setIncidentAddress(address);
    }

    List<ParticipantEntity> participantEntities =
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

    ScreeningEntity screeningEntity = screeningMapper.map(screening);
    ScreeningEntity createdScreeningEntity = screeningDao.create(screeningEntity);
    String screeningId = createdScreeningEntity.getId();
    screening.setId(screeningId);

    createUpdateDeleteCrossReports(screening);
    createOrUpdateAddresses(screening);
    createUpdateDeleteParticipants(screening);
    createUpdateDeleteAllegations(screening);

    return screening;
  }

  public Screening updateScreening(String id, Screening screening) {
    if (screening == null) {
      throw new ServiceException("Screening for update is null");
    }
    if (!id.equals(screening.getId())) {
      throw new ServiceException("Primary key mismatch, [" + id + " != " + screening.getId() + "]");
    }

    ScreeningEntity screeningEntity = screeningMapper.map(screening);
    screeningDao.update(screeningEntity);

    createUpdateDeleteCrossReports(screening);
    createOrUpdateAddresses(screening);
    createUpdateDeleteParticipants(screening);
    createUpdateDeleteAllegations(screening);

    return screening;
  }

  private void validateParticipants(Screening screening) {
    for (ParticipantIntakeApi participantIntakeApi : screening.getParticipantIntakeApis()) {
      if (participantIntakeApi.getScreeningId() != null
          && !Objects.equals(participantIntakeApi.getScreeningId(), screening.getId())) {
        throw new ServiceException("Screening id and participant's screeningId doesn't match");
      }
    }
  }

  private void createUpdateDeleteAllegations(Screening screening) {
    Set<Integer> allegationIdsOld = allegationDao.findByScreeningId(screening.getId()).stream()
        .map(AllegationEntity::getId).collect(Collectors.toSet());

    for (AllegationIntake allegation : screening.getAllegations()) {
      AllegationEntity allegationEntity = allegationMapper.map(allegation);
      allegationEntity.setScreeningId(screening.getId());
      Date now = new Date();
      allegationEntity.setUpdatedAt(now);
      if (allegationEntity.getId() == null) {
        allegationEntity.setCreatedAt(now);
        AllegationEntity createdAllegationEntity = allegationDao.create(allegationEntity);
        allegation.setId(String.valueOf(createdAllegationEntity.getId()));
      } else {
        AllegationEntity managedAllegationEntity = allegationDao.find(allegationEntity.getId());
        if (managedAllegationEntity == null) {
          throw new ServiceException(
              "Cannot update allegation that doesn't exist. id = " + allegationEntity.getId());
        }
        allegationEntity.setCreatedAt(managedAllegationEntity.getCreatedAt());
        allegationDao.getSessionFactory().getCurrentSession().detach(managedAllegationEntity);
        allegationDao.update(allegationEntity);
        allegationIdsOld.remove(allegationEntity.getId());
      }
    }
    // Delete old ones that are not in the new.
    allegationIdsOld.forEach(allegationId -> allegationDao.delete(allegationId));

  }

  private void createUpdateDeleteCrossReports(Screening screening) {
    Set<String> crossReportIdsOld = crossReportDao.findByScreeningId(screening.getId()).stream()
        .map(CrossReportEntity::getId).collect(Collectors.toSet());

    for (CrossReportIntake crossReport : screening.getCrossReports()) {
      CrossReportEntity crossReportEntity = crossReportMapper.map(crossReport);
      crossReportEntity.setScreeningId(screening.getId());
      Date now = new Date();
      crossReportEntity.setUpdatedAt(now);
      if (crossReportEntity.getId() == null) {
        crossReportEntity.setCreatedAt(now);
        CrossReportEntity createdCrossReportEntity = crossReportDao.create(crossReportEntity);
        crossReport.setId(createdCrossReportEntity.getId());
      } else {
        CrossReportEntity managedCrossReportEntity = crossReportDao.find(crossReportEntity.getId());
        if (managedCrossReportEntity == null) {
          throw new ServiceException(
              "Cannot update cross report that doesn't exist. id = " + crossReportEntity.getId());
        }
        crossReportEntity.setCreatedAt(managedCrossReportEntity.getCreatedAt());
        crossReportDao.getSessionFactory().getCurrentSession().detach(managedCrossReportEntity);
        crossReportDao.update(crossReportEntity);
        crossReportIdsOld.remove(crossReportEntity.getId());
      }

      createOrUpdateAgencies(crossReport);
    }

    // Delete old ones that are not in the new.
    crossReportIdsOld.forEach(crossReportId -> crossReportDao.delete(crossReportId));
  }

  private void createOrUpdateAgencies(CrossReportIntake crossReport) {
    for (GovernmentAgencyIntake agency : crossReport.getAgencies()) {
      GovernmentAgencyEntity agencyEntity = agencyMapper.map(agency);
      agencyEntity.setCrossReportId(crossReport.getId());
      Date now = new Date();
      agencyEntity.setUpdatedAt(now);
      if (agencyEntity.getId() == null) {
        agencyEntity.setCreatedAt(now);
        GovernmentAgencyEntity createdAgencyEntity = agencyDao.create(agencyEntity);
        agency.setId(createdAgencyEntity.getId());
      } else {
        GovernmentAgencyEntity managedAgencyEntity = agencyDao.find(agencyEntity.getId());
        if (managedAgencyEntity == null) {
          throw new ServiceException(
              "Cannot update agency that doesn't exist. id = " + agency.getId());
        }
        agencyEntity.setCreatedAt(managedAgencyEntity.getCreatedAt());
        agencyDao.getSessionFactory().getCurrentSession().detach(managedAgencyEntity);
        agencyDao.update(agencyEntity);
      }
    }
  }

  private void createOrUpdateAddresses(Screening screening) {
    AddressIntakeApi address = screening.getIncidentAddress();
    if (address == null) {
      return;
    }
    Addresses addressEntity = addressMapper.map(address);
    if (addressEntity.getId() == null) {
      Addresses createdAddress = addressesDao.create(addressEntity);
      ScreeningAddressEntity screeningAddressesEntity = new ScreeningAddressEntity();
      screeningAddressesEntity.setScreeningId(screening.getId());
      screeningAddressesEntity.setAddressId(createdAddress.getId());
      address.setId(String.valueOf(addressEntity.getId()));
      screeningAddressDao.create(screeningAddressesEntity);
    } else {
      addressesDao.update(addressEntity);
    }
  }

  private void createUpdateDeleteParticipants(Screening screening) {
    Set<ParticipantIntakeApi> participantIntakeApis = new HashSet<>();
    Set<String> participantIdsOld = participantIntakeApiService.getByScreeningId(screening.getId())
        .stream().map(ParticipantEntity::getId).collect(Collectors.toSet());

    for (ParticipantIntakeApi participantIntakeApi : screening.getParticipantIntakeApis()) {
      String participantIntakeApiId = participantIntakeApi.getId();
      if (participantIntakeApiId == null) {
        participantIntakeApi.setScreeningId(screening.getId());
        ParticipantIntakeApi createdParticipantIntakeApi =
            participantIntakeApiService.create(participantIntakeApi);
        participantIntakeApis.add(createdParticipantIntakeApi);
      } else {
        ParticipantIntakeApi updatedParticipantIntakeApi =
            participantIntakeApiService.update(participantIntakeApiId, participantIntakeApi);
        participantIntakeApis.add(updatedParticipantIntakeApi);
        participantIdsOld.remove(participantIntakeApiId);
      }
    }
    // Delete old ones that are not in the new.
    participantIdsOld.forEach(participantId -> participantIntakeApiService.delete(participantId));

    screening.setParticipantIntakeApis(participantIntakeApis);
  }

  void setEsDao(ElasticsearchDao esDao) {
    this.esDao = esDao;
  }

  void setScreeningDao(ScreeningDao screeningDao) {
    this.screeningDao = screeningDao;
  }

}
