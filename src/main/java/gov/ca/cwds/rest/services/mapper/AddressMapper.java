package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import java.util.List;
import java.util.Set;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
@Mapper(uses = LegacyDescriptorMapper.class)
@SuppressWarnings("squid:S1214")
public interface AddressMapper {
  AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

  AddressIntakeApi map(Addresses addressEntity);

  @InheritInverseConfiguration
  Addresses map(AddressIntakeApi address);

  List<Addresses> map(Set<AddressIntakeApi> addresses);

  @InheritInverseConfiguration
  Set<AddressIntakeApi> map(List<Addresses> addressEntities);
}
