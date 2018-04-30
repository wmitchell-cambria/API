package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;
import java.util.List;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
@Mapper(uses = AllegationTypeMapper.class)
public interface AllegationMapper {
  AllegationMapper INSTANCE = Mappers.getMapper(AllegationMapper.class);

  @Mapping(target = "perpetratorPersonId", source = "perpetratorId")
  @Mapping(target = "victimPersonId", source = "victimId")
  Allegation map(AllegationEntity allegationEntity);

  @InheritInverseConfiguration
  AllegationEntity map(Allegation allegation);

  List<AllegationEntity>
  map(Set<Allegation> allegations);

  @InheritInverseConfiguration
  Set<Allegation> map(List<AllegationEntity> allegationEntities);

  @AfterMapping
  default void toDomain(@MappingTarget Allegation allegation, AllegationEntity allegationEntity) {
    String[] allegationTypes = allegationEntity.getAllegationTypes();
    if (allegationTypes.length > 1) {
      throw new ServiceException("Cannot map allegation type list to single value");
    }
    if (allegationTypes.length != 0) {
      allegation.setType(SystemCodeCache.global().getSystemCodeId(allegationTypes[0],
          SystemCodeCategoryId.INJURY_HARM_TYPE));
    }
  }

  @AfterMapping
  default void toEntity(@MappingTarget AllegationEntity allegationEntity, Allegation allegation) {
    Short type = allegation.getType();
    if (type != null) {
      allegationEntity.setAllegationTypes(
          new String[]{SystemCodeCache.global().getSystemCodeShortDescription(type)});
    }
  }
}
