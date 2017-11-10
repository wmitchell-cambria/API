package gov.ca.cwds.rest.api.domain.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class CmsRecordDescriptorTest {

  private ObjectMapper MAPPER = new ObjectMapper();

  private static final String ID = "1234567ABC";
  private static final String UI_ID = "111122223333444455";
  private static final String TABLE_NAME = "REFERL_T";
  private static final String DESCRIPTION = "REFERRAL";

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * 
   */
  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    CmsRecordDescriptor cmsLegacyDescriptor = new CmsRecordDescriptor();
    assertNotNull(cmsLegacyDescriptor);
  }

  /**
   * 
   */
  @Test
  public void shouldCreateObjectWithSpecifiedValues() {
    CmsRecordDescriptor cmsLegacyDescriptor =
        new CmsRecordDescriptor(ID, UI_ID, TABLE_NAME, DESCRIPTION);
    assertEquals(cmsLegacyDescriptor.getId(), ID);
    assertEquals(cmsLegacyDescriptor.getUiId(), UI_ID);
    assertEquals(cmsLegacyDescriptor.getTableName(), TABLE_NAME);
    assertEquals(cmsLegacyDescriptor.getTableDescription(), DESCRIPTION);
  }

  /**
   * 
   */
  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    CmsRecordDescriptor cmsLegacyDescriptor =
        new CmsRecordDescriptor(ID, UI_ID, TABLE_NAME, DESCRIPTION);
    CmsRecordDescriptor otherCmsRecordDescriptor =
        new CmsRecordDescriptor(ID, UI_ID, TABLE_NAME, DESCRIPTION);

    assertEquals(cmsLegacyDescriptor, otherCmsRecordDescriptor);
  }

  /**
   * 
   */
  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    CmsRecordDescriptor cmsLegacyDescriptor =
        new CmsRecordDescriptor(ID, UI_ID, TABLE_NAME, DESCRIPTION);
    CmsRecordDescriptor otherCmsRecordDescriptor =
        new CmsRecordDescriptor(ID, UI_ID, TABLE_NAME, DESCRIPTION);

    Set<Object> items = new HashSet<>();
    items.add(cmsLegacyDescriptor);
    items.add(otherCmsRecordDescriptor);

    assertTrue(items.contains(cmsLegacyDescriptor));
    assertTrue(items.contains(otherCmsRecordDescriptor));
    assertEquals(1, items.size());
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CmsRecordDescriptor.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void testWithIdTooLongFails() {
    CmsRecordDescriptor cmsLegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("12345678901").build();
    Set<ConstraintViolation<CmsRecordDescriptor>> constraintViolations =
        validator.validate(cmsLegacyDescriptor);
    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 0 and 10",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testBlankTableNameFails() {
    CmsRecordDescriptor cmsLegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setTableName("").build();
    Set<ConstraintViolation<CmsRecordDescriptor>> constraintViolations =
        validator.validate(cmsLegacyDescriptor);
    assertEquals(1, constraintViolations.size());
    assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());
    // for (ConstraintViolation<?> violation : constraintViolations) {
    // System.out.println(violation.getMessage());
    // }

  }

  // @Test
  // @Ignore
  // public void testSerializedOutput()
  // throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
  // CmsRecordDescriptor cmsRecordDescriptor = new CmsRecordDescriptorEntityBuilder().build();
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(cmsRecordDescriptor);
  // System.out.println(expected);
  // }

  @Test
  public void deserializesFromJSON() throws Exception {
    CmsRecordDescriptor cmsRecordDescriptor = new CmsRecordDescriptorEntityBuilder().build();
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/investigation/cmsRecordDescriptor/valid.json"),
            CmsRecordDescriptor.class),
        is(equalTo(cmsRecordDescriptor)));
  }

}
