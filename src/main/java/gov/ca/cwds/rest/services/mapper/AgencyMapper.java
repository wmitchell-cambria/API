package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity;
import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import java.util.List;
import java.util.Set;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
@Mapper
@SuppressWarnings("squid:S1214")
public interface AgencyMapper {
  AgencyMapper INSTANCE = Mappers.getMapper(AgencyMapper.class);

  @Mapping(target = "type", source = "category")
  GovernmentAgency map(GovernmentAgencyEntity governmentAgencyEntity);

  @InheritInverseConfiguration
  GovernmentAgencyEntity map(GovernmentAgency governmentAgency);

  Set<GovernmentAgency> map(List<GovernmentAgencyEntity> governmentAgencyEntity);

  @InheritInverseConfiguration
  List<GovernmentAgencyEntity> map(Set<GovernmentAgency> governmentAgencyEntity);
}
