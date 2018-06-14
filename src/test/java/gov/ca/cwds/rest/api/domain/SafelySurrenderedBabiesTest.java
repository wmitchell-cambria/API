package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class SafelySurrenderedBabiesTest {

  SafelySurrenderedBabies target;

  @Before
  public void setup() throws Exception {
    target = new SafelySurrenderedBabies();
  }

  @Test
  public void type() throws Exception {
    assertThat(SafelySurrenderedBabies.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getSurrenderedByName_A$() throws Exception {
    String actual = target.getSurrenderedByName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSurrenderedByName_A$String() throws Exception {
    String surrenderedByName = null;
    target.setSurrenderedByName(surrenderedByName);
  }

  @Test
  public void getRelationToChild_A$() throws Exception {
    Integer actual = target.getRelationToChild();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelationToChild_A$Integer() throws Exception {
    Integer relationToChild = null;
    target.setRelationToChild(relationToChild);
  }

  @Test
  public void getBraceletId_A$() throws Exception {
    String actual = target.getBraceletId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setBraceletId_A$String() throws Exception {
    String braceletId = null;
    target.setBraceletId(braceletId);
  }

  @Test
  public void getBraceletInfoCode_A$() throws Exception {
    String actual = target.getBraceletInfoCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setBraceletInfoCode_A$String() throws Exception {
    String braceletInfoCode = null;
    target.setBraceletInfoCode(braceletInfoCode);
  }

  @Test
  public void getMedicalQuestionaireCode_A$() throws Exception {
    String actual = target.getMedicalQuestionaireCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMedicalQuestionaireCode_A$String() throws Exception {
    String medicalQuestionaireCode = null;
    target.setMedicalQuestionaireCode(medicalQuestionaireCode);
  }

  @Test
  public void getMedicalQuestionaireReturnDate_A$() throws Exception {
    LocalDate actual = target.getMedicalQuestionaireReturnDate();
    LocalDate expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMedicalQuestionaireReturnDate_A$LocalDate() throws Exception {
    LocalDate medicalQuestionaireReturnDate = LocalDate.now();
    target.setMedicalQuestionaireReturnDate(medicalQuestionaireReturnDate);
  }

  @Test
  public void getComments_A$() throws Exception {
    String actual = target.getComments();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setComments_A$String() throws Exception {
    String comments = null;
    target.setComments(comments);
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    assertThat(actual, is(not(equalTo(0))));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
