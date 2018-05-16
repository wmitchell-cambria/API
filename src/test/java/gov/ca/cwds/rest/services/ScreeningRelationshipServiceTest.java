package gov.ca.cwds.rest.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import java.io.Serializable;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class ScreeningRelationshipServiceTest {
  private ScreeningRelationshipService service;
  private ScreeningRelationship relationship;
  private Relationship relationshipEntity;
  private Serializable serialized;
  private RelationshipDao relationshipDao;

  @Before
  public void setup(){
    relationshipDao = mock(RelationshipDao.class);
    service = new ScreeningRelationshipService(relationshipDao);
    relationshipEntity = new Relationship("123", "ClientID", "RelationId", 190, new Date(), new Date());
    relationship = new ScreeningRelationship("456", "XYZ", "SS2", 190, new Date(), new Date());
  }

  @Test
  public void shouldReturnNullWhenFindIsCalled(){
    assertNull(service.find(serialized));
  }

  @Test
  public void shouldReturnNullWhenDeleteIsCalled(){
    assertNull(service.delete(serialized));
  }

  @Test
  public void shouldSaveRelationshipWhenCreateIsCalled(){
    when(relationshipDao.create(any())).thenReturn(relationshipEntity);
    service.create(relationship);
    verify(relationshipDao).create(isA(Relationship.class));
  }

  @Test
  public void shouldReturnSavedRelationWhenCreateIsCalled(){
    String newId = "123";
    relationshipEntity = new Relationship(newId, relationship.getClientId(),
        relationship.getRelativeId(), relationship.getRelationshipType(),
        relationship.getCreatedAt(), relationship.getUpdatedAt());

    when(relationshipDao.create(any())).thenReturn(relationshipEntity);
    ScreeningRelationship saved = (ScreeningRelationship)service.create(relationship);
    assertEquals(newId, saved.getId());
    assertEquals(relationshipEntity.getId(), saved.getId());
    assertEquals(relationshipEntity.getClientId(), saved.getClientId());
    assertEquals(relationshipEntity.getRelativeId(), saved.getRelativeId());
    assertEquals(relationshipEntity.getRelationshipType(), saved.getRelationshipType());
    assertEquals(relationshipEntity.getCreatedAt(), saved.getCreatedAt());
    assertEquals(relationshipEntity.getUpdatedAt(), saved.getUpdatedAt());
  }

  @Test
  public void shouldUpdateTimeStampsWhenCreateIsCalled(){
    Date beforeTestTime = new Date();
    when(relationshipDao.create(any())).thenReturn(relationshipEntity);
    relationship.setCreatedAt(null);
    relationship.setUpdatedAt(null);
    ScreeningRelationship saved = (ScreeningRelationship)service.create(relationship);
    assertTrue(saved.getCreatedAt().getTime() >= beforeTestTime.getTime());
    assertTrue(saved.getUpdatedAt().getTime() >= beforeTestTime.getTime());
  }

  @Test
  public void shouldReturnNullWhenUpdateIsCalled(){
    assertNull(service.update(serialized, relationship ));
  }
}