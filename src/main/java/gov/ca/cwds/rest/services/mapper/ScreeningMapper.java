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
  Screening map(ScreeningEntity screeningEntity);

  @InheritInverseConfiguration
  ScreeningEntity map(Screening screening);
}
