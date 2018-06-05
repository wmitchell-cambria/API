package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.dao.investigation.InjuryBodyDetailDao;
import gov.ca.cwds.data.dao.investigation.InjuryHarmDetailDao;
import gov.ca.cwds.data.persistence.cms.InjuryBodyDetail;
import gov.ca.cwds.data.persistence.cms.InjuryHarmDetail;
import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.AllegationSubType;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation Allegation
 * 
 * @author CWDS API Team
 */

public class AllegationService implements TypedCrudsService<String, Allegation, Response> {

  private InjuryBodyDetailDao injuryBodyDetailDao;
  private InjuryHarmDetailDao injuryHarmDetailDao;

  private Allegation validAllegation = new AllegationEntityBuilder().build();

  /**
   * @param injuryBodyDetailDao - injury body detail data access object
   * @param injuryHarmDetailDao - injury harm detail data access object
   */
  @Inject
  public AllegationService(InjuryBodyDetailDao injuryBodyDetailDao,
      InjuryHarmDetailDao injuryHarmDetailDao) {
    super();
    this.injuryBodyDetailDao = injuryBodyDetailDao;
    this.injuryHarmDetailDao = injuryHarmDetailDao;

  }

  @Override
  public Response create(Allegation allegation) {
    return validAllegation;
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response find(String primaryKey) {
    throw new NotImplementedException("find not implemented");
  }

  @Override
  public Response update(String investigationId, Allegation allegation) {
    return validAllegation;

  }

  /**
   * populating allegations details
   * 
   * @param persistedAllegations - list of persisted allegations
   * @return list of allegations
   */
  public Set<Allegation> populateAllegations(
      Set<gov.ca.cwds.data.persistence.cms.Allegation> persistedAllegations) {
    Set<Allegation> allegations = new HashSet<>();
    for (gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation : persistedAllegations) {
      allegations.add(new Allegation(persistedAllegation,
          this.populateAllegationSubTypes(persistedAllegation.getId())));

    }
    return allegations;
  }

  /**
   * populating allegation sub types
   * 
   * @param allegationId -allegation id
   * @return list of allegation sub types
   */
  private Set<AllegationSubType> populateAllegationSubTypes(String allegationId) {
    Set<AllegationSubType> allegationSubTypes = new HashSet<>();
    AllegationSubType allegationSubType = null;
    Short injuryHarmSubType = null;
    InjuryHarmDetail[] injuryHarmDetails =
        this.injuryHarmDetailDao.findInjuryHarmDetailsByAllegationId(allegationId);
    InjuryBodyDetail[] injuryBodyDetails = null;
    for (InjuryHarmDetail harmDetail : injuryHarmDetails) {
      injuryBodyDetails = this.injuryBodyDetailDao
          .findInjuryBodyDetailsByInjuryHarmDetailId(harmDetail.getThirdId());

      for (InjuryBodyDetail injuryBodyDetail : injuryBodyDetails) {
        injuryHarmSubType = injuryBodyDetail.getPhysicalAbuseBodyPartType();
      }

      allegationSubType = new AllegationSubType(harmDetail.getInjuryHarmType(), injuryHarmSubType);
      allegationSubTypes.add(allegationSubType);

    }
    return allegationSubTypes;

  }

}
