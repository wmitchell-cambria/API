package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.resources.ParticipantResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 * 
 */
public class R06238SsnShouldBeNumericTest {

  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private Set<String> roles = new HashSet<>();
  private Set<Address> addresses = new HashSet<>();
  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

  private static final ParticipantResource mockedParticipantResource =
      mock(ParticipantResource.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  List<Short> racecodes = new ArrayList<>();
  List<Short> hispaniccodes = new ArrayList<>();

  /**
   * 
   */
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedParticipantResource).build();

  /**
   * 
   */
  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    Participant validParticipant = new ParticipantResourceBuilder().createParticipant();
    roles.add("Victim");
    racecodes.add((short) 841);
    hispaniccodes.add((short) 3164);
    Address address =
        new Address("", "", "123 First St", "San Jose", 1828, "94321", 32, legacyDescriptor);
    addresses.add(address);
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    when(mockedParticipantResource.create(eq(validParticipant)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testParticipantSsnTooLongFail() throws Exception {
    Participant toValidate =
        new ParticipantResourceBuilder().setSsn("1234567890").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must match \"^(|[0-9]{9})$\"",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testParticipantSsnNonNumericFail() throws Exception {
    Participant toValidate =
        new ParticipantResourceBuilder().setSsn("1234567kk").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must match \"^(|[0-9]{9})$\"",
        constraintViolations.iterator().next().getMessage());
  }

}
