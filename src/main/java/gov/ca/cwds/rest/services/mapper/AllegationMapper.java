package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.rest.api.domain.AllegationIntake;
import java.util.List;
import java.util.Set;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
@Mapper(uses = AllegationTypeMapper.class)
public interface AllegationMapper {
  @SuppressWarnings("squid:S1214")
  AllegationMapper INSTANCE = Mappers.getMapper(AllegationMapper.class);

  @Mapping(target = "perpetratorPersonId", source = "perpetratorId")
  @Mapping(target = "victimPersonId", source = "victimId")
  @Mapping(target = "types", source = "allegationTypes")
  AllegationIntake map(AllegationEntity allegationEntity);

  @InheritInverseConfiguration
  AllegationEntity map(AllegationIntake allegation);

  List<AllegationEntity> map(Set<AllegationIntake> allegations);

  @InheritInverseConfiguration
  Set<AllegationIntake> map(List<AllegationEntity> allegationEntities);
}
