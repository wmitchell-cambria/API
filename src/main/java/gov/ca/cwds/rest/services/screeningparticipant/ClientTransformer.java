package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.submit.Gender;

/**
 * @author CWDS API Team
 *
 */
public class ClientTransformer implements ParticipantMapper {

  @Override
  public ParticipantIntakeApi tranform(CmsPersistentObject object) {
    Client client = (Client) object;
    RaceAndEthnicityConveter raceAndEthnicityConveter = new RaceAndEthnicityConveter();
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(client.getId(), null, new DateTime(client.getLastUpdatedTime()),
            LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());

    String gender = StringUtils.isNotBlank(client.getGenderCode())
        ? (Gender.findByCmsDescription(client.getGenderCode().toUpperCase())).getNsDescription()
        : null;

    List<String> languages = setLanguages(client);
    String races = raceAndEthnicityConveter.createRace(client);
    String hispanic = raceAndEthnicityConveter.createHispanic(client);
    return new ParticipantIntakeApi(null, LegacyTable.CLIENT.getName(), client.getId(),
        legacyDescriptor, client.getFirstName(), client.getMiddleName(), client.getLastName(),
        client.getNameSuffix(), gender, "Age", "AgeUnit", "ssn", client.getBirthDate(), languages,
        races, hispanic, "screeningId", new HashSet<>(), null, null, setSealedIndicator(client),
        setSensitivieIndicator(client));
  }

  private List<String> setLanguages(Client client) {
    List<String> languages = new LinkedList<>();
    if (client.getPrimaryLanguageType() == 0) {
      return languages;
    } else if (client.getPrimaryLanguageType() != 0 && client.getSecondaryLanguageType() != 0) {
      return new LinkedList<>(Arrays.asList(
          IntakeCodeCache.global()
              .getIntakeCodeForLegacySystemCode(client.getPrimaryLanguageType()),
          IntakeCodeCache.global()
              .getIntakeCodeForLegacySystemCode(client.getSecondaryLanguageType())));
    } else if (client.getPrimaryLanguageType() != 0 && client.getSecondaryLanguageType() == 0) {
      return new LinkedList<>(Arrays.asList(IntakeCodeCache.global()
          .getIntakeCodeForLegacySystemCode(client.getPrimaryLanguageType())));
    }
    return languages;
  }

  private Boolean setSealedIndicator(Client client) {
    Boolean sealed = false;
    if ("R".equals(client.getSensitivityIndicator())) {
      return true;
    }
    return sealed;
  }

  private Boolean setSensitivieIndicator(Client client) {
    Boolean sensitive = false;
    if ("S".equals(client.getSensitivityIndicator())) {
      return true;
    }
    return sensitive;
  }

}
