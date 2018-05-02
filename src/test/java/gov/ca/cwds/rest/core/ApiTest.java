package gov.ca.cwds.rest.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApiTest {
    @Test
    public void shouldContainResourceClientServiceEndPointName(){
        assertEquals(Api.RESOURCE_CLIENT, "clients");
    }
}