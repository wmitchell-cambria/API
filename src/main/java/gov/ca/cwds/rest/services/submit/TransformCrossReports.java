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
    for (CrossReportIntake nsCrossReport : crossReportsIntake) {
      Integer method = StringUtils.isNotBlank(nsCrossReport.getMethod())
          ? nsLovMap.get(nsCrossReport.getMethod()).getLegacySystemCodeId().intValue()
          : null;
      String county = StringUtils.isNotBlank(nsCrossReport.getCountyId())
          ? cmsSysIdToNsLovMap.get(nsCrossReport.getCountyId()).getLegacyLogicalCode()
          : null;
      crossReports.add(new CrossReport(nsCrossReport.getId(), nsCrossReport.getLegacySourceTable(),
          nsCrossReport.getLegacyId(), nsCrossReport.isFiledOutOfState(), method,
          nsCrossReport.getInformDate(), county, nsCrossReport.getAgencies()));
    }
    return crossReports;

  }

}
