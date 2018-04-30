package gov.ca.cwds.rest.services.mapper;

import java.util.Set;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
@Mapper
public interface AllegationTypeMapper {
  AllegationTypeMapper INSTANCE = Mappers.getMapper(AllegationTypeMapper.class);

  Set<String> mapToDomain(String[] allegationType);

  @InheritInverseConfiguration
  String[] mapToEntity(Set<String> allegationType);

}
