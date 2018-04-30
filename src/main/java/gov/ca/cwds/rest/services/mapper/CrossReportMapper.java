package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.DomainChef;
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
@Mapper(uses = AgencyMapper.class)
public interface CrossReportMapper {
  CrossReportMapper INSTANCE = Mappers.getMapper(CrossReportMapper.class);

  @Mapping(target = "method", source = "communicationMethod", ignore = true)
  @Mapping(target = "informDate", source = "informDate", ignore = true)
  CrossReport map(CrossReportEntity crossReportEntity);

  @InheritInverseConfiguration
  CrossReportEntity map(CrossReport crossReport);

  Set<CrossReport> map(List<CrossReportEntity> crossReportEntity);

  @InheritInverseConfiguration
  List<CrossReportEntity> map(Set<CrossReport> crossReport);

  @AfterMapping
  default void toDomain(@MappingTarget CrossReport crossReport, CrossReportEntity crossReportEntity) {
    crossReport.setInformDate(DomainChef.cookDate(crossReportEntity.getInformDate()));

    String communicationMethod = crossReportEntity.getCommunicationMethod();
    if (communicationMethod != null) {
      Short method = SystemCodeCache.global().getSystemCodeId(communicationMethod,
          SystemCodeCategoryId.CROSS_REPORT_METHOD);

      crossReport.setMethod(Integer.valueOf(method));
    }
  }

  @AfterMapping
  default void toEntity(@MappingTarget CrossReportEntity crossReportEntity, CrossReport crossReport) {
    crossReportEntity.setInformDate(DomainChef.uncookDateString(crossReport.getInformDate()));

    Integer method = crossReport.getMethod();
    if (method != null) {
      String communicationMetod = SystemCodeCache.global().getSystemCodeShortDescription(method);
      crossReportEntity.setCommunicationMethod(communicationMetod);
    }
  }
}
