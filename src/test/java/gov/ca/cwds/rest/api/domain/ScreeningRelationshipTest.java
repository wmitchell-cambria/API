package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;

public class ScreeningRelationshipTest {

  public static final int RELATIONSHIP_TYPE = 190;
  ScreeningRelationship relationship;

  @Test
  public void shouldHaveAllFieldsWhenCreatingWithSimpleConstructor(){
    relationship = new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId",
        RELATIONSHIP_TYPE);
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
    assertTrue(isCurrentDateTime(relationship.getCreatedAt()));
    assertNull(relationship.getUpdatedAt());
  }

  @Test
  public void shouldHaveAllFieldsWhenCreatingFullSimpleConstructor(){
    Date anHourAgo = new Date(System.currentTimeMillis() - 3600 * 1000);
    Date now = new Date();
    relationship = new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId",
        RELATIONSHIP_TYPE, anHourAgo, now);
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
    assertTrue(relationship.getCreatedAt().equals(anHourAgo));
    assertTrue(relationship.getUpdatedAt().equals(now));
  }

  private boolean isCurrentDateTime(Date date){
    Long now = new Date().getTime();
    Long createdTime = date.getTime();
    Long aSecondAgo = 1000L;
    return createdTime <= now && createdTime > now - aSecondAgo;
  }
}