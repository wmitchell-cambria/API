package gov.ca.cwds.fixture.hoi;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;

/**
 * @author CWDS API Team
 */
public class HOIScreeningBuilder {

  private String id = "224";
  private String screeningName = "screening name";
  private String startDate = "2017-11-30";
  private String endDate = "2017-12-10";
  private String decision = "promote to referral";
  private String decisionDetail = "drug counseling";
  private Integer countyId = 1101;
  private String countyDescription = "Sacramento";
  private Set<HOIPerson> allPeople = new HashSet<>();
  private HOIReporter reporter;
  private HOISocialWorker socialWorker;

  public HOIScreeningBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public HOIScreeningBuilder setName(String name) {
	this.screeningName = name;
	return this;
  }
  
  public HOIScreeningBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public HOIScreeningBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public HOIScreeningBuilder setDecision(String decision) {
    this.decision = decision;
    return this;
  }

  public HOIScreeningBuilder setDecisionDetail(String decisionDetail) {
    this.decisionDetail = decisionDetail;
    return this;
  }

  public HOIScreeningBuilder setCountyId(int countyId) {
    this.countyId = countyId;
    return this;
  }

  public HOIScreeningBuilder setCountyDescription(String countyDescription) {
    this.countyDescription = countyDescription;
    return this;
  }

  public HOIScreeningBuilder addHOIPerson(HOIPerson person) {
    this.allPeople.add(person);
    return this;
  }

  public HOIScreeningBuilder setReporter(HOIReporter reporter) {
    this.reporter = reporter;
    return this;
  }

  public HOIScreeningBuilder setSocialWorker(HOISocialWorker socialWorker) {
    this.socialWorker = socialWorker;
    return this;
  }

  public HOIScreening createHOIScreening() {
    HOIScreening screening = new HOIScreening();
    screening.setId(id);
    screening.setName(screeningName);
    screening.setStartDate(DomainChef.uncookDateString(startDate));
    screening.setEndDate(DomainChef.uncookDateString(endDate));
    screening.setCounty(new SystemCodeDescriptor(countyId.shortValue(), countyDescription));
    screening.setDecision(decision);
    screening.setDecisionDetail(decisionDetail);
    screening.setAllPeople(allPeople);
    screening.setReporter(reporter);
    screening.setAssignedSocialWorker(socialWorker);
    return screening;
  }
}
