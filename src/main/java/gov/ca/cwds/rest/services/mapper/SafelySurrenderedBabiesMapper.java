package gov.ca.cwds.rest.services.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity;
import gov.ca.cwds.rest.api.domain.SafelySurenderedBabies;

@Mapper
@SuppressWarnings("squid:S1214")
public interface SafelySurrenderedBabiesMapper {
  SafelySurrenderedBabiesMapper INSTANCE = Mappers.getMapper(SafelySurrenderedBabiesMapper.class);

  SafelySurenderedBabies map(SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity);

  @InheritInverseConfiguration
  SafelySurrenderedBabiesEntity map(SafelySurenderedBabies safelySurrenderedBabies);

}
