package gov.ca.cwds.rest.api.domain;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.fixture.investigation.AllegationListEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.AllegationList;

@SuppressWarnings("javadoc")
public class AllegationListTest {
  private Validator validator;

  private ObjectMapper MAPPER = new ObjectMapper();

  private Allegation allegation1;
  private Allegation allegation2;
  private Set<Allegation> allegations = new HashSet<>();

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    allegations.add(allegation1);
    allegations.add(allegation2);
  }

  @Test
  @Ignore
  public void testSerilizedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    AllegationList allegationList = new AllegationListEntityBuilder().build();
    final String expected =
        MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(allegationList);
    System.out.println(expected);
  }
}
