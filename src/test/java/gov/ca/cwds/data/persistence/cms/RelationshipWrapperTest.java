package gov.ca.cwds.data.persistence.cms;

import org.junit.Test;

import static org.junit.Assert.*;

public class RelationshipWrapperTest {

    @Test
    public void shouldDefaultValuesWhenNoParamsGiven(){
        String relationId = "";
        String primaryLegacyId = "";
        String secondaryLegacyId = "";
        String primaryFirstName = "";
        String primaryLastName = "";
        String secondaryFirstName ="";
        String secondaryLastName = "";
        String primaryRelationshipCode = "";
        String secondaryRelationshipCode = "";

        RelationshipWrapper relationshipWrapper = new RelationshipWrapper( relationId,  primaryLegacyId,  secondaryLegacyId,  primaryFirstName,  primaryLastName,  secondaryFirstName,  secondaryLastName,  primaryRelationshipCode,  secondaryRelationshipCode);
        assertEquals(relationId, relationshipWrapper.getRelationId() );
        assertEquals(primaryLegacyId, relationshipWrapper.getPrimaryLegacyId());
        assertEquals(secondaryLegacyId, relationshipWrapper.getSecondaryLegacyId() );
        assertEquals(primaryFirstName, relationshipWrapper.getPrimaryFirstName() );
        assertEquals(primaryLastName, relationshipWrapper.getPrimaryLastName() );
        assertEquals(secondaryFirstName, relationshipWrapper.getSecondaryFirstName() );
        assertEquals(secondaryLastName, relationshipWrapper.getSecondaryLastName() );
        assertEquals(primaryRelationshipCode, relationshipWrapper.getPrimaryRelationshipCode() );
        assertEquals(secondaryRelationshipCode, relationshipWrapper.getSecondaryRelationshipCode() );
    }
    @Test

    public void shouldConstructClassWhenParametersAreGiven(){
        String relationId = "relationId";
        String primaryLegacyId = "primaryLegacyId";
        String secondaryLegacyId = "secondaryLegacyId";
        String primaryFirstName = "primaryFirstName";
        String primaryLastName = "primaryLastName";
        String secondaryFirstName ="secondaryFirstName";
        String secondaryLastName = "secondaryLastName";
        String primaryRelationshipCode = "primaryRelationshipCode";
        String secondaryRelationshipCode = "secondaryRelationshipCode";

        RelationshipWrapper relationshipWrapper = new RelationshipWrapper( relationId,  primaryLegacyId,  secondaryLegacyId,  primaryFirstName,  primaryLastName,  secondaryFirstName,  secondaryLastName,  primaryRelationshipCode,  secondaryRelationshipCode);
        assertEquals(relationId, relationshipWrapper.getRelationId() );
        assertEquals(primaryLegacyId, relationshipWrapper.getPrimaryLegacyId());
        assertEquals(secondaryLegacyId, relationshipWrapper.getSecondaryLegacyId() );
        assertEquals(primaryFirstName, relationshipWrapper.getPrimaryFirstName() );
        assertEquals(primaryLastName, relationshipWrapper.getPrimaryLastName() );
        assertEquals(secondaryFirstName, relationshipWrapper.getSecondaryFirstName() );
        assertEquals(secondaryLastName, relationshipWrapper.getSecondaryLastName() );
        assertEquals(primaryRelationshipCode, relationshipWrapper.getPrimaryRelationshipCode() );
        assertEquals(secondaryRelationshipCode, relationshipWrapper.getSecondaryRelationshipCode() );
    }

}