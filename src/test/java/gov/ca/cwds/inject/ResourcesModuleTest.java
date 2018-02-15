package gov.ca.cwds.inject;

import com.google.inject.Injector;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedServiceBackedResourceDelegate;
import gov.ca.cwds.rest.services.RelationshipsService;
import gov.ca.cwds.rest.services.TypedCrudsService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResourcesModuleTest {
    ResourcesModule resourceModule;
    Injector injector;

    @Before
    public void setup(){
        resourceModule = new ResourcesModule();
        injector =  mock(Injector.class);
    }

    @Test
    public void RelationshipBackedResourceShouldRetrieveServiceFromInjector(){
        resourceModule.relationshipBackedResource(injector);
        verify(injector).getInstance(RelationshipsService.class);
    }
}