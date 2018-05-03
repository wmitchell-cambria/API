package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import gov.ca.cwds.rest.services.mapper.AddressMapper;
import gov.ca.cwds.rest.services.mapper.AgencyMapper;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.AllegationTypeMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.LegacyDescriptorMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;

/**
 * CWDS API Team
 */
public class MappingModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AgencyMapper.class).to(AgencyMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(AllegationMapper.class).to(AllegationMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(AllegationTypeMapper.class).to(AllegationTypeMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(AddressMapper.class).to(AddressMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(CrossReportMapper.class).to(CrossReportMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(LegacyDescriptorMapper.class).to(LegacyDescriptorMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(ScreeningMapper.class).to(ScreeningMapper.INSTANCE.getClass())
        .asEagerSingleton();
  }
}
