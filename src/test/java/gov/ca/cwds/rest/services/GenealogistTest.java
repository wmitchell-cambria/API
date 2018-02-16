package gov.ca.cwds.rest.services;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientRelationshipEntityBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenealogistTest {

    Genealogist genealogist;
    ClientDao clientDao;

    private String clientId = "AXD19DS";
    private String relatedId1 = "XZD876DS7";
    private String relatedId2 = "ZZYW3214D";

    Client client;
    Client relation1;
    Client relation2;

    ClientRelationship relationship1;
    ClientRelationship relationship2;
    ClientRelationship[] relationships = new ClientRelationship[2] ;

    @Before
    public void setup(){

        clientDao = mock(ClientDao.class);
        client = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie").setSensitivityIndicator("R").build();
        relation1 = new ClientEntityBuilder().setId("1").setCommonFirstName("Ralph").setSensitivityIndicator("S").build();
        relation2 = new ClientEntityBuilder().setId("2").setCommonFirstName("Rene").build();
        when(clientDao.find(clientId)).thenReturn(client);
        when(clientDao.find(relatedId1)).thenReturn(relation1);
        when(clientDao.find(relatedId2)).thenReturn(relation2);

        relationship1 = new ClientRelationshipEntityBuilder().setPrimaryClientId(clientId).setSecondaryClientId(relatedId1).build();
        relationship2 = new ClientRelationshipEntityBuilder().setPrimaryClientId(clientId).setSecondaryClientId(relatedId2).build();
        relationships = new ClientRelationship[] {relationship1, relationship2};

        genealogist = new Genealogist(clientDao);
    }


    @Test
    public void shouldSearchForRelatedClients(){
        genealogist.buildRelationForClient(clientId, relationships );
        verify(clientDao).find(clientId);
        verify(clientDao).find(relatedId1);
        verify(clientDao).find(relatedId2);
    }

    @Test
    public void relationshipShouldContainDataForTheClient(){
        Relationship relationship = genealogist.buildRelationForClient(clientId, relationships );
        String clientBday = DomainChef.cookDate(client.getBirthDate());
        assertEquals(clientBday, relationship.getDateOfBirth());
        assertEquals(client.getCommonFirstName(), relationship.getFirstName());
        assertEquals(client.getCommonLastName(), relationship.getLastName());
        assertEquals(client.getSuffixTitleDescription(), relationship.getSuffixName());
    }

    @Test
    public void relationshipShouldSetClientSealedIndicator(){
        client = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie").setSensitivityIndicator("R").build();
        Relationship relationship = genealogist.buildRelationForClient(clientId, relationships );
        assertTrue(relationship.getSealed());
        assertFalse(relationship.getSensitive());
    }

    @Test
    public void shouldSetClientSensitiveIndicator(){
        client = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie").setSensitivityIndicator("S").build();
        when(clientDao.find(clientId)).thenReturn(client);
        Relationship relationship = genealogist.buildRelationForClient(clientId, relationships );
        assertFalse(relationship.getSealed());
        assertTrue(relationship.getSensitive());
    }

    @Test
    public void shouldContainRelatedClients(){
        Relationship relationship = genealogist.buildRelationForClient(clientId, relationships );
        ArrayList<RelationshipTo> orderedRelationships = convertToOrderedRelationships(relationship);

        assertEquals(relation1.getCommonFirstName(), orderedRelationships.get(0).getRelatedFirstName());
        assertEquals(relation1.getCommonLastName(), orderedRelationships.get(0).getRelatedLastName());
        assertEquals(relation2.getCommonFirstName(), orderedRelationships.get(1).getRelatedFirstName());
        assertEquals(relation2.getCommonLastName(), orderedRelationships.get(1).getRelatedLastName());
    }

    private ArrayList<RelationshipTo> convertToOrderedRelationships(Relationship relationship) {
        ArrayList<RelationshipTo> orderedRelationships = new ArrayList();
        RelationshipTo secondRelationship = null;
        for(RelationshipTo relation : relationship.getRelatedTo()){
            if (relation.getRelatedFirstName().equals(relation1.getCommonFirstName())){
                orderedRelationships.add(relation);
            }else{
                secondRelationship = relation;
            }
        }
        orderedRelationships.add(secondRelationship);
        return orderedRelationships;
    }
}