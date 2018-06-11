package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.submit.Gender;

/**
 * @author CWDS API Team
 *
 */
public class CollateralIndividualTranformer implements ParticipantMapper {

  @Override
  public ParticipantIntakeApi tranform(CmsPersistentObject object) {
    CollateralIndividual collateralIndividual = (CollateralIndividual) object;

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(collateralIndividual.getId(), null,
        new DateTime(collateralIndividual.getLastUpdatedTime()),
        LegacyTable.COLLATERAL_INDIVIDUAL.getName(),
        LegacyTable.COLLATERAL_INDIVIDUAL.getDescription());

    String gender =
        StringUtils.isNotBlank(collateralIndividual.getGenderCode())
            ? (Gender.findByCmsDescription(collateralIndividual.getGenderCode().toUpperCase()))
                .getNsDescription()
            : null;
    String streetAddress =
        collateralIndividual.getStreetNumber() + " " + collateralIndividual.getStreetName();
    String state = IntakeCodeCache.global()
        .getIntakeCodeForLegacySystemCode(collateralIndividual.getStateCode());
    String zip =
        collateralIndividual.getZipNumber() + "-" + collateralIndividual.getZipSuffixNumber();

    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays.asList(new AddressIntakeApi(null, null,
        streetAddress, collateralIndividual.getCity(), state, zip, null, legacyDescriptor)));
    addresses = Collections.unmodifiableSet(addresses);

    String phone = collateralIndividual.getPrimaryPhoneNo() != null
        ? collateralIndividual.getPrimaryPhoneNo().toString()
        : null;
    Set<PhoneNumber> phoneNumbers =
        new HashSet<>(Arrays.asList(new PhoneNumber(null, phone, null)));

    return new ParticipantIntakeApi(null, null, null, legacyDescriptor,
        collateralIndividual.getFirstName(), collateralIndividual.getMiddleInitialName(),
        collateralIndividual.getLastName(), collateralIndividual.getSuffixTitleDescription(),
        gender, null, null, null, collateralIndividual.getBirthDate(), new LinkedList<>(), null,
        null, null, new HashSet<>(), addresses, phoneNumbers, false, false);
  }

}
