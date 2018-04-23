package gov.ca.cwds.rest.authenticate;

/**
 * To get and set the staffId and incidentCounty.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class UserInfo {

  private String staffId;
  private String incidentCounty;

  /**
   * getStaffId.
   * 
   * @return the staffId
   */
  public String getStaffId() {
    return staffId;
  }

  /**
   * staffId.
   * 
   * @param staffId the staffId
   */
  public void setStaffId(String staffId) {
    this.staffId = staffId;
  }

  /**
   * getIncidentCounty.
   * 
   * @return the incidentCounty
   */
  public String getIncidentCounty() {
    return incidentCounty;
  }

  /**
   * incidentCounty.
   * 
   * @param incidentCounty the incidentCounty
   */
  public void setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
  }

}
