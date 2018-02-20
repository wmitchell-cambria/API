package gov.ca.cwds.rest.services;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang3.NotImplementedException;

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

    @Before
    public void setup(){
        clientId = "1XVZ43D";
        primaryRelationships = new ClientRelationship[2];
        secondaryRelationships = new ClientRelationship[2];


        relationshipDao = mock(ClientRelationshipDao.class);
        when(relationshipDao.findByPrimaryClientId(clientId)).thenReturn(primaryRelationships);
        when(relationshipDao.findBySecondaryClientId(clientId)).thenReturn(secondaryRelationships);

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

    @Test(expected = NotImplementedException.class)
    public void shouldNotImplementDelete(){
        service.delete("something");
    }

    @Test(expected = NotImplementedException.class)
    public void shouldNotImplementreate(){
        Relationship relationship = new Relationship();
        service.create(relationship);
    }

    @Test(expected = NotImplementedException.class)
    public void shouldNotImplementUpdate(){
        Relationship relationship = new Relationship();
        service.update("something", relationship);
    }
}
