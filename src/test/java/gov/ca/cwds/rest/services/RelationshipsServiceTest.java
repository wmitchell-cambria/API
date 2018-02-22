package gov.ca.cwds.rest.services;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RelationshipsServiceTest {
    private RelationshipsService service;
    private ClientRelationshipDao relationshipDao;
    private Genealogist genealogist;
    private ClientRelationship[] primaryRelationships;
    private ClientRelationship[] secondaryRelationships;
    private String clientId;
    private String clientId2 = "1XVZ43D";

    private List<String> clientIds = Arrays.asList(clientId, clientId2);
    private ClientRelationship [] client2PrimaryRelationships = new ClientRelationship[2];
    private ClientRelationship [] client2SecondaryRelationships = new ClientRelationship[2];

    @Before
    public void setup(){
        clientId = "1XVZ43D";
        primaryRelationships = new ClientRelationship[2];
        secondaryRelationships = new ClientRelationship[2];

        clientId2 = "2ZY52Q";
        client2PrimaryRelationships = new ClientRelationship[2];
        client2SecondaryRelationships = new ClientRelationship[2];

        clientIds = Arrays.asList(clientId, clientId2);

        relationshipDao = mock(ClientRelationshipDao.class);
        when(relationshipDao.findByPrimaryClientId(clientId)).thenReturn(primaryRelationships);
        when(relationshipDao.findBySecondaryClientId(clientId)).thenReturn(secondaryRelationships);
        when(relationshipDao.findByPrimaryClientId(clientId2)).thenReturn(client2PrimaryRelationships);
        when(relationshipDao.findBySecondaryClientId(clientId2)).thenReturn(client2SecondaryRelationships);

        genealogist = mock(Genealogist.class);
        service = new RelationshipsService(relationshipDao, genealogist);
    }

    @Test
    public void shouldSearchForClientsRelatedToClient(){
        service.find(clientId);
        verify(relationshipDao).findByPrimaryClientId(clientId);
        verify(relationshipDao).findBySecondaryClientId(clientId);
    }

    @Test
    public void shouldBuildRelationshipForClient(){
        service.find(clientId);
        verify(genealogist).buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
    }

    @Test
    public void findShouldReturnTheClientAndHisRelations(){
        Relationship relationship = new Relationship();
        when(genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships)).thenReturn(relationship);

        Relationship foundRelationship = (Relationship)service.find(clientId);
        assertSame(relationship, foundRelationship);
    }

    @Test
    public void shouldSearchRelationshipsForMultipleClients(){
        service.findForIds(clientIds);
        verify(relationshipDao).findByPrimaryClientId(clientId);
        verify(relationshipDao).findBySecondaryClientId(clientId);
        verify(relationshipDao).findByPrimaryClientId(clientId2);
        verify(relationshipDao).findBySecondaryClientId(clientId2);

    }

    @Test
    public void shouldBuildRelationshipForMultipleClients(){
        service.findForIds(clientIds);
        verify(genealogist).buildRelationForClient(clientId, primaryRelationships, secondaryRelationships);
        verify(genealogist).buildRelationForClient(clientId2, client2PrimaryRelationships, client2SecondaryRelationships);
    }
    @Test
    public void findShouldReturnTheMultipleClientsRelations(){
        Relationship client1Relationship = new RelationshipEntityBuilder().setFirstName("Fred").build();
        Relationship client2Relationship = new RelationshipEntityBuilder().setFirstName("Bill").build();
        when(genealogist.buildRelationForClient(clientId, primaryRelationships, secondaryRelationships)).thenReturn(client1Relationship);
        when(genealogist.buildRelationForClient(clientId2, client2PrimaryRelationships, client2SecondaryRelationships)).thenReturn(client2Relationship);

        RelationshipList foundRelationship = (RelationshipList)service.findForIds(clientIds);
        List relationships = new ArrayList();
        relationships.addAll(foundRelationship.getRelationship());
        assertTrue(relationships.contains(client1Relationship));
        assertTrue(relationships.contains(client2Relationship));
    }

    @Test(expected = NotImplementedException.class)
    public void shouldNotImplementDelete(){
        service.delete("something");
    }

    @Test(expected = NotImplementedException.class)
    public void shouldNotImplementreate(){
        service.create(new Relationship());
    }

    @Test(expected = NotImplementedException.class)
    public void shouldNotImplementUpdate(){
        service.update("something", new Relationship());
    }
}
