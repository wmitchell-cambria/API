package gov.ca.cwds.rest.services.submit;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.AllegationIntake;

/**
 * Business layer object to transform NS {@link AllegationIntake} to {@link Allegation}
 * 
 * @author CWDS API Team
 */
public class AllegationsTransformer {

  public Set<Allegation> transform(Set<AllegationIntake> allegationsIntake,
      Map<String, IntakeLov> nsCodeToNsLovMap) {
    Set<Allegation> allegations = new HashSet<>();
    for (AllegationIntake allegationIntake : allegationsIntake) {
      for (String description : allegationIntake.getTypes()) {
        Short allegationSysId = StringUtils.isNotBlank(description)
            ? nsCodeToNsLovMap.get(description).getLegacySystemCodeId().shortValue()
            : null;
        Allegation allegation =
            new Allegation(allegationIntake.getLegacySourceTable(), allegationIntake.getLegacyId(),
                allegationIntake.getVictimPersonId(), allegationIntake.getPerpetratorPersonId(),
                allegationSysId, allegationIntake.getCounty());
        allegations.add(allegation);
      }
    }
    return allegations;
  }

}
