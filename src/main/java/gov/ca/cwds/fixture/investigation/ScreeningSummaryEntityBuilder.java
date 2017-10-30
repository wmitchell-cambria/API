package gov.ca.cwds.fixture.investigation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.api.domain.investigation.SimpleAllegation;

@SuppressWarnings("javadoc")
public class ScreeningSummaryEntityBuilder {


  private String perpetratorId = "11";
  private Set<String> allegationTypes = validAllegationTypes();
  private String victimId = "22";

  private String additionalInformation = "There was excessive evidence of abuse";
  private String decision = "promote_to_referral";
  private String decisionDetail = "immediate";
  private String name = "Henderson Screening";
  private Set<String> safetyAlerts = validSafetyAletrs();
  private Date startedAt = DomainChef.uncookStrictTimestampString("2010-10-01T15:26:42.000-0700");
  private String safetyInformation = "The animal at residence is a lion";
  private String id = "1";
  private Set<SimpleAllegation> allegations = validAllegationsSet();

  private Set<String> validSafetyAletrs() {
    Set<String> safetyAlerts = new HashSet<>();
    safetyAlerts.add("Dangerous Animal on Premises");
    safetyAlerts.add("Firearms in Home");
    safetyAlerts.add("Severe Mental Health Status");
    return safetyAlerts;
  }

  private Set<SimpleAllegation> validAllegationsSet() {
    Set<SimpleAllegation> validAllegations = new HashSet<>();
    SimpleAllegation allegation = new SimpleAllegation(victimId, perpetratorId, allegationTypes);
    validAllegations.add(allegation);
    return validAllegations;
  }

  private Set<String> validAllegationTypes() {
    Set<String> allegationTypes = new HashSet<>();
    allegationTypes.add("General neglect");
    allegationTypes.add("Physical abuse");
    return allegationTypes;
  }

  /**
   * 
   * @return ScreeningSummary object
   */
  public ScreeningSummary build() {
    return new ScreeningSummary(id, name, decision, decisionDetail, safetyAlerts, safetyInformation,
        additionalInformation, startedAt, allegations);

  }

}
