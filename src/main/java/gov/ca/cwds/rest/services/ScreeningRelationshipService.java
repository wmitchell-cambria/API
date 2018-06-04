package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.services.mapper.RelationshipMapper;

/**
 * Business layer object services {@link Relationship}.
 * 
 * @author CWDS API Team
 */
public class ScreeningRelationshipService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningRelationshipService.class);

  private RelationshipDao relationshipDao;
  private RelationshipMapper relationshipMapper;

  @Inject
  public ScreeningRelationshipService(RelationshipDao relationshipDao,
      RelationshipMapper relationshipMapper) {
    super();
    this.relationshipDao = relationshipDao;
    this.relationshipMapper = relationshipMapper;
  }

  @Override
  public Response find(Serializable serializable) {
    assert serializable instanceof String;
    final Relationship entity = relationshipDao.find(serializable);
    if (entity != null) {
      return relationshipMapper.map(entity);
    }
    return null;
  }

  @Override
  public Response delete(Serializable serializable) {
    return null;
  }

  @Override
  public Response create(Request request) {
    final ScreeningRelationship relationship = (ScreeningRelationship) request;
    final Relationship entity = relationshipDao
        .create(new Relationship(null, relationship.getClientId(), relationship.getRelativeId(),
            relationship.getRelationshipType(), new Date(), new Date()));
    relationship.setId(entity.getId());
    LOGGER.debug("saved relationship {}", relationship);
    return relationship;
  }

  @Override
  public Response update(Serializable serializable, Request request) {
    return null;
  }

}
