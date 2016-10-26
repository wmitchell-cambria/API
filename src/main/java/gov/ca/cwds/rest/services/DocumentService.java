package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.ns.ScreeningDao;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class DocumentService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentService.class);
  private ScreeningDao screeningDao;
  private PersonService personService;

  /**
   * 
   * @param screeningDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.ns.Screening} objects.
   * @param personService The person service
   */
  public DocumentService(ScreeningDao screeningDao, PersonService personService) {
    this.screeningDao = screeningDao;
    this.personService = personService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public ScreeningResponse find(Serializable primaryKey) {
    assert (primaryKey instanceof Long);

    gov.ca.cwds.rest.api.persistence.ns.Screening screening = screeningDao.find(primaryKey);
    if (screening != null) {
      return new ScreeningResponse(screening, buildParticipantList(screening));
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert (primaryKey instanceof Long);
    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedScreening create(Request request) {
    assert (request instanceof ScreeningReference);

    ScreeningReference screeningReference = (ScreeningReference) request;
    gov.ca.cwds.rest.api.persistence.ns.Screening managed =
        new gov.ca.cwds.rest.api.persistence.ns.Screening(screeningReference.getReference());

    managed = screeningDao.create(managed);
    return new PostedScreening(managed.getId(), managed.getReference());
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ScreeningResponse update(Serializable primaryKey, Request request) {
    assert (primaryKey instanceof Long);
    assert (request instanceof ScreeningRequest);

    ScreeningRequest screeningRequest = (ScreeningRequest) request;
    gov.ca.cwds.rest.api.persistence.ns.Screening screening =
        new gov.ca.cwds.rest.api.persistence.ns.Screening((Long) primaryKey, screeningRequest,
            null);

    // TODO before we update we need to ensure some RI on the participant list. This needs to be
    // refactored away from a comma delimited string of ids to a xref table or something of the
    // sort.
    // See https://www.pivotaltracker.com/story/show/132727211
    for (Long participantId : screeningRequest.getParticipant_ids()) {
      Person person = personService.find(participantId);
      if (person == null) {
        String msg = MessageFormat.format("Unable to find participant with id={0}", participantId);
        LOGGER.warn(msg);
        throw new ServiceException(new EntityNotFoundException(msg));
      }
    }

    screening = screeningDao.update(screening);
    return new ScreeningResponse(screening, buildParticipantList(screening));
  }

  private List<gov.ca.cwds.rest.api.domain.Person> buildParticipantList(
      gov.ca.cwds.rest.api.persistence.ns.Screening screening) {
    List<gov.ca.cwds.rest.api.domain.Person> retval = null;
    // TODO : making some assumptions here that this string is a comma delimited list of longs.
    // Need to refactor the database to actually have a xref table.
    // See - https://www.pivotaltracker.com/story/show/132727211
    if (StringUtils.isNotBlank(screening.getParticipantIds())) {
      ImmutableList.Builder<gov.ca.cwds.rest.api.domain.Person> builder = ImmutableList.builder();
      for (String particpantIdString : screening.getParticipantIds().split(",")) {
        Long participantId = Long.valueOf(particpantIdString.trim());
        gov.ca.cwds.rest.api.domain.Person person = personService.find(participantId);
        if (person == null) {
          // NOTE : the database doesn't enforce RI so we are expecting an issue COULD happen. It
          // shouldn't though
          // Database should be refactored to enforce RI
          String msg =
              MessageFormat.format("Unable to find participant with id={0}", participantId);
          LOGGER.warn(msg);
          throw new ServiceException(new EntityNotFoundException(msg));
        }
        builder.add(person);
      }
      retval = builder.build();
    }
    return retval;
  }
}
