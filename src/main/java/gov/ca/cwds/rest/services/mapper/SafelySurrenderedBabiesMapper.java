package gov.ca.cwds.rest.services.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity;
import gov.ca.cwds.rest.api.domain.SafelySurenderedBabiesIntakeApi;

@Mapper
@SuppressWarnings("squid:S1214")
public interface SafelySurrenderedBabiesMapper {
  SafelySurrenderedBabiesMapper INSTANCE = Mappers.getMapper(SafelySurrenderedBabiesMapper.class);

  SafelySurenderedBabiesIntakeApi map(SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity);

  @InheritInverseConfiguration
  SafelySurrenderedBabiesEntity map(SafelySurenderedBabiesIntakeApi safelySurrenderedBabies);

  @InheritInverseConfiguration
  SafelySurrenderedBabiesEntity map(SafelySurenderedBabiesIntakeApi safelySurrenderedBabies,
      @MappingTarget SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity);

}
