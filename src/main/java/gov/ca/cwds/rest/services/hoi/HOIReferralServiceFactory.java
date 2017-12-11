package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIAllegation;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;
import gov.ca.cwds.rest.api.domain.hoi.Perpetrator;
import gov.ca.cwds.rest.api.domain.hoi.SocialWorker;
import gov.ca.cwds.rest.api.domain.hoi.Victim;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralServiceFactory {
  HOIReferral hoiReferral = new HOIReferral();

  /**
   * @param referral - referral
   * @param staffPerson - staffPerson
   * @param reporter - reporter
   * @param allegationMap - allegationMap
   * @param role - role
   * @return the built referral HOI
   */
  public HOIReferral createHOIReferral(Referral referral, StaffPerson staffPerson,
      Reporter reporter, Map<Allegation, List<Client>> allegationMap, Role role) {

    hoiReferral.setId(referral.getId());
    hoiReferral.setStartDate(referral.getReceivedDate());
    hoiReferral.setEndDate(referral.getClosureDate());
    hoiReferral.setCounty(new SystemCodeDescriptor(referral.getGovtEntityType(),
        SystemCodeCache.global().getSystemCodeShortDescription(referral.getGovtEntityType())));
    hoiReferral.setResponseTime(
        new SystemCodeDescriptor(referral.getReferralResponseType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getReferralResponseType())));
    buildAssignedSocialWorkerDomain(staffPerson);
    buildReporterDomain(reporter, role);
    buildAccessLimitationDomain(referral);
    List<HOIAllegation> hoiAllegatons = new ArrayList<>();
    for (Map.Entry<Allegation, List<Client>> allegation : allegationMap.entrySet()) {
      hoiAllegatons.add(buildAllegationDomain(allegation));
    }
    hoiReferral.setAllegations(hoiAllegatons);
    hoiReferral.setLegacyDescriptor(
        new LegacyDescriptor(referral.getId(), null, new DateTime(referral.getLastUpdatedTime()),
            LegacyTable.REFERRAL.getName(), LegacyTable.REFERRAL.getDescription()));
    return hoiReferral;
  }

  private void buildAccessLimitationDomain(Referral referral) {
    hoiReferral.setAccessLimitation(new AccessLimitation(LimitedAccessType.NONE,
        referral.getLimitedAccessDate(), referral.getLimitedAccessDesc(),
        new SystemCodeDescriptor(referral.getLimitedAccessGovtAgencyType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getLimitedAccessGovtAgencyType()))));
  }

  private HOIAllegation buildAllegationDomain(Map.Entry<Allegation, List<Client>> allegation) {
    HOIAllegation hoiAllegation = new HOIAllegation(allegation.getKey().getId(),
        new SystemCodeDescriptor(allegation.getKey().getAllegationType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getKey().getAllegationType())),
        new SystemCodeDescriptor(allegation.getKey().getAllegationDispositionType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getKey().getAllegationDispositionType())),
        null, null,
        new LegacyDescriptor(allegation.getKey().getId(), null,
            new DateTime(allegation.getKey().getLastUpdatedTime()),
            LegacyTable.ALLEGATION.getName(), LegacyTable.ALLEGATION.getDescription()));
    allegation.getValue()
        .forEach(eachclient -> buildClientsDomain(allegation, hoiAllegation, eachclient));
    return hoiAllegation;
  }

  private void buildClientsDomain(Map.Entry<Allegation, List<Client>> allegation,
      HOIAllegation hoiAllegation, Client eachclient) {

    if (eachclient.getId().equals(allegation.getKey().getVictimClientId())) {
      hoiAllegation.setVictim(
          new Victim(eachclient.getId(), eachclient.getFirstName(), eachclient.getLastName(),
              new LegacyDescriptor(eachclient.getId(), null,
                  new DateTime(eachclient.getLastUpdatedTime()), LegacyTable.CLIENT.getName(),
                  LegacyTable.CLIENT.getDescription())));
    } else {
      hoiAllegation.setPerpetrator(
          new Perpetrator(eachclient.getId(), eachclient.getFirstName(), eachclient.getLastName(),
              new LegacyDescriptor(eachclient.getId(), null,
                  new DateTime(eachclient.getLastUpdatedTime()), LegacyTable.CLIENT.getName(),
                  LegacyTable.CLIENT.getDescription())));
    }
  }

  private void buildReporterDomain(Reporter reporter, Role role) {
    if (reporter != null) {
      hoiReferral.setReporter(new HOIReporter(role, reporter.getReferralId(),
          reporter.getFirstName(), reporter.getLastName(),
          new LegacyDescriptor(reporter.getReferralId(), null,
              new DateTime(reporter.getLastUpdatedTime()), LegacyTable.REPORTER.getName(),
              LegacyTable.REPORTER.getDescription())));
    } else {
      hoiReferral.setReporter(new HOIReporter(role, null, null, null, null));
    }
  }

  private void buildAssignedSocialWorkerDomain(StaffPerson staffPerson) {
    hoiReferral.setAssignedSocialWorker(
        new SocialWorker(staffPerson.getId(), staffPerson.getFirstName(), staffPerson.getLastName(),
            new LegacyDescriptor(staffPerson.getId(), null,
                new DateTime(staffPerson.getLastUpdatedTime()), LegacyTable.STAFF_PERSON.getName(),
                LegacyTable.STAFF_PERSON.getDescription())));
  }

}
