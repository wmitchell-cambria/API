package gov.ca.cwds.rest.services.submit;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.AllegationIntake;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;

/**
 * Business layer object to transform NS {@link AllegationIntake} to {@link Allegation}
 * 
 * @author CWDS API Team
 */
public class AllegationsTransformer {

  /**
   * @param allegationsIntake - allegationsIntake
   * @return the {@link Allegation}
   */
  public Set<Allegation> transform(Set<AllegationIntake> allegationsIntake) {
    Set<Allegation> allegations = new HashSet<>();
    for (AllegationIntake allegationIntake : allegationsIntake) {
      for (String description : allegationIntake.getTypes()) {
        Short allegationType = setAllegationType(description);
        Allegation allegation =
            new Allegation(allegationIntake.getLegacySourceTable(), allegationIntake.getLegacyId(),
                allegationIntake.getVictimPersonId(), allegationIntake.getPerpetratorPersonId(),
                allegationType, allegationIntake.getCounty());
        allegations.add(allegation);
      }

    }
    return allegations;
  }

  private Short setAllegationType(String description) {
    return StringUtils.isNotBlank(description) ? IntakeCodeCache.global()
        .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.INJURY_HARM_TYPE, description)
        : null;
  }

}
