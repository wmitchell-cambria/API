package gov.ca.cwds.rest.services.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;

/**
 * CWDS API Team
 */
@Mapper
@SuppressWarnings("squid:S1214")
public interface AgencyMapper {
  AgencyMapper INSTANCE = Mappers.getMapper(AgencyMapper.class);

  @Mapping(target = "type", source = "category")
  GovernmentAgencyIntake map(GovernmentAgencyEntity governmentAgencyEntity);

  @InheritInverseConfiguration
  GovernmentAgencyEntity map(GovernmentAgencyIntake governmentAgencyIntake);

  Set<GovernmentAgencyIntake> map(List<GovernmentAgencyEntity> governmentAgencyEntity);

  @InheritInverseConfiguration
  List<GovernmentAgencyEntity> map(Set<GovernmentAgencyIntake> governmentAgencyEntity);
}
