package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Screening;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
@Mapper(imports = DomainChef.class)
@SuppressWarnings("squid:S1214")
public interface ScreeningMapper {
  ScreeningMapper INSTANCE = Mappers.getMapper(ScreeningMapper.class);

  @Mapping(target = "reportNarrative", source = "narrative")
  @Mapping(target = "allegations", source = "allegations", ignore = true)
  @Mapping(target = "startedAt", expression = "java(DomainChef.cookISO8601Timestamp(screeningEntity.getStartedAt()))")
  @Mapping(target = "endedAt", expression = "java(DomainChef.cookISO8601Timestamp(screeningEntity.getEndedAt()))")
  @Mapping(target = "incidentDate", expression = "java(DomainChef.cookDate(screeningEntity.getIncidentDate()))")
  Screening map(ScreeningEntity screeningEntity);

  @InheritInverseConfiguration
  @Mapping(target = "startedAt", expression = "java(DomainChef.uncookISO8601Timestamp(screening.getStartedAt()))")
  @Mapping(target = "endedAt", expression = "java(DomainChef.uncookISO8601Timestamp(screening.getEndedAt()))")
  @Mapping(target = "incidentDate", expression = "java(DomainChef.uncookDateString(screening.getIncidentDate()))")
  ScreeningEntity map(Screening screening);
}
