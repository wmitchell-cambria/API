package gov.ca.cwds.rest.api.persistence.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import io.dropwizard.jackson.Jackson;

import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StaffPersonTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/StaffPerson.json"), StaffPerson.class));

        assertThat(MAPPER.writeValueAsString(staffPerson())).isEqualTo(expected);
    }
    
    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/StaffPerson.json"), StaffPerson.class))
                .isEqualTo(staffPerson());
    }
    
    private StaffPerson staffPerson() {
    	return new StaffPerson("ABC", 
				"2016-08-07",
				"John",
				"CEO",
				"Doe",
				"C",
				"Mr",
				new BigDecimal(9165551212L),
				22,
				"2001-01-02",
				"Suffix",
				"Y",
				"q38",
				"2016-08-07T16:41:49.214",
				"MIZN02k11B",
				"abc",
				"def",
				"99",
				"N",
				"3XPCP92b24",
				"john.doe@anyco.com"
				);
    }
}