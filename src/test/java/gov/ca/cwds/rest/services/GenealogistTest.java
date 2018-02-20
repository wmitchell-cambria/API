package gov.ca.cwds.rest.services;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientRelationshipEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GenealogistTest {

    private Genealogist genealogist;
    private ClientDao clientDao;

    private String clientId = "AXD19DS";
    private String relatedId1 = "XZD876DS7";
    private String relatedId2 = "ZZYW3214D";

    private Client client;
    private Client relation1;
    private Client relation2;

    private ClientRelationship primaryRelationship1;
    private ClientRelationship primaryRelationship2;
    private ClientRelationship secondaryRelationship1;
    private ClientRelationship secondaryRelationship2;
    private ClientRelationship[] primaryRelationships = new ClientRelationship[2] ;
    private ClientRelationship[] secondaryRelationships = new ClientRelationship[2] ;

    @Before
    public void setup(){
        clientDao = mock(ClientDao.class);
        client = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie").setSensitivityIndicator("R").build();
        relation1 = new ClientEntityBuilder().setId("1").setCommonFirstName("Ralph").setSensitivityIndicator("S").build();
        relation2 = new ClientEntityBuilder().setId("2").setCommonFirstName("Rene").build();
        when(clientDao.find(clientId)).thenReturn(client);
        when(clientDao.find(relatedId1)).thenReturn(relation1);
        when(clientDao.find(relatedId2)).thenReturn(relation2);

        primaryRelationship1 = new ClientRelationshipEntityBuilder().setPrimaryClientId(clientId).setSecondaryClientId(relatedId1).setClientRelationshipType(new Short("287")).build();
        primaryRelationship2 = new ClientRelationshipEntityBuilder().setPrimaryClientId(clientId).setSecondaryClientId(relatedId2).setClientRelationshipType(new Short("291")).build();
        secondaryRelationship1 = new ClientRelationshipEntityBuilder().setPrimaryClientId(relatedId1).setSecondaryClientId(clientId).setClientRelationshipType(new Short("211")).build();
        secondaryRelationship2 = new ClientRelationshipEntityBuilder().setPrimaryClientId(relatedId2).setSecondaryClientId(clientId).setClientRelationshipType(new Short("252")).build();
        primaryRelationships = new ClientRelationship[] {primaryRelationship1, primaryRelationship2};
        secondaryRelationships = new ClientRelationship[] {secondaryRelationship1, secondaryRelationship2};

        genealogist = new Genealogist(clientDao);
    }

    @Test
    public void shouldFindFourRelations(){
        int expectedNumberOfRelations = 4;
        Relationship foundRelations = genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        assertEquals(expectedNumberOfRelations, foundRelations.getRelatedTo().size());
    }

    @Test
    public void shouldSearchForRelatedClientsWhenClientHas2Relationships(){
        int numberOfClientsSearchedFor = 1;
        int numberOfRelationsSearchedFor = 2;
        genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        verify(clientDao, times(numberOfClientsSearchedFor)).find(clientId);
        verify(clientDao, times(numberOfRelationsSearchedFor)).find(relatedId1);
        verify(clientDao, times(numberOfRelationsSearchedFor)).find(relatedId2);
    }

    @Test
    public void relationshipShouldContainClientInformation(){
        Relationship relationship = genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        String clientBday = DomainChef.cookDate(client.getBirthDate());
        assertEquals(clientBday, relationship.getDateOfBirth());
        assertEquals(client.getCommonFirstName(), relationship.getFirstName());
        assertEquals(client.getCommonLastName(), relationship.getLastName());
        assertEquals(client.getSuffixTitleDescription(), relationship.getSuffixName());
    }

    @Test
    public void relationshipShouldSetClientSealedIndicator(){
        client = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie").setSensitivityIndicator("R").build();
        Relationship relationship = genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        assertTrue(relationship.getSealed());
        assertFalse(relationship.getSensitive());
    }

    @Test
    public void shouldSetClientSensitiveIndicator(){
        client = new ClientEntityBuilder().setId("1").setCommonFirstName("Charlie").setSensitivityIndicator("S").build();
        when(clientDao.find(clientId)).thenReturn(client);
        Relationship relationship = genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        assertFalse(relationship.getSealed());
        assertTrue(relationship.getSensitive());
    }

    @Test
    public void shouldContainRelationsIdentifiedByClient(){
        Relationship relationship = genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        ArrayList<RelationshipTo> orderedRelationships = convertToOrderedRelationships(relationship);

        assertEquals(relation1.getCommonFirstName(), orderedRelationships.get(0).getRelatedFirstName());
        assertEquals(relation1.getCommonLastName(), orderedRelationships.get(0).getRelatedLastName());
        assertEquals(secondaryRelationship1.getClientRelationshipType().toString(), orderedRelationships.get(0).getRelationshipToPerson());

        assertEquals(relation1.getCommonFirstName(), orderedRelationships.get(1).getRelatedFirstName());
        assertEquals(relation1.getCommonLastName(), orderedRelationships.get(1).getRelatedLastName());
        assertEquals(primaryRelationship1.getClientRelationshipType().toString(), orderedRelationships.get(1).getRelationshipToPerson());

        assertEquals(relation2.getCommonFirstName(), orderedRelationships.get(2).getRelatedFirstName());
        assertEquals(relation2.getCommonLastName(), orderedRelationships.get(2).getRelatedLastName());
        assertEquals(secondaryRelationship2.getClientRelationshipType().toString(), orderedRelationships.get(2).getRelationshipToPerson());

        assertEquals(relation2.getCommonFirstName(), orderedRelationships.get(3).getRelatedFirstName());
        assertEquals(relation2.getCommonLastName(), orderedRelationships.get(3).getRelatedLastName());
        assertEquals(primaryRelationship2.getClientRelationshipType().toString(), orderedRelationships.get(3).getRelationshipToPerson());
    }

    @Test
    public void shouldFindClientRelationNotPointingBackToClient(){
        ClientRelationship oneWayRelationShip = new ClientRelationshipEntityBuilder().setPrimaryClientId(clientId).setSecondaryClientId(relatedId1).setClientRelationshipType(new Short("287")).build();
        primaryRelationships = new ClientRelationship[] {oneWayRelationShip};
        secondaryRelationships = new ClientRelationship[] {};

        Relationship relationship = genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        ArrayList<RelationshipTo> orderedRelationships = convertToOrderedRelationships(relationship);
        assertEquals(1, orderedRelationships.size());
        assertTrue(orderedRelationships.get(0).getRelatedFirstName().equals(relation1.getCommonFirstName()));
    }

    @Test
    public void shouldFindRelationsNotIdentifiedBYClient(){
        ClientRelationship secondaryRelationship1 = new ClientRelationshipEntityBuilder().setPrimaryClientId(relatedId1).setSecondaryClientId(clientId).setClientRelationshipType(new Short("211")).build();
        primaryRelationships = new ClientRelationship[] {};
        secondaryRelationships = new ClientRelationship[] {secondaryRelationship1};

        Relationship relationship = genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        ArrayList<RelationshipTo> orderedRelationships = convertToOrderedRelationships(relationship);
        assertEquals(1, orderedRelationships.size());
        assertTrue(orderedRelationships.get(0).getRelatedFirstName().equals(relation1.getCommonFirstName()));
    }

    private ArrayList<RelationshipTo> convertToOrderedRelationships(Relationship relationship) {
        ArrayList<RelationshipTo> orderedRelationships = new ArrayList();
        for(RelationshipTo relation : relationship.getRelatedTo()){
            orderedRelationships.add(relation);
        }
        return sortByNameAndClient(orderedRelationships);
    }

    private ArrayList sortByNameAndClient(ArrayList<RelationshipTo> orderedRelationships ){
        Comparator relationshipComparator = Comparator.comparing((RelationshipTo p)->p.getRelatedFirstName())
                .thenComparing(p->p.getRelationshipToPerson());
        Collections.sort(orderedRelationships, relationshipComparator);
        return orderedRelationships;
    }
}