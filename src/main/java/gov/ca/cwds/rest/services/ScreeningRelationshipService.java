package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreeningRelationshipService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningRelationshipService.class);
  private RelationshipDao relationshipDao;

  @Inject
  public ScreeningRelationshipService(RelationshipDao relationshipDao){
    super();
    this.relationshipDao = relationshipDao;
  }

  @Override
  public Response find(Serializable serializable) {
    return null;
  }

  @Override
  public Response delete(Serializable serializable) {
    return null;
  }

  @Override
  public Response create(Request request) {
    ScreeningRelationship relationship = (ScreeningRelationship) request;
    Relationship entity = new Relationship(null, relationship.getClientId(),
        relationship.getRelativeId(), relationship.getRelationshipType(),
        new Date(), new Date());
        entity = relationshipDao.create(entity);
    relationship.setId(entity.getId());
    relationship.setCreatedAt(entity.getCreatedAt());
    relationship.setUpdatedAt(entity.getUpdatedAt());
    LOGGER.debug("saved relationship {}", relationship);
    return relationship;
  }

  @Override
  public Response update(Serializable serializable, Request request) {
    return null;
  }
}
