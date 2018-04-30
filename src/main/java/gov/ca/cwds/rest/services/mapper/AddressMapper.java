package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
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
@Mapper(uses = LegacyDescriptorMapper.class)
public interface AddressMapper {
  AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
  String DEFAULT_TYPE = "Other";

  @Mapping(target = "type", source = "type", ignore = true)
  AddressIntakeApi map(Addresses addressEntity);

  @InheritInverseConfiguration
  Addresses map(AddressIntakeApi address);

  List<Addresses> map(Set<AddressIntakeApi> addresses);

  @InheritInverseConfiguration
  Set<AddressIntakeApi> map(List<Addresses> addressEntities);

  @AfterMapping
  default void toDomain(@MappingTarget AddressIntakeApi addressIntakeApi, Addresses addresses) {
    String type = addresses.getType();
    if (type == null) {
      Short typeCode = SystemCodeCache.global().getSystemCodeId(addresses.getType(),
          SystemCodeCategoryId.ADDRESS_TYPE);

      addressIntakeApi.setType(String.valueOf(typeCode));
    } else {
      addressIntakeApi.setType(DEFAULT_TYPE);
    }
  }

  @AfterMapping
  default void toEntity(@MappingTarget Addresses addresses , AddressIntakeApi addressIntakeApi) {
    String typeCode = addressIntakeApi.getType();
    if (typeCode != null && !DEFAULT_TYPE.equals(typeCode)) {
      String type = SystemCodeCache.global().getSystemCodeShortDescription(Short.valueOf(typeCode));
      addresses.setType(type);
    }
  }
}
