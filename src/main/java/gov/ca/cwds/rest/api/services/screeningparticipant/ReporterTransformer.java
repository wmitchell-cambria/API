package gov.ca.cwds.rest.api.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
public class ReporterTransformer implements ParticipantMapper {

  @Override
  public ParticipantIntakeApi tranform(CmsPersistentObject object) {
    Reporter reporter = (Reporter) object;

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(reporter.getReferralId(), null,
        new DateTime(reporter.getLastUpdatedTime()), LegacyTable.REPORTER.getName(),
        LegacyTable.REPORTER.getDescription());

    String state =
        IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(reporter.getStateCodeType());
    String streetAddress = reporter.getStreetNumber() + " " + reporter.getStreetName();
    String zip = reporter.getZipNumber() + "-" + reporter.getZipSuffixNumber();
    Set<AddressIntakeApi> addresses =
        new HashSet<>(Arrays.asList(new AddressIntakeApi(LegacyTable.REPORTER.getName(), null,
            streetAddress, reporter.getCity(), state, zip, null, legacyDescriptor)));
    addresses = Collections.unmodifiableSet(addresses);
    Set<PhoneNumber> phoneNumbers = new HashSet<>(
        Arrays.asList(new PhoneNumber(null, reporter.getPrimaryPhoneNumber().toString(), null)));

    return new ParticipantIntakeApi(null, LegacyTable.REPORTER.getName(), reporter.getReferralId(),
        legacyDescriptor, reporter.getFirstName(), reporter.getMiddleInitialName(),
        reporter.getLastName(), reporter.getSuffixTitleDescription(), null, null, null, null, null,
        new LinkedList<>(), null, null, null, new HashSet<>(), addresses, phoneNumbers, null, null);
  }

}
