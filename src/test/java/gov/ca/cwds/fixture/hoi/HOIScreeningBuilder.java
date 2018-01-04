package gov.ca.cwds.fixture.hoi;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;

/**
 * @author CWDS API Team
 */
public class HOIScreeningBuilder {

  private String id;
  private String startDate;
  private String endDate;
  private String decision;
  private String decisionDetail;
  private Integer countyId;
  private String countyDescription;

  public HOIScreeningBuilder setId(String id) {
    this.id = id;
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

  public HOIScreening createHOIScreening() {
    HOIScreening screening = new HOIScreening();
    screening.setId(id);
    screening.setStartDate(DomainChef.uncookDateString(startDate));
    screening.setEndDate(DomainChef.uncookDateString(endDate));
    screening.setCounty(new SystemCodeDescriptor(countyId.shortValue(), countyDescription));
    screening.setDecision(decision);
    screening.setDecisionDetail(decisionDetail);
    return screening;
  }
}
