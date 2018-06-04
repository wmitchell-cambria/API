package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@FunctionalInterface
@Mapper
@SuppressWarnings("squid:S1214")
public interface RelationshipMapper {
  RelationshipMapper INSTANCE = Mappers.getMapper(RelationshipMapper.class);

  ScreeningRelationship map(Relationship relationship);
}
