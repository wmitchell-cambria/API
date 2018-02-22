package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.AllegationPerson;
import gov.ca.cwds.rest.api.domain.investigation.AllegationSubType;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

@SuppressWarnings("javadoc")
public class AllegationEntityBuilder {

  private static final Short INJURY_HARM_TYPE_1 = 1372;
  private static final Short INJURY_HARM_TYPE_2 = 1372;
  private static final Short INJURY_HARM_SUBTYPE_1 = 6;
  private static final Short INJURY_HARM_SUBTYPE_2 = 7;

  private AllegationSubType allegationSubType1 =
      new AllegationSubType(INJURY_HARM_TYPE_1, INJURY_HARM_SUBTYPE_1);
  private AllegationSubType allegationSubType2 =
      new AllegationSubType(INJURY_HARM_TYPE_2, INJURY_HARM_SUBTYPE_2);
  private Set<AllegationSubType> allegationSubTypes = new HashSet<>();

  protected Short allegationType = 2179;
  protected Boolean createdByScreener = Boolean.FALSE;
  protected Short dispositionType = 46;
  protected String rational = "disposition reason explained";

  private CmsRecordDescriptor legacyDescriptor =
      new CmsRecordDescriptor("1234567ABC", "001-2000-3399-415790", "ALLGTN_T", "Allegation");
  private AllegationPerson victim = new AllegationPersonEntityBuilder().build();
  private AllegationPerson perpetrator =
      new AllegationPersonEntityBuilder().setFirstName("jack").setLastName("kennson")
          .setDateOfBirth("2001-09-30").setSuffixTitle("III").setMiddleName("Bill").build();

  public Allegation build() {
    allegationSubTypes.add(allegationSubType1);
    allegationSubTypes.add(allegationSubType2);

    return new Allegation(allegationType, createdByScreener, allegationSubTypes, dispositionType,
        rational, legacyDescriptor, victim, perpetrator);
  }

  public AllegationEntityBuilder setAllegationType(Short allegationType) {
    this.allegationType = allegationType;
    return this;
  }

  public AllegationEntityBuilder setCreatedByScreener(Boolean createdByScreener) {
    this.createdByScreener = createdByScreener;
    return this;
  }

  public AllegationEntityBuilder setAllegationSubTypes(Set<AllegationSubType> allegationSubTypes) {
    this.allegationSubTypes = allegationSubTypes;
    return this;
  }

  public AllegationEntityBuilder setDispositionType(Short dispositionType) {
    this.dispositionType = dispositionType;
    return this;
  }

  public AllegationEntityBuilder setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public AllegationEntityBuilder setVictim(AllegationPerson victim) {
    this.victim = victim;
    return this;
  }

  public AllegationEntityBuilder setRational(String rational) {
    this.rational = rational;
    return this;
  }

  public AllegationEntityBuilder setPerpetrator(AllegationPerson perpetrator) {
    this.perpetrator = perpetrator;
    return this;
  }

}
