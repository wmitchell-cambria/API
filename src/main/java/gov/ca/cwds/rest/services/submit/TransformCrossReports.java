package gov.ca.cwds.rest.services.submit;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;

/**
 * Business layer object to transform NS {@link CrossReportIntake } to {@link CrossReport }
 * 
 * @author CWDS API Team
 */
public class TransformCrossReports {

  public Set<CrossReport> transform(Set<CrossReportIntake> crossReportsIntake,
      Map<String, IntakeLov> nsLovMap, Map<String, IntakeLov> cmsSysIdToNsLovMap) {
    Set<CrossReport> crossReports = new HashSet<>();
    for (CrossReportIntake ci : crossReportsIntake) {
      Integer method = StringUtils.isNotBlank(ci.getMethod())
          ? nsLovMap.get(ci.getMethod()).getLegacySystemCodeId().intValue()
          : 0;
      String county = cmsSysIdToNsLovMap.get(ci.getCountyId()).getLegacyLogicalCode();
      crossReports.add(new CrossReport(ci.getId(), ci.getLegacySourceTable(), ci.getLegacyId(),
          true, method, ci.getInformDate(), county, ci.getAgencies()));
    }
    return crossReports;

  }

}
