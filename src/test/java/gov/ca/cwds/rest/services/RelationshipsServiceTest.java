package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang3.NotImplementedException;

import static org.junit.Assert.*;

public class RelationshipsServiceTest {

    RelationshipsService service;

    @Before
    public void setup(){
        service = new RelationshipsService();
    }

    @Test(expected = NotImplementedException.class)
    public void shouldNotImplementFind(){
        service.find("something");
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