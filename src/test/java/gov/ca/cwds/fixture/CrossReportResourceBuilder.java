package gov.ca.cwds.fixture;

import org.assertj.core.util.Sets;

/**
 * 
 * @author CWDS API Team
 */
public class CrossReportResourceBuilder {

  String id = "";
  String legacySourceTable = "";
  String legacyId = "";
  boolean filedOutOfState = false;
  Integer method = 2095; // "electronic report"
  String informDate = "2017-03-15";
  String countyId = "34";

  /**
   * @param id - id
   * @return the id
   */
  public CrossReportResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   * @param legacySourceTable - legacySourceTable
   * @return the legacySourceTable
   */
  public CrossReportResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  /**
   * @param legacyId - legacyId
   * @return the legacyId
   */
  public CrossReportResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  /**
   * @param method - method
   * @return the method
   */
  public CrossReportResourceBuilder setMethod(Integer method) {
    this.method = method;
    return this;
  }

  /**
   * @param informDate - informDate
   * @return the informDate
   */
  public CrossReportResourceBuilder setInformDate(String informDate) {
    this.informDate = informDate;
    return this;
  }

  /**
   * @param filedOutOfState - filedOutOfState
   * @return the filedOutOfState
   */
  public CrossReportResourceBuilder setFiledOutOfState(boolean filedOutOfState) {
    this.filedOutOfState = filedOutOfState;
    return this;
  }

  /**
   * @param countyId - countyId
   * @return the countyId
   */
  public CrossReportResourceBuilder setCountyId(String countyId) {
    this.countyId = countyId;
    return this;
  }

  /**
   * @return the CrossReport
   */
  public gov.ca.cwds.rest.api.domain.CrossReport createCrossReport() {
    return new gov.ca.cwds.rest.api.domain.CrossReport(id, legacySourceTable, legacyId,
        filedOutOfState, method, informDate, countyId, Sets.newHashSet());
  }
}
