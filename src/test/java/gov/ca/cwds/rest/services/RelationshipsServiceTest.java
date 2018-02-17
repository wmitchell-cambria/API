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
    RelationshipsService service;
    ClientRelationshipDao relationshipDao;
    Genealogist genealogist;

    @Before
    public void setup(){
        relationshipDao = mock(ClientRelationshipDao.class);
        genealogist = mock(Genealogist.class);
        service = new RelationshipsService(relationshipDao, genealogist);
    }

    @Test
    public void shouldFindRelationShips(){
        String clientId = "1XVZ43D";
        ClientRelationship[] relationships = new ClientRelationship[2];
        when(relationshipDao.findByPrimaryClientId(clientId)).thenReturn(relationships);

        service.find(clientId);
        verify(relationshipDao).findByPrimaryClientId(clientId);
        verify(genealogist).buildRelationForClient(clientId, relationships);
    }

    @Test
    public void findShouldReturnTheFoundRelations(){
        Relationship relationship = new Relationship();

        String clientId = "1XVZ43D";
        ClientRelationship[] relationships = new ClientRelationship[2];
        when(relationshipDao.findByPrimaryClientId(clientId)).thenReturn(relationships);
        when(genealogist.buildRelationForClient(clientId, relationships)).thenReturn(relationship);

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
