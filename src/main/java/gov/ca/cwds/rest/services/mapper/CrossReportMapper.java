package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.DomainChef;
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
@Mapper(uses = AgencyMapper.class)
@SuppressWarnings("squid:S1214")
public interface CrossReportMapper {
  CrossReportMapper INSTANCE = Mappers.getMapper(CrossReportMapper.class);

  @Mapping(target = "method", source = "communicationMethod")
  @Mapping(target = "informDate", source = "informDate", ignore = true)
  CrossReportIntake map(CrossReportEntity crossReportEntity);

  @InheritInverseConfiguration
  CrossReportEntity map(CrossReportIntake crossReport);

  Set<CrossReportIntake> map(List<CrossReportEntity> crossReportEntity);

  @InheritInverseConfiguration
  List<CrossReportEntity> map(Set<CrossReportIntake> crossReport);

  @AfterMapping
  default void toDomain(@MappingTarget CrossReportIntake crossReport, CrossReportEntity crossReportEntity) {
    crossReport.setInformDate(DomainChef.cookDate(crossReportEntity.getInformDate()));
  }

  @AfterMapping
  default void toEntity(@MappingTarget CrossReportEntity crossReportEntity, CrossReportIntake crossReport) {
    crossReportEntity.setInformDate(DomainChef.uncookDateString(crossReport.getInformDate()));
  }
}
