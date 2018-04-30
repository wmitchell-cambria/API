package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
@Mapper
public interface LegacyDescriptorMapper {
  LegacyDescriptorMapper INSTANCE = Mappers.getMapper(LegacyDescriptorMapper.class);

  LegacyDescriptor map(LegacyDescriptorEntity legacyDescriptorEntity);

  @InheritInverseConfiguration
  LegacyDescriptorEntity map(LegacyDescriptor legacyDescriptor);
}
