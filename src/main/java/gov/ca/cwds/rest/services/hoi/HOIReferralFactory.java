package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
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
import gov.ca.cwds.rest.api.domain.hoi.HOIPerpetrator;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;
import gov.ca.cwds.rest.api.domain.hoi.HOIVictim;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralFactory {

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
    HOIReferral hoiReferral = new HOIReferral();

    hoiReferral.setId(referral.getId());
    hoiReferral.setStartDate(referral.getReceivedDate());
    hoiReferral.setEndDate(referral.getClosureDate());
    hoiReferral.setCounty(new SystemCodeDescriptor(referral.getGovtEntityType(),
        SystemCodeCache.global().getSystemCodeShortDescription(referral.getGovtEntityType())));
    hoiReferral.setResponseTime(
        new SystemCodeDescriptor(referral.getReferralResponseType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getReferralResponseType())));
    hoiReferral.setAssignedSocialWorker(buildAssignedSocialWorkerDomain(staffPerson));
    hoiReferral.setReporter(buildReporterDomain(reporter, role));
    hoiReferral.setAccessLimitation(buildAccessLimitationDomain(referral));
    List<HOIAllegation> hoiAllegatons = new ArrayList<>();
    for (Map.Entry<Allegation, List<Client>> allegation : allegationMap.entrySet()) {
      hoiAllegatons.add(buildAllegationDomain(allegation));
    }
    hoiReferral.setAllegations(hoiAllegatons);
    String referralId = referral.getId();
    hoiReferral.setLegacyDescriptor(
        new LegacyDescriptor(referralId, CmsKeyIdGenerator.getUIIdentifierFromKey(referralId),
            new DateTime(referral.getLastUpdatedTime()), LegacyTable.REFERRAL.getName(),
            LegacyTable.REFERRAL.getDescription()));
    return hoiReferral;
  }

  private AccessLimitation buildAccessLimitationDomain(Referral referral) {
    return new AccessLimitation(LimitedAccessType.getByValue(referral.getLimitedAccessCode()),
        referral.getLimitedAccessDate(), referral.getLimitedAccessDesc(),
        new SystemCodeDescriptor(referral.getLimitedAccessGovtAgencyType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getLimitedAccessGovtAgencyType())));
  }

  private HOIAllegation buildAllegationDomain(Map.Entry<Allegation, List<Client>> allegation) {
    String allegationId = allegation.getKey().getId();
    HOIAllegation hoiAllegation = new HOIAllegation(allegationId,
        new SystemCodeDescriptor(allegation.getKey().getAllegationType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getKey().getAllegationType())),
        new SystemCodeDescriptor(allegation.getKey().getAllegationDispositionType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getKey().getAllegationDispositionType())),
        null, null,

        new LegacyDescriptor(allegationId, CmsKeyIdGenerator.getUIIdentifierFromKey(allegationId),
            new DateTime(allegation.getKey().getLastUpdatedTime()),
            LegacyTable.ALLEGATION.getName(), LegacyTable.ALLEGATION.getDescription()));
    allegation.getValue()
        .forEach(eachclient -> buildClientsDomain(allegation, hoiAllegation, eachclient));
    return hoiAllegation;
  }

  private void buildClientsDomain(Map.Entry<Allegation, List<Client>> allegation,
      HOIAllegation hoiAllegation, Client eachclient) {
    String clientId = eachclient.getId();
    if (eachclient.getId().equals(allegation.getKey().getVictimClientId())) {

      hoiAllegation.setVictim(
          new HOIVictim(
              clientId,
              eachclient.getFirstName(),
              eachclient.getLastName(),
              eachclient.getNameSuffix(),
              new LegacyDescriptor(
                  clientId,
                  CmsKeyIdGenerator.getUIIdentifierFromKey(clientId),
                  new DateTime(eachclient.getLastUpdatedTime()),
                  LegacyTable.CLIENT.getName(),
                  LegacyTable.CLIENT.getDescription())));
    } else {
      hoiAllegation.setPerpetrator(
          new HOIPerpetrator(
              clientId,
              eachclient.getFirstName(),
              eachclient.getLastName(),
              eachclient.getNameSuffix(),
              new LegacyDescriptor(
                  clientId,
                  CmsKeyIdGenerator.getUIIdentifierFromKey(clientId),
                  new DateTime(eachclient.getLastUpdatedTime()),
                  LegacyTable.CLIENT.getName(),
                  LegacyTable.CLIENT.getDescription())));
    }
  }

  private HOIReporter buildReporterDomain(Reporter reporter, Role role) {
    HOIReporter hoiReporter = null;
    if (reporter != null) {
      String reporterId = reporter.getReferralId();
      hoiReporter =
          new HOIReporter(
              role,
              reporter.getReferralId(),
              reporter.getFirstName(),
              reporter.getLastName(),
              reporter.getNameSuffix(),
              new LegacyDescriptor(
                  reporterId,
                  CmsKeyIdGenerator.getUIIdentifierFromKey(reporterId),
                  new DateTime(reporter.getLastUpdatedTime()),
                  LegacyTable.REPORTER.getName(),
                  LegacyTable.REPORTER.getDescription()));
    } else {
      hoiReporter = new HOIReporter(role, null, null, null, null, null);
    }
    return hoiReporter;
  }

  private HOISocialWorker buildAssignedSocialWorkerDomain(StaffPerson staffPerson) {
    String staffId = staffPerson.getId();
    return new HOISocialWorker(
        staffPerson.getId(),
        staffPerson.getFirstName(),
        staffPerson.getLastName(),
        staffPerson.getNameSuffix(),
        new LegacyDescriptor(
            staffId,
            staffId,
            new DateTime(staffPerson.getLastUpdatedTime()),
            LegacyTable.STAFF_PERSON.getName(),
            LegacyTable.STAFF_PERSON.getDescription()));
  }
}
