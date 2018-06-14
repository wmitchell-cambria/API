package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.util.Doofenshmirtz;

public class RelationshipWrapperTest extends Doofenshmirtz<ClientAddress> {

  RelationshipWrapper target;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new RelationshipWrapper();
  }

  @Test
  public void shouldDefaultValuesWhenNoParamsGiven() {
    String relationId = "";
    String primaryLegacyId = "";
    String secondaryLegacyId = "";
    String primaryFirstName = "";
    String primaryLastName = "";
    String secondaryFirstName = "";
    String secondaryLastName = "";
    String primaryRelationshipCode = "";
    String secondaryRelationshipCode = "";
    RelationshipWrapper relationshipWrapper = new RelationshipWrapper(relationId, primaryLegacyId,
        secondaryLegacyId, primaryFirstName, primaryLastName, secondaryFirstName, secondaryLastName,
        primaryRelationshipCode, secondaryRelationshipCode);
    assertEquals(relationId, relationshipWrapper.getRelationId());
    assertEquals(primaryLegacyId, relationshipWrapper.getPrimaryLegacyId());
    assertEquals(secondaryLegacyId, relationshipWrapper.getSecondaryLegacyId());
    assertEquals(primaryFirstName, relationshipWrapper.getPrimaryFirstName());
    assertEquals(primaryLastName, relationshipWrapper.getPrimaryLastName());
    assertEquals(secondaryFirstName, relationshipWrapper.getSecondaryFirstName());
    assertEquals(secondaryLastName, relationshipWrapper.getSecondaryLastName());
    assertEquals(primaryRelationshipCode, relationshipWrapper.getPrimaryRelationshipCode());
    assertEquals(secondaryRelationshipCode, relationshipWrapper.getSecondaryRelationshipCode());
  }

  @Test
  public void shouldConstructClassWhenParametersAreGiven() {
    String relationId = "relationId";
    String primaryLegacyId = "primaryLegacyId";
    String secondaryLegacyId = "secondaryLegacyId";
    String primaryFirstName = "primaryFirstName";
    String primaryLastName = "primaryLastName";
    String secondaryFirstName = "secondaryFirstName";
    String secondaryLastName = "secondaryLastName";
    String primaryRelationshipCode = "primaryRelationshipCode";
    String secondaryRelationshipCode = "secondaryRelationshipCode";
    RelationshipWrapper relationshipWrapper = new RelationshipWrapper(relationId, primaryLegacyId,
        secondaryLegacyId, primaryFirstName, primaryLastName, secondaryFirstName, secondaryLastName,
        primaryRelationshipCode, secondaryRelationshipCode);
    assertEquals(relationId, relationshipWrapper.getRelationId());
    assertEquals(primaryLegacyId, relationshipWrapper.getPrimaryLegacyId());
    assertEquals(secondaryLegacyId, relationshipWrapper.getSecondaryLegacyId());
    assertEquals(primaryFirstName, relationshipWrapper.getPrimaryFirstName());
    assertEquals(primaryLastName, relationshipWrapper.getPrimaryLastName());
    assertEquals(secondaryFirstName, relationshipWrapper.getSecondaryFirstName());
    assertEquals(secondaryLastName, relationshipWrapper.getSecondaryLastName());
    assertEquals(primaryRelationshipCode, relationshipWrapper.getPrimaryRelationshipCode());
    assertEquals(secondaryRelationshipCode, relationshipWrapper.getSecondaryRelationshipCode());
  }

  @Test
  public void type() throws Exception {
    assertThat(RelationshipWrapper.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getRelationId_A$() throws Exception {
    String actual = target.getRelationId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelationId_A$String() throws Exception {
    String relationId = null;
    target.setRelationId(relationId);
  }

  @Test
  public void getPrimaryLegacyId_A$() throws Exception {
    String actual = target.getPrimaryLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrimaryLegacyId_A$String() throws Exception {
    String primaryLegacyId = null;
    target.setPrimaryLegacyId(primaryLegacyId);
  }

  @Test
  public void getSecondaryLegacyId_A$() throws Exception {
    String actual = target.getSecondaryLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSecondaryLegacyId_A$String() throws Exception {
    String secondaryLegacyId = null;
    target.setSecondaryLegacyId(secondaryLegacyId);
  }

  @Test
  public void getPrimaryFirstName_A$() throws Exception {
    String actual = target.getPrimaryFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrimaryFirstName_A$String() throws Exception {
    String primaryFirstName = null;
    target.setPrimaryFirstName(primaryFirstName);
  }

  @Test
  public void getPrimaryLastName_A$() throws Exception {
    String actual = target.getPrimaryLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrimaryLastName_A$String() throws Exception {
    String primaryLastName = null;
    target.setPrimaryLastName(primaryLastName);
  }

  @Test
  public void getSecondaryFirstName_A$() throws Exception {
    String actual = target.getSecondaryFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSecondaryFirstName_A$String() throws Exception {
    String secondaryFirstName = null;
    target.setSecondaryFirstName(secondaryFirstName);
  }

  @Test
  public void getSecondaryLastName_A$() throws Exception {
    String actual = target.getSecondaryLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSecondaryLastName_A$String() throws Exception {
    String secondaryLastName = null;
    target.setSecondaryLastName(secondaryLastName);
  }

  @Test
  public void getPrimaryRelationshipCode_A$() throws Exception {
    String actual = target.getPrimaryRelationshipCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrimaryRelationshipCode_A$String() throws Exception {
    String primaryRelationshipCode = null;
    target.setPrimaryRelationshipCode(primaryRelationshipCode);
  }

  @Test
  public void getSecondaryRelationshipCode_A$() throws Exception {
    String actual = target.getSecondaryRelationshipCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSecondaryRelationshipCode_A$String() throws Exception {
    String secondaryRelationshipCode = null;
    target.setSecondaryRelationshipCode(secondaryRelationshipCode);
  }

  @Test
  public void getPrimaryNameSuffix_A$() throws Exception {
    String actual = target.getPrimaryNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrimaryNameSuffix_A$String() throws Exception {
    String primaryNameSuffix = null;
    target.setPrimaryNameSuffix(primaryNameSuffix);
  }

  @Test
  public void getSecondaryNameSuffix_A$() throws Exception {
    String actual = target.getSecondaryNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSecondaryNameSuffix_A$String() throws Exception {
    String secondaryNameSuffix = null;
    target.setSecondaryNameSuffix(secondaryNameSuffix);
  }

}
