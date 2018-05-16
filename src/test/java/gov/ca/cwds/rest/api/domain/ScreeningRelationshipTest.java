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
  }

  @Test
  public void shouldHaveAllFieldsWhenCreatingFullSimpleConstructor(){
    Date anHourAgo = new Date(System.currentTimeMillis() - 3600 * 1000);
    Date now = new Date();
    relationship = new ScreeningRelationship("123", "PersonLegacyId", "RelationLegacydId",
    assertEquals(relationship.getId(), "123");
    assertEquals(relationship.getClientId(), "PersonLegacyId");
    assertEquals(relationship.getRelativeId(), "RelationLegacydId");
    assertEquals(relationship.getRelationshipType(), RELATIONSHIP_TYPE);
  }

  private boolean isCurrentDateTime(Date date){
    Long now = new Date().getTime();
    Long createdTime = date.getTime();
    Long aSecondAgo = 1000L;
    return createdTime <= now && createdTime > now - aSecondAgo;
  }
}