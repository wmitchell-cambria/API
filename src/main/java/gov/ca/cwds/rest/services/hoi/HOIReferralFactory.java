package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.persistence.cms.ReferralClient;
import java.util.List;

import java.util.stream.Collectors;
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
 */
class HOIReferralFactory {

  /**
   * @param referral - Referral
   * @param referralClient - ReferralClient
   * @return the built referral HOI
   */
  HOIReferral createHOIReferral(Referral referral, ReferralClient referralClient,
      SystemCodeDescriptor county) {
    HOIReferral hoiReferral = new HOIReferral();
    hoiReferral.setId(referral.getId());
    hoiReferral.setStartDate(referral.getReceivedDate());
    hoiReferral.setEndDate(referral.getClosureDate());
    hoiReferral.setCounty(county);
    hoiReferral.setResponseTime(
        new SystemCodeDescriptor(referral.getReferralResponseType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getReferralResponseType())));
    hoiReferral.setAssignedSocialWorker(buildAssignedSocialWorkerDomain(referral.getStaffPerson()));
    hoiReferral.setReporter(buildReporterDomain(referral, referralClient));
    hoiReferral.setAccessLimitation(buildAccessLimitationDomain(referral));

    List<HOIAllegation> hoiAllegations = referral.getAllegations().stream()
        .map(this::buildAllegationDomain).collect(Collectors.toList());
    hoiReferral.setAllegations(hoiAllegations);

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

  private HOIAllegation buildAllegationDomain(Allegation allegation) {
    String allegationId = allegation.getId();
    HOIAllegation hoiAllegation = new HOIAllegation(allegationId,
        new SystemCodeDescriptor(allegation.getAllegationType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getAllegationType())),
        new SystemCodeDescriptor(allegation.getAllegationDispositionType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getAllegationDispositionType())),
        null, null,

        new LegacyDescriptor(allegationId, CmsKeyIdGenerator.getUIIdentifierFromKey(allegationId),
            new DateTime(allegation.getLastUpdatedTime()),
            LegacyTable.ALLEGATION.getName(), LegacyTable.ALLEGATION.getDescription()));

    if (allegation.getVictim() != null) {
      hoiAllegation.setVictim(buildHOIVictimDomain(allegation.getVictim()));
    }
    if (allegation.getPerpetrator() != null) {
      hoiAllegation.setPerpetrator(buildHOIPerpetratorDomain(allegation.getPerpetrator()));
    }

    return hoiAllegation;
  }

  private HOIVictim buildHOIVictimDomain(Client client) {
    String clientId = client.getId();
    return new HOIVictim(
        clientId,
        client.getFirstName(),
        client.getLastName(),
        client.getNameSuffix(),
        new LegacyDescriptor(
            clientId,
            CmsKeyIdGenerator.getUIIdentifierFromKey(clientId),
            new DateTime(client.getLastUpdatedTime()),
            LegacyTable.CLIENT.getName(),
            LegacyTable.CLIENT.getDescription()));
  }

  private HOIPerpetrator buildHOIPerpetratorDomain(Client client) {
    String clientId = client.getId();
    return new HOIPerpetrator(
        clientId,
        client.getFirstName(),
        client.getLastName(),
        client.getNameSuffix(),
        new LegacyDescriptor(
            clientId,
            CmsKeyIdGenerator.getUIIdentifierFromKey(clientId),
            new DateTime(client.getLastUpdatedTime()),
            LegacyTable.CLIENT.getName(),
            LegacyTable.CLIENT.getDescription()));
  }

  private HOIReporter buildReporterDomain(Referral referral, ReferralClient referralClient) {
    Role role = getReporterRole(referral, referralClient);
    HOIReporter hoiReporter;
    Reporter reporter = referral.getReporter();
    if (reporter != null) {
      String reporterId = reporter.getReferralId();
      hoiReporter = new HOIReporter(
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

  private Role getReporterRole(Referral referral, ReferralClient referralClient) {
    Reporter reporter = referral.getReporter();
    if (reporter == null) {
      if ("Y".equals(referral.getAnonymousReporterIndicator())) {
        return Role.ANONYMOUS_REPORTER;
      } else if ("Y".equals(referralClient.getSelfReportedIndicator())) {
        return Role.SELF_REPORTER;
      }
    } else {
      if ("Y".equals(reporter.getMandatedReporterIndicator())) {
        return Role.MANDATED_REPORTER;
      } else {
        return Role.NON_MANDATED_REPORTER;
      }
    }
    return null;
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
