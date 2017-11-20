package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;

/**
 * @author CWDS API Team
 * 
 */
public class R06238SsnShouldBeNumericTest {

  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private Set<String> roles = new HashSet<>();
  private Set<Address> addresses = new HashSet<>();
  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

  private Validator validator;

  List<Short> racecodes = new ArrayList<>();
  List<Short> hispaniccodes = new ArrayList<>();

  /**
   * 
   */
  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    roles.add("Victim");
    racecodes.add((short) 841);
    hispaniccodes.add((short) 3164);
    Address address =
        new Address("", "", "123 First St", "San Jose", 1828, "94321", 32, legacyDescriptor);
    addresses.add(address);

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
