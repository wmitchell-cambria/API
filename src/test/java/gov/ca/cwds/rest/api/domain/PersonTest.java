package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.PersonResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PersonTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    
    private static final PersonResource mockedPersonResource =
    	      mock(PersonResource.class);
    
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
        .addResource(mockedPersonResource).build();


    /*
     * Serialization and deserialization
     */
    @Test
    public void serializesToJSON() throws Exception {
    	Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    	String expected = MAPPER.writeValueAsString( new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address) );

        String serialized = MAPPER.writeValueAsString(
        		MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class));

        assertThat(serialized, is(expected));
    }
    
    @Test
    public void deserializesFromJSON() throws Exception {
    	Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
        Person expected = new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address); 
        Person serialized = MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);
        assertThat(serialized, is(expected));
    }
    
    @Test
    public void equalsHashCodeWork() {
        EqualsVerifier.forClass(Person.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
    
    /*
     * validation tests
     */
}