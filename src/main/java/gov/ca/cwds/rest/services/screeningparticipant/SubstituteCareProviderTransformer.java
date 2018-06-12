package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Business layer object to transform a {@link SubstituteCareProvider} to an
 * {@link ParticipantIntakeApi}
 * 
 * @author CWDS API Team
 *
 */
public class SubstituteCareProviderTransformer implements ParticipantMapper {

  @Override
  public ParticipantIntakeApi tranform(CmsPersistentObject object) {
    SubstituteCareProvider substituteCareProvider = (SubstituteCareProvider) object;

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(substituteCareProvider.getId(), null,
        new DateTime(substituteCareProvider.getLastUpdatedTime()),
        LegacyTable.SUBSTITUTE_CARE_PROVIDER.getName(),
        LegacyTable.SUBSTITUTE_CARE_PROVIDER.getDescription());

    String state = IntakeCodeCache.global()
        .getIntakeCodeForLegacySystemCode(substituteCareProvider.getStateCodeType());
    String streetAddress =
        substituteCareProvider.getStreetNumber() + " " + substituteCareProvider.getStreetName();
    String zip =
        substituteCareProvider.getZipNumber() + "-" + substituteCareProvider.getZipSuffixNumber();
    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays.asList(
        new AddressIntakeApi(LegacyTable.SUBSTITUTE_CARE_PROVIDER.getName(), null, streetAddress,
            substituteCareProvider.getCityName(), state, zip, null, legacyDescriptor)));
    addresses = Collections.unmodifiableSet(addresses);
    String sensitivityIndicator = substituteCareProvider.getSensitivityIndicator() != null
        ? substituteCareProvider.getSensitivityIndicator()
        : "";

    Set<PhoneNumber> phoneNumbers = new HashSet<>(Arrays.asList(
        new PhoneNumber(null, substituteCareProvider.getAdditionalPhoneNumber().toString(), null)));

    return new ParticipantIntakeApi(null, null, substituteCareProvider.getId(), legacyDescriptor,
        substituteCareProvider.getFirstName(), substituteCareProvider.getMiddleName(),
        substituteCareProvider.getLastName(), substituteCareProvider.getSuffixTitleDescription(),
        null, null, null, substituteCareProvider.getSsn(), substituteCareProvider.getBirthDate(),
        new LinkedList<>(), null, null, null, new HashSet<>(), addresses, phoneNumbers,
        "R".equals(sensitivityIndicator), "S".equals(sensitivityIndicator));
  }

}
